import moment from 'moment';
import "../../styles/Profile.css"
import { Avatar, Box, Button, Icon, Typography } from "@mui/material";

interface IFormRegisterData {
    dataNascimento: string,
    dataRelacionamento: string,
    email: string,
    id: string,
    imagem: string,
    nome: string,
    registroAcademico: number,
    segmento: string,
    seguido: boolean,
    seguidores: number,
    telefone: string,
    tipoConta: string,
    myAccount: boolean
}

export const UserItem: React.FC<IFormRegisterData> = ({ id, dataNascimento, email, imagem, nome, registroAcademico, segmento, seguido, seguidores, telefone, tipoConta, myAccount=null }) => {
    console.log(imagem)
    const inputDate = moment(dataNascimento, "YYYY-MM-DD");
    const formattedDateStr = inputDate.format("DD/MM/YYYY");
    const avatarImage = "data:image/png;base64,"+imagem;

    const buttonSubmit = {
        backgroundColor: "#262d63",
        display: "flex",
        justifyContent: "center",
        '&:hover': {
          backgroundColor: '#262d63',
        }
    };
  
    return (
        <Box display="flex" alignItems="center" flexDirection="column" justifyContent="space-between" sx={{ cursor: "pointer" }} rowGap={1}>
            <Box>
               <Avatar alt="Remy Sharp" src={avatarImage} sx={{ width: 120, height: 120 }}>{}</Avatar>
            </Box>
            <Box>
               <span className="profile__name">{nome}</span>
            </Box>
            <Box>
               <span className="profile__email">{email}</span>
            </Box>
            <Box>
                <span className="profile__register">{registroAcademico}</span>
            </Box>
            <Box>
                <span className="profile__data">{formattedDateStr}</span>
            </Box>

            {seguido && !myAccount && (
                <Button variant="contained" sx={buttonSubmit} onClick={() => {}} endIcon={<Icon>bookmark</Icon>}>
                    Seguido
                </Button>
            )}

            {!seguido && !myAccount && (
                <Button variant="contained" sx={buttonSubmit} onClick={() => {}} endIcon={<Icon>bookmark_border</Icon>}>
                    Seguir
                </Button>
            )}

            <Typography variant="caption">Seguidores: <strong>{seguidores}</strong></Typography>
        </Box>
    )
}
