import { useRef, useState } from "react";
import { editor as editorTypes } from "monaco-editor";
import { OmitId, ScriptDetailsWithBody } from "../../types";
import OidaScriptEditor from "../OidaScriptEditor";

interface ScriptFormProps {
    script?: Partial<ScriptDetailsWithBody>;
    onSubmit?: (script: OmitId<ScriptDetailsWithBody>) => void;
    submitLabel?: string;
}

export default function ScriptForm({
    script,
    onSubmit,
    submitLabel = "Submit",
}: ScriptFormProps) {
    const [name, setName] = useState(script?.name ?? "");

    const editorRef = useRef<editorTypes.IStandaloneCodeEditor>();

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (onSubmit) {
            const body = editorRef.current?.getValue() ?? "";
            const newScript = { name, body };
            onSubmit(newScript);
        }
    };

    return (
        <form action="#" onSubmit={handleSubmit}>
            <label>
                Name{" "}
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
            </label>

            <OidaScriptEditor body={script?.body} editorRef={editorRef} />

            <button type="submit">{submitLabel}</button>
        </form>
    );
}
