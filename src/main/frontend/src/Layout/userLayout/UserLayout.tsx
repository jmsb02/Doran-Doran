import { Outlet } from "react-router";

import style from "./index.module.css";
import Container from "../../components/commons/container";

export default function UserLayout() {
  return (
    <div className={style.bg}>
      <Container width={"var(--length-width)"} height={"70vh"}>
        <Outlet />
      </Container>
    </div>
  );
}
