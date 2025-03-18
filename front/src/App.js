import React from "react";
import MapComponent from "./googleMap/gMCall";
import SignIn from "./login/SignIn";
import SingUP from "./login/SignUp";

function App() {
    return (
        <div>
            <SignIn />
            <SingUP />
        </div>
    );
}
/*
<MapComponent />
<SignIn />
*/
export default App;
