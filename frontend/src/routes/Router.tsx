import { createBrowserRouter } from "react-router-dom";

import UserLayout from "../Layout/userLayout/UserLayout";

import MainPage from "../components/MainPage/MainPage";
import LoginPage from "../components/LoginPage/LoginPage";
import SignUpPage from "../components/SignUpPage/SignUpPage";
import HelpPage from "../components/HelpPage/HelpPage";
import MyPage from "../components/MyPage/MyPage";
import HeaderLayout from "../Layout/headerLayout/HeaderLayout";

const router = createBrowserRouter([
  // 로그인이 필요한 곳 => header 적용
  {
    element: <HeaderLayout />,
    children: [
      {
        path: "/",
        element: <MainPage />,
      },
      {
        path: "/my",
        element: <MyPage />,
      },
    ],
  },
  // 로그인 필요 없는 곳 => header 없는 레이아웃
  {
    element: <UserLayout />,
    children: [
      {
        path: "/signup",
        element: <SignUpPage />,
      },
      {
        path: "/help",
        element: <HelpPage />,
      },
    ],
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
]);

export default router;
