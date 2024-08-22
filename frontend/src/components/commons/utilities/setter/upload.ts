import { GetProp, UploadFile, UploadProps } from "antd";
import { UploadChangeParam } from "antd/es/upload";

/**
 *  공통 기능으로 제작한거라서 바꿔야 하거나 잘못된 경우 제작자 김동우한테 문의 바랍니다.
 *  antd upload 공통적으로 사용한 기능이라서 대부분 곳에서 될 것입니다.
 *  예시 SideBar.tsx
 */

/**
 * @param e: 이벤트 객체 파일리스트 받아오기
 */
export const handleFileChange: UploadProps["onChange"] = (
  e: UploadChangeParam<UploadFile<any>>
) => {
  // fileList는 유사 배열, 그렇기때문에 배열로 만들어 주는 과정이 필요함
  const uploadFile = Array.from(e.fileList);
  return uploadFile;
};

/**
 * @param file: 배열로된 UploadFile 필요
 * @param setfiles: set useState용 하나 필요
 */
export const setFileChange = (
  file: UploadFile<any>[],
  setfiles: React.Dispatch<React.SetStateAction<UploadFile<any>[]>>
) => {
  // validation 작업
  for (let i = 0; i < file.length; i++) {
    if (file[i].size! > 5 * 1024 * 1024) {
      return alert(`${file[i].name} 용량이 너무 큽니다. (제한: 5MB)`);
    }

    if (!file[i].type?.includes("jpeg") && !file[i].type?.includes("png")) {
      return alert(`jpeg 또는 png 파일만 업로드 가능합니다.`);
    }
  }
  setfiles(file);
};

/**
 * handlePreview 사진 등록할때 눈표시 누르면 미리보기 기능
 */
type FileType = Parameters<GetProp<UploadProps, "beforeUpload">>[0];

const getBase64 = (file: FileType): Promise<string> =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result as string);
    reader.onerror = (error) => reject(error);
  });

export const handlePreview = async (
  file: UploadFile,
  setPreviewImage: React.Dispatch<React.SetStateAction<string>>,
  setPreviewOpen: React.Dispatch<React.SetStateAction<boolean>>
) => {
  if (!file.url && !file.preview) {
    file.preview = await getBase64(file.originFileObj as FileType);
  }

  setPreviewImage(file.url || (file.preview as string));
  setPreviewOpen(true);
};
