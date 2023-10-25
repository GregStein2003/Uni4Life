import { Button, Card, CardActions, CardContent, Paper, TextField, Typography } from "@mui/material";
import { LayoutBaseLogin } from "../../layouts/";
import { Box } from "@mui/system";
import bgLogin from "../../../images/bg-login.jpg"
import { VTextField, VForm, useVForm, } from "../../forms/";
import { useState } from "react";

interface ILoginProps {
    children: React.ReactNode;
}

interface IFormData {
    email: string;
    password: string;
}

export const Login: React.FC<ILoginProps> = () => {
    const { formRef } = useVForm();
    const [isLoading, setIsLoading] = useState(false);

    const handleSave = (dados: IFormData) => {}

    return (
        <LayoutBaseLogin>
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

                <Card component={Paper} elevation={3} sx={{ maxWidth: 550, width: "95%", paddingY: 5, zIndex: 2}}>
                    <VForm ref={formRef} onSubmit={handleSave} style={{height: "100%"}}>
                        <Typography variant='h3' align='center'>Login</Typography>
                        <CardContent>
                            <Box display='flex' alignContent="center" flexDirection='column' gap={2}>
                                <VTextField 
                                    label="E-mail: "
                                    name="email"
                                    fullWidth
                                    disabled={isLoading} 
                                />

                                <VTextField
                                    label="Password: " 
                                    name="password" 
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
                                    sx={{ borderRadius: 6 }}
                                    fullWidth
                                    variant="contained"
                                    disabled={false}
                                    onClick={() => {}}
                                    endIcon=""
                                >
                                Entrar
                                </Button>
                            </Box>
                            <Box display="flex" justifyContent="space-between" alignItems="center" marginTop={2}>
                                <Typography variant='body2' onClick={() => {}}>Cadastre-se</Typography>
                                <Typography variant='body2' onClick={() => {}}>Esqueceu sua senha?</Typography>
                            </Box>
                        </CardActions>
                    </VForm>
                </Card>
            </Box>
        </LayoutBaseLogin>
    )
}