import { loader } from "@monaco-editor/react";

export function createMonacoLanguage() {
    loader.init().then((monaco) => {
        monaco.languages.register({ id: "oidascript" });
        monaco.languages.setMonarchTokensProvider("oidascript", {
            tokenizer: {
                root: [
                    [/\bdie\s+Funktion\b/, "functionDefinition"],
                    [/\bmacht\b/, "functionDefinition"],
                    [/\bund endet hier\b/, "functionDefinition"],
                    [/\b(der|die|das)\b/, "variableDefinition"],
                    [/ ist /, "variableDefinitionAssign"],
                    [/"[^"]+"/, "string"],
                    ["!", "exclaim"],
                    [/\bgib\s/, "return"],
                    [/\szurück\b/, "return"],
                    [/\bnix\b/, "null"],
                ],
            },
        });
        monaco.editor.defineTheme("oidascriptTheme", {
            base: "vs-dark",
            inherit: true,
            rules: [
                {
                    token: "functionDefinition",
                    foreground: "ff005f",
                },
                {
                    token: "variableDefinition",
                    foreground: "5ed7ff",
                },
                {
                    token: "variableDefinitionAssign",
                    foreground: "5ed7ff",
                },
                {
                    token: "string",
                    foreground: "d4d787",
                },
                {
                    token: "exclaim",
                    foreground: "6c6c6c",
                },
                {
                    token: "return",
                    foreground: "ff005f",
                },
                {
                    token: "null",
                    foreground: "ff005f",
                },
            ],
            colors: {},
        });
    });
}
