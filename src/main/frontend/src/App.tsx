import {useEffect, useState} from 'react';
import axios from 'axios';
<<<<<<< HEAD
import MainPage from './components/MainPage/MainPage';

export default function App() {
    return <MainPage/>
/*    const [hello, setHello] = useState('')
=======

export default function App() {
   const [hello, setHello] = useState('')
>>>>>>> db8679ef84435c1e6cc5c80caf402d8cc4c23b36

    useEffect(() => {
        axios.get('/api/hello')
        .then(response => setHello(response.data))
        .catch(error => console.log(error))
    }, []);

    return (
        <div>
            백엔드에서 가져온 데이터입니다 : {hello}
        </div>
<<<<<<< HEAD
    ); */
=======
    );
>>>>>>> db8679ef84435c1e6cc5c80caf402d8cc4c23b36
}

