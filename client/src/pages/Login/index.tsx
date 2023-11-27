import { useState } from "react";
import { login } from "../../api";
import LoginForm from "../../components/LoginForm";
import { useStore } from "../../store";
import { LoginData } from "../../types";

export default function Login() {
    const setUser = useStore(({ setUser }) => setUser);

    const [error, setError] = useState<string | null>(null);

    function onLogin(data: LoginData) {
        login(data).then((result) => {
            if (result.ok) {
                setUser(result.ok);
            } else {
                console.error("[login err]", result.err);
                setError(result.err.message);
            }
        });
    }

    return (
        <>
            <h1>Login User</h1>

            {error && <div>{error}</div>}

            <LoginForm onSubmit={onLogin} submitLabel="Login" />
        </>
    );
}
