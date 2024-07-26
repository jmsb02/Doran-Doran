import { useState } from "react";
import { createMarkerSlice } from "../../../store/bound/slice/marker";
import { hideMarker } from "../../commons/utilities/setter/map";
import type { UploadFile } from "antd";

import CircleUpload from "../../commons/circleUpload";
import Input from "../../commons/input";
import TextArea from "../../commons/textarea";
import style from "./sidebar.module.css";
import Button from "../../commons/button";

interface CustomSidebar {
  setShowSideBar: React.Dispatch<
    React.SetStateAction<{
      show: boolean;
    }>
  >;
}

export default function SideBar({ setShowSideBar }: CustomSidebar) {
  const marker = createMarkerSlice((state) => state.consultingMarker);
  const [fileList, setFileList] = useState<UploadFile[]>([
    {
      uid: "-1",
      name: "image.png",
      status: "done",
      url: "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png",
    },
    {
      uid: "1",
      name: "image.png",
      status: "done",
      url: "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png",
    },
  ]);

  function cancel() {
    if (marker) {
      hideMarker(marker);
      setShowSideBar((prev) => {
        return {
          ...prev,
          show: false,
        };
      });
    }
  }

  return (
    <div className={style.sidebar}>
      <div className={style.titleSection}>
        <label className={style.label}>
          제목<span className={style.requiredColor}>*</span>
        </label>
        <Input placeholder="제목을 입력해주세요." name="title" />
      </div>
      <div className={style.contentSection}>
        <label className={style.label}>
          내용<span className={style.requiredColor}>*</span>
        </label>
        <TextArea
          rows={14}
          cols={33}
          placeholder="동아리에 대한 설명을 써주세요."
        />
      </div>
      <div className={style.pictureSection}>
        <label className={style.label}>사진 (최대 3개 까지)</label>
        <CircleUpload fileList={fileList} />
      </div>
      <div className={style.buttonSection}>
        <Button type="submit" name="마커 표시하기" styled="save" />
        <Button
          type="submit"
          name="취소하기"
          styled="cancel"
          onClick={cancel}
        />
      </div>
    </div>
  );
}
