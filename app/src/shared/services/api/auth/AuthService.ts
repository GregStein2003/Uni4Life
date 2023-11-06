import { AxiosError } from 'axios';
import { Environment } from "../../../environment";
import { api } from "../axios-config";

interface IAuth {
    access_token: string;
}

const auth = async (email: string, password: string): Promise<IAuth | Error> => {
    try{

        let formData = new FormData();

        formData.append("username", email);
        formData.append("password", password);
        formData.append("grant_type", "password")
        formData.append("scope", "app")

        const { data } = await api.post(`${Environment.urlBase}/oauth/token`, formData, {
            headers: {
                'Authorization' : 'Basic dW5pNGxpZmUtY2xpZW50OnVuaTRsaWZlLXNlY3JldA=='
            },
        });    

        if(data){
            return data.access_token;
        }

        return new Error("Erro no login.");
    }catch(error){
        const axiosError = error as AxiosError;

        if(axiosError.response){
            return new Error(axiosError.response.data.message);
        }else {
            return new Error((error as { message: string }).message || `${error}: Erro ao realizar login.`);
        }
    }
};

export const AuthService = {
    auth,
};