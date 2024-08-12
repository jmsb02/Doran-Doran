import style from "./index.module.css";

type CustomTextArea = {
  rows?: number;
  cols?: number;
  placeholder?: string;
  onChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
};

export default function TextArea({ rows, cols, placeholder, onChange }: CustomTextArea) {
  return (
    <textarea
      className={style.textArea}
      rows={rows}
      cols={cols}
      placeholder={placeholder}
      onChange={onChange}
    />
  );
}
