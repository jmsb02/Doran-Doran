import { useParams } from "react-router";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser } from "@fortawesome/free-solid-svg-icons";

import dog from "../../assets/dog.png";
import dog2 from "../../assets/dog2.png";
import bird from "../../assets/bird.png";

import style from "./itemPage.module.css";
import { faPenToSquare } from "@fortawesome/free-regular-svg-icons";
import TextArea from "../commons/textarea";
import Button from "../commons/button";
import { changeYYMMDD } from "../commons/utilities/setter/date";

const data = {
  address: "엉터리생고기 무한대패 간석사거리점",
  title: "[모집]삼겹살 드실분 모집합니다.",
  content: "삼겹살 혼자 먹기 그래서 같이 드실분 1명만 더\n모집합니다.",
  name: "아무개123456789",
  profile: "1",
  x: "126.720459599288",
  y: "37.4614862017884",
  pictures: [dog, dog2, bird],
  createdAt: "2024-08-12T12:00:00",
};

const COLORS = ["#512da7", "#0098a6", "#ef6c00"];

function NoneProfile() {
  return (
    <>
      <div
        className={style.profile_frame}
        style={{
          backgroundColor: `${COLORS[Math.round(Math.random() * 2)]}`,
          zIndex: -1,
        }}
      />
      <FontAwesomeIcon
        icon={faUser}
        className={style.none_profile}
        style={{ color: "#fff" }}
      />
    </>
  );
}

function Profile() {
  return (
    <img
      src={data.pictures[0]}
      alt="프로필 사진"
      className={style.profile}
      width="60"
      height="60"
    />
  );
}

export default function ItemPage() {
  /* const {id} = useParams(); */
  console.log(data);
  return (
    <div className={style.wrap}>
      <div className={style.container}>
        <section className={style.left}>
          <div className={style.profile_wrap}>
            {data.profile ? <Profile /> : <NoneProfile />}
            <div className={style.user_info}>
              <p className={style.name}>@{data.name}</p>
              <p className={style.created_at}>
                작성일: {changeYYMMDD(data.createdAt)}
              </p>
            </div>
          </div>
          <div className={style.title}>{data.title}</div>
          <div className={style.main_content}>
            <div className={style.pictures}>
              {data.pictures.map((item, idx) => {
                return (
                  <img
                    key={idx+1}
                    src={item}
                    alt={`게시판에 올린 사진${idx + 1}`}
                    width={1100}
                  />
                );
              })}
            </div>
            <div className={style.content}>
              <p>{data.content}</p>
            </div>
          </div>
        </section>
        <section className={style.right}>
          <div className={style.comment_header}>
            <FontAwesomeIcon
              icon={faPenToSquare}
              style={{ color: "#20c997", marginRight: "5px" }}
            />
            댓글작성
          </div>
          <div className={style.comment_text_area}>
            <TextArea
              className={"comment"}
              placeholder={`인터넷은 우리가 함께 만들어 가는 소중한 공간입니다. 댓글 작성시 타인의 대한 배려와 책임을 담아주세요.`}
              rows={14}
              cols={33}
              onChange={() => {}}
            />
            <Button name="등록" styled="comment_submit" />
          </div>
        </section>
      </div>
    </div>
  );
}
