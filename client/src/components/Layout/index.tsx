import { Outlet } from "react-router-dom";

export default function Layout() {
    return (
        <>
            <h1>OidaScript</h1>
            <Outlet />
        </>
    );
}
