import { Environment } from "../../../environment";
import { api } from "../axios-config";

export interface IFormPost {
    titulo: string;
    tipoConteudo: string;
    link ?: string;
    descricao ?: string;
    imagem: string;
}

export interface IFormPostReturn {
    autor: string;
    dataAtualizacao?: string;
    dataCriacao: string;
    descricao: string;
    link: string;
    id: string;
    tipoConteudo: string;
    titulo: string;
    imagem: string;
}

export type TFormPostTotalCount = {
    conteudo: IFormPostReturn[]; // 
    numeroPagina: number,
    elementosPagina: number,
    elementosTotais: number,
    ultimaPagina: boolean
}

let token = localStorage.getItem("APP_ACCESS_TOKEN");

if(token){
    token = token.substring(1, token.length - 1);
}

const create = async (dados: IFormPost): Promise<IFormPostReturn | Error> => {
    try{
        dados.tipoConteudo = dados.tipoConteudo.toUpperCase();

        dados.imagem = dados.imagem.replace("data:image/png;base64,", "");
        dados.imagem = dados.imagem.replace("data:image/jpeg;base64,", "");
        dados.imagem = dados.imagem.replace("data:image/jpg;base64,", "");

        const { data } = await api.post(`${Environment.URL_BASE}/conteudos`, dados, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }); 
    
        if(data) {
            return data;
        }

        return new Error("Erro ao criar post.");

    }catch(error){
        console.error(error);
        return new Error((error as { message: string }).message || `${error}: Erro ao criar post.`);
    }
};

const getAll = async (page = 1): Promise<TFormPostTotalCount | Error> => {
    try{        
        const query = `${Environment.URL_BASE}/conteudos/meu`;



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

        return new Error("Erro ao listar cidades");

    }catch(error){
        console.error(error);
        return new Error((error as { message: string }).message || `${error}: Erro ao listar cidades.`);
    }
};

export const ContentService = { create, getAll };