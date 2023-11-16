import * as yup from 'yup';
import { useState } from "react";
import { Box } from "@mui/system";
import "../../styles/default-styles.css"
import Loader from "../../../images/loader.gif"
import { useAuthContext } from '../../contexts';
import bgLogin from "../../../images/bg-login.jpg"
import { LayoutBaseWelcome } from "../../layouts/";
import { Register } from "../welcome/Register"
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
    registroAcademico: string;
    dataNascimento: string;
    telefone: string;
    senha: string;
    confirmarSenha: string;
    imagem: string
}

const formRegisterValidationSchema: yup.Schema<IFormRegisterData> = yup.object().shape({
    nome: yup.string().required(),
    email: yup.string().email().required(),
    dataNascimento: yup.string().required(),
    telefone: yup.string().required(),
    registroAcademico: yup.string().required(),
    tipoConta: yup.string().required(),
    senha: yup.string().required().min(8),
    confirmarSenha: yup.string().oneOf([yup.ref('senha'), undefined], "Senhas precisam ser iguais").required(),
    img: yup.string().optional()
});

export const Welcome: React.FC<IWelcomeProps> = ({ children }) => {
    const { formRef, submit } = useVForm();
    const [isLoading, setIsLoading] = useState(false);
    const { isAuthenticated, Login } = useAuthContext();
    const [isRegister, setIsRegister] = useState(false);
    const [passwordError, setPasswordError] = useState('');
    const [emailError, setEmailError] = useState('');

    const validationErros: IVFormErrors = {};

    const handleLogin = (dados: IFormLoginData) => {
        setIsLoading(true);

        formLoginValidationSchema.validate(dados, { abortEarly: false }).then(dadosValidados => {
            Login(dadosValidados.email, dadosValidados.senha).then(result => {
                
              if(!!result){
                validationErros["senha"] = result;
                formRef.current?.setErrors(validationErros);
              }

              setIsLoading(false);
            })
        }).catch((error: yup.ValidationError) => {
            const validationErros: IVFormErrors = {};
            error.inner.forEach(error => {
                if (!error.path) return 
                validationErros[error.path] = error.message;
            });
            formRef.current?.setErrors(validationErros);
            setIsLoading(false);
        });
    }

    const handleRegister = (dados: any) => {
        formRegisterValidationSchema.validate(dados, { abortEarly: false }).then(dadosValidados => {
            setIsLoading(true);

            WelcomeService.create(dadosValidados).then(result => {
                setIsLoading(false);

                if(result.error == true){
                    if(result.field == "email"){
                        validationErros["email"] = result.data;
                        formRef.current?.setErrors(validationErros);
                    }

                    if(result.field == "telefone"){
                        validationErros["telefone"] = result.data;
                        formRef.current?.setErrors(validationErros);
                    }

                }else {
                    alert("Cadastro realizado com sucesso");
                    setIsRegister(false)
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

    if (isAuthenticated) return (
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
                            backgroundRepeat: "no-repeat",
                            backgroundSize: "cover",
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
                                            error={!!emailError}
                                            helperText={emailError}
                                        />

                                        <VTextField
                                            label="Senha: " 
                                            name="senha"
                                            required 
                                            fullWidth 
                                            error={!!passwordError}
                                            helperText={passwordError}
                                            type="password"
                                            disabled={isLoading} 
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
                            <Register formRef={formRef} handleAction={handleRegister} isLoading={isLoading} setIsRegister={setIsRegister} submit={submit} register={false} />
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