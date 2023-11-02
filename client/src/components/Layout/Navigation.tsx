import { Link } from "react-router-dom";
import styles from "./navigation.module.css";

export default function Navigation() {
    return (
        <nav className={styles.nav}>
            <ul>
                <li>
                    <Link to="/playground">Playground</Link>
                </li>
                <li>
                    <Link to="/register">Register</Link>
                </li>
            </ul>
        </nav>
    );
}
