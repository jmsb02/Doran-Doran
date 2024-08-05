import { ForwardedRef, forwardRef, useImperativeHandle } from "react";
import style from "./index.module.css";



interface CustomInput {
  type?: "text" | "password" | "email";
  placeholder?: string;
  value?: string | number;
  name?: string;
  show?: boolean;
  setValue?:
    | React.Dispatch<React.SetStateAction<string | number >>
    | ((value: string) => void);
}

function Input(
  {
    type = "text",
    placeholder = "label",
    value = "",
    name,
    show = true,
    setValue = () => {},
  }: CustomInput,
  ref?:ForwardedRef<HTMLInputElement>
) {

  return (
    <div className={style.wrap}>
      {show ? (
        <div className={style.container}>
          <input
            value={value}
            id={name}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
              setValue(e.target.value);
            }}
            className={`${String(value).length > 0 && style["writed"]}`}
            type={type}
          />
          <label htmlFor={name}>{placeholder}</label>
        </div>
      ) : (
        <input
          value={value}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
            setValue(e.target.value);
          }}
          ref={ref}
          type={type}
          className={`${style.input} ${name && style[name]}`}
          placeholder={placeholder}
        />
      )}
    </div>
  );
} 

export default forwardRef(Input);