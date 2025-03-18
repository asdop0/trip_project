//회원가입
import React, { useState } from "react";

const SingUP = () => {
    const [isId, setIsId] = useState("");
    const [password, setPassword] = useState("");
    const [passwordCheck, setPasswordCheck] = useState("");
    const [nickName, setNickName] = useState("");
    const [isLogin, setIsLogin] = useState(false);

    const handleChange = (e) => {
        if (e.target.name === "id") setIsId(e.target.value);
        if (e.target.name === "pw") setPassword(e.target.value);
        if (e.target.name === "pwCheck") setPasswordCheck(e.target.value);
        if (e.target.name === "nickName") setNickName(e.target.value);
    };
    const handleSignUp = () => {
        // 서버로 회원가입 요청 처리
        console.log(
            "id:",
            isId,
            "\npw",
            password,
            "\npwCheck",
            passwordCheck,
            "\nnickName",
            nickName
        );
    };
    return (
        <div>
            <h1>회원가입</h1>
            <form>
                <input
                    type="text"
                    name="id"
                    placeholder="아이디"
                    value={isId}
                    onChange={handleChange}
                />
                <br />
                <input
                    type="password"
                    name="pw"
                    placeholder="비밀번호"
                    value={password}
                    onChange={handleChange}
                />
                <br />
                <input
                    type="password"
                    name="pwCheck"
                    placeholder="비밀번호확인"
                    value={passwordCheck}
                    onChange={handleChange}
                />
                <br />
                <input
                    type="text"
                    name="nickName"
                    placeholder="닉네임"
                    value={nickName}
                    onChange={handleChange}
                />
                <br />
                <button onClick={handleSignUp}>회원가입</button>
            </form>
        </div>
    );
};

export default SingUP;
