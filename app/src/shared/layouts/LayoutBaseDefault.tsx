import { useState } from "react";
import { Box } from "@mui/system";
import { Grid } from "@mui/material";
import "../styles/LayoutBaseDefault.css"
import Loader from "../../images/loader.gif"
import { useAppDrawerContext } from "../contexts";
import Brand from "../../images/brand-unisinos.png"
import { MenuLateral } from "../components/menu-lateral/MenuLateral";
import { Typography, useTheme, IconButton, Icon } from "@mui/material";
import { FollowItem } from "../components";

interface ILayoutBaseDefaultProps {
    children: React.ReactNode;
}

export const LayoutBaseDefault: React.FC<ILayoutBaseDefaultProps> = ({ children }) => {
    const theme = useTheme();
    const [isLoading, setIsLoading] = useState(false);

    const { toggleDrawerOpen } = useAppDrawerContext();

    return (
        <>
            <Box height={"10rem"} color="white" display="block" position="relative" top="0" flexDirection="column" gap={1} bgcolor={theme.palette.background.default}>
                <Box display="flex" alignItems="center" justifyContent="center" paddingY={2}>
                    <Typography variant="h1">Uni4Life</Typography>
                </Box>
                            
                <Box className="layout-base-default__menu-container">
                    <IconButton onClick={toggleDrawerOpen}>
                        <Icon className="layout-base-default__menu">menu</Icon>
                    </IconButton>
                </Box>
            </Box>
            <Grid className="grid-wrapper" container item direction="row" spacing={2}>
                <Grid item xs={12} md={3}>
                    <Box className="menuLateralWrapper">
                        <MenuLateral />
                    </Box>
                </Grid>
                <Grid item xs={12} md={5} sx={{ paddingLeft: 0 }} className="teste">
                    {children}
                </Grid>
                <Grid item xs={12} md={4} display="flex" alignItems="flex-start" justifyContent="center">
                    <FollowItem  />
                </Grid>
            </Grid>

            {isLoading && (
                <Box sx={{ width: "100vw", height: "100vh", bgcolor: "white", position: "fixed", opacity: ".7", zIndex: 1000, top: 0, display: "grid", placeItems: "center" }}>
                    <img src={Loader} alt="Loader" />
                </Box>
            )}
        </>
    )
}