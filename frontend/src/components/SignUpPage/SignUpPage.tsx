import { useState } from "react";
import Button from "../commons/button";
import Email from "../commons/email";
import Input from "../commons/input";

import style from "./index.module.css";
import { useNavigate } from "react-router";
import { useToastStore } from "../../store/ui/toast";
import DaumPostcodeEmbed from "react-daum-postcode";
import { UploadFile } from "antd";
import {
  handleFileChange,
  handlePreview,
  setFileChange,
} from "../commons/utilities/setter/upload";
import CustomUpload from "../commons/customUpload/CustomUpload";

type FormProps = {
  name: string;
  loginId: string;
  password: string;
  email: string;
  address: {
    x: number;
    y: number;
  };
  profileImg: "";
};

const GEOCODER = new window.kakao.maps.services.Geocoder();
const ID_REGEX = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]+$/;
const PWD_REGEX =
  /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;
const EMAIL_REGEX = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

export default function SignUpPage() {
  const navigate = useNavigate();

  const [fileList, setFileList] = useState<UploadFile[]>([]);
  const [previewOpen, setPreviewOpen] = useState(false);
  const [previewImage, setPreviewImage] = useState("");

  const [fake, setFake] = useState("");

  const [errForm, setErrForm] = useState({
    nameErr: {
      check: false,
      text: "",
    },
    loginErr: {
      check: false,
      text: "",
    },
    passwordErr: {
      check: false,
      text: "",
    },
    emailErr: {
      check: false,
      text: "",
    },
    addressErr: {
      check: false,
      text: "",
    },
  });

  const [form, setForm] = useState<FormProps>({
    name: "",
    loginId: "",
    password: "",
    email: "",
    address: {
      x: 0,
      y: 0,
    },
    profileImg: "",
  });

  const setNickname = (value: string) => {
    setForm({ ...form, name: value });
  };
  const setId = (value: string) => {
    setForm({ ...form, loginId: value });
  };
  const setPassword = (value: string) => {
    setForm({ ...form, password: value });
  };
  const setEmail = (value: string) => {
    setForm({ ...form, email: value });
  };

  const [isActiveAddress, setIsActiveAddress] = useState(false);

  const toggleAddress = () => {
    setIsActiveAddress(!isActiveAddress);
  };

  const getAddressCoords = (address: string) => {
    return new Promise((resolve, reject) => {
      GEOCODER.addressSearch(address, (result: any, status: any) => {
        if (status === "OK") {
          const coords = new window.kakao.maps.LatLng(result[0].x, result[0].y);
          resolve(coords);
        } else {
          reject(status);
        }
      });
    });
  };

  const getAddress = async (data: any) => {
    const mainAddress = data.roadAddress || data.jibunAddress;
    const coords: any = await getAddressCoords(mainAddress);
    const x = coords.getLng();
    const y = coords.getLat();

    setFake(mainAddress);
    setForm({ ...form, address: { x, y } });
    toggleAddress();
  };

  const { addToast } = useToastStore();

  const errorName = () => {
    if (form.name === "") {
      setErrForm((prev) => {
        return {
          ...prev,
          nameErr: {
            check: true,
            text: "※ 닉네임을 입력해주세요!",
          },
        };
      });
    } else {
      setErrForm((prev) => {
        return {
          ...prev,
          nameErr: {
            check: false,
            text: "",
          },
        };
      });
    }
  };

  const errorId = () => {
    if (ID_REGEX.test(form.loginId)) {
      setErrForm((prev) => {
        return {
          ...prev,
          loginErr: {
            check: false,
            text: "",
          },
        };
      });
    } else {
      setErrForm((prev) => {
        return {
          ...prev,
          loginErr: {
            check: true,
            text: "※ 회원 아이디에 영문, 숫자가 있는지 확인해주세요!",
          },
        };
      });
    }
  };

  const errorPwd = () => {
    if (PWD_REGEX.test(form.password)) {
      setErrForm((prev) => {
        return {
          ...prev,
          passwordErr: {
            check: false,
            text: "",
          },
        };
      });
    } else {
      setErrForm((prev) => {
        return {
          ...prev,
          passwordErr: {
            check: true,
            text: "※ 비밀번호 영문, 숫자, 특수문자로 구성된 8자 이상인지 확인해주세요!",
          },
        };
      });
    }
  };

  const errorEmail = () => {
    if (EMAIL_REGEX.test(form.email)) {
      setErrForm((prev) => {
        return {
          ...prev,
          emailErr: {
            check: false,
            text: "",
          },
        };
      });
    } else {
      setErrForm((prev) => {
        return {
          ...prev,
          emailErr: {
            check: true,
            text: "※ 이메일을 입력해주세요!",
          },
        };
      });
    }
  };

  const errorAddress = () => {
    if (form.address.x) {
      setErrForm((prev) => {
        return {
          ...prev,
          addressErr: {
            check: false,
            text: "",
          },
        };
      });
    } else {
      setErrForm((prev) => {
        return {
          ...prev,
          addressErr: {
            check: true,
            text: "※ 주소를 입력해주세요!",
          },
        };
      });
    }
  };

  const errorCheck = () => {
    errorName();
    errorId();
    errorPwd();
    errorEmail();
    errorAddress();
  };

  const accessSignUp = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    errorCheck();
    if (
      !(
        errForm.nameErr.check &&
        errForm.loginErr.check &&
        errForm.passwordErr.check &&
        errForm.emailErr.check &&
        errForm.addressErr.check
      ) &&
      form.name &&
      form.loginId &&
      form.password &&
      form.email &&
      form.address.x
    ) {
      try {
        const res = await fetch("http://localhost:8080/api/members/signup", {
          method: "POST",
          body: JSON.stringify({
            name: form.name,
            loginId: form.loginId,
            email: form.email,
            password: form.password,
            address: {
              x: form.address.x,
              y: form.address.y,
            },
            profileImg: fileList.length > 0 ? fileList[0].thumbUrl : "",
          }),
          headers: {
            "Content-Type": "application/json",
          },
        });
        if (res.status === 201) {
          addToast("😃이제 로그인을 해주세요.");
          navigate("/");
        }
      } catch (err) {
        console.log(err, "err");
      }
    }
  };

  return (
    <form className={style.form} onSubmit={accessSignUp}>
      <div className={style.content}>
        <div className={style.inputWrap}>
          <span className={style.important}>
            닉네임<span className={style.red}>＊</span>
          </span>
          <Input
            name="nickname"
            value={form.name}
            show={false}
            setValue={setNickname}
            placeholder="닉네임"
          />
          {errForm.nameErr.check && (
            <span className={style.err}>{errForm.nameErr.text}</span>
          )}
        </div>
        <div className={style.inputWrap}>
          <span className={style.important}>
            회원 아이디<span className={style.red}>＊</span>
          </span>
          <Input
            name="id"
            value={form.loginId}
            show={false}
            setValue={setId}
            placeholder="회원 아이디 (아이디는 영문과 숫자를 혼합해주세요.)"
          />
          {errForm.loginErr.check && (
            <span className={style.err}>{errForm.loginErr.text}</span>
          )}
        </div>
        <span className={style.important}>
          비밀번호<span className={style.red}>＊</span>
        </span>
        <Input
          type="password"
          name="password"
          show={false}
          value={form.password}
          setValue={setPassword}
          placeholder="비밀번호 (알파벳 대소문자(a~z, A~Z), 숫자(0~9),특수문자로 구성, 8자 이상으로 해주세요)"
        />
        {errForm.passwordErr.check && (
          <span className={style.err}>{errForm.passwordErr.text}</span>
        )}
        <span className={style.important}>
          이메일<span className={style.red}>＊</span>
        </span>
        <Email name="email" value={form.email} setValue={setEmail} />
        {errForm.emailErr.check && (
          <span className={style.err}>{errForm.emailErr.text}</span>
        )}
        <div className={style.address}>
          <span className={style.important}>
            주소검색<span className={style.red}>＊</span>
          </span>
          <div className={style.address_set}>
            <Button name="주소 검색" styled="address" onClick={toggleAddress} />
            {isActiveAddress && (
              <div onClick={toggleAddress} className={style.popup}>
                <DaumPostcodeEmbed
                  style={{ width: "500px", height: "600px" }}
                  autoClose={false}
                  onComplete={getAddress}
                />
              </div>
            )}
            <div>{fake ? fake : "주소 검색을 시작해 주세요."}</div>
          </div>
          {errForm.addressErr.check && (
            <span className={style.err}>{errForm.addressErr.text}</span>
          )}
        </div>
        <div className={style.profile}>
          <p className={style.important} style={{ marginBottom: "10px" }}>
            이미지 프로필 사진
          </p>
          <CustomUpload
            listType={"picture-circle"}
            fileList={fileList}
            maxPicture={1}
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
      </div>
      <div className={style.btnWrap}>
        <Button styled="danger" name="취소" onClick={() => navigate("/")} />
        <Button styled="submit" type="submit" name="가입" />
      </div>
    </form>
  );
}
