export interface LoginData {
    username: string;
    password: string;
}

export type Result<T, E = string> = {
    ok: T,
    err?: undefined,
} | {
    ok?: undefined,
    err: E,
}
