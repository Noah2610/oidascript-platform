import { register } from "../../api";
import LoginForm from "../../components/LoginForm";
import { LoginData } from "../../types";

export default function Register() {
    function onRegister(login: LoginData) {
        register(login).then((result) =>
            result.ok
                ? console.info("[register ok]", result.ok)
                : console.error("[register err]", result.err),
        );
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
