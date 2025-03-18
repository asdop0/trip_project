//로그인
import React, { useState } from "react";

const SignIn = () => {
    const [isId, setIsId] = useState("");
    const [password, setPassword] = useState("");
    const [isLogin, setIsLogin] = useState(false);

    const handleChangeId = (e) => {
        setIsId(e.target.value);
    };
    const handleChangePw = (e) => {
        setPassword(e.target.value);
    };
    const handleLogin = () => {
        // 서버로 로그인 요청 처리
        console.log("email:", isId, "\npw", password);
    };
    return (
        <div>
            <h1>로그인</h1>
            <form>
                <input
                    type="text"
                    placeholder="아이디"
                    value={isId}
                    onChange={handleChangeId}
                />
                <br />
                <input
                    type="password"
                    placeholder="비밀번호"
                    value={password}
                    onChange={handleChangePw}
                />
                <br />
                <button onClick={handleLogin}>로그인</button>
            </form>
        </div>
    );
};

export default SignIn;
