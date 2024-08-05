import { RouterProvider } from "react-router-dom";
import router from "./routes/Router";

import "./App.css";
import Toast from "./components/commons/toast";
import Popup from "./components/commons/popup";

export default function App() {
  return (
    <div style={{overflow: "hidden"}}>
      <RouterProvider router={router} />
      <Toast />
      <Popup />
    </div>
  );
}

// export default function App() {
//   return <MainPage />;
//   /*    const [hello, setHello] = useState('')

// export default function App() {
//    const [hello, setHello] = useState('')

//     useEffect(() => {
//         axios.get('/api/hello')
//         .then(response => setHello(response.data))
//         .catch(error => console.log(error))
//     }, []);

//     return (
//         <div>
//             백엔드에서 가져온 데이터입니다 : {hello}
//         </div>
//     ); */
// }
