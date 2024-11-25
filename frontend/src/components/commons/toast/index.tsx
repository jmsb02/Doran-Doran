import { useToastStore } from "../../../store/ui/toast";
import style from "./index.module.css";

const List = ({
  toast,
}: {
  toast: {
    id: number;
    message: string;
  };
}) => {
  const { removeToast } = useToastStore();

  return <li onClick={() => removeToast(toast.id)}>{toast.message}</li>;
};

export default function Toast() {
  const { list } = useToastStore();

  return (
    <ul className={style.wrap}>
      {list.map((toast) => (
        <List toast={toast} key={toast.id}/>
      ))}
    </ul>
  );
}
