import { useState, useEffect, useRef } from "react";
import { setMarker } from "../../commons/utilities/setter/map";
import { createMapSlice } from "../../../store/bound/slice/map";

import axios from "axios";

import Button from "../Button/Button";
import SideBar from "../SideBar/SideBar";

export default function Map() {
  const [showGeolo, setShowGeolo] = useState(false);
  const [showSideBar, setShowSideBar] = useState({
    show: false,
  });

  const [myGeolo, setMyGeolo] = useState({
    latitude: 37.5666805,
    longitude: 126.9784147,
  });


  const userMap = createMapSlice((state) => state.updateMap);

  const mapRef = useRef<naver.maps.Map | null>(null);

  useEffect(() => {
    if (navigator.geolocation) {
      setShowGeolo(true);
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;

          mapRef.current = new naver.maps.Map("map", {
            center: new naver.maps.LatLng(latitude, longitude),
            zoom: 17,
          });

          userMap(mapRef.current);

          setMyGeolo({
            latitude,
            longitude,
          });

          const marker = setMarker(
            new naver.maps.LatLng(latitude, longitude),
            mapRef.current,
            {}
          );

          new window.naver.maps.Marker(marker);
        },
        (err) => {
          console.log("you would get a concrete error message!", err);
          setShowGeolo(false);
        }
      );
    } else {
      console.log("no geolocation");
    }
  }, []);

  return (
    <div
      style={{
        display: "flex",
        flexDirection: "row",
        alignItems: "center",
        justifyContent: "center",
        position: "relative",
      }}
    >
      {showGeolo ? (
        <>
          <div
            id="map"
            style={{
              minWidth: "100vw",
              height: "100vh",
              position: "relative",
            }}
          />
          {[0, 1, 2].map((idx) => {
            return (
              <Button
                type={idx}
                key={idx + 1}
                geolo={myGeolo}
                setShowSideBar={setShowSideBar}
              />
            );
          })}
          {showSideBar.show && <SideBar setShowSideBar={setShowSideBar} />}
        </>
      ) : (
        <div style={{ minWidth: "100vw", height: "100vh" }}>
          오 이런 위치 설정을 허용 해주셔야 합니다.
        </div>
      )}
    </div>
  );
}
