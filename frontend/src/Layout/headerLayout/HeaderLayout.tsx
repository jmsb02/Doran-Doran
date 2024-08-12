import { Location, Outlet, useLocation, useNavigate } from "react-router";

import style from "./index.module.css";
import { useEffect, useState } from "react";




const NavList = ({
  list,
  location
}: {
  list: { url: string; name: string; },
  location:Location<any> 
}) => {
  const navigate = useNavigate();
  const [active, setActive] = useState<boolean>(false);

  useEffect(() => {
    if (location.pathname === list.url) {
      setActive(true);
    } else {
      setActive(false);
    }
  }, [location.pathname]);
  return (
    <button
      className={active ? style.active : ""}
      onClick={() => navigate(list.url)}
    >
      {list.name}
    </button>
  );
};
export default function HeaderLayout() {
  // 로그인 구현 시 로그인 상태가 아니면 리다이렉션 되는 것 적용

  const navigate = useNavigate();
  const location = useLocation();


  const navList = [
    {
      url: "/",
      name: "Home",
    },
    {
      url: "/community",
      name: "community",
    },
  ];
  return (
    <>
      <header
        className={location.pathname === "/" ? style.overmain : style.header}
      >
        <div className={style.wrap}>
          <div className={style.main}>
            <button
              className={style.logo}
              onClick={() => navigate("/")}
            ></button>
            <nav>
              {navList.map((list) => (
                <NavList list={list} location={location} key={list.name}/>
              ))}
            </nav>
          </div>
          <button className={style.myself} onClick={() => navigate("/my")}>
            000님
          </button>
        </div>
      </header>
      <main>
        <Outlet />
      </main>
    </>
  );
}
