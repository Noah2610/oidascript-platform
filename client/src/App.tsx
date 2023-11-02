import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Layout from "./components/Layout";
import Playground from "./pages/Playground";
import Register from "./pages/Register";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Layout />,
        children: [
            {
                path: "/playground",
                element: <Playground />
            },
            {
                path: "/register",
                element: <Register />
            }
        ]
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
