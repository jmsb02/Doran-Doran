import { useEffect, useState } from "react";
import axios from "axios";
import List from "./List/List";
import Pagination from "./Pagination/Pagination";
import BlankList from "../NotFoundPage/NotFoundPage";
import style from "./community.module.css"

export default function Community() {
  const [board, setBoard] = useState<any>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [postsPerPage, setPostsPerPage] = useState(10);

  const lastPostIndex = currentPage * postsPerPage;
  const firstPostIndex = lastPostIndex - postsPerPage;
  const currentPosts = board.slice(firstPostIndex, lastPostIndex);

  const paginate = (pageNumber: number) => {
    if (
      pageNumber < 0 &&
      pageNumber <= Math.ceil(board.length / postsPerPage)
    ) {
      setCurrentPage(1);
    } else if (
      pageNumber > 0 &&
      pageNumber <= Math.ceil(board.length / postsPerPage)
    ) {
      setCurrentPage(pageNumber);
    }
  };

  useEffect(() => {
    axios
      .get("http://localhost:3001/community")
      .then((res) => {
        const { data } = res;
        setBoard([...data]);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <>
      <div className={style.wrap}>
        <div className={style.container}>
          <div className={style.introduce}>
            동아리 / 동호회 모집
            <p className={style.detail}>
              (※게시글 내용은 메인화면에 마커 작성으로부터 생성됩니다.)
            </p>
          </div>
          <List board={currentPosts} />
          <footer className={style.footer}>
            <Pagination
              totalPosts={board.length}
              postsPerpage={postsPerPage}
              paginate={paginate}
              currentPage={currentPage}
            />
          </footer>
        </div>
      </div>
      {/* {board.length === 0 && <BlankList />} */}
    </>
  );
}
