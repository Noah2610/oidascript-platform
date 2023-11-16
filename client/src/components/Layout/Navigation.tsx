import { Link } from "react-router-dom";
import { useUser } from "../../hooks";
import styles from "./navigation.module.css";

export default function Navigation() {
    const user = useUser();

    return (
        <nav className={styles.nav}>
            <ul>
                <li>
                    <Link to="/playground">Playground</Link>
                </li>
                {user ? (
                    <li>
                        {/* TODO */}
                        <a href="#logout" onClick={(e) => e.preventDefault()}>
                            Logout
                        </a>
                    </li>
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
