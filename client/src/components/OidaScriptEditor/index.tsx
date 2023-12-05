import { Editor } from "@monaco-editor/react";
import { editor as editorModule } from "monaco-editor";
import { useRef, useState } from "react";
import { run as realRunOida } from "oidascript";
import { Result } from "oidascript/result";
import { LangError } from "oidascript/langError";
import {
    createMonacoLanguage,
    setupEditorActions,
    setupVim,
} from "./createMonacoLanguage";

import styles from "./editor.module.css";

window.addEventListener("load", () => {
    createMonacoLanguage();
});

function runOida(code: string): Result<string, LangError> {
    const origLog = console.log;

    const output: string[] = [];

    console.log = (...msgs: string[]) => {
        output.push(...msgs);
    };

    const result = realRunOida(code);

    if (result.isErr()) {
        return Result.err(result.getError()!);
    }

    console.log = origLog;

    return Result.ok(output.join("\n"));
}

interface OidaScriptEditorProps {
    body?: string;
    editorRef?: React.MutableRefObject<
        editorModule.IStandaloneCodeEditor | undefined
    >;
}

export default function OidaScriptEditor({
    body,
    editorRef: editorRefProp,
}: OidaScriptEditorProps) {
    body ??= 'zeig "Hallo Welt!" an!';

    const editorRef = useRef<editorModule.IStandaloneCodeEditor>();
    const [output, setOutput] = useState("");

    const vimStatusRef = useRef<HTMLElement | null>(null);

    function onRun() {
        const editor = editorRef.current;
        if (!editor) return;

        const code = editor.getValue();
        const result = runOida(code);

        if (result.isOk()) {
            setOutput(result.getValue()!);
        } else {
            setOutput(result.getError()!.display());
        }
    }

    function handleSetupVim() {
        if (!editorRef.current || !vimStatusRef.current) return;
        setupVim(editorRef.current, vimStatusRef.current);
    }

    return (
        <>
            <div>
                <button onClick={onRun}>Run</button>
            </div>

            <textarea value={output} readOnly />

            <Editor
                language="oidascript"
                theme="oidascriptTheme"
                className={styles.editor}
                defaultValue={body}
                onMount={(editor) => {
                    editorRef.current = editor;
                    if (editorRefProp) {
                        editorRefProp.current = editor;
                    }
                    setupEditorActions(editor, onRun);
                    handleSetupVim();
                }}
            />

            <code
                ref={(el) => (vimStatusRef.current = el) && handleSetupVim()}
            ></code>
        </>
    );
}
