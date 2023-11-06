import { createContext, useCallback, useContext, useEffect, useMemo, useState } from "react";
import { AuthService } from "../services/api/auth/AuthService";

interface IAuthContextData {
    logout: () => void;
    isAuthenticated: boolean;
    Login: (email: string, senha: string) => Promise<string | void>;
  }
  
  const AuthContext = createContext({} as IAuthContextData);
  
  const LOCAL_STORAGE_KEY__ACCESS_TOKEN = 'APP_ACCESS_TOKEN';
  
  interface IAuthProviderProps {
    children: React.ReactNode;
  }

  export const AuthProvider: React.FC<IAuthProviderProps> = ({ children }) => {
    const [accessToken, setAccessToken] = useState<any>();
  
    useEffect(() => {
      const accessToken = localStorage.getItem(LOCAL_STORAGE_KEY__ACCESS_TOKEN);
  
      if (!!accessToken) {
        setAccessToken(accessToken);
      } else {
        setAccessToken(undefined);
      }
    }, []);
  
  
    const handleLoginAuth = useCallback(async (email: string, senha: string) => {
      const result = await AuthService.auth(email, senha);
      if (result instanceof Error) {
        return result.message;
      } else {
        localStorage.setItem(LOCAL_STORAGE_KEY__ACCESS_TOKEN, JSON.stringify(result));
        setAccessToken(result);
      }
    }, []);
  
    const handleLogout = useCallback(() => {
      localStorage.removeItem(LOCAL_STORAGE_KEY__ACCESS_TOKEN);
      setAccessToken(undefined);
    }, []);
  
    const isAuthenticated = useMemo(() => !!accessToken, [accessToken]);
  
    return (
      <AuthContext.Provider value={{ isAuthenticated, Login: handleLoginAuth, logout: handleLogout }}>
        {children}
      </AuthContext.Provider>
    );
  };
  
  export const useAuthContext = () => useContext(AuthContext);