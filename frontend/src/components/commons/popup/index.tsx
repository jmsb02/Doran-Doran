import { usePopupStore } from "../../../store/ui/popup";
import Container from "../container";
import style from "./index.module.css";

export default function Popup() {
  const { active, content, closePopup } = usePopupStore();
  return (
    <>
      {active && (
        <div
          onClick={() => {
            closePopup();
          }}
          className={style.wrap}
        >
          <div
            onClick={(e) => {
              e.stopPropagation();
            }}
          >
            <Container>{content}</Container>
          </div>
        </div>
      )}
    </>
  );
}
