import { Environment } from "../../../environment";
import { api } from "../axios-config";

export interface IFollowReturn {
    dataNascimento?: Date;
    dataRelacionamento?: Date;
    email?: string;
    id: string;
    nome: string;
    registroAcademico: string;
    segmento?: string;
    seguidores: number;
    telefone?: string;
    tipoConta?: string;
    imagem ?: string;
}

export type TFollowTotalCount = {
    conteudo: IFollowReturn[]; // 
    numeroPagina: number,
    elementosPagina: number,
    elementosTotais: number,
    ultimaPagina: boolean
}

function returnValue(value=""){
    return value.substring(1, value.length - 1);
}

let token = localStorage.getItem("APP_ACCESS_TOKEN");

if(token){
    token = returnValue(token);
}

const getAll = async (): Promise<TFollowTotalCount | Error> => {
    try{        
        const query = `${Environment.URL_BASE}/usuarios/seguir`;

        const { data } = await api.get(query, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }); 

        if(data) {
            return data;
        }

        return { 
            "conteudo": [],
            "numeroPagina": 0,
            "elementosPagina": 0,
            "elementosTotais": 0,
            "ultimaPagina": true
        };
    }catch(error){
        console.error(error);
        return new Error((error as { message: string }).message || `${error}: Erro ao listar cidades.`);
    }
};

const add = async (id: string): Promise<any | Error> => {
    try{
        const query = `${Environment.URL_BASE}/seguidores?idSeguido=${id}`;

        const { data } = await api.post(query, {}, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }); 

        if(data) {
            return data;
        }

        return new Error("Erro Follow");

    }catch(error){
        console.error(error);
        return new Error((error as { message: string }).message || `${error}: Erro Follow`);
    }
};

const remove = async (id: string): Promise<any | Error> => {
    try{
        const query = `${Environment.URL_BASE}/seguidores?idSeguido=${id}`;

        const { data } = await api.delete(query, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }); 

        if(data) {
            return data;
        }

        return new Error("Erro Unfollow");

    }catch(error){
        console.error(error);
        return new Error((error as { message: string }).message || `${error}: Erro Unfollow.`);
    }
};

export const FollowService = { getAll, add, remove };