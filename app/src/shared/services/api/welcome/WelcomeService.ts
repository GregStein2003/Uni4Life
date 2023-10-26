import { Environment } from "../../../environment";
import { api } from "../axios-config";

export interface ILogin {
    email: string;
    password: string;
}

const verify = async (dados: ILogin): Promise<any> => { // Incluir Typagem apos receber retorno da API
    try{
        const { data } = await api.post<ILogin>(`/login`, dados);

        if(data) {
            return data;
        }

        return new Error("Erro ao criar registro.");

    }catch(error){
        console.error(error);
        return new Error((error as { message: string }).message || `${error}: Erro ao criar registro.`);
    }
};

export const WelcomeService = { verify };