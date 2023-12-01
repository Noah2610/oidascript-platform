import {
    LoginData,
    OmitId,
    Result,
    ScriptDetails,
    ScriptDetailsWithBody,
} from "../types";

const BASE_URL = import.meta.env.VITE_BACKEND_URL;

export interface ResponseError<T> {
    ok: boolean;
    status: number;
    statusText: string;
    body: T;
    message: string;
}

export interface RegisterResponseBody {
    id: number;
    username: string;
    passwordHash: string;
}

// TODO
export interface LoginResponseBody {
    username: string;
}

export interface GetSessionResponseBody {
    username: string;
}

export interface GetUserScriptsResponseBody {
    scriptDetails: ScriptDetails[];
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
): Promise<Result<LoginResponseBody, ResponseError<string>>> {
    const res = await fetchApi("/sessions", {
        method: "POST",
        body: JSON.stringify(login),
    });

    if (res.ok) {
        const ok = (await res.json()) as LoginResponseBody;
        return { ok };
    }

    return {
        err: await newResponseError(res, "text", "Failed to fetch login"),
    };
}

export async function getSession(): Promise<
    Result<GetSessionResponseBody, ResponseError<string>>
> {
    const res = await fetchApi("/sessions");
    if (res.ok) {
        return { ok: await res.json() };
    }

    return {
        err: await newResponseError(res, "text", "Failed to fetch session"),
    };
}

export async function logout(): Promise<Result<string, ResponseError<string>>> {
    const res = await fetchApi("/sessions", { method: "DELETE" });
    if (res.ok) {
        return { ok: await res.text() };
    }
    return { err: await newResponseError(res, "text", "Failed to logout") };
}

export async function getUserScripts(): Promise<
    Result<GetUserScriptsResponseBody, ResponseError<string>>
> {
    const res = await fetchApi("/scripts");
    if (res.ok) {
        return { ok: await res.json() };
    }
    return {
        err: await newResponseError(res, "text", "Failed to get user scripts"),
    };
}

export async function getScript(
    id: number,
): Promise<Result<ScriptDetailsWithBody, ResponseError<string>>> {
    const res = await fetchApi(`/scripts/${id}`);
    if (res.ok) {
        return { ok: await res.json() };
    }
    return {
        err: await newResponseError(res, "text", "Failed to get user script"),
    };
}

export async function createScript(
    script: OmitId<ScriptDetailsWithBody>,
): Promise<Result<ScriptDetailsWithBody, ResponseError<string>>> {
    const res = await fetchApi("/scripts", {
        method: "POST",
        body: JSON.stringify(script),
    });
    if (res.ok) {
        return { ok: await res.json() };
    }
    return {
        err: await newResponseError(res, "text", "Failed to create new script"),
    };
}

export async function updateScript(
    id: number,
    script: OmitId<ScriptDetailsWithBody>,
): Promise<Result<ScriptDetails, ResponseError<string>>> {
    const res = await fetchApi(`/scripts/${id}`, {
        method: "PATCH",
        body: JSON.stringify(script),
    });
    if (res.ok) {
        return { ok: await res.json() };
    }
    return {
        err: await newResponseError(res, "text", "Failed to update script"),
    };
}

export async function deleteScript(
    id: number,
): Promise<Result<string, ResponseError<string>>> {
    const res = await fetchApi(`/scripts/${id}`, { method: "DELETE" });
    if (res.ok) {
        return { ok: await res.text() };
    }
    return {
        err: await newResponseError(res, "text", "Failed to delete script"),
    };
}

function fetchApi(endpoint: string, options: RequestInit = {}) {
    const getUrl = (path: string) => BASE_URL + path;
    return fetch(getUrl(endpoint), {
        credentials: "include",
        ...options,
        headers: {
            "Content-Type": "application/json",
            ...options.headers,
        },
    });
}

async function newResponseError<T>(
    response: Response,
    bodyFormat: "text" | "json",
    message: string,
): Promise<ResponseError<T>> {
    const { ok, status, statusText } = response;
    return {
        ok,
        status,
        statusText,
        body: await response[bodyFormat](),
        message,
    };
}
