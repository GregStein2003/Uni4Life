import { Environment } from "../../../environment";
import { api } from "../axios-config";

export interface IUsuario {
    id: number;
    tipoConta: string;
    nome: string;
    email: string;
    registroAcademico: number;
    dataNascimento: Date;
    telefone: string;
    senha: string;
}

const create = async (dados: Omit<IUsuario, "id">): Promise<number | Error> => {
    try{
        dados.tipoConta = dados.tipoConta == "private" ? "PRIVADA" : "PUBLICA";

        const { data } = await api.post<IUsuario>(`${Environment.urlBase}/usuarios`, dados);

        if(data) {
            return data.id;
        }

        return new Error("Erro ao criar Usuario.");

    }catch(error){
        console.error(error);
        return new Error((error as { message: string }).message || `${error}: Erro ao criar Usuario.`);
    }
};

export const WelcomeService = { create };