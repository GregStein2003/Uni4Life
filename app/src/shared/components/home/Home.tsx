import { LayoutBaseDefault } from "../../layouts/";
import { Dashboard } from "../dashboard/Dashboard";

export const Home: React.FC = () => {
    return (
        <LayoutBaseDefault>
            <Dashboard />
        </LayoutBaseDefault>
    )
}