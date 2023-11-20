import moment from 'moment';
import { Box } from "@mui/system";
import "../../styles/default-styles.css"
import { AutoComplete } from "./AutoComplete";
import { VTextField, VForm, VTextTelMask, VTextDateMask, VTextUpload } from "../../forms/";
import { Button, CardActions, CardContent, Grid, Icon, Paper, Typography } from "@mui/material";
import { useEffect } from "react";
import { ProfileService } from "../../services/api/profile/ProfileService";


interface IWelcomeProps {
    formRef: any;
    handleAction: any;
    isLoading: boolean;
    setIsRegister: () => void;
    submit: () => void;
    update: boolean;
    handleClose: () => void;
}

export const Register: React.FC<IWelcomeProps> = ({ formRef, handleAction, isLoading, setIsRegister, submit, update, handleClose}) => {
    const textButton = update ? "Salvar" : "Concluir";

    useEffect(() => {
        if(update){
            ProfileService.getUser().then(result => {
                formRef.current?.setFieldValue('nome', result.nome);
                formRef.current?.setFieldValue('imagem', result.imagem);
                formRef.current?.setFieldValue('email', result.email);
                formRef.current?.setFieldValue('registroAcademico', result.registroAcademico);
                formRef.current?.setFieldValue('tipoConta', result.tipoConta);
                const inputDate = moment(result.dataNascimento, "YYYY-MM-DD");
                const formattedDateStr = inputDate.format("DD/MM/YYYY");
                formRef.current?.setFieldValue('dataNascimento', formattedDateStr);
                formRef.current?.setFieldValue('telefone', result.telefone);
            })
        }
    }, [])


    const ButtonSubmitHover = {
        borderColor: "#262d63",
        color: "#262d63",
        marginTop: 1,
        '&:hover': {
          borderColor: "#262d63",
        }
    };
 
    return (
        <VForm ref={formRef} onSubmit={handleAction} style={{height: "100%"}}>

            <Box component={update ? Paper : undefined} padding={update ? 2 : ""} elevation={update ? 0 : 2}>

                { !update && (
                    <Typography variant='h3' align='center'>Cadastre-se</Typography>
                )}
                    
                <CardContent>
                    <Box display='flex' alignContent="center" flexDirection='column' gap={2}>
                        <VTextField 
                            label="Nome do usuário: "
                            required={update ? false : true}
                            name="nome"
                            fullWidth
                            disabled={isLoading} 
                        />

                        <VTextUpload 
                            formRef={formRef}
                            label="Imagem de Perfil "
                            name="imagem"
                            fullWidth
                            disabled={isLoading} 
                        />

                        <VTextField
                            label="Email: " 
                            name="email"
                            required={update ? false : true}
                            fullWidth 
                            disabled={isLoading}
                        />

                        <VTextField
                            label="Registro Acadêmico: " 
                            name="registroAcademico"
                            required={update ? false : true}
                            fullWidth 
                            disabled={isLoading} 
                        />

                        <Grid container item direction="row" spacing={2}>
                                <Grid item xs={12} sm={6}>
                                    <VTextDateMask
                                        formRef={null}
                                        name="dataNascimento"
                                    />  
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <VTextTelMask
                                        formRef={null}
                                        name="telefone"
                                    />
                                </Grid>

                                <Grid item xs={12}>
                                    <AutoComplete name="tipoConta" label='Tipo: ' options={["Privado", "Público"]} />
                                </Grid>

                                <Grid item xs={12} sm={6}>
                                    <VTextField
                                        label="Senha: "
                                        required={update ? false : true}
                                        name="senha"
                                        fullWidth 
                                        type='password'
                                        disabled={isLoading} 
                                    />
                                </Grid>

                                <Grid item xs={12} sm={6}>
                                    <VTextField
                                        label="Confirme sua senha: "
                                        required={update ? false : true}
                                        name="confirmarSenha"
                                        fullWidth 
                                        type='password'
                                        disabled={isLoading} 
                                    />
                                </Grid>

                        </Grid>
                    </Box>
                </CardContent>
                <CardActions sx={{ paddingX: 2, display: "block" }}>
                    <Box width='100%' display='flex' justifyContent='center' flexDirection="column">
                        <Button
                            sx={{ bgcolor:"#262d63", '&:hover': { backgroundColor: "#214099" } }}
                            fullWidth
                            variant="contained"
                            disabled={false}
                            onClick={submit}
                            endIcon=""
                        >
                        {textButton}
                        </Button>
                        <Button 
                            variant='outlined'
                            sx={ButtonSubmitHover}
                            fullWidth
                            onClick={handleClose}
                        >
                        Voltar
                        </Button>
                    </Box>
                </CardActions>
            </Box>

            {!update && (
                <Box width="100%" paddingX={2} display="flex" alignItems="center" columnGap={1} sx={{ cursor: "pointer" }} onClick={() => setIsRegister(false)}>
                    <Icon sx={{ fontSize: "4rem" }}>keyboard_backspace</Icon>
                    <Typography variant='body1' fontWeight="700">Voltar</Typography>
                </Box>
            )}


        </VForm>
    )
}