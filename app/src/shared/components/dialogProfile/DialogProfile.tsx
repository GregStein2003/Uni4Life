import * as yup from 'yup';
import { useState } from "react";
import "../../styles/DialogPost.css"
import { useVForm, IVFormErrors } from "../../forms";
import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, Typography } from "@mui/material"
import { Register } from '../welcome/Register';
import { ProfileService } from '../../services/api/profile/ProfileService';
import { useNavigate } from 'react-router-dom';

interface IDialogPostProps{
    openState: boolean;
    eventOpenState: () => void;
    handleClickOpen: () => void;
    handleClose: () => void;
  }

interface IFormRegisterData {
    tipoConta: string;
    nome: string;
    email: string;
    registroAcademico: string;
    dataNascimento: string;
    telefone: string;
    senha: string;
    confirmarSenha: string;
    imagem: string;
}

const formRegisterValidationSchema: yup.Schema<IFormRegisterData> = yup.object().shape({
    nome: yup.string().required(),
    email: yup.string().email().required(),
    dataNascimento: yup.string().required(),
    telefone: yup.string().required(),
    registroAcademico: yup.string().required(),
    tipoConta: yup.string().required(),
    imagem: yup.string().optional()
});


export const DialogProfile: React.FC<IDialogPostProps> = ({ openState = false, eventOpenState, handleClose }) => {
    const navigate = useNavigate();
    const { formRef, submit } = useVForm();
    const [isLoading, setIsLoading] = useState(false);
    const validationErros: IVFormErrors = {};

    const handleUpdate = (dados: any) => {
        formRegisterValidationSchema.validate(dados, { abortEarly: false }).then(dadosValidados => {
            ProfileService.update(dadosValidados).then(result => {
                setIsLoading(false);

                formRef.current?.reset(dadosValidados);

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
                    alert("Atualizado com sucesso");
                    eventOpenState(false);
                    navigate("/profile");
                }
            });
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
      <>
          <Dialog
            open={openState}
            onClose={handleClose}
            aria-labelledby="responsive-dialog-title"
          >
            <DialogContent className="form-add-post" sx={{ marginTop: "1.6rem", overflowY: "unset" }}>
              <Box display="flex" flexDirection="column" gap={2}>
                <Register formRef={formRef} handleAction={handleUpdate} isLoading={isLoading} setIsRegister={() => {}} submit={submit} update={true} handleClose={handleClose}/>
              </Box>
            </DialogContent>
          </Dialog>
      </>
    )
} 