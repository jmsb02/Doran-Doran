import { useState } from "react";
import Button from "../commons/button";
import Email from "../commons/email";
import Input from "../commons/input";

import style from "./index.module.css";

export default function SignUpPage() {
  const [form, setForm] = useState<{
    nickname: string;
    id: string;
    password: string;
    email: string;
    address: string;
  }>({
    nickname: "",
    id: "",
    password: "",
    email: "",
    address: "",
  });
  const [passCheck, setPassCheck] = useState<string>("");

  const setNickname = (value: string) => {
    setForm({ ...form, nickname: value });
  };
  const setId = (value: string) => {
    setForm({ ...form, id: value });
  };
  const setPassword = (value: string) => {
    setForm({ ...form, password: value });
  };
  const setEmail = (value: string) => {
    setForm({ ...form, email: value });
  };
  // const setAddress = (value: string) => {
  //   setForm({ ...form, address: value });
  // };

  const accessSignUp = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log(form);
  };

  return (
    <form onSubmit={accessSignUp}>
      <div className={style.content}>
        <div className={style.inputWrap}>
          <Input
            value={form.nickname}
            setValue={setNickname}
            placeholder="닉네임"
          />
          <Button
            name="중복확인"
            onClick={() => {
              console.log("ss");
            }}
          />
        </div>
        <div className={style.inputWrap}>
          <Input value={form.id} setValue={setId} placeholder="회원 아이디" />
          <Button
            name="중복확인"
            onClick={() => {
              console.log("ss");
            }}
          />
        </div>
        <Input
          type="password"
          value={form.password}
          setValue={setPassword}
          placeholder="비밀번호"
        />
        <Input
          type="password"
          value={passCheck}
          setValue={setPassCheck}
          placeholder="비밀번호 확인"
        />
        {/* <Input
          type="email"
          value={form.email}
          setValue={setEmail}
          placeholder="이메일"
        /> */}
        <Email value={form.email} setValue={setEmail} />
        <div className="adress">
          <Button name="주소 검색" onClick={() => console.log("ss")} />
          <div>주소 검색을 시작해 주세요.</div>
        </div>
      </div>
      <div className={style.btnWrap}>
        <Button styled="danger" name="취소" onClick={() => console.log("ss")} />
        <Button type="submit" name="가입" />
      </div>
    </form>
  );
}
