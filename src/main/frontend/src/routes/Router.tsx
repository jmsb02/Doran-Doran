import { createBrowserRouter } from "react-router-dom";

import UserLayout from "./../Layout/UserLayout";

import MainPage from "../components/MainPage/MainPage";
import LoginPage from "../components/LoginPage/LoginPage";
import SignUpPage from "../components/SignUpPage/SignUpPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <MainPage />,
  },
  {
    path: "/user",
    element: <UserLayout />,
    children: [
      {
        path: "signup",
        element: <SignUpPage />,
      },
    ],
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
]);

export default router;
