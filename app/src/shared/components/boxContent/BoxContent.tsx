import { Avatar, Box, CardMedia, Icon, IconButton, InputAdornment, Paper, TextField, Typography } from "@mui/material"
import { format } from 'date-fns';

interface IBoxContentProps {
    id: string;
    autor: string;
    imagemAutor: string;
    data: string;
    descricao: string;
    link?: string;
    tipoConteudo: string;
    titulo: string;
    imagem: string;
}

export const BoxContent: React.FC<IBoxContentProps> = ({ id, autor, imagemAutor, data, descricao, link, tipoConteudo, titulo, imagem  }) => {
    imagem = imagem ? "data:image/png;base64,"+imagem : "";
    imagemAutor = imagemAutor ? "data:image/png;base64,"+imagemAutor : "";

    const iconTypeContent = () => {
        switch (tipoConteudo) {
          case 'LIVRO':
            return "auto_stories";
          case 'PODCAST':
            return "keyboard_voice";
          case 'VIDEO':
            return "ondemand_video";
        case 'TEXTO':
            return "text_fields";
          default:
            return "";
        }
      };
    
    return (
        <>
            <Box
                position="relative"
                gap={1} 
                paddingY={3}
                paddingX={3}
                component={Paper}
                marginBottom={2}
            >                
                <Box display="flex" justifyContent="space-between" alignContent="flex-start">
                    <Box display="flex" alignItems="center" justifyContent="center" columnGap={1} marginBottom={1}>
                        <Box >
                            <Avatar alt="Remy Sharp" src={imagemAutor} sx={{ width: 50, height: 50 }}>{autor[0]}</Avatar>
                        </Box>
                        <Box>
                            <span className="dashboard__name">{autor}</span>
                            <span className="dashboard__data">{format(new Date(data), "dd/MM/yyyy")}</span>
                        </Box>
                    </Box>
                    <Box>
                        <IconButton onClick={() => console.log("teste")}>
                            <Icon fontSize="large" color="error">favorite</Icon>
                        </IconButton>
                        <IconButton onClick={() => console.log("teste")}>
                            <Icon fontSize="large">favorite_border</Icon>
                        </IconButton>
                        <IconButton onClick={() => console.log("teste")}>
                            <Icon fontSize="large">timeline</Icon>
                        </IconButton>
                        <IconButton>
                            <Icon fontSize="large" className="dashboard__icon">{iconTypeContent()}</Icon>
                        </IconButton>
                    </Box>
                </Box>

                <Box marginBottom={2}>
                    <Typography gutterBottom variant="h3">
                        {titulo}
                    </Typography>
                    <Typography variant="body1" paddingBottom={2}>
                        {descricao}
                    </Typography>


                    {imagem && (
                        <CardMedia
                            component="img"
                            height="100%"
                            sx={{ objectFit: "contain", maxHeight: "300px" }}
                            image={imagem}
                            alt="Imagem"
                        />
                    )}

                    {tipoConteudo == "Video" && link && (
                        <iframe width="100%" height="300" src={link} title={titulo} frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
                    )}
                </Box>

                <Box className="dashboard__icons-container">
                    <Box className="dashboard__icon-item">
                        <Box>
                            <Icon fontSize="large">thumb_up_off_alt</Icon>
                            <Icon fontSize="large" sx={{display: "none"}}>thumb_up_alt</Icon>
                        </Box>
                        <span className="dashboard__icon-text">Curtir</span>  
                    </Box>

                    <Box className="dashboard__icon-item">
                        <Box>
                            <Icon fontSize="large">chat</Icon>
                        </Box>
                        <span className="dashboard__icon-text">Comentário</span>  
                    </Box>

                    <Box className="dashboard__icon-item">
                        <Box>
                            <Icon fontSize="large">reply</Icon>
                        </Box>
                        <span className="dashboard__icon-text">Compartilhar</span>  
                    </Box>
                </Box>

                <Box
                    marginTop={2}
                    display="flex"
                    alignItems="center"
                    justifyContent="space-between"
                    borderRadius={4}
                >
                    <TextField 
                        size="small"
                        fullWidth
                        placeholder="Insira um comentário"
                        value=""
                        InputProps={{
                            endAdornment: (
                            <InputAdornment sx={{cursor: "pointer"}} position="end">
                                <Icon fontSize="large">send</Icon>
                            </InputAdornment>
                            ),
                        }}
                        onChange={(e) => console.log(e)}/>
                </Box>
            </Box>
        </>
    )
}