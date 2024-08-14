export var MarkerOverlapRecognizer = function (opts) {
  this._options = Object.assign(
    {
      tolerance: 5,
      highlightRect: true,
      highlightRectStyle: {
        strokeColor: "#ff0000",
        strokeOpacity: 1,
        strokeWeight: 2,
        strokeStyle: "dot",
        fillColor: "#ff0000",
        fillOpacity: 0.5,
      },
      intersectNotice: true,
      intersectNoticeTemplate:
        '<div style="width:180px;border:solid 1px #333;background-color:#fff;padding:5px;"><span style="font-weight:bold;color: #20c997;">{{count}}</span>마커가 있습니다.<br/>(마커를 클릭해주세요)</div>',
      intersectList: true,
      intersectListTemplate:
        '<div style="width:200px;border:solid 1px #333;background-color:#fff;padding:5px;">' +
        '<ul style="list-style:none;margin:0;padding:0;">' +
        "{{#repeat}}" +
        '<li style="list-style:none;margin:0;padding:0;"><a href="#" style="color: inherit; text-decoration: none;"><span style="color:#20c997;">{{order}}</span>. {{name}}</a></li>' +
        "{{/#repeat}}" +
        "</ul>" +
        "</div>",
    },
    opts
  );

  this._listeners = [];
  this._markers = [];

  this._rectangle = new window.naver.maps.Rectangle(
    this._options.highlightRectStyle
  );
  this._overlapInfoEl = document.createElement("div");
  this._overlapInfoEl.style =
    "position:absolute;z-index:100;margin:0;padding:0;display:none;";

  this._overlapListEl = document.createElement("div");
  this._overlapListEl.style =
    "position:absolute;z-index:100;margin:0;padding:0;display:none;";
};

