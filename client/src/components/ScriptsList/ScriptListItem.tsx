import { useState } from "react";
import { Link } from "react-router-dom";
import { deleteScript } from "../../api";
import { ScriptDetails } from "../../types";
import Error from "../Error";

interface ScriptListItemProps {
    script: ScriptDetails;
}

export default function ScriptListItem({ script }: ScriptListItemProps) {
    const [isDeleted, setIsDeleted] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleDelete = () => {
        deleteScript(script.id).then((result) => {
            if (result.ok) {
                setIsDeleted(true);
            } else {
                console.error(result.err);
                setError("Failed to delete script");
            }
        });
    };

    if (error) {
        return <Error>{error}</Error>;
    }

    if (isDeleted) {
        return <>Deleted script</>;
    }

    return (
        <>
            <button onClick={handleDelete}>Delete</button>
            <Link to={`/scripts/${script.id}`}>{script.name}</Link>
        </>
    );
}
