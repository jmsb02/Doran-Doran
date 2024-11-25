import style from "./profile.module.css"
import dog from "../../../assets/dog.png";
import dog2 from "../../../assets/dog2.png";
import bird from "../../../assets/bird.png";

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

export default function Profile() {
  return (
    <div className={style.wrap}>
      <img
        src={data.pictures[0]}
        alt="프로필 사진"
        className={style.profile}
        width="60"
        height="60"
      />
      <span className={style.name}>@{data.name}</span>
    </div>
  );
}
