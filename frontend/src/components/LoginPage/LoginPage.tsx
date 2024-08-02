import { useState } from "react";
import Button from "../commons/button";
import Container from "../commons/container";
import Input from "../commons/input";

import style from "./index.module.css";
import { useNavigate } from "react-router";
import { useToastStore } from "../../store/ui/toast";

export default function LoginPage() {
  const navigate = useNavigate();

  const [id, setId] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const { addToast } = useToastStore();
  const login = () => {
    addToast("로그인에 성공하였습니다.");
    console.log(id, password);
  };

  return (
    <main className={style.bg}>
      <h1 className="a11y-hidden">도란도란</h1>
      <div className={style.logo}></div>
      <h2 className={style.subTitle}>위치 기반 동호회 및 동아리 매칭 서비스</h2>
      <Container width="500px">
        <div className={style.contentWrap}>
          <Input name="id" value={id} setValue={setId} placeholder="아이디" />
          <Input
            name="password"
            value={password}
            setValue={setPassword}
            type="password"
            placeholder="비밀번호"
          />
          <div className={style.btnWrap}>
            <button className={style.link} onClick={() => navigate("/signup")}>
              계정 생성
            </button>
            <button className={style.link} onClick={() => navigate("/help")}>
              계정 찾기
            </button>
            <Button name="로그인" onClick={login} />
          </div>
        </div>
      </Container>
    </main>
  );
}
