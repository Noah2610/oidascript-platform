import { Link, Outlet } from "react-router-dom";
import Navigation from "./Navigation";

import styles from "./layout.module.css";

export default function Layout() {
    return (
        <div className={styles.layout}>
            <div className={styles.header}>
                <Logo />
                <Navigation />
            </div>
            <main className={styles.main}>
                <Outlet />
            </main>
        </div>
    );
}

function Logo() {
    return (
        <span className={styles.logo}>
            <Link to="/">Oida</Link>
        </span>
    );
}
