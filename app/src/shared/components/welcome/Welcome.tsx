import * as yup from 'yup';
import { useState } from "react";
import { Box } from "@mui/system";
import { LayoutBaseWelcome } from "../../layouts/";
import bgLogin from "../../../images/bg-login.jpg"
import Loader from "../../../images/loader.gif"
import { AutoComplete } from "./AutoComplete";
import { VTextField, VForm, useVForm, IVFormErrors } from "../../forms/";
import { WelcomeService } from "../../services/api/welcome/WelcomeService";
import { Button, Card, CardActions, CardContent, Grid, Icon, Paper, Typography } from "@mui/material";


interface IWelcomeProps {
    children: React.ReactNode;
}

interface IFormLoginData {
    email: string;
    senha: string;
}

const formLoginValidationSchema: yup.Schema<IFormLoginData> = yup.object().shape({
    email: yup.string().email().required(),
    senha: yup.string().required(),
});

interface IFormRegisterData {
    tipoConta: string;
    nome: string;
    email: string;
    registroAcademico: number;
    dataNascimento: Date;
    telefone: string;
    senha: string;
    confirmarSenha: string;
}

const formRegisterValidationSchema: yup.Schema<IFormRegisterData> = yup.object().shape({
    nome: yup.string().required(),
    email: yup.string().email().required(),
    dataNascimento: yup.date().required().typeError('Insira uma data válida'),
    telefone: yup.string().required(),
    registroAcademico: yup.number().required(),
    tipoConta: yup.string().required(),
    senha: yup.string().required().min(8),
    confirmarSenha: yup.string().oneOf([yup.ref('senha'), undefined], "Senhas precisam ser iguais").required(),
});



export const Welcome: React.FC<IWelcomeProps> = ({ children }) => {
    const { formRef, submit } = useVForm();
    const [isLoading, setIsLoading] = useState(false);
    const [isRegister, setIsRegister] = useState(false);
    

    const handleLogin = (dados: IFormLoginData) => {
        formLoginValidationSchema.validate(dados, { abortEarly: false }).then(dadosValidados => {
            setIsLoading(true);

            WelcomeService.verify(dadosValidados).then(result => {
                setIsLoading(false);
                if(result instanceof Error){
                    alert(result.message);
                }else {
                    console.log("teste")
                }
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

    const handleRegister = (dados: any) => {
        formRegisterValidationSchema.validate(dados, { abortEarly: false }).then(dadosValidados => {
            setIsLoading(true);

            WelcomeService.create(dadosValidados).then(result => {
                setIsLoading(false);
                if(result instanceof Error){
                    alert(result.message);
                }else {
                    console.log("teste")
                }
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

    if (true) return (
        <>{children}</>
      );

    return (
        <LayoutBaseWelcome>
            <Box display="flex" justifyContent="center" alignItems="center" height="100%" width="100%" position="relative" paddingY={2} flex={1}>
                    <Box 
                        sx={{ 
                            backgroundImage: `url(${bgLogin})`,
                            height: "100%",
                            width: "100%",
                            objectFit: "contain",
                            position:"absolute",
                            opacity:".8",
                            zIndex: 1
                        }}>
                    </Box>

                    <Card component={Paper} elevation={3} sx={{ maxWidth: 550, width: "95%", paddingY: 2, zIndex: 2}}>
                        {!isRegister && (
                            <VForm ref={formRef} onSubmit={handleLogin} style={{height: "100%"}}>
                                <Typography variant='h3' align='center'>Login</Typography>
                                <CardContent>
                                    <Box display='flex' alignContent="center" flexDirection='column' gap={2}>
                                        <VTextField 
                                            label="E-mail: "
                                            required
                                            name="email"
                                            fullWidth
                                            disabled={isLoading} 
                                        />

                                        <VTextField
                                            label="Senha: " 
                                            name="senha"
                                            required 
                                            fullWidth 
                                            type="password"
                                            disabled={isLoading} 
                                            onChange={() => {}} 
                                        />
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
                                        Entrar
                                        </Button>
                                    </Box>
                                    <Box display="flex" justifyContent="center" alignItems="center" marginTop={2}>
                                        <Typography variant='body2' onClick={() => setIsRegister(true)}>Cadastre-se</Typography>
                                    </Box>
                                </CardActions>
                            </VForm>
                        )}

                        {isRegister && (
                            <VForm ref={formRef} onSubmit={handleRegister} style={{height: "100%"}}>
                                <Typography variant='h3' align='center'>Cadastre-se</Typography>
                                <CardContent>
                                    <Box display='flex' alignContent="center" flexDirection='column' gap={2}>
                                        <VTextField 
                                            label="Nome do usuário: "
                                            required
                                            name="nome"
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
                                                    <VTextField
                                                        label="DD/MM/AAAA" 
                                                        name="dataNascimento"
                                                        required
                                                        fullWidth 
                                                        disabled={isLoading} 
                                                    />
                                                </Grid>
                                                <Grid item xs={12} sm={6}>
                                                    <VTextField
                                                        label="(DD)XXXXX-XXXX" 
                                                        required
                                                        name="telefone"
                                                        fullWidth 
                                                        disabled={isLoading} 
                                                    />
                                                </Grid>

                                                <Grid item xs={12}>
                                                    <AutoComplete />
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
                                        Cadastrar
                                        </Button>
                                    </Box>
                                </CardActions>

                                <Box width="100%" paddingX={2} display="flex" alignItems="center" columnGap={1} sx={{ cursor: "pointer" }} onClick={() => setIsRegister(false)}>
                                    <Icon sx={{ fontSize: "4rem" }}>keyboard_backspace</Icon>
                                    <Typography variant='body1' fontWeight="700">Voltar</Typography>
                                </Box>
                            </VForm>
                        )}

                    </Card>
            </Box>

            {isLoading && (
                <Box sx={{ width: "100vw", height: "100vh", bgcolor: "white", position: "fixed", opacity: ".7", zIndex: 1000, top: 0, display: "grid", placeItems: "center" }}>
                    <img src={Loader} alt="Loader" />
                </Box>
            )}
        </LayoutBaseWelcome>
    )
}