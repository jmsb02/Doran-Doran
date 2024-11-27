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
  }, []);
  return (
    <div className={style.bg}>
      <Container width={"var(--length-width)"} height={"90vh"}>
        <div className={style.row}>
          <div className={style.titleWrap}>
            <div className={style.logo}>
              <p className={style.logo_line_one}>
                <span style={{ color: "#20c997" }}>DOR</span>AN
              </p>
              <p className={style.logo_line_two}>
                DOR<span style={{ color: "#20c997" }}>AN</span>
              </p>
            </div>
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
