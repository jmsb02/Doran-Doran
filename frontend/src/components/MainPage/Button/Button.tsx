import { createMapSlice } from "../../../store/bound/slice/map";
import { createMarkerSlice } from "../../../store/bound/slice/marker";
import { hideMarker } from "../../commons/utilities/setter/map";

import style from "./button.module.css";
import TYPE from "./type";

interface IButton {
  type: number;
  geolo: {
    latitude: number;
    longitude: number;
  };
  setShowSideBar: React.Dispatch<
    React.SetStateAction<{
      geocoding: boolean;
      show: boolean;
    }>
  >;
}

export default function Button({ type, geolo, setShowSideBar }: IButton) {
  const map = createMapSlice((state) => state.consultingMap);
  const marker = createMarkerSlice((state) => state.consultingMarker);
  const setMarker = createMarkerSlice((state) => state.updateMarker);
  const updateCoordinateXY = createMarkerSlice((state) => state.updateCoordinateXY);
  const resetCoordinateXY = createMarkerSlice((state) => state.resetCoordinateXY);
  const changeTurnOnOff = createMarkerSlice((state) => state.changeTurnOnOff);

  

  const originLocation = (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    if (marker) hideMarker(marker);
    new window.naver.maps.Event.clearListeners(map, "click");
    resetCoordinateXY();
    changeTurnOnOff(false);

    setShowSideBar(() => {
      return {
        geocoding: false,
        show: false,
      };
    });

    if (e.currentTarget.id === "location") {
      const latLng = new window.naver.maps.LatLng(
        geolo.latitude,
        geolo.longitude
      );

      if (map) {
        const latLngBound = new window.naver.maps.LatLngBounds(latLng);
        map.fitBounds(latLngBound, { maxZoom: 17 });
      }
      return new window.naver.maps.Event.clearListeners(map, "click");
    }

    if (e.currentTarget.id === "marker") {
      changeTurnOnOff(true);
      new window.naver.maps.Event.addListener(
        map,
        "click",
        (e: {
          coord: any;
          domEvent: Object;
          latlng: { [key: string]: number };
          offset: { [key: string]: number };
          originalEvent: Object;
          point: { [key: string]: number };
          pointerEvent: Object;
          type: string;
        }) => {
          if (map) {
            const originMarker = new naver.maps.Marker({
              position: new window.naver.maps.LatLng(e.coord.y, e.coord.x),
              map: map,
            });
            updateCoordinateXY({ x: e.coord.x, y:e.coord.y});
            setMarker(originMarker);
            setShowSideBar((prev) => {
              return {
                ...prev,
                show: true,
              };
            });
          }
          changeTurnOnOff(false);
          return new window.naver.maps.Event.clearListeners(map, "click");
        }
      );
    }

    if (e.currentTarget.id === "way") {
      setShowSideBar(() => {
        return {
          geocoding: true,
          show: true,
        };
      });
      return new window.naver.maps.Event.clearListeners(map, "click");
    }
  };
  return (
    <button
      style={{
        position: "absolute",
        right: 16,
        top: TYPE[type].top,
        width: "42px",
        height: "42px",
        borderRadius: "12%",
        backgroundColor: "white",
        borderStyle: "none",
        boxShadow: "1px 1px 5px #5b5b5b",
        cursor: "pointer",
      }}
      id={TYPE[type].id}
      onClick={(e) => {
        originLocation(e);
      }}
    >
      {TYPE[type].icon}
      <p className={style.arrow_box}>{TYPE[type].title}</p>
    </button>
  );
}
