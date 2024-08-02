import style from "./index.module.css";

export default function Input({
  type = "text",
  placeholder = "label",
  value = "",
  name,
  setValue = () => {},
}: {
  type?: "text" | "password" | "email";
  placeholder?: string;
  value?: string | number;
  name?: string;
  setValue?:
    | React.Dispatch<React.SetStateAction<string | number>>
    | ((value: string) => void);
}) {
  return (
    <div className={style.wrap}>
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
      {/* <input
        value={value}
        onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
          setValue(e.target.value);
        }}
        type={type}
        className={`${style.input} ${name && style[name]}`}
        placeholder={placeholder}
      /> */}
    </div>
  );
}
