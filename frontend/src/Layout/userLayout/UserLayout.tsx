import { Outlet, useLocation } from "react-router";

import style from "./index.module.css";
import Container from "../../components/commons/container";
import { useEffect, useState } from "react";

export default function UserLayout() {
  const location = useLocation();

  const [title, setTitle] = useState("");

  useEffect(() => {
    if (location.pathname === "/signup") {
      setTitle("계정 생성");
    }
    if (location.pathname === "/help") {
      setTitle("계정 찾기");
    }
  }, []);
  return (
    <div className={style.bg}>
      <Container width={"var(--length-width)"} height={"70vh"}>
        <div className={style.row}>
          <div className={style.titleWrap}>
            <div className={style.logo}></div>
            <div className={style.subTitle}>{title}</div>
          </div>
          <div className={style.contentWrap}>
            <Outlet />
          </div>
        </div>
      </Container>
    </div>
  );
}
