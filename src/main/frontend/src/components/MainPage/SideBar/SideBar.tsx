export default function SideBar() {
  return (
    <div
      style={{
        position: "absolute",
        left: 0,
        top: 0,
        height: "100vh",
        width: "24rem",
        backgroundColor: "#fff",
        transform: "translatex(0%)",
        transition: "transform 0.4s ease-in-out",
        boxShadow: "1px 1px 5px #5b5b5b",
      }}
    >
      사이드바입니다.
    </div>
  );
}
