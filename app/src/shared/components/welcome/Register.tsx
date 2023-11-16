import moment from 'moment';
import { Box } from "@mui/system";
import "../../styles/default-styles.css"
import { AutoComplete } from "./AutoComplete";
import { VTextField, VForm, VTextTelMask, VTextDateMask, VTextUpload } from "../../forms/";
import { Button, CardActions, CardContent, Grid, Icon, Paper, Typography } from "@mui/material";
import { useEffect, useRef, useState } from "react";
import { WelcomeService } from "../../services/api/welcome/WelcomeService";


interface IWelcomeProps {
    formRef: any;
    handleAction: any;
    isLoading: boolean;
    setIsRegister: () => void;
    submit: () => void;
    update: boolean;
}

export const Register: React.FC<IWelcomeProps> = ({ formRef, handleAction, isLoading, setIsRegister, submit, update}) => {
    const title = update ? "Alterar Dados" : "Cadastre-se";
    const textButton = update ? "Salvar" : "Concluir";
    const [valueProfile, setValueProfile] = useState("");
    const [valueDataNascimento, setValueDataNascimento] = useState("");
    const [valueTefone, setValueTelefone] = useState("");

    useEffect(() => {
        WelcomeService.getUser().then(result => {
            formRef.current?.setFieldValue('nome', result.nome);
            formRef.current?.setFieldValue('imagem', "data:image/png;base64,"+result.imagem);
            formRef.current?.setFieldValue('email', result.email);
            formRef.current?.setFieldValue('registroAcademico', result.registroAcademico);
            formRef.current?.setFieldValue('tipoConta', result.tipoConta);
            const inputDate = moment(result.dataNascimento, "YYYY-MM-DD");
            const formattedDateStr = inputDate.format("DD/MM/YYYY");

            const anotherFormRef = useRef(null);
            console.log(anotherFormRef)
        }).catch(() => {})
    }, [])
 
    return (
        <VForm ref={formRef} onSubmit={handleAction} style={{height: "100%"}}>

            <Box component={update ? Paper : undefined} padding={update ? 2 : ""}>
                <Typography variant='h3' align='center'>{title}</Typography>
                    
                <CardContent>
                    <Box display='flex' alignContent="center" flexDirection='column' gap={2}>
                        <VTextField 
                            label="Nome do usuário: "
                            required
                            name="nome"
                            fullWidth
                            disabled={isLoading} 
                        />

                        <VTextUpload 
                            label="Imagem de Perfil "
                            name="imagem"
                            fullWidth
                            disabled={isLoading} 
                        />

                        <VTextField
                            label="Email: " 
                            name="email"
                            required 
                            fullWidth 
                            disabled={isLoading}
                        />

                        <VTextField
                            label="Registro Acadêmico: " 
                            name="registroAcademico"
                            required 
                            fullWidth 
                            disabled={isLoading} 
                        />

                        <Grid container item direction="row" spacing={2}>
                                <Grid item xs={12} sm={6}>
                                    <VTextDateMask
                                        setValueUpdate={valueDataNascimento}
                                        name="dataNascimento"
                                    />  
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <VTextTelMask
                                        name="telefone"
                                    />
                                </Grid>

                                <Grid item xs={12}>
                                    <AutoComplete name="tipoConta" label='Tipo: ' options={["Privado", "Público"]} />
                                </Grid>

                                <Grid item xs={12} sm={6}>
                                    <VTextField
                                        label="Senha: "
                                        required 
                                        name="senha"
                                        fullWidth 
                                        type='password'
                                        disabled={isLoading} 
                                    />
                                </Grid>

                                <Grid item xs={12} sm={6}>
                                    <VTextField
                                        label="Confirme sua senha: "
                                        required 
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
                    <Box width='100%' display='flex' justifyContent='center'>
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