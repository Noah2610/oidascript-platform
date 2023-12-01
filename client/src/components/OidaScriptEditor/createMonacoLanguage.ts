import { loader } from "@monaco-editor/react";

export function createMonacoLanguage() {
    loader.init().then((monaco) => {
        monaco.languages.register({ id: "oidascript" });
        monaco.languages.setMonarchTokensProvider("oidascript", {
            tokenizer: {
                root: [
                    [/die\s+Funktion\s+.+? macht/, "functionDefinition"],
                    [/(der|die|das)\s+.+? ist /, "variableDefinition"],
                ],
            },
        });
        monaco.editor.defineTheme("oidascriptTheme", {
            base: "vs-dark",
            inherit: true,
            rules: [
                {
                    token: "functionDefinition",
                    foreground: "ff0000",
                },
                {
                    token: "variableDefinition",
                    foreground: "0000ff",
                },
            ],
            colors: {},
        });
    });
}
