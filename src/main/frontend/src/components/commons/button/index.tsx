import { MouseEventHandler } from "react";
import style from "./index.module.css";

type ButtonProps = {
  type?: "submit" | "reset" | "button";
  name: string;
  onClick: MouseEventHandler<HTMLButtonElement>;
};

export default function Button({ type, name, onClick }: ButtonProps) {
  return (
    <button type={type} className={style.btn} onClick={onClick}>
      {name}
    </button>
  );
}
