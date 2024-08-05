import { createMapSlice } from "../../../store/bound/slice/map";
import { createMarkerSlice } from "../../../store/bound/slice/marker";
import { CustomAddress } from "../../../store/bound/slice/type";
import { hideMarker } from "../../commons/utilities/setter/map";
import { CustomValue } from "../SideBar/interface";
import style from "./keywordList.module.css";

interface CustomKeywordList {
  resultAddress: CustomAddress[];
  setInputValues: React.Dispatch<React.SetStateAction<CustomValue>>;
  resetAddress: () => void;
}

export default function KeywordList({
  resultAddress,
  setInputValues,
  resetAddress,
}: CustomKeywordList) {
  const userMap = createMapSlice((state) => state.consultingMap);
  const marker = createMarkerSlice((state) => state.consultingMarker);
  const setMarker = createMarkerSlice((state) => state.updateMarker);

  const goToMarker = (y: string, x: string) => {
    if (marker) hideMarker(marker);
    const mapLatLng = new naver.maps.LatLng(Number(y), Number(x));
    userMap?.panTo(mapLatLng);

    const originMarker = new naver.maps.Marker({
      position: mapLatLng,
      map: userMap!,
    });

    setMarker(originMarker);
  };

  return (
    <ul className={style.keywordListWrap}>
      {resultAddress.map((item) => {
        return (
          <li
            key={item.id}
            onClick={() => {
              setInputValues((prev) => {
                return {
                  ...prev,
                  address: item.place_name,
                  x: item.x,
                  y: item.y,
                  keywordList: false,
                };
              });
              resetAddress();
              goToMarker(item.y, item.x);
            }}
          >
            <p>{item.place_name}</p>
            <span>{item.address_name}</span>
          </li>
        );
      })}
    </ul>
  );
}
