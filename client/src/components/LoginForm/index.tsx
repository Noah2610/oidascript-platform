import { FormEvent, useEffect, useState } from "react";

import styles from "./loginForm.module.css";

const MIN_USERNAME_LEN = 2;
const MIN_PASSWORD_LEN = 6;

export interface LoginData {
    username: string;
    password: string;
}

interface LoginFormProps {
    passwordConfirmation?: boolean;
    onSubmit?: (login: LoginData) => void;
    submitLabel?: string;
}

export default function LoginForm({
    passwordConfirmation = false,
    submitLabel = "Register",
    onSubmit,
}: LoginFormProps) {
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
        if (!passwordConfirmation) return;
        if (passwordConfirm !== password) {
            setPasswordConfirmError("Passwords must match");
        } else {
            setPasswordConfirmError(null);
        }
    }, [passwordConfirmation, passwordConfirm, password]);

    function onFormSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();

        if (!isValid()) return;

        if (onSubmit) {
            const login = {
                username,
                password,
            };
            onSubmit(login);
        }
    }

    function isValid() {
        return (
            usernameError === null &&
            passwordError === null &&
            (passwordConfirmation ? passwordConfirmError === null : true)
        );
    }

    return (
        <>
            <form action="#" onSubmit={onFormSubmit} className={styles.form}>
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

                {passwordConfirmation && (
                    <div className={styles.formItem}>
                        <label className={styles.formControl}>
                            Confirm Password
                            <input
                                type="password"
                                placeholder="Password again..."
                                value={passwordConfirm}
                                onChange={(e) =>
                                    setPasswordConfirm(e.target.value)
                                }
                            />
                        </label>
                        {passwordConfirmError && (
                            <span className={styles.formError}>
                                {passwordConfirmError}
                            </span>
                        )}
                    </div>
                )}

                <button type="submit" disabled={!isValid()}>
                    {submitLabel}
                </button>
            </form>
        </>
    );
}
