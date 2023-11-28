import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createScript } from "../../api";
import ScriptForm from "../../components/ScriptForm";
import Error from "../../components/Error";
import { OmitId, ScriptDetailsWithBody } from "../../types";

export default function NewScript() {
    const navigate = useNavigate();

    const [error, setError] = useState<string | null>(null);

    const onSubmit = (script: OmitId<ScriptDetailsWithBody>) => {
        createScript(script).then((result) => {
            if (result.ok) {
                navigate(`/scripts/${result.ok.id}`);
            } else {
                console.error(result.err);
                setError(result.err.message);
            }
        });
    };

    return (
        <>
            {error && <Error>{error}</Error>}
            <ScriptForm onSubmit={onSubmit} />
        </>
    );
}
