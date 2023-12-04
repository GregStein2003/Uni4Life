import * as yup from 'yup';
import { Avatar, Box, CardMedia, Icon, IconButton, InputAdornment, Paper, TextField, Typography } from "@mui/material"
import { format } from 'date-fns';
import { ContentService } from "../../services/api/formPost/FormPost";
import { useNavigate, useLocation } from 'react-router-dom';
import { VForm, VTextField, useVForm, VTextFieldComment, IVFormErrors } from "../../forms";

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
    favoritado: boolean;
    curtido: boolean;
    myPosts: boolean;
    comentarios: any;
}

let formValidationSchema = yup.object({
    comentario: yup.string().required(),
  });

export const BoxContent: React.FC<IBoxContentProps> = ({ id, autor, imagemAutor, data, descricao, link, tipoConteudo, titulo, imagem, favoritado, curtido, myPosts=false, comentarios }) => {
    const { formRef, submit } = useVForm();
    imagem = imagem ? "data:image/png;base64,"+imagem : "";
    imagemAutor = imagemAutor ? "data:image/png;base64,"+imagemAutor : "";
    const navigate = useNavigate();
    const location = useLocation();
    const endpointURL = location.pathname;

    const addFavorite = (id: string ) => {
        ContentService.addFavorite(id).then(result => {
            navigate(`"${endpointURL}"`);
        }).catch(error => {
            console.log("Error: ", error)
        })
    }

    const removeFavorite = (id: string ) => {
        ContentService.removeFavorite(id).then(result => {
            navigate(`"${endpointURL}"`);
        }).catch(error => {
            console.log("Error: ", error)
        })
    }

    const addLike = (id: string ) => {
        ContentService.addLike(id).then(result => {
            navigate(`"${endpointURL}"`);
        }).catch(error => {
            console.log("Error: ", error)
        })
    }

    const removeLike = (id: string ) => {
        ContentService.removeLike(id).then(result => {
            navigate(`"${endpointURL}"`);
        }).catch(error => {
            console.log("Error: ", error)
        })
    }

    const handleSubmit = (dados) => {
        formValidationSchema.validate(dados, { abortEarly: false }).then(dadosValidados => {
            ContentService.addComment(dadosValidados, id).then(() => {
                formRef.current?.setFieldValue('comentario', "");
                navigate(`"${endpointURL}"`);
            })
          }).catch((error: yup.ValidationError) => {
              const validationErros: IVFormErrors = {};
              error.inner.forEach(error => {
                  if (!error.path) return 
                  validationErros[error.path] = error.message;
              });
              formRef.current?.setErrors(validationErros);
          });
    }

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
                        {favoritado && !myPosts && (
                            <IconButton onClick={() => removeFavorite(id)}>
                                <Icon fontSize="large" color="error">favorite</Icon>
                            </IconButton>
                        )}

                        {!favoritado && !myPosts && (
                            <IconButton onClick={() => addFavorite(id)}>
                                <Icon fontSize="large">favorite_border</Icon>
                            </IconButton>
                        )}

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
                        {curtido && (
                            <>
                                <Box display="flex" gap={2} onClick={() => removeLike(id)}>
                                    <Box>
                                        <Icon fontSize="large">thumb_up</Icon>
                                    </Box>
                                    <span className="dashboard__icon-text">Curtido</span>  
                                </Box>
                            </>
                        )}

                        {!curtido && (
                            <>
                                <Box display="flex" gap={2} onClick={() => addLike(id)}>
                                    <Box>
                                        <Icon fontSize="large">thumb_up_off_alt</Icon>
                                    </Box>
                                    <span className="dashboard__icon-text">Curtir</span>  
                                </Box>
                            </>
                        )}

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

                <VForm ref={formRef} onSubmit={handleSubmit}>
                    <Box
                        marginTop={2}
                        display="flex"
                        alignItems="center"
                        justifyContent="space-between"
                        borderRadius={4}
                    >
                        <VTextFieldComment
                            placeholder="Insira um comentário: "
                            name="comentario"
                            fullWidth
                            disabled={false} 
                            error={false}
                            InputProps={{
                                endAdornment: (
                                <InputAdornment onClick={() => console.log("teste")} sx={{cursor: "pointer", zIndex: 99999}} position="end">
                                    <Icon fontSize="large">send</Icon>
                                </InputAdornment>
                                ),
                            }}
                        />
                    </Box>

                    {console.log(comentarios)}
                    
                    {comentarios.map(comentario => ( 
                        <Box component={Paper} display="flex" alignItems="center" padding={1} justifyContent="flex-start" columnGap={1} marginY={2} elevation={3} key={comentario.id} sx={{ cursor: "pointer" }} onClick={() => navigate(`/profile/${comentario.idAutor}`)}>
                            <Box>
                                <Avatar alt="Icone" src={"data:image/png;base64,"+comentario.imagemAutor} sx={{ width: 50, height: 50 }}>{autor[0]}</Avatar>
                            </Box>
                            <Box>
                                <span className="dashboard__name-comment">{comentario.autor}</span>
                                <span className="dashboard__description-comment">{comentario.descricao}</span>
                            </Box>
                        </Box>
                    ))}                    
                </VForm>
            </Box>
        </>
    )
}