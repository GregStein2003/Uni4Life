import { Drawer, Icon, List, ListItemButton, ListItemIcon, ListItemText, Paper, useMediaQuery, useTheme } from "@mui/material"
import { Box } from "@mui/system";
import { useAppDrawerContext, useAppThemeContext, useAuthContext } from "../../contexts";
import { useLocation, useNavigate } from "react-router-dom";
import { MenuLateralStyles } from "../../styles"

interface IListItemLinkProps {
    to: string; // Link de redirecionamento
    label: string;
    icon: string;
    onClick: (() => void) | undefined;
}

const ListItemLink: React.FC<IListItemLinkProps> = ({ to, label, icon, onClick }) => {
    const classes = MenuLateralStyles();
    const navigate = useNavigate();
    const location = useLocation();

    const handleClick = () => {
        navigate(to);
        onClick?.(); // Se nao for undefined, executa a funcao
    }

    const selectItemMenu = location.pathname === to ? true : false;

    return (
        <ListItemButton selected={selectItemMenu} onClick={handleClick}>
            <ListItemIcon>
                <Icon sx={{ fontSize:"3rem",  color: "white", minWidth: "100%", marginRight: "12px" }}>{icon}</Icon>
            </ListItemIcon>
            <ListItemText primary={label} className={classes.menuLateralItem}/>    
        </ListItemButton>
    )
}

export const MenuLateral: React.FC = () => {
    const classes = MenuLateralStyles();
    const theme = useTheme();
    const smDown = useMediaQuery(theme.breakpoints.down("sm"));

    const { isDrawerOpen, toggleDrawerOpen, drawerOptions } = useAppDrawerContext();
    const { toggleTheme } = useAppThemeContext();
    const { logout } = useAuthContext();

    return (
        <>
            <Drawer open={isDrawerOpen} variant={smDown ? "temporary" : "permanent"} onClose={toggleDrawerOpen}>
                <Box width="100%">
                    <List component="nav" sx={{ display: "flex", flexDirection: "column", rowGap: 1, width: "100%" }}>
                        {drawerOptions.map(drawerOption => (
                            <ListItemLink 
                                to={drawerOption.path}
                                key={drawerOption.path}
                                icon={drawerOption.icon }
                                label={drawerOption.label }
                                onClick={smDown ? toggleDrawerOpen : undefined} 
                            />
                        ))}
                        <ListItemButton onClick={toggleTheme} sx={{ display: "flex", alignItems: "center" }}>
                            <ListItemIcon>
                                <Icon sx={{ fontSize:"3rem",  color: "white", minWidth: "100%", marginRight: "12px" }}>dark_mode</Icon>
                            </ListItemIcon>
                            <ListItemText primary="Alternar tema" className={classes.menuLateralItem}/>    
                        </ListItemButton>
                        <ListItemButton onClick={logout}>
                            <ListItemIcon>
                                <Icon sx={{ fontSize:"3rem",  color: "white", minWidth: "100%", marginRight: "12px" }}>logout</Icon>
                            </ListItemIcon>
                            <ListItemText primary="Sair" className={classes.menuLateralItem} />    
                        </ListItemButton>
                    </List>
                </Box>

            </Drawer>
        </>
    );
};