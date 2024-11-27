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

export interface CustomCaution {
  cautionTitle: boolean;
  cautionContent: boolean;
}

export interface CustomValue {
  address: string;
  title: string;
  content: string;
  x: number;
  y: number;
  keywordList: boolean;
}
