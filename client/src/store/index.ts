import { create } from "zustand";

interface UserName {
    username: string;
}

export interface Store {
    user: UserName | null;

    setUser: (user: UserName) => void;
}

export const useStore = create<Store>((set, get) => ({
    user: null,
    setUser: (user) => set({ user }),
}));
