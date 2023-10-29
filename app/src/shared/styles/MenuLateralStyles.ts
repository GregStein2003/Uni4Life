import { makeStyles } from '@material-ui/core/styles';

export const MenuLateralStyles = makeStyles(() => ({
    menuLateralItem: {
      color: 'white',
      fontSize: "4rem",
      '& span': { 
        fontSize: "2rem",
        textCenter: "left",
        fontFamily: "Jost",
        letterSpacing: ".1rem"
      }
    },
    menuLateralIcon: {
      '& span': { 
        fontSize: "6rem",
      }

    }
}));