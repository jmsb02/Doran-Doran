import style from "./notFoundPage.module.css"

export default function NotFoundPage() {
  return (
    <div className={style.wrap}>
      😅 해당 페이지가 존재하지 않습니다.
      url를 다시 확인해주세요.
    </div>
  );
}
