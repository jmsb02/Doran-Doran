import { useState } from "react";
import Button from "../commons/button";
import Email from "../commons/email";
import Input from "../commons/input";

import style from "./index.module.css";
import { useNavigate } from "react-router";

const FindPw = () => {
  const navigate = useNavigate();

  const [email, setemail] = useState<string>("");
  const [id, setId] = useState<string>("");
  return (
    <>
      <div className={style.form}>
        <Email name="email" value={email} setValue={setemail} />
        <Input
          name="id"
          value={id}
          setValue={setId}
          placeholder="회원 아이디"
        />
      </div>
      <div className={style.btnWrap}>
        <Button styled="danger" name="취소" onClick={() => navigate(-1)} />
        <Button name="검색" onClick={() => console.log(email, id)} />
      </div>
    </>
  );
};

const FindId = () => {
  const navigate = useNavigate();

  const [email, setemail] = useState<string>("");
  return (
    <>
      <div className={style.form}>
        <Email name="email" value={email} setValue={setemail} />
      </div>
      <div className={style.btnWrap}>
        <Button styled="danger" name="취소" onClick={() => navigate(-1)} />
        <Button name="검색" onClick={() => console.log(email)} />
      </div>
    </>
  );
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
    <div className={style.wrap}>
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
      <div className={style.content}>
        {nowFind === "id" ? <FindId /> : <FindPw />}
      </div>
    </div>
  );
}
