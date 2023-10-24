import axios from "axios";
import { errorInterceptor, responseInterceptor } from "./interceptors";
import { Environment } from "../../../environment";

const api = axios.create({
   // baseURL: Environment,
    headers: {
     //   Authorization: `Bearer ${localStorage.getItem(Environment)}`,
    }
});

api.interceptors.response.use(
    (response) => responseInterceptor(response),
    (error) => errorInterceptor(error),
)

export { api };