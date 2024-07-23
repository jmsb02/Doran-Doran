import { useState } from "react";
import Button from "../commons/button";
import Container from "../commons/container";
import Input from "../commons/input";

import style from "./index.module.css";

export default function LoginPage() {
  const [id, setId] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const login = () => {
    console.log(id, password);
  };
  return (
    <main className={style.bg}>
      <h1 className={style.title}>도란도란</h1>
      <h2 className={style.subTitle}>위치 기반 동호회 및 동아리 매칭 서비스</h2>
      <Container width="500px">
        <div className={style.contentWrap}>
          <Input value={id} setValue={setId} placeholder="아이디" />
          <Input
            value={password}
            setValue={setPassword}
            type="password"
            placeholder="비밀번호"
          />
          <div className={style.btnWrap}>
            <Button name="로그인" onClick={login} />
          </div>
        </div>
      </Container>
    </main>
  );
}
