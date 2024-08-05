import { Upload } from "antd";

import type { UploadFile } from "antd";
import { UploadChangeParam, UploadListType } from "antd/es/upload/interface";

type CustomCircleUpload = {
  listType: UploadListType;
  fileList: UploadFile[];
  maxPicture: number;
  onChange?:(info: UploadChangeParam<UploadFile<any>>) => void;
};

export default function CustomUpload({ listType, fileList, maxPicture, onChange }: CustomCircleUpload) {
  return (
    <Upload listType={listType} fileList={fileList} onChange={onChange}>
      {fileList.length >= maxPicture ? null : (
        <div>
          <div style={{ marginTop: 8 }}>Upload</div>
        </div>
      )}
    </Upload>
  );
}
