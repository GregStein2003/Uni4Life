import * as yup from 'yup';
import { useState } from "react";
import { Box } from "@mui/system";
import { LayoutBaseWelcome } from "../../layouts/";
import bgLogin from "../../../images/bg-login.jpg"
import { VTextField, VForm, useVForm, IVFormErrors } from "../../forms/";
import { WelcomeService } from "../../services/api/welcome/WelcomeService";
import { Button, Card, CardActions, CardContent, FormControl, FormControlLabel, FormLabel, Grid, Icon, Paper, Radio, RadioGroup, Typography } from "@mui/material";


interface IWelcomeProps {
    children: React.ReactNode;
}

interface IFormLoginData {
    email: string;
    password: string;
}

const formLoginValidationSchema: yup.Schema<IFormLoginData> = yup.object().shape({
    email: yup.string().email().required(),
    password: yup.string().required().min(5),
});

type typeAccount = "private" | "public";

interface IFormRegisterData {
    type: string;
    nameUser: string;
    email: string;
    recordRegister: number;
    birthDate?: string;
    telephone?: string
    password: string;
    confirmPassword: string;
}

const formRegisterValidationSchema: yup.Schema<IFormRegisterData> = yup.object().shape({
    nameUser: yup.string().required(),
    email: yup.string().email().required(),
    recordRegister: yup.number().required(),
    type: yup.string().required(),
    password: yup.string().required().min(5),
    confirmPassword: yup.string().required().min(5)
});

export const Welcome: React.FC<IWelcomeProps> = () => {
    const { formRef, submit } = useVForm();
    const [isLoading, setIsLoading] = useState(false);
    const [isRegister, setIsRegister] = useState(true);
    

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

    const handleRegister = (dados: IFormRegisterData) => {
        console.log(dados);
        formRegisterValidationSchema.validate(dados, { abortEarly: false }).then(dadosValidados => {
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

    return (
        <LayoutBaseWelcome>
            <Box display="flex" justifyContent="center" alignItems="center" height="100%" width="100%" position="relative" flex={1}>
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
                                            name="password"
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
                                    <Box display="flex" justifyContent="space-between" alignItems="center" marginTop={2}>
                                        <Typography variant='body2' onClick={() => setIsRegister(true)}>Cadastre-se</Typography>
                                        <Typography variant='body2' onClick={() => {}}>Esqueceu sua senha?</Typography>
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
                                            name="nameUser"
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
                                            name="recordAcademic"
                                            required 
                                            fullWidth 
                                            disabled={isLoading} 
                                        />

                                        <Grid container item direction="row" spacing={2}>
                                                <Grid item xs={12} sm={6}>
                                                    <VTextField
                                                        label="Data de Nascimento: " 
                                                        name="birthDate"
                                                        fullWidth 
                                                        disabled={isLoading} 
                                                    />
                                                </Grid>
                                                <Grid item xs={12} sm={6}>
                                                    <VTextField
                                                        label="Telefone: " 
                                                        name="telephone"
                                                        fullWidth 
                                                        disabled={isLoading} 
                                                    />
                                                </Grid>

                                                <Grid item xl={12}>
                                                    <FormControl>
                                                        <FormLabel>Sua conta será?</FormLabel>
                                                        <RadioGroup sx={{ flexDirection: "row" }}>
                                                            <FormControlLabel value="private" control={<Radio />} label="Privada" />
                                                            <FormControlLabel value="public" control={<Radio />} label="Pública" />
                                                        </RadioGroup>
                                                    </FormControl>
                                                </Grid>

                                                <Grid item xs={12} sm={6}>
                                                    <VTextField
                                                        label="Senha: "
                                                        required 
                                                        name="password"
                                                        fullWidth 
                                                        type='password'
                                                        disabled={isLoading} 
                                                    />
                                                </Grid>

                                                <Grid item xs={12} sm={6}>
                                                    <VTextField
                                                        label="Confirme sua senha: "
                                                        required 
                                                        name="confirmPassword"
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
        </LayoutBaseWelcome>
    )
}