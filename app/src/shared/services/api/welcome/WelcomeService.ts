import { api } from "../axios-config";

type typeAccount = "private" | "public" | "PUBLICA" | "PRIVADA";
export interface IUsuario {
    id: number;
    tipoConta: typeAccount;
    nome: string;
    email: string;
    registroAcademico: number;
    dataNascimento: Date;
    telefone: string;
    senha: string;
}
export interface ILogin {
    email: string;
    senha: string;
}

const verify = async (dados: ILogin): Promise<any> => { // Incluir Typagem apos receber retorno da API
    try{
        const { data } = await api.post<ILogin>(`/login`, dados);

        if(data) {
            return data;
        }

        return new Error("Erro ao consultar login.");

    }catch(error){
        console.error(error);
        return new Error((error as { message: string }).message || `${error}: Erro ao consultar login.`);
    }
};

const create = async (dados: Omit<IUsuario, "id">): Promise<number | Error> => {
    try{

        dados.tipoConta = dados.tipoConta == "private" ? "PRIVADA" : "PUBLICA";

        console.log(dados)

        const { data } = await api.post<IUsuario>(`http://localhost:3131/uni4life/usuarios`, dados);

        if(data) {
            return data.id;
        }

        return new Error("Erro ao criar Usuario.");

    }catch(error){
        console.error(error);
        return new Error((error as { message: string }).message || `${error}: Erro ao criar Usuario.`);
    }
};

export const WelcomeService = { verify, create };