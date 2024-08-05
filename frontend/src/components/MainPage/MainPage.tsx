import { createMarkerSlice } from "../../store/bound/slice/marker";
import Map from "./Map/Map";
import TurnOnOff from "./TurnOnOff/TurnOnOff";

export default function MainPage() {
  const checkTurnOnOff = createMarkerSlice((state) => state.turnOnOff);

  return (
    <div style={{ position: "relative" }}>
      <Map />
      {checkTurnOnOff && <TurnOnOff />}
    </div>
  );
}
