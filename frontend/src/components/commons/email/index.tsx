import Input from "../input";

import Button from "../button";

// import Select from "../select";
import style from "./index.module.css";

export default function Email({
  value = "",
  setValue = () => {},
}: {
  value?: string;
  setValue?:
    | React.Dispatch<React.SetStateAction<string | number>>
    | ((value: string) => void);
}) {
  // const emailList = ["naver.com", "google.com"];
  return (
    <>
      <div className={style.email}>
        <Input
          type="email"
          value={value}
          setValue={setValue}
          placeholder="이메일"
        />
        <Button name="확인" />
      </div>
      {/* <div className={style.email}>
        <Input placeholder="이메일 아이디" /> @
        <Select list={emailList} />
        <Button name="확인" onClick={() => console.log("ss")} />
      </div> */}
    </>
  );
}
