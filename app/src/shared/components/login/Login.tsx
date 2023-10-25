import { LayoutBaseLogin } from "../../layouts/";
import { Box } from "@mui/system";

interface ILoginProps {
    children: React.ReactNode;
}

export const Login: React.FC<ILoginProps> = () => {
    return (
        <LayoutBaseLogin>
            <Box display="flex" flex={1}>
                teste
            </Box>
        </LayoutBaseLogin>
    )
}