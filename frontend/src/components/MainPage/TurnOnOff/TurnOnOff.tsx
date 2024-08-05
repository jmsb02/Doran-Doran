import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faExclamation } from "@fortawesome/free-solid-svg-icons";

import style from "./turnonoff.module.css"

export default function TurnOnOff() {
  return (
    <div className={style.turnOnOffWrap}>
      <FontAwesomeIcon
        icon={faExclamation}
        className={style.exclamation}
      />
      마커를 지정하는중 입니다.
    </div>
  );
}
