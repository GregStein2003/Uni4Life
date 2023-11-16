import moment from 'moment';
import { api } from "../axios-config";
import { Environment } from "../../../environment";

export interface IUsuario {
    id: number;
    tipoConta: string;
    nome: string;
    email: string;
    registroAcademico: string;
    dataNascimento: string;
    telefone: string;
    senha: string;
    imagem: string;
}

function returnValue(value=""){
    return value.substring(1, value.length - 1);
}

let token = localStorage.getItem("APP_ACCESS_TOKEN");

if(token){
    token = returnValue(token);
}


const create = async (dados: Omit<IUsuario, "id">): Promise<number | any> => {
    try{
        dados.tipoConta = dados.tipoConta == "private" ? "PRIVADA" : "PUBLICA";
        const inputDate = moment(dados.dataNascimento, "DD/MM/YYYY");
        const formattedDateStr = inputDate.format("YYYY-MM-DD");

        dados.dataNascimento = formattedDateStr;

        dados.imagem = dados.imagem.replace("data:image/png;base64,", "");
        dados.imagem = dados.imagem.replace("data:image/jpeg;base64,", "");
        dados.imagem = dados.imagem.replace("data:image/jpg;base64,", "");

        const { data } = await api.post<IUsuario>(`${Environment.URL_BASE}/usuarios`, dados);

        if(data) {
            return data.id;
        }

        return new Error("Erro ao criar Usuario.");

    }catch(error){
        return {
            "error": true,
            "data": error.response.data.message,
            "field": error.response.data.field
        }

    }
};

const getUser = async (): Promise<IUsuario | any> => {
    try{
        const query = `${Environment.URL_BASE}/usuarios`;

        const { data } = await api.get(query, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }); 

        if(data) {
            return data;
        }

        return new Error("Erro ao consultar usuario.");

    }catch(error){
        return {
            "error": true,
            "data": error.response.data.message,
            "field": error.response.data.field
        }

    }
};

export const WelcomeService = { create, getUser };