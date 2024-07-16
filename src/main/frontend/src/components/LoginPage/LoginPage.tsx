import Button from "../commons/button";
import Container from "../commons/container";
import Input from "../commons/input";

import style from "./index.module.css";

export default function LoginPage() {
  return (
    <main className={style.bg}>
      <h1 className={style.title}>도란도란</h1>
      <h2 className={style.subTitle}>위치 기반 동호회 및 동아리 매칭 서비스</h2>
      <Container width="500px">
        <div className={style.contentWrap}>
          <Input placeholder="아이디" />
          <Input placeholder="비밀번호" />
          <div className={style.btnWrap}>
            <Button name="로그인" onClick={() => console.log("ss")} />
          </div>
        </div>
      </Container>
    </main>
  );
}
