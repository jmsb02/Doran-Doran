import {
  faLocationCrosshairs,
  faLocationDot,
  faRoute,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const TOP = 180 * 2;

const TOP_GAP = 60;

const TYPE = [
  {
    type: 0,
    id: "location",
    title: "현 위치 돌아가기",
    borderColor: "#ff8d4e",
    top: TOP,
    icon: (
      <FontAwesomeIcon
        icon={faLocationCrosshairs}
        fontSize={20}
        color={"#333"}
      />
    ),
  },
  {
    type: 1,
    id: "marker",
    title: "마커 생성",
    borderColor: "#ff8d4e",
    top: TOP + TOP_GAP,
    icon: <FontAwesomeIcon icon={faLocationDot} fontSize={20} color={"#333"} />,
  },
  {
    type: 2,
    id: "way",
    title: "길찾기",
    borderColor: "#ff8d4e",
    top: TOP + TOP_GAP * 2,
    icon: <FontAwesomeIcon icon={faRoute} fontSize={24} color={"#333"} />,
  },
];

export default TYPE;
