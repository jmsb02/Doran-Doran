import { UploadProps } from "antd";

export const handleFileChange: UploadProps["onChange"] = ({fileList}) => {
    console.log(fileList)
};
