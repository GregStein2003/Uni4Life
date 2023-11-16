import * as yup from 'yup';
import { useState } from "react";
import "../../styles/DialogPost.css"
import { AutoComplete } from "../welcome/AutoComplete";
import { VForm, VTextField, useVForm, IVFormErrors, VTextUpload } from "../../forms";
import { ContentService } from "../../services/api/formPost/FormPost";
import { Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, Typography, useTheme } from "@mui/material"

interface IDialogPostProps{
  openState: boolean;
  eventOpenState: () => void;
  handleClickOpen: () => void;
  handleClose: () => void;
}

interface IFormPostData {
    titulo: string;
    tipoConteudo: string;
    link?: string;
    descricao?: string;
}

let formValidationSchema = yup.object({
  titulo: yup.string().required(),
  tipoConteudo: yup.string().required(),
  link: yup.string().when('tipoConteudo', { is: "Podcast", then: (schema) => schema.required()}).when('tipoConteudo', {
    is: "Imagem",
    then: (schema) => schema.required(),
  }),
  imagem: yup.string().when('tipoConteudo', {
    is: "Imagem",
    then: (schema) => schema.required(),
  }),
  descricao: yup.string().when('tipoConteudo', { is: "Livro", then: (schema) => schema.required()}).when('tipoConteudo', {
    is: "Texto",
    then: (schema) => schema.required(),
  }),
});


export const DialogPost: React.FC<IDialogPostProps> = ({ openState = false, eventOpenState, handleClose }) => {
  const theme = useTheme();
  const { formRef, submit } = useVForm();
  const [isLoading, setIsLoading] = useState(false);
  
  const handleSubmit = (dados: IFormPostData) => {
    setIsLoading(true);

    formValidationSchema.validate(dados, { abortEarly: false }).then(dadosValidados => {
      ContentService.create(dadosValidados).then(() => {
        eventOpenState(false);
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

    const buttonSubmit = {
      backgroundColor: "#262d63",
      '&:hover': {
        backgroundColor: '#262d63',
      }
    };

    const ButtonSubmitHover = {
      borderColor: "#262d63",
      color: "#262d63",
      '&:hover': {
        borderColor: "#262d63",
      }
    };

    return (
      <>
        <VForm ref={formRef} onSubmit={handleSubmit}>
          <Dialog
            open={openState}
            onClose={handleClose}
            aria-labelledby="responsive-dialog-title"
          >
            <DialogTitle id="responsive-dialog-title">
              <Typography variant="h3" textAlign="center">Inserir conteúdo</Typography>
            </DialogTitle>
            <DialogContent className="form-add-post" sx={{ marginTop: "1.6rem", overflowY: "unset" }}>
              <Box display="flex" flexDirection="column" gap={2}>
                <VTextField 
                  label="Título: "
                  required
                  name="titulo"
                  fullWidth
                  disabled={false} 
                  error={false}
                  helperText=""
                />

                <AutoComplete name="tipoConteudo" label='Categoria: ' options={["Livro", "Podcast", "Imagem", "Texto"]}/>

                <VTextField 
                  label="Link: "
                  name="link"
                  fullWidth
                  disabled={false} 
                  error={false}
                  helperText=""
                />

                <VTextField             
                  label="Descrição: "
                  name="descricao"
                  fullWidth
                  disabled={false} 
                  error={false}
                  helperText=""
                />

                <VTextUpload
                  label="Imagem: "
                  name="imagem"
                  fullWidth
                  disabled={isLoading} 
                />

              </Box>
            </DialogContent>
            <DialogActions sx={{ paddingX: 3 }} className='form-add-post__container-buttons'>
              <Button autoFocus variant='outlined' sx={ButtonSubmitHover} onClick={handleClose}>
                Voltar
              </Button>
              <Button onClick={submit} variant='contained' sx={buttonSubmit} autoFocus>
                Cadastrar
              </Button>
            </DialogActions>
          </Dialog>
        </VForm>
      </>
    )
} 