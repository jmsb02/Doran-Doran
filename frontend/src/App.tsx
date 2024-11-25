import { RouterProvider } from "react-router-dom";
import router from "./routes/Router";

import "./App.css";
import Toast from "./components/commons/toast";
import Popup from "./components/commons/popup";

export default function App() {
  return (
    <div style={{overflow: "hidden"}}>
      <RouterProvider router={router} />
      <Toast />
      <Popup />
    </div>
  );
}

