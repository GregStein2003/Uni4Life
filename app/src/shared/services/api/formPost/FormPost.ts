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
    imagemAutor: string;
    dataCriacao: string;
    descricao: string;
    link: string;
    id: string;
    tipoConteudo: string;
    titulo: string;
    imagem: string;
    favoritado: boolean;
    curtido: boolean;
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

const getAll = async (): Promise<TFormPostTotalCount | Error> => {
    try{        
        const query = `${Environment.URL_BASE}/conteudos/seguidos`;

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

        return new Error("Erro ao listar posts");

    }catch(error){
        return new Error((error as { message: string }).message || `${error}: Erro ao listar posts.`);
    }
};

const getByMyUser = async (): Promise<TFormPostTotalCount | Error> => {
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


    }catch(error){
        return new Error((error as { message: string }).message || `${error}: Erro ao listar meus posts.`);
    }
};

const getById = async (id: string): Promise<TFormPostTotalCount | Error> => {
    try{        
        const query = `${Environment.URL_BASE}/conteudos?idUsuario=${id}`;

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
        return new Error((error as { message: string }).message || `${error}: Erro ao listar meus posts.`);
    }
};

const addFavorite = async (id: string): Promise<any | Error> => {
    try{
        const query = `${Environment.URL_BASE}/conteudos/favoritar?idConteudo=${id}`;

        const { data } = await api.post(query, {}, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }); 

        if(data) {
            return data;
        }

        return new Error("Erro Favorite");

    }catch(error){
        console.error(error);
        return new Error((error as { message: string }).message || `${error}: Erro Favorite`);
    }
};

const removeFavorite = async (id: string): Promise<any | Error> => {
    try{
        const query = `${Environment.URL_BASE}/conteudos/favoritar?idConteudo=${id}`;

        const { data } = await api.delete(query, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }); 

        if(data) {
            return data;
        }

        return new Error("Erro Unfavorite");

    }catch(error){
        console.error(error);
        return new Error((error as { message: string }).message || `${error}: Erro Unfavorite.`);
    }
};

const addLike = async (id: string): Promise<any | Error> => {
    try{
        const query = `${Environment.URL_BASE}/conteudos/curtir?idConteudo=${id}`;

        const { data } = await api.post(query, {}, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }); 

        if(data) {
            return data;
        }

        return new Error("Erro Like");

    }catch(error){
        console.error(error);
        return new Error((error as { message: string }).message || `${error}: Erro Like`);
    }
};

const removeLike = async (id: string): Promise<any | Error> => {
    try{
        const query = `${Environment.URL_BASE}/conteudos/curtir?idConteudo=${id}`;

        const { data } = await api.delete(query, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }); 

        if(data) {
            return data;
        }

        return new Error("Erro UnLike");

    }catch(error){
        console.error(error);
        return new Error((error as { message: string }).message || `${error}: Erro UnLike.`);
    }
};

const addComment = async (dados, id): Promise<any> => {
    try{

        let content = {
            descricao: dados.comentario,
            idConteudo: id,
            titulo: "Titulo"

        }

        const { data } = await api.post(`${Environment.URL_BASE}/comentarios`, content, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        console.log(data)

        if(data) {
            return data;
        }



        return new Error("Erro ao adicionar commentario.");

    }catch(error){
        return {
            "error": true,
            "data": error.response.data.message,
            "field": error.response.data.field
        }

    }
};

export const ContentService = { create, getAll, getByMyUser, getById, addFavorite, removeFavorite, addLike, removeLike, addComment };