interface ErrorProps {
    children: JSX.Element | React.ReactNode;
}

export default function ErrorComp({ children }: ErrorProps) {
    return <div className="error">{children}</div>;
}
