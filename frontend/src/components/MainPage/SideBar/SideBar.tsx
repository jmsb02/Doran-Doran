import type { UploadFile } from "antd";
import { useEffect, useRef, useState } from "react";
import { createMarkerSlice } from "../../../store/bound/slice/marker";
import { hideMarker } from "../../commons/utilities/setter/map";

import { CustomSidebar, CustomValue } from "./interface";
import { createMapSlice } from "../../../store/bound/slice/map";
import {
  handleFileChange,
  setFileChange,
} from "../../commons/utilities/setter/upload";

import Input from "../../commons/input";
import TextArea from "../../commons/textarea";
import style from "./sidebar.module.css";
import Button from "../../commons/button";
import CustomUpload from "../../commons/customUpload/CustomUpload";
import KeywordList from "../KeywordList/KeywordList";
import axios from "axios";

const PLACES = new window.kakao.maps.services.Places();
let timer: any;

export default function SideBar({
  setShowSideBar,
  showSideBar,
}: CustomSidebar) {
  const marker = createMarkerSlice((state) => state.consultingMarker);
  const coordinateXY = createMarkerSlice((state) => state.coordinateXY);
  const resultAddress = createMapSlice((state) => state.address);
  const updateAddress = createMapSlice((state) => state.updateAddress);
  const updateReloadMap = createMapSlice((state) => state.updateReloadMap);
  const resetAddress = createMapSlice((state) => state.resetAddress);

  const [fileList, setFileList] = useState<UploadFile[]>([]);

  const [inputValues, setInputValues] = useState<CustomValue>({
    address: "",
    title: "",
    content: "",
    x: "",
    y: "",
    keywordList: true,
  });

  const inputRef = useRef<HTMLInputElement>(null);

  /**
   * 마커 표시하기
   * @param inputValues: address, title, content, x, y (필수 값)
   * @param fileList: uploda 하는 사진들 (선택 값)
   */
  const submit = async (
    inputValues: CustomValue,
    fileList: UploadFile<any>[]
  ) => {
    /* if (coordinateXY.x && coordinateXY.y) {
      inputValues.x = String(coordinateXY.x);
      inputValues.y = String(coordinateXY.y);
    }
    console.log(inputValues);
    axios
      .post("http://localhost:3001/users", {
        ...inputValues,
        fileList,
      })
      .then(() => {
        alert("마커가 생성되었습니다.");
        setShowSideBar((prev) => {
          return {
            ...prev,
            show: false,
          };
        });
        updateReloadMap(true);
      })
      .catch((err) => {
        alert("마커 생성에 실패하였습니다.");
        console.log(err);
      });
    updateReloadMap(false); */
  };

  const cancel = () => {
    if (marker) {
      hideMarker(marker);
    }
    setShowSideBar((prev) => {
      return {
        ...prev,
        show: false,
      };
    });
  };

  /**
   * 카카오용
   * @param address: 입력받은 주소값
   */
  const searchAddressToCoordinate = (address: string) => {
    if (address) {
      if (timer) clearTimeout(timer);

      timer = setTimeout(() => {
        if (address.length > 1) PLACES.keywordSearch(address, callback);
      }, 1000);
    }
    return;
  };

  /**
   * 카카오용
   * @param result: 검색어
   * @param status: callback 함수의미
   */

  const callback = (result: any, status: any) => {
    if (status === window.kakao.maps.services.Status.OK) {
      updateAddress([...result]);
      return;
    }
  };

  useEffect(() => {
    resetAddress();
    searchAddressToCoordinate(inputValues.address);
  }, [inputValues.address]);

  return (
    <div className={style.sidebar}>
      {showSideBar.geocoding && (
        <div className={style.addressSection}>
          <label className={style.label}>
            주소검색<span className={style.requiredColor}>*</span>
          </label>
          <Input
            placeholder="주소 또는 도로명을 입력해주세요."
            name="address"
            ref={inputRef}
            setValue={(value: string) => {
              setInputValues((prev: CustomValue) => {
                return {
                  ...prev,
                  address: value,
                  keywordList: true,
                };
              });
            }}
            show={false}
            value={inputValues.address}
          />
          {resultAddress.length > 0 && inputValues.keywordList && (
            <KeywordList
              resultAddress={resultAddress}
              setInputValues={setInputValues}
              resetAddress={resetAddress}
            />
          )}
        </div>
      )}
      <div className={style.titleSection}>
        <label className={style.label}>
          제목<span className={style.requiredColor}>*</span>
        </label>
        <Input
          placeholder="제목을 입력해주세요."
          name="title"
          show={false}
          setValue={(value: string) => {
            setInputValues((prev: CustomValue) => {
              return {
                ...prev,
                title: value,
              };
            });
          }}
          value={inputValues.title}
        />
      </div>
      <div className={style.contentSection}>
        <label className={style.label}>
          내용<span className={style.requiredColor}>*</span>
        </label>
        <TextArea
          rows={14}
          cols={33}
          placeholder="동아리에 대한 설명을 써주세요."
          onChange={(e) => {
            setInputValues((prev) => {
              return {
                ...prev,
                content: e.target.value,
              };
            });
          }}
        />
      </div>
      <div className={style.pictureSection}>
        <label className={style.label}>사진 (최대 3개 까지)</label>
        <CustomUpload
          listType={"picture-circle"}
          fileList={fileList}
          maxPicture={3}
          onChange={(e) => {
            const file = handleFileChange?.(e);
            setFileChange(file!, setFileList);
          }}
        />
      </div>
      <div className={style.buttonSection}>
        <Button
          type="submit"
          name="마커 표시하기"
          styled="save"
          onClick={() => {
            submit(inputValues, fileList);
          }}
        />
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
