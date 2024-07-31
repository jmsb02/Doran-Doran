import Button from "../button";

import style from "./index.module.css";

export default function Confirm({
  message,
  yes,
  no,
}: {
  message: string;
  yes: (value: unknown) => void;
  no: () => void;
}) {
  return (
    <div className={style.confirm}>
      <div>{message}</div>
      <div className={style.buttonWrap}>
        <Button onClick={yes} name="네" />
        <Button onClick={no} styled="danger" name="아니오" />
      </div>
    </div>
  );
}
