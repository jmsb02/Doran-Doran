import { hideMarker } from "../utilities/setter/map";
import style from "./button.module.css";

import TYPE from "./type";

interface IButton {
  type: number;
  geolo: {
    latitude: number;
    longitude: number;
  };
  mapRef: React.MutableRefObject<naver.maps.Map | null>;
  setShowSideBar: React.Dispatch<
    React.SetStateAction<{
      show: boolean;
    }>
  >;
}

let marker: naver.maps.Marker;

export default function Button({ type, geolo, mapRef, setShowSideBar }: IButton) {
  const originLocation = (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    hideMarker(marker);
    setShowSideBar((prev) => {
      return {
        ...prev,
        show: false,
      };
    });
    
    if (e.currentTarget.id === "location") {
      const latLng = new window.naver.maps.LatLng(
        geolo.latitude,
        geolo.longitude
      );

      if (mapRef.current) {
        const latLngBound = new window.naver.maps.LatLngBounds(latLng);
        mapRef.current.fitBounds(latLngBound, { maxZoom: 17 });
      }
    }

    if (e.currentTarget.id === "marker") {
      new window.naver.maps.Event.addListener(
        mapRef.current,
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
          hideMarker(marker);

          if (mapRef.current) {
            marker = new naver.maps.Marker({
              position: new window.naver.maps.LatLng(e.coord.y, e.coord.x),
              map: mapRef.current,
            });
            setShowSideBar((prev) => {
              return {
                ...prev,
                show: true
              }
            })
          }

          return new window.naver.maps.Event.clearListeners(
            mapRef.current,
            "click"
          );
        }
      );
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
