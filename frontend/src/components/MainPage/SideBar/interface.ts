import { UploadFile } from "antd";

export interface CustomSidebar {
  setShowSideBar: React.Dispatch<
    React.SetStateAction<{
      geocoding: boolean;
      show: boolean;
    }>
  >;
  showSideBar: {
    geocoding: boolean;
    show: boolean;
  };
}

export interface CustomValue {
  address: string;
  title: string;
  content: string;
  x: string;
  y: string;
  keywordList: boolean;
}
