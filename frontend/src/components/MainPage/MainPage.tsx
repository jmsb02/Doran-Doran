import { createMarkerSlice } from "../../store/bound/slice/marker";
import Map from "./Map/Map";
import TurnOnOff from "./TurnOnOff/TurnOnOff";
import style from "./mainPage.module.css"

export default function MainPage() {
  const checkTurnOnOff = createMarkerSlice((state) => state.turnOnOff);

  return (
    <div className={style.wrap}>
      <Map />
      {checkTurnOnOff && <TurnOnOff />}
    </div>
  );
}
