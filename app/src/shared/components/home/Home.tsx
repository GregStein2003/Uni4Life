import { useState } from "react";
import { Box } from "@mui/system";
import Loader from "../../../images/loader.gif"
import { LayoutBaseDefault } from "../../layouts/";
import { MenuLateral } from "../menu-lateral/MenuLateral";
import { Grid } from "@mui/material";

export const Home: React.FC = () => {
    const [isLoading, setIsLoading] = useState(false);

    return (
        <LayoutBaseDefault>

            <Grid container item direction="row" spacing={2}>
                <Grid item xs={12} sm={2} sx={{ position: "relative", display: "block"}} >
                    <MenuLateral />
                </Grid>
            </Grid>



            {isLoading && (
                <Box sx={{ width: "100vw", height: "100vh", bgcolor: "white", position: "fixed", opacity: ".7", zIndex: 1000, top: 0, display: "grid", placeItems: "center" }}>
                    <img src={Loader} alt="Loader" />
                </Box>
            )}
        </LayoutBaseDefault>
    )
}