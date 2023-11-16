import { useStore } from "../store";

export function useUser() {
    return useStore(({ user }) => user);
}
