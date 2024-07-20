import Button from "../button";
import Input from "../input";
import style from "./index.module.css";

export default function Email() {
  return (
    <div className={style.email}>
      <Input placeholder="이메일 아이디" /> @
      <select>
        <option>ss</option>
      </select>
      <Button name="확인" onClick={() => console.log("ss")} />
    </div>
  );
}
