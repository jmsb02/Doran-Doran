import { useState } from "react";
import Button from "../commons/button";
import Email from "../commons/email";
import Input from "../commons/input";

import style from "./index.module.css";

const FindPw = () => {
  return (
    <>
      <Email />
      <Input placeholder="회원 아이디" />
    </>
  );
};

const FindId = () => {
  return <Email />;
};

export default function HelpPage() {
  const [nowFind, setNowFind] = useState("id");

  const menu = [
    {
      value: "id",
      name: "아이디",
    },
    {
      value: "pw",
      name: "비밀번호",
    },
  ];

  return (
    <>
      <div className={style.content}>
        <div className={style.menu}>
          {menu.map((list) => (
            <button
              className={`${nowFind === list.value && style.active}`}
              onClick={() => setNowFind(list.value)}
            >
              {list.name}
            </button>
          ))}
        </div>
        {nowFind === "id" ? <FindId /> : <FindPw />}
      </div>
      <div className={style.btnWrap}>
        <Button styled="danger" name="취소" onClick={() => console.log("ss")} />
        <Button name="검색" onClick={() => console.log("ss")} />
      </div>
    </>
  );
}
