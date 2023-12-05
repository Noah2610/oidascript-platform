import * as monaco from "monaco-editor";
import { loader } from "@monaco-editor/react";
import { initVimMode } from "monaco-vim";

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

export function setupVim(
    editor: monaco.editor.IStandaloneCodeEditor,
    statusEl: HTMLElement,
) {
    const state = {
        vimMode: null as any,
        isEnabled: false,
    };

    function toggleVim() {
        state.isEnabled = !state.isEnabled;
        if (state.isEnabled) {
            enableVim();
        } else {
            disableVim();
        }
    }

    function enableVim() {
        state.vimMode = initVimMode(editor, statusEl);
    }

    function disableVim() {
        state.vimMode?.dispose();
    }

    editor.addAction({
        id: "toggle-vim-mode",
        label: "Toggle VIM mode",
        keybindings: [monaco.KeyMod.CtrlCmd | monaco.KeyCode.KeyJ],
        run: toggleVim,
    });
}

export function setupEditorActions(
    editor: monaco.editor.IStandaloneCodeEditor,
    onRun: () => void,
) {
    editor.addAction({
        id: "run-oidascript",
        label: "Run Oidascript",
        keybindings: [monaco.KeyMod.CtrlCmd | monaco.KeyCode.Enter],
        run: onRun,
    });
}
