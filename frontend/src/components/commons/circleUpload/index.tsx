import { Upload } from "antd";

import type { UploadFile } from "antd";

type CustomCircleUpload = {
  fileList: UploadFile[];
};

export default function CircleUpload({ fileList }: CustomCircleUpload) {
  return (
    <Upload listType="picture-circle" fileList={fileList}>
      {fileList.length >= 3 ? null : (
        <div>
          <div style={{ marginTop: 8 }}>Upload</div>
        </div>
      )}
    </Upload>
  );
}
