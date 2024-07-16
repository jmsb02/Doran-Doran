import style from "./index.module.css";

export default function Input({ placeholder }: { placeholder: string }) {
  return <input className={style.input} placeholder={placeholder} />;
}
