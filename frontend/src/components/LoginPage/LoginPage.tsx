import { useState } from "react";
import Button from "../commons/button";
import Container from "../commons/container";
import Input from "../commons/input";

import style from "./index.module.css";
import { useNavigate } from "react-router";
import { useToastStore } from "../../store/ui/toast";
import { createMemberSlice } from "../../store/bound/slice/member";

export default function LoginPage() {
  const updateMember = createMemberSlice((state) => state.updateMember);
  const navigate = useNavigate();

  const [errForm, setErrForm] = useState({
    errId: {
      check: false,
      text: "",
    },
    errPwd: {
      check: false,
      text: "",
    },
  });

  const [id, setId] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const { addToast } = useToastStore();

  const errorId = () => {
    if (id === "") {
      setErrForm((prev) => {
        return {
          ...prev,
          errId: {
            check: true,
            text: "아이디를 입력해주세요!",
          },
        };
      });
    } else {
      setErrForm((prev) => {
        return {
          ...prev,
          errId: {
            check: false,
            text: "",
          },
        };
      });
    }
  };

  const errorPwd = () => {
    if (password === "") {
      setErrForm((prev) => {
        return {
          ...prev,
          errPwd: {
            check: true,
            text: "비밀번호를 입력해주세요!",
          },
        };
      });
    } else {
      setErrForm((prev) => {
        return {
          ...prev,
          errPwd: {
            check: false,
            text: "",
          },
        };
      });
    }
  };

  const login = async () => {
    errorId();
    errorPwd();
    if (id.length > 0 && password.length > 0) {
      try {
        fetch("http://localhost:8080/api/members/login", {
          method: "POST",
          body: JSON.stringify({
            loginId: id,
            password,
          }),
          headers: {
            "Content-Type": "application/json",
          },
          credentials: "include",
        })
          .then((res) => {
            if (res.ok) {
              res.json().then((response) => {
                updateMember(response);
                addToast("😃로그인에 성공하였습니다.");
                navigate("/main");
              });
            } else {
              throw res;
            }
          })
          .catch((err) => {
            err.text().then((msg: string) => {
              alert(msg);
            });
          });
      } catch (err) {
        console.log(err);
      }
    }
  };

  return (
    <main className={style.bg}>
      <h1 className="a11y-hidden">도란도란</h1>
      <div className={style.logo}>
        <p className={style.logo_line_one}>
          <span style={{ color: "#20c997" }}>DOR</span>AN
        </p>
        <p className={style.logo_line_two}>
          DOR<span style={{ color: "#20c997" }}>AN</span>
        </p>
      </div>
      <h2 className={style.subTitle}>
        마음에 맞는 사람들끼리 놀고 웃자!, 도란도란
      </h2>
      <Container width="500px">
        <div className={style.contentWrap}>
          <Input
            name="id"
            show={false}
            value={id}
            setValue={setId}
            placeholder="아이디"
          />
          {errForm.errId.check && (
            <span className={style.err}>※ 아이디를 입력해주세요!</span>
          )}
          <Input
            name="password"
            show={false}
            value={password}
            setValue={setPassword}
            type="password"
            placeholder="비밀번호"
          />
          {errForm.errPwd.check && (
            <span className={style.err}>※ 비밀번호를 입력해주세요!</span>
          )}
          <div className={style.btnWrap}>
            <Button
              name="계정 생성"
              onClick={() => navigate("/signup")}
              styled="create"
            />
            <Button name="로그인" onClick={login} styled="login" />
          </div>
        </div>
      </Container>
    </main>
  );
}
