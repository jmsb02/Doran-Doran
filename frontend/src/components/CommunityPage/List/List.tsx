import { useNavigate } from "react-router-dom";
import style from "../community.module.css";

export default function List({ board }: any) {
  const navigate = useNavigate();
  return (
    <>
      <div className={style.thrid_detail}>
        <span>제목</span>
        <span>내용</span>
        <span>작성자</span>
        <span>작성일</span>
      </div>
      <ul className={`${style.list_wrap}`}>
        {board.map((item: any) => {
          return (
            <li
              className={style.list}
              key={item.id}
              onClick={() => {
                navigate(`/community/${item.id}`);
              }}
            >
              <span>{item.title}</span>
              <span>{item.content}</span>
              <span>{item.author}</span>
              <span>{item.createdAt}</span>
            </li>
          );
        })}
      </ul>
    </>
  );
}
