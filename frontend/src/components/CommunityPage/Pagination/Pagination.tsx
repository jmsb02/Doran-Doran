import { faAngleLeft, faAngleRight } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import style from "./pagination.module.css";

interface CustomPagination {
  totalPosts: number;
  postsPerpage: number;
  currentPage: number;
  paginate: (pageNumber: number) => void;
}

export default function Pagination({
  totalPosts,
  postsPerpage,
  currentPage,
  paginate,
}: CustomPagination) {
  let pages = [];
  for (let i = 1; i <= Math.ceil(totalPosts / postsPerpage); i++) {
    pages.push(i);
  }
  return (
    <nav className={style.wrap}>
      <button
        onClick={() => paginate(currentPage - 10)}
        disabled={currentPage === 1 || pages.length <= 10}
        className={style.btn}
      >
        <FontAwesomeIcon icon={faAngleLeft} />
      </button>
      <ul className={style.page_item_wrap}>
        {pages.map((page, idx) => {
          return (
            <li
              key={idx}
              className={`${style.page_item} ${
                currentPage === page ? `${style.active}` : ""
              }`}
            >
              <a
                href="#none"
                onClick={() => {
                  paginate(page);
                }}
                className={style.page_link}
              >
                {page}
              </a>
            </li>
          );
        })}
      </ul>
      <button
        onClick={() => paginate(currentPage + 10)}
        disabled={currentPage === pages.length || pages.length !== 10}
        className={style.btn}
      >
        <FontAwesomeIcon icon={faAngleRight} />
      </button>
    </nav>
  );
}
