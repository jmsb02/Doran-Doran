import { useState, useEffect, useRef } from "react";
import { getUsers, initMap } from "../../commons/utilities/setter/map";
import { createMapSlice } from "../../../store/bound/slice/map";

import Button from "../Button/Button";
import SideBar from "../SideBar/SideBar";
import { createMemberSlice } from "../../../store/bound/slice/member";

export default function Map() {
  const [showGeolo, setShowGeolo] = useState(false);
  const [showSideBar, setShowSideBar] = useState({
    geocoding: false,
    show: false,
  });

  const [myGeolo, setMyGeolo] = useState({
    latitude: 37.5666805,
    longitude: 126.9784147,
  });

  const userMap = createMapSlice((state) => state.updateMap);
  const consultingMap = createMapSlice((state) => state.consultingMap);
  const reloadMap = createMapSlice((state) => state.reloadMap);
  const user = createMemberSlice((state) => state.consultingMember);

  const markersRef = useRef<any>(null);
  const mapRef = useRef<naver.maps.Map | null>(null);

  useEffect(() => {
    if (navigator.geolocation) {
      setShowGeolo(true);
      navigator.geolocation.getCurrentPosition(
        async (position) => {
          initMap(mapRef, markersRef, position, userMap, setMyGeolo);
        },
        (err) => {
          // gps 기능을 안 킬때는 회원가입일때 사용한 정보로 사용
          console.log(user)
          const position = {
            coords: {
              latitude: user?.address?.x,
              longitude: user?.address?.y,
            },
          };
          initMap(mapRef, markersRef, position, userMap, setMyGeolo);
        }
      );
    } else {
      console.log("no geolocation");
    }
  }, []);

  useEffect(() => {
    if (reloadMap) {
      getUsers(consultingMap).then((res) => {
        markersRef.current = [...res!];
      });
    }
  }, [reloadMap]);

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
          {showSideBar.show && (
            <SideBar
              setShowSideBar={setShowSideBar}
              showSideBar={showSideBar}
            />
          )}
        </>
      ) : (
        <div style={{ minWidth: "100vw", height: "100vh" }}>
          오 이런 위치 설정을 허용 해주셔야 합니다.
        </div>
      )}
    </div>
  );
}
