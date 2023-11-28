import { Link } from "react-router-dom";
import { ScriptDetails } from "../../types";

interface ScriptsListProps {
    scripts: ScriptDetails[];
}

export default function ScriptsList({ scripts }: ScriptsListProps) {
    if (scripts.length === 0) {
        return (
            <p>
                No scripts, <Link to="/scripts/new">create new script</Link>
            </p>
        );
    }

    return (
        <ul>
            {scripts.map((script, i) => (
                <li key={i}>
                    <Link to={`/scripts/${script.id}`}>{script.name}</Link>
                </li>
            ))}
        </ul>
    );
}
