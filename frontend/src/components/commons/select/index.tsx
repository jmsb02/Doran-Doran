import style from "./index.module.css";

export default function Select({ list }: { list: string[] }) {
  return (
    <select className={style.select}>
      {list.map((option) => (
        <option>{option}</option>
      ))}
    </select>
  );
}
