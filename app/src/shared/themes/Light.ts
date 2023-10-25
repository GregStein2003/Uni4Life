import { createTheme } from "@mui/material";
import { yellow, cyan, blue } from "@mui/material/colors";

export const LightTheme = createTheme({
    palette: {
        primary: {
            main: blue[700],
            dark: blue[800],
            light: blue[500],
            contrastText: "#FFFFFF", 
        },
        secondary: {
            main: cyan[500],
            dark: cyan[400],
            light: cyan[300],
            contrastText: "#FFFFFF", 
        },
        background: {
            default: "#214099",
            paper: "white",
        }
    },
    typography: {
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
            fontSize: "1.6rem"
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