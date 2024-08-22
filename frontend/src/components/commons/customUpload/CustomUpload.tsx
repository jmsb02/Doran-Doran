import { Image, Upload } from "antd";

import type { UploadFile } from "antd";
import { UploadChangeParam, UploadListType } from "antd/es/upload/interface";


type CustomCircleUpload = {
  listType: UploadListType;
  fileList: UploadFile<any>[];
  maxPicture: number;
  previewOpen: boolean;
  setPreviewOpen: React.Dispatch<React.SetStateAction<boolean>>;
  previewImage: string;
  setPreviewImage: React.Dispatch<React.SetStateAction<string>>;
  onChange: (info: UploadChangeParam<UploadFile<any>>) => void;
  handlePreview: (
    file: UploadFile,
    setPreviewImage: React.Dispatch<React.SetStateAction<string>>,
    setPreviewOpen: React.Dispatch<React.SetStateAction<boolean>>
  ) => Promise<void>;
};

export default function CustomUpload({
  listType,
  fileList,
  maxPicture,
  previewOpen,
  setPreviewOpen,
  previewImage,
  setPreviewImage,
  onChange,
  handlePreview,
}: CustomCircleUpload) {
  return (
    <>
      <Upload
        listType={listType}
        fileList={fileList}
        beforeUpload={() => false}
        onChange={onChange}
        onPreview={(e) => {
          handlePreview(e, setPreviewImage, setPreviewOpen);
        }}
      >
        {fileList.length >= maxPicture ? null : (
          <div>
            <div style={{ marginTop: 8 }}>Upload</div>
          </div>
        )}
      </Upload>
      {previewImage && (
        <Image
          wrapperStyle={{ display: "none" }}
          preview={{
            visible: previewOpen,
            onVisibleChange: (visible) => setPreviewOpen(visible),
            afterOpenChange: (visible) => !visible && setPreviewImage(""),
          }}
          src={previewImage}
        />
      )}
    </>
  );
}
