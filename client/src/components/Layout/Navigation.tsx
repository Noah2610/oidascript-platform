import { Link } from "react-router-dom";
import { logout } from "../../api";
import { useUser } from "../../hooks";
import { useStore } from "../../store";
import styles from "./navigation.module.css";

export default function Navigation() {
    const isLoggedIn = !!useUser();
    const clearUser = useStore(({ clearUser }) => clearUser);

    const doLogout = () =>
        logout().then((result) =>
            result.ok
                ? clearUser()
                : console.error("[logout error]", result.err),
        );

    return (
        <nav className={styles.nav}>
            <ul>
                <li>
                    <Link to="/playground">Playground</Link>
                </li>
                {isLoggedIn ? (
                    <>
                        <li>
                            <Link to="/scripts">My Scripts</Link>
                        </li>
                        <li>
                            {/* TODO */}
                            <a
                                href="#logout"
                                onClick={(e) => {
                                    e.preventDefault();
                                    doLogout();
                                }}
                            >
                                Logout
                            </a>
                        </li>
                    </>
                ) : (
                    <>
                        <li>
                            <Link to="/register">Register</Link>
                        </li>
                        <li>
                            <Link to="/login">Login</Link>
                        </li>
                    </>
                )}
            </ul>
        </nav>
    );
}
