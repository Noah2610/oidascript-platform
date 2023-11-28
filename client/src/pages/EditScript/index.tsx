import { useEffect, useMemo, useState } from "react";
import { useParams } from "react-router-dom";
import { updateScript } from "../../api";
import Error from "../../components/Error";
import { OmitId, ScriptDetailsWithBody } from "../../types";
import ScriptForm from "../../components/ScriptForm";

export default function EditScript() {
    const { id: idS } = useParams();
    const id = useMemo(() => parseInt(idS ?? ""), [idS]);

    const [error, setError] = useState<string | null>(null);
    const [message, setMessage] = useState<string | null>(null);

    const onSubmit = (script: OmitId<ScriptDetailsWithBody>) => {
        updateScript(id, script).then((result) => {
            if (result.ok) {
                setMessage("Saved");
            } else {
                console.error(result.err);
                setError(result.err.message);
            }
        });
    };

    if (!Number.isFinite(id)) {
        return <Error>Expected script ID in URL path</Error>;
    }

    return (
        <>
            {error && <Error>{error}</Error>}
            {message && <Notif>{message}</Notif>}
            <ScriptForm onSubmit={onSubmit} submitLabel="Save" />
        </>
    );
}

// TODO
interface NotifProps {
    children: string;
}

function Notif({ children }: NotifProps) {
    const [isVisible, setIsVisible] = useState(true);

    useEffect(() => {
        setIsVisible(true);
        const timeoutId = setTimeout(() => setIsVisible(false), 2000);
        return () => clearTimeout(timeoutId);
    }, [children]);

    if (!isVisible) {
        return null;
    }

    return <div>{children}</div>;
}
