import { LoginData, Result } from "../types";

const BASE_URL = import.meta.env.VITE_BACKEND_URL;

export interface RegisterResponseBody {
    id: number;
    username: string;
    passwordHash: string;
}

// TODO
export interface LoginResponseBody {
    username: string
}

export async function register(
    login: LoginData,
): Promise<Result<RegisterResponseBody, string>> {
    const res = await fetchApi("/users", {
        method: "POST",
        body: JSON.stringify(login),
    });

    if (res.ok) {
        const ok = (await res.json()) as RegisterResponseBody;
        return { ok };
    }

    const err = await res.text();
    return { err };
}

export async function login(
    login: LoginData,
): Promise<Result<LoginResponseBody, string>> {
    // TODO
    const res = await fetchApi("/sessions", {
        method: "POST",
        body: JSON.stringify(login),
    });

    if (res.ok) {
        const ok = (await res.json()) as LoginResponseBody;
        return { ok };
    }

    const err = await res.text();
    return { err };
}

function fetchApi(endpoint: string, options: RequestInit = {}) {
    const getUrl = (path: string) => BASE_URL + path;
    return fetch(getUrl(endpoint), {
        ...options,
        headers: {
            "Content-Type": "application/json",
            ...options.headers,
        },
    });
}
