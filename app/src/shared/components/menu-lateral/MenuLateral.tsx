import "../../styles/MenuLateral.css"
import { Drawer, Icon, List, ListItemButton, ListItemIcon, ListItemText, useMediaQuery, useTheme } from "@mui/material"
import { Box } from "@mui/system";
import { useAppDrawerContext, useAppThemeContext, useAuthContext } from "../../contexts";
import { useLocation, useNavigate } from "react-router-dom";

interface IListItemLinkProps {
    to: string; // Link de redirecionamento
    label: string;
    icon: string;
    onClick: (() => void) | undefined;
}

const ListItemLink: React.FC<IListItemLinkProps> = ({ to, label, icon, onClick }) => {
    const navigate = useNavigate();
    const location = useLocation();

    const handleClick = () => {
        navigate(to);
        onClick?.(); // Se nao for undefined, executa a funcao
    }

    const selectItemMenu = location.pathname === to ? true : false;

    return (
        <ListItemButton selected={selectItemMenu} className="menuLateralItem" onClick={handleClick}>
            <ListItemIcon>
                <Icon sx={{ fontSize:"3rem",  color: "white", minWidth: "100%", marginRight: "12px" }}>{icon}</Icon>
            </ListItemIcon>
            <ListItemText primary={label} className="menuLateralItemText"/>    
        </ListItemButton>
    )
}

export const MenuLateral: React.FC = () => {
    const theme = useTheme();
    const smDown = useMediaQuery(theme.breakpoints.down("md"));

    const { isDrawerOpen, toggleDrawerOpen, drawerOptions } = useAppDrawerContext();
    const { toggleTheme } = useAppThemeContext();
    const { logout } = useAuthContext();

    return (
        <>
            <Drawer open={isDrawerOpen} variant={smDown ? "temporary" : "permanent"} onClose={toggleDrawerOpen} className="menuLateral">
                <Box width="100%">
                    <List component="nav" sx={{ display: "flex", flexDirection: "column", rowGap: 1, width: "100%" }} className="menuLateralNav">
                        {drawerOptions.map(drawerOption => (
                            <ListItemLink 
                                to={drawerOption.path}
                                key={drawerOption.path}
                                icon={drawerOption.icon }
                                label={drawerOption.label }
                                onClick={smDown ? toggleDrawerOpen : undefined} 
                            />
                        ))}
                        <ListItemButton onClick={toggleTheme} sx={{ display: "flex", alignItems: "center" }} className="menuLateralItem">
                            <ListItemIcon>
                                <Icon sx={{ fontSize:"3rem",  color: "white", minWidth: "100%", marginRight: "12px" }}>dark_mode</Icon>
                            </ListItemIcon>
                            <ListItemText primary="Alternar tema" className="menuLateralItemText"/>    
                        </ListItemButton>
                        <ListItemButton onClick={logout} className="menuLateralItem">
                            <ListItemIcon>
                                <Icon sx={{ fontSize:"3rem",  color: "white", minWidth: "100%", marginRight: "12px" }}>logout</Icon>
                            </ListItemIcon>
                            <ListItemText primary="Sair" className="menuLateralItemText" />    
                        </ListItemButton>
                    </List>
                </Box>
            </Drawer>
        </>
    );
};