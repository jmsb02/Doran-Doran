import Button from "../commons/button";
import Email from "../commons/email";
import Input from "../commons/input";

import style from "./index.module.css";

export default function SignUpPage() {
  return (
    <>
      <div className={style.content}>
        <div className={style.inputWrap}>
          <Input placeholder="닉네임" />
          <Button
            name="중복확인"
            onClick={() => {
              console.log("ss");
            }}
          />
        </div>
        <div className={style.inputWrap}>
          <Input placeholder="회원 아이디" />
          <Button
            name="중복확인"
            onClick={() => {
              console.log("ss");
            }}
          />
        </div>
        <Input placeholder="비밀번호" />
        <Input placeholder="비밀번호 확인" />
        <Email />
        <div className="adress">
          <Button name="주소 검색" onClick={() => console.log("ss")} />
          <div>주소 검색을 시작해 주세요.</div>
        </div>
      </div>
      <div className={style.btnWrap}>
        <Button styled="danger" name="취소" onClick={() => console.log("ss")} />
        <Button name="가입" onClick={() => console.log("ss")} />
      </div>
    </>
  );
}
