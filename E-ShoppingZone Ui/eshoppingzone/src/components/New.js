import { useRef } from "react";
import LoginForm from "./LoginForm";

const ComponentTwo = () => {
    const componentOneRef = useRef(null);
    const componentOne = <LoginForm ref={ componentOneRef } />;
   
    return (
        <div>
            <h1>mm</h1>
            <h1>nn</h1>
            <button onClick={ () => componentOneRef.current.hello() }>Hello</button>
        </div>
    );
}

export default ComponentTwo;