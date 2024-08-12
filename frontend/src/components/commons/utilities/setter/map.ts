import { createMapSlice } from "../../../../store/bound/slice/map";
import marker from "../../../../assets/marker.png";
import character from "../../../../assets/character.png";
import axios from "axios";

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
                  <img src=${marker} width=${50} height=${50}/>
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
  /* const { data } = await axios.get("http://localhost:3001/users");

  const setManyMarkers = () => {
    for (let i = 0; i < data.length; i++) {
      new naver.maps.Marker({
        position: new naver.maps.LatLng(data[i].y, data[i].x),
        map: consultingMap!,
      });
    }
  };
  setManyMarkers(); */
};