MarkerOverlapRecognizer.prototype = {
  constructor: MarkerOverlapRecognizer,

  setMap: function (map) {
    var oldMap = this.getMap();

    if (map === oldMap) return;

    this._unbindEvent();

    this.hide();

    if (map) {
      this._bindEvent(map);
      map.getPanes().floatPane.appendChild(this._overlapInfoEl);
      map.getPanes().floatPane.appendChild(this._overlapListEl);
    }

    this.map = map || null;
  },

  getMap: function () {
    return this.map;
  },

  _bindEvent: function (map) {
    var listeners = this._listeners,
      self = this;

    listeners.push(
      map.addListener("idle", this._onIdle.bind(this)),
      map.addListener("click", this._onIdle.bind(this))
    );

    this.forEach(function (marker) {
      listeners = listeners.concat(self._bindMarkerEvent(marker));
    });
  },

  _unbindEvent: function (map_) {
    var map = map_ || this.getMap();

    new window.naver.maps.Event.removeListener(this._listeners);
    this._listeners = [];

    this._rectangle.setMap(null);
    this._overlapInfoEl.remove();
    this._overlapListEl.remove();
  },

  add: function (marker) {
    this._listeners = this._listeners.concat(this._bindMarkerEvent(marker));
    this._markers.push(marker);
  },

  remove: function (marker) {
    this.forEach(function (m, i, markers) {
      if (m === marker) {
        markers.splice(i, 1);
      }
    });
    this._unbindMarkerEvent(marker);
  },

  forEach: function (callback) {
    var markers = this._markers;

    for (var i = markers.length - 1; i >= 0; i--) {
      callback(markers[i], i, markers);
    }
  },

  hide: function () {
    this._overlapListEl.style.display = "none";
    this._overlapInfoEl.style.display = "none";
    this._rectangle.setMap(null);
  },

  _bindMarkerEvent: function (marker) {
    marker.__intersectListeners = [
      marker.addListener("mouseover", this._onOver.bind(this)),
      marker.addListener("mouseout", this._onOut.bind(this)),
      marker.addListener("mousedown", this._onDown.bind(this)),
    ];

    return marker.__intersectListeners;
  },

  _unbindMarkerEvent: function (marker) {
    new window.naver.maps.Event.removeListener(marker.__intersectListeners);
    delete marker.__intersectListeners;
  },

  getOverlapRect: function (marker) {
    var map = this.getMap(),
      proj = map.getProjection(),
      position = marker.getPosition(),
      offset = proj.fromCoordToOffset(position),
      tolerance = this._options.tolerance || 3,
      rectLeftTop = offset.clone().sub(tolerance, tolerance),
      rectRightBottom = offset.clone().add(tolerance, tolerance);

    return new window.naver.maps.PointBounds.bounds(
      rectLeftTop,
      rectRightBottom
    );
  },

  getOverlapedMarkers: function (marker) {
    var baseRect = this.getOverlapRect(marker),
      overlaped = [
        {
          marker: marker,
          rect: baseRect,
        },
      ],
      self = this;

    this.forEach(function (m) {
      if (m === marker) return;

      var rect = self.getOverlapRect(m);

      if (rect.intersects(baseRect)) {
        overlaped.push({
          marker: m,
          rect: rect,
        });
      }
    });

    return overlaped;
  },

  _onIdle: function () {
    this._overlapInfoEl.style.display = "none";
    this._overlapListEl.style.display = "none";
  },

  _onOver: function (e) {
    var marker = e.overlay,
      map = this.getMap(),
      proj = map.getProjection(),
      offset = proj.fromCoordToOffset(marker.getPosition()),
      overlaped = this.getOverlapedMarkers(marker);

    if (overlaped.length > 1) {
      if (this._options.highlightRect) {
        var bounds;

        for (var i = 0, ii = overlaped.length; i < ii; i++) {
          if (bounds) {
            bounds = bounds.union(overlaped[i].rect);
          } else {
            bounds = overlaped[i].rect.clone();
          }
        }

        var min = proj.fromOffsetToCoord(bounds.getMin()),
          max = proj.fromOffsetToCoord(bounds.getMax());

        this._rectangle.setBounds(
          new window.naver.maps.LatLngBounds.bounds(min, max)
        );
        this._rectangle.setMap(map);
      }

      if (this._options.intersectNotice) {
        this._overlapInfoEl.innerHTML =
          this._options.intersectNoticeTemplate.replace(
            /\{\{count\}\}/g,
            overlaped.length
          );
        this._overlapInfoEl.style.left = offset.x + "px";
        this._overlapInfoEl.style.top = offset.y + "px";
        this._overlapInfoEl.style.display = "block";
      }
    } else {
      this.hide();
    }
  },

  _onOut: function () {
    this._rectangle.setMap(null);
    this._overlapInfoEl.style.display = "none";
  },

  _guid: function () {
    return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx"
      .replace(/[xy]/g, function (c) {
        var r = (Math.random() * 16) | 0,
          v = c == "x" ? r : (r & 0x3) | 0x8;
        return v.toString(16);
      })
      .toUpperCase();
  },

  _renderIntersectList: function (overlaped, offset) {
    if (!this._options.intersectList) return;

    var listLayer = this._overlapListEl;

    var REPEAT_REGEX = /\{\{#repeat\}\}([\s\S]*)\{\{\/#repeat\}\}/gm,
      template = this._options.intersectListTemplate,
      itemTemplate = null,
      itemPlace = null,
      matches = REPEAT_REGEX.exec(template),
      uid = this._guid(),
      self = this;

    listLayer.innerHTML = "";

    if (matches && matches.length >= 2) {
      template = template.replace(
        matches[0],
        '<div id="intersects-' + uid + '"></div>'
      );
      itemTemplate = matches[1];

      listLayer.innerHTML = template;

      var placeholder = document.getElementById("intersects-" + uid);

      itemPlace = placeholder.parentNode;

      placeholder.remove();
      placeholder = null;
    } else {
      itemTemplate = template;
      itemPlace = listLayer;
    }

    var items = [];
    for (var i = 0, ii = overlaped.length; i < ii; i++) {
      /* eslint-disable no-loop-func */
      /* eslint-disable no-undef */
      var c = overlaped[i],
        item = itemTemplate.replace(/\{\{(\w+)\}\}/g, function (match, symbol) {
          if (symbol === "order") {
            return i + 1;
          } else if (symbol in marker) {
            return c.marker[symbol];
          } else if (c.marker.get(symbol)) {
            return c.marker.get(symbol);
          } else {
            return match;
          }
        });
      /* eslint-enable no-undef */
      /* eslint-enable no-loop-func */
      var itemElement = document.createElement("div");
      itemElement.innerHTML = item;
      itemElement.addEventListener(
        "click",
        self._onClickItem.bind(self, c.marker)
      );
      items.push(itemElement);
    }

    for (var j = 0, jj = items.length; j < jj; j++) {
      itemPlace.appendChild(items[j]);
    }

    listLayer.style.left = offset.x + 5 + "px";
    listLayer.style.top = offset.y + "px";
    listLayer.style.display = "block";
  },

  _onDown: function (e) {
    var marker = e.overlay,
      map = this.getMap(),
      proj = map.getProjection(),
      offset = proj.fromCoordToOffset(marker.getPosition()),
      overlaped = this.getOverlapedMarkers(marker),
      self = this;

    new window.naver.maps.Event.resumeDispatch(marker, "click");

    if (overlaped.length <= 1) {
      return;
    }

    new window.naver.maps.Event.stopDispatch(marker, "click");
    this._renderIntersectList(overlaped, offset);
    this._overlapInfoEl.style.display = "none";

    new window.naver.maps.Event.trigger(this, "overlap", overlaped);
  },

  _onClickItem: function (marker, e) {
    new window.naver.maps.Event.resumeDispatch(marker, "click");

    new window.naver.maps.Event.trigger(this, "clickItem", {
      overlay: marker,
      originalEvent: e,
    });

    marker.trigger("click");
  },
};
