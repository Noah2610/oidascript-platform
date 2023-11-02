import { FormEvent, useEffect, useState } from "react";
import LoginForm, { LoginData } from "../../components/LoginForm";

export default function Register() {
    function onRegister(login: LoginData) {
        // TODO
        throw new Error("unimplemented: register");
    }

    return (
        <>
            <h1>Register User</h1>

            <LoginForm
                onSubmit={onRegister}
                submitLabel="Register"
                passwordConfirmation
            />
        </>
    );
}
