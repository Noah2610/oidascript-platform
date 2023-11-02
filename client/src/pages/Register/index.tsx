import { FormEvent, useEffect, useLayoutEffect, useRef, useState } from "react";

import styles from "./register.module.css";

const MIN_USERNAME_LEN = 2;
const MIN_PASSWORD_LEN = 6;

export default function Register() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [passwordConfirm, setPasswordConfirm] = useState("");
    const [usernameError, setUsernameError] = useState<string | null>(null);
    const [passwordError, setPasswordError] = useState<string | null>(null);
    const [passwordConfirmError, setPasswordConfirmError] = useState<
        string | null
    >(null);

    useEffect(() => {
        if (username.trim().length < MIN_USERNAME_LEN) {
            setUsernameError(
                `Username must be ${MIN_USERNAME_LEN} characters or longer`,
            );
        } else {
            setUsernameError(null);
        }
    }, [username]);

    useEffect(() => {
        if (password.length < MIN_PASSWORD_LEN) {
            setPasswordError(
                `Password must be ${MIN_PASSWORD_LEN} characters or longer`,
            );
        } else {
            setPasswordError(null);
        }
    }, [password]);

    useEffect(() => {
        if (passwordConfirm !== password) {
            setPasswordConfirmError("Passwords must match");
        } else {
            setPasswordConfirmError(null);
        }
    }, [passwordConfirm, password]);

    function onSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
    }

    function isValid() {
        return (
            usernameError === null &&
            passwordError === null &&
            passwordConfirmError === null
        );
    }

    return (
        <>
            <h1>Register User</h1>

            <form action="#" onSubmit={onSubmit} className={styles.form}>
                <div className={styles.formItem}>
                    <label className={styles.formControl}>
                        Username
                        <input
                            type="text"
                            placeholder="Username..."
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </label>
                    {usernameError && (
                        <span className={styles.formError}>
                            {usernameError}
                        </span>
                    )}
                </div>

                <div className={styles.formItem}>
                    <label className={styles.formControl}>
                        Password
                        <input
                            type="password"
                            placeholder="Password..."
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </label>
                    {passwordError && (
                        <span className={styles.formError}>
                            {passwordError}
                        </span>
                    )}
                </div>

                <div className={styles.formItem}>
                    <label className={styles.formControl}>
                        Confirm Password
                        <input
                            type="password"
                            placeholder="Password again..."
                            value={passwordConfirm}
                            onChange={(e) => setPasswordConfirm(e.target.value)}
                        />
                    </label>
                    {passwordConfirmError && (
                        <span className={styles.formError}>
                            {passwordConfirmError}
                        </span>
                    )}
                </div>

                <button type="submit" disabled={!isValid()}>
                    Register
                </button>
            </form>
        </>
    );
}
