import { getSession } from "../api";
import { useStore } from "../store";

let didFetchSession = false;

export function useUser() {
    const user = useStore(({ user }) => user);
    const setUser = useStore(({ setUser }) => setUser);

    if (!didFetchSession) {
        didFetchSession = true;
        getSession().then((result) =>
            result.ok
                ? setUser(result.ok)
                : console.error("[useUser error]", result.err),
        );
    }

    return user;
}
