import markerImg from "../../../../assets/marker.png";
import character from "../../../../assets/character.png";
import axios from "axios";
import { MarkerOverlapRecognizer } from "./overlapMarker";
/**
 *  맵 기능 안되면 제작자 김동우한테 문의 바랍니다.
 */

/**
 * 네이버용
 * 초기 맵 설정
 */
export const initMap = async (
  mapRef: React.MutableRefObject<naver.maps.Map | null>,
  markersRef: React.MutableRefObject<any>,
  ...args: any
) => {
  const position = args[0];
  const userMap = args[1];
  const setMyGeolo = args[2];
  let { latitude, longitude } = position.coords;

  mapRef.current = new naver.maps.Map("map", {
    center: new naver.maps.LatLng(latitude, longitude),
    zoom: 17,
  });

  if (userMap) userMap(mapRef.current);

  if (setMyGeolo)
    setMyGeolo({
      latitude,
      longitude,
    });

  const marker = setMarker(
    new naver.maps.LatLng(latitude, longitude),
    mapRef.current,
    {}
  );
  

  markersRef.current = await getUsers(mapRef.current);
  new window.naver.maps.Marker(marker);
};

/**
 * 네이버용
 * @param position: 위도, 경도 값
 * @param map: any(초기 지도값)
 * @param icon: 백엔드 api로 유저 정보에서 프로필 사진 값
 * @returns 마커 객체를 반환
 * 나중에 api 받으면 추가해야함
 */
export const setMarker = (
  position: any,
  map: any,
  icon: { profile?: string }
) => {
  return {
    position,
    map,
    icon: {
      content: `<div style="position:relative">
                  <div style="position: absolute; left:12px; top:-17px;">⭐</div>
                  <img src=${markerImg} width=${50} height=${50}/>
                  <img src=${character} width=${35} height=${35} style="border-radius: 50%; 
                                                                        position: absolute; 
                                                                        left: 7px; 
                                                                        top: 4px
                                                                        "/>
                </div>`,
      size: new window.naver.maps.Size(25, 34),
      scaleSize: new window.naver.maps.Size(25, 34),
      anchor: new window.naver.maps.Point(19, 38),
    },
  };
};

/**
 * 네이버용
 * @param marker: 마커
 */
export const hideMarker = (marker: naver.maps.Marker) => {
  if (marker) return marker.setMap(null);
};


/**
 * 네이버용
 * @param consultingMap: 지도
 */
export const getUsers = async (consultingMap: naver.maps.Map | null) => {
  const markers: Array<naver.maps.Marker> = [];
  try {
    const { data } = await axios.get("http://localhost:3001/users");

    const recognizer = new MarkerOverlapRecognizer({
      highlightRect: false,
      tolerance: 5,
    });

    recognizer.setMap(consultingMap);

    
    const setManyMarkers = () => {
      for (let i = 0; i < data.length; i++) {
        const marker = new naver.maps.Marker({
          position: new naver.maps.LatLng(data[i].y, data[i].x),
          map: consultingMap!,
          icon: {
            content: `<div style="position:relative">
                  <img src=${markerImg} width=${50} height=${50}/>
                  <img src=${character} width=${35} height=${35} style="border-radius: 50%; 
                                                                        position: absolute; 
                                                                        left: 7px; 
                                                                        top: 4px
                                                                        "/>
                </div>`,
            size: new window.naver.maps.Size(25, 34),
            anchor: new window.naver.maps.Point(19, 38),
          },
          zIndex: 100,
          name: data[i].title,
        });
        markers.push(marker);

        //마커 마우스 올릴때 index 값 증가
        marker.addListener("mouseover", function (e) {
          marker.setZIndex(1000);
        });
        //마커 마우스 내릴때 index 값 감소
        marker.addListener("mouseout", function (e) {
          marker.setZIndex(100);
        });
        recognizer.add(marker);
        setInfowindow(data, i, marker, consultingMap);
      }
    };
    setManyMarkers();
    return markers;
  } catch (err) {
    alert(err);
  }
};

const updateShowMarkers = (map: naver.maps.Map, marker: naver.maps.Marker) => {
  if (marker.getMap()) return;
  marker.setMap(map);
};

const updateHideMarkers = (marker: naver.maps.Marker) => {
  if (!marker.getMap()) return;
  marker.setMap(null);
};

/**
 * 네이버용
 * @param map: naver.maps.Map
 * @param markers: naver.maps.Map[]
 */
export const updateMarkers = (
  map: naver.maps.Map,
  markers: naver.maps.Marker[]
) => {
  const mapBounds = map.getBounds();
  let marker: naver.maps.Marker;
  let position: naver.maps.Coord;

  for (let i = 0; i < markers.length; i++) {
    marker = markers[i];
    position = marker.getPosition();

    if (mapBounds.hasPoint(position)) {
      updateShowMarkers(map, marker);
    } else {
      updateHideMarkers(marker);
    }
  }
};

/**
 * 네이버용
 * 마커 최적화 불러오기 기능 - 불러오는 지역 마커 보이기, 없는 지역은 마커 안보이기
 * @param mapRef: 지도 객체
 * @param markersRef: 마커 객체
 */
export const moveMapEventListener = (
  mapRef: React.MutableRefObject<naver.maps.Map | null>,
  markersRef: any
) => {
  const moveEvent = naver.maps.Event.addListener(mapRef.current, "idle", () => {
    if (mapRef.current && markersRef)
      updateMarkers(mapRef.current, markersRef.current);
  });
  return () => {
    naver.maps.Event.removeListener(moveEvent);
  };
};

/**
 * 네이버용
 * 마커 클릭시 인포창
 */
export const setInfowindow = (
  data: any,
  i: any,
  marker: naver.maps.Marker,
  consultingMap: naver.maps.Map | null
) => {
  const infoWindow = new naver.maps.InfoWindow({
    content: [
      '<div style="padding: 10px; box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 16px 0px;">',
      `   <div style="font-weight: bold; margin-bottom: 5px;">제목: ${data[i].title}</div>`,
      `   <div style="font-size: 13px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">내용: ${data[i].content}<div>`,
      "</div>",
    ].join(""),
    maxWidth: 300,
    anchorSize: {
      width: 12,
      height: 14,
    },
    borderColor: "#cecdc7",
  });

  naver.maps.Event.addListener(marker, "click", () => {
    if (infoWindow.getMap()) {
      // 정보창이 닫힐 때 이벤트 발생
      infoWindow.close();
    } else if (consultingMap !== null) {
      // 정보창이 열릴 때 이벤트 발생
      infoWindow.open(consultingMap, marker);
    }
  });
};
