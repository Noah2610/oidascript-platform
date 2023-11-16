import { login } from "../../api";
import LoginForm, { LoginData } from "../../components/LoginForm";
import { useStore } from "../../store";

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
