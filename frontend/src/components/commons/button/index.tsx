import { KeyboardEventHandler, MouseEventHandler } from "react";
import style from "./index.module.css";

type ButtonProps = {
  type?: "submit" | "reset" | "button";
  styled?: string;
  name: string;
  onClick?: MouseEventHandler<HTMLButtonElement>;
};

export default function Button({
  type = "button",
  styled = "default",
  name,
  onClick,
}: ButtonProps) {
  return (
    <button
      type={type}
      className={`${style.btn} ${
        styled !== "default" && styled && style[styled]
      }`}
      onClick={onClick}
    >
      {name}
    </button>
  );
}
