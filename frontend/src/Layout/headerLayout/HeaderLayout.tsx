import { Location, Outlet, useLocation, useNavigate } from "react-router";

import style from "./index.module.css";
import { useEffect, useState } from "react";
import { createMemberSlice } from "../../store/bound/slice/member";
import { useToastStore } from "../../store/ui/toast";

const NavList = ({
  list,
  location,
}: {
  list: { url: string; name: string };
  location: Location<any>;
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
  const user = createMemberSlice((state) => state.consultingMember);
  const user_logout = createMemberSlice((state) => state.resetMember);

  const { addToast } = useToastStore();

  const logout = () => {
    user_logout();
    addToast("😃로그아웃이 정상처리되었습니다.");
    navigate("/");
  };

  const navList = [
    {
      url: "/main",
      name: "Main",
    },
  ];
  return (
    <>
      <header
        className={location.pathname === "/" ? style.overmain : style.header}
      >
        <div className={style.wrap}>
          <div className={style.container}>
            <div className={style.main}>
              <button className={style.logo} onClick={() => navigate("/main")}>
                <div className={style.logo_text}>
                  <p className={style.logo_line_one}>
                    <span style={{ color: "#20c997" }}>DOR</span>AN
                  </p>
                  <p className={style.logo_line_two}>
                    DOR<span style={{ color: "#20c997" }}>AN</span>
                  </p>
                </div>
              </button>
              <nav>
                {navList.map((list) => (
                  <NavList list={list} location={location} key={list.name} />
                ))}
              </nav>
            </div>
            <div>
              <button className={style.myself}>
                <span className={style.name}>{user?.name}</span>님 반갑습니다!
              </button>
              <button
                className={style.logout}
                onClick={() => {
                  logout();
                }}
              >
                로그아웃
              </button>
            </div>
          </div>
        </div>
      </header>
      <main>
        <Outlet />
      </main>
    </>
  );
}
