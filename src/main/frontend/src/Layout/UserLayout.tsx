import { Outlet } from "react-router";

import "./index.css";

export default function UserLayout() {
  return (
    <main>
      <div className="container">
        <Outlet />
      </div>
    </main>
  );
}
