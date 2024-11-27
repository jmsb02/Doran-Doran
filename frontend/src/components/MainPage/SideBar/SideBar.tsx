import type { UploadFile } from "antd";
import { useEffect, useRef, useState } from "react";
import { createMarkerSlice } from "../../../store/bound/slice/marker";
import { hideMarker } from "../../commons/utilities/setter/map";

import { CustomCaution, CustomSidebar, CustomValue } from "./interface";
import { createMapSlice } from "../../../store/bound/slice/map";
import {
  handleFileChange,
  handlePreview,
  setFileChange,
} from "../../commons/utilities/setter/upload";

import Input from "../../commons/input";
import TextArea from "../../commons/textarea";
import style from "./sidebar.module.css";
import Button from "../../commons/button";
import CustomUpload from "../../commons/customUpload/CustomUpload";
import KeywordList from "../KeywordList/KeywordList";

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
  const [previewOpen, setPreviewOpen] = useState(false);
  const [previewImage, setPreviewImage] = useState("");

  const [errForm, setErrForm] = useState({
    errAddress: {
      check: false,
      text: "",
    },
    errTitle: {
      check: false,
      text: "",
    },
    errContent: {
      check: false,
      text: "",
    },
  });

  const errAddress = () => {
    if (inputValues.address) {
      setErrForm((prev) => {
        return {
          ...prev,
          errAddress: {
            check: false,
            text: "",
          },
        };
      });
    } else {
      setErrForm((prev) => {
        return {
          ...prev,
          errAddress: {
            check: true,
            text: "※ 주소를 검색해주세요!",
          },
        };
      });
    }
  };

  const errTitle = () => {
    if (inputValues.title) {
      setErrForm((prev) => {
        return {
          ...prev,
          errTitle: {
            check: false,
            text: "",
          },
        };
      });
    } else {
      setErrForm((prev) => {
        return {
          ...prev,
          errTitle: {
            check: true,
            text: "※ 제목을 입력해주세요!",
          },
        };
      });
    }
  };

  const errContent = () => {
    if (inputValues.content) {
      setErrForm((prev) => {
        return {
          ...prev,
          errContent: {
            check: false,
            text: "",
          },
        };
      });
    } else {
      setErrForm((prev) => {
        return {
          ...prev,
          errContent: {
            check: true,
            text: "※ 내용을 입력해주세요!",
          },
        };
      });
    }
  };

  const errCheck = () => {
    errAddress();
    errTitle();
    errContent();
  };

  useEffect(() => {
    setErrForm({
      errAddress: {
        check: false,
        text: "",
      },
      errTitle: {
        check: false,
        text: "",
      },
      errContent: {
        check: false,
        text: "",
      },
    });
  }, [showSideBar.geocoding]);

  const [inputValues, setInputValues] = useState<CustomValue>({
    address: "",
    title: "",
    content: "",
    x: 0,
    y: 0,
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
    errCheck();
    let files = [];

    if (!inputValues.address) {
      inputValues.x = 0;
      inputValues.y = 0;
    }
    if (coordinateXY.x && coordinateXY.y) {
      inputValues.x = coordinateXY.y;
      inputValues.y = coordinateXY.x;
    }
    console.log(inputValues, fileList);

    if (fileList.length > 0) {
      for (let i = 0; i < fileList.length; i++) {
        if (fileList[i].name) {
          files.push({ base64Data: fileList[i].thumbUrl });
        }
      }
    }

    if (
      inputValues.content &&
      inputValues.title &&
      inputValues.x &&
      inputValues.y
    ) {
      await fetch("http://localhost:8080/api/markers", {
        method: "POST",
        body: JSON.stringify({
          markerDTO: {
            title: inputValues.title,
            content: inputValues.content,
            address: {
              x: inputValues.x,
              y: inputValues.y,
            },
          },
          files,
        }),
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((res) => {
          console.log(res);
          if (res.ok) {
            setShowSideBar((prev) => {
              return {
                ...prev,
                show: false,
              };
            });
            alert("마커가 생성되었습니다.");
            updateReloadMap(true);
            hideMarker(marker!);
          }
        })
        .catch((err) => {
          alert("마커 생성에 실패하였습니다.");
          console.log(err);
        });
      updateReloadMap(false);
    }
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
          {<span className={style.err}>{errForm.errAddress.text}</span>}
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
        {<span className={style.err}>{errForm.errTitle.text}</span>}
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
        {<span className={style.err}>{errForm.errContent.text}</span>}
      </div>
      <div className={style.pictureSection}>
        <label className={style.label}>사진 (최대 3개 까지)</label>
        <CustomUpload
          listType={"picture-circle"}
          fileList={fileList}
          maxPicture={3}
          previewOpen={previewOpen}
          setPreviewOpen={setPreviewOpen}
          previewImage={previewImage}
          setPreviewImage={setPreviewImage}
          handlePreview={handlePreview}
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
