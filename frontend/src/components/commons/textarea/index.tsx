import style from "./index.module.css";

type CustomTextArea = {
  rows?: number;
  cols?: number;
  placeholder?: string;
}

export default function TextArea({ rows, cols, placeholder }: CustomTextArea) {
  return (
    <textarea
      className={style.textArea}
      rows={rows}
      cols={cols}
      placeholder={placeholder}
    />
  );
}
