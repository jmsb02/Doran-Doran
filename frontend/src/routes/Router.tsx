import { createBrowserRouter } from "react-router-dom";

import UserLayout from "../Layout/userLayout/UserLayout";

import MainPage from "../components/MainPage/MainPage";
import LoginPage from "../components/LoginPage/LoginPage";
import SignUpPage from "../components/SignUpPage/SignUpPage";
import HelpPage from "../components/HelpPage/HelpPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <MainPage />,
  },
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
