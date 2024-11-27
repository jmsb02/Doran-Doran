import dog2 from "../../../assets/dog2.png";
import Profile from "../../commons/profile/Profile";
import style from "./comment.module.css"

const COMMENTS = [
  {
    id: "9999",
    profile: dog2,
    comment: `댓글입니다 안녕하세요`,
  },
];

export default function Comment() {
  return (
    <div className={style.wrap}>
      <Profile />
      <div className={style.comment_container}>
        <p className={style.comment}>{COMMENTS[0].comment}</p>
      </div>
    </div>
  );
}
