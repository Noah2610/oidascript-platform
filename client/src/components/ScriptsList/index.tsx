import { Link } from "react-router-dom";
import { ScriptDetails } from "../../types";
import ScriptListItem from "./ScriptListItem";

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
                    <ScriptListItem script={script} />
                </li>
            ))}
        </ul>
    );
}
