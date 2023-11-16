import { login } from "../../api";
import LoginForm from "../../components/LoginForm";
import { useStore } from "../../store";
import { LoginData } from "../../types";

export default function Login() {
    const setUser = useStore(({ setUser }) => setUser);

    function onLogin(data: LoginData) {
        login(data).then((result) =>
            result.ok
                ? setUser(result.ok)
                : console.error("[login err]", result.err),
        );
    }

    return (
        <>
            <h1>Login User</h1>

            <LoginForm onSubmit={onLogin} submitLabel="Login" />
        </>
    );
}
