import style from "./index.module.css";

type CustomTextArea = {
  className?: string;
  rows?: number;
  cols?: number;
  placeholder?: string;
  onChange: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
};

export default function TextArea({
  className = "textArea",
  rows,
  cols,
  placeholder,
  onChange,
}: CustomTextArea) {
  return (
    <textarea
      className={style[className]}
      rows={rows}
      cols={cols}
      placeholder={placeholder}
      onChange={onChange}
    />
  );
}
