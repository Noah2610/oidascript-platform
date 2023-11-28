import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Layout from "./components/Layout";
import EditScript from "./pages/EditScript";
import Login from "./pages/Login";
import NewScript from "./pages/NewScript";
import Playground from "./pages/Playground";
import Register from "./pages/Register";
import Scripts from "./pages/Scripts";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Layout />,
        children: [
            {
                path: "/playground",
                element: <Playground />,
            },
            {
                path: "/register",
                element: <Register />,
            },
            {
                path: "/login",
                element: <Login />,
            },
            {
                path: "/scripts",
                element: <Scripts />,
            },
            {
                path: "/scripts/new",
                element: <NewScript />,
            },
            {
                path: "/scripts/:id",
                element: <EditScript />,
            },
        ],
    },
]);

function App() {
    return (
        <>
            <RouterProvider router={router} />
        </>
    );
}

export default App;
