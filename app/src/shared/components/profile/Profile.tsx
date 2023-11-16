import * as yup from 'yup';
import { useState } from "react";
import { IVFormErrors, useVForm } from "../../forms";
import { LayoutBaseDefault } from "../../layouts/";
import { Register } from "../welcome/Register";

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

export const Profile: React.FC = () => {
    const { formRef, submit } = useVForm();
    const [isLoading, setIsLoading] = useState(false);
    const validationErros: IVFormErrors = {};

    const handleUpdate = (dados: any) => {
        formRegisterValidationSchema.validate(dados, { abortEarly: false }).then(dadosValidados => {
            console.log(dadosValidados)
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
        <LayoutBaseDefault>
            <Register formRef={formRef} handleAction={handleUpdate} isLoading={isLoading} setIsRegister={() => {}} submit={submit} update={true} />
        </LayoutBaseDefault>
    )
}