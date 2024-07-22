import Button from "../button";
import Input from "../input";
import Select from "../select";
import style from "./index.module.css";

export default function Email() {
  const emailList = ["naver.com", "google.com"];
  return (
    <div className={style.email}>
      <Input placeholder="이메일 아이디" /> @
      <Select list={emailList} />
      <Button name="확인" onClick={() => console.log("ss")} />
    </div>
  );
}
