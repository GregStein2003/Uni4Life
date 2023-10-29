import { createTheme } from "@mui/material";
import { yellow, cyan, grey } from "@mui/material/colors";

export let DarkTheme = createTheme({
    palette: {
        mode: "dark",
        primary: {
            main: grey[700],
            dark: grey[800],
            light: grey[500],
            contrastText: "#FFFFFF", 
        },
        secondary: {
            main: cyan[500],
            dark: cyan[400],
            light: cyan[300],
            contrastText: "#FFFFFF", 
        },
        background: {
            default: "#202124",
            paper: "rgb(244, 244, 244)",
        }
    },
    typography: {
        allVariants: {
            color: "white"
        },
        h1: {
            fontSize: "6rem",
            fontFamily: "Jost",
            fontWeight: 700,
        },
        h2: {
            fontSize: "5rem",
            fontFamily: "Jost",
            fontWeight: 700,
        },
        h3: {
            fontSize: "3rem",
            fontFamily: "Jost",
            fontWeight: 400,
        },
        body1: { // <p></p>
            fontSize: "1.6rem",
            fontFamily: "Roboto",
            fontWeight: 400
        },
        button: {
            fontSize: "1.6rem",
            borderRadius: 2,
            bgcolor:"#262d63",
            '&:hover': { backgroundColor: "#214099" }
        },
        body2: { // <a></a>
            fontSize: "1.4rem",
            paddingBottom: ".6rem",
            textDecoration: "underline",
            fontFamily: "Roboto",
            fontWeight: 400,
            color: "#000000",
            cursor: "pointer"
        }
    }
});

DarkTheme = createTheme(DarkTheme, {
    components: {
        MuiFormHelperText: {
            styleOverrides: {
              root: {
                    fontSize: "1.5rem",
                    marginLeft: 0
                }
            }
        },
        MuiDrawer: {
            styleOverrides: {
                paper: {
                    position: 'absolute',
                    border: "none",
                    height: "auto",
                    width: "100%"
                },
            },
        },
        MuiListItemButton: {
            styleOverrides: {
              root: {
                backgroundColor: DarkTheme.palette.background.default,
                borderRadius: '40px',
                '&:hover': { backgroundColor: "#214099" },
                color: "white",
                fontSize: "3rem"
              },
            },
        },
      }
})