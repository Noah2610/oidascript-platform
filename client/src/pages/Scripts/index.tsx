import { useEffect, useState } from "react";
import { getUserScripts } from "../../api";
import Error from "../../components/Error";
import Loading from "../../components/Loading";
import ScriptsList from "../../components/ScriptsList";
import { ScriptDetails } from "../../types";

export default function Scripts() {
    const [scripts, setScripts] = useState<ScriptDetails[] | null>(null);

    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        getUserScripts().then((result) => {
            if (result.ok) {
                setScripts(result.ok);
            } else {
                console.error("[getUserScripts error]", result.err);
                setError(result.err.message);
            }
        });
    }, []);

    return (
        <>
            <h1>My Scripts</h1>

            {error && <Error>{error}</Error>}

            {scripts ? <ScriptsList scripts={scripts} /> : <Loading />}
        </>
    );
}
