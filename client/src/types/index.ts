export interface LoginData {
    username: string;
    password: string;
}

export type Result<T, E = string> =
    | {
          ok: T;
          err?: undefined;
      }
    | {
          ok?: undefined;
          err: E;
      };

export interface ScriptDetails {
    id: number;
    name: string;
}

export type ScriptDetailsWithBody = ScriptDetails & {
    body: string;
};

export type OmitId<T> = Omit<T, "id">;
