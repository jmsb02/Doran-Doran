import { ReactNode } from "react";
import style from "./index.module.css";

export default function Container({
  children,
  width,
  height,
}: {
  children: ReactNode;
  width?: string;
  height?: string;
}) {
  return (
    <div className={style.container} style={{ width: width, height: height }}>
      {children}
    </div>
  );
}
