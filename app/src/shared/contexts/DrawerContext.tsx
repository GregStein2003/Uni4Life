import { createContext, useCallback, useState, useContext } from "react";

interface IDrawerOptionsProps {
    icon: string;
    path: string;
    label: string;
}

interface IDrawerContextData {
    isDrawerOpen: boolean;
    drawerOptions: IDrawerOptionsProps[]
    toggleDrawerOpen: () => void;
    setDrawerOptions: (newDrawerOptions: IDrawerOptionsProps[]) => void;
}

const DrawerContext = createContext({} as IDrawerContextData);

export const useAppDrawerContext = () => {
    return useContext(DrawerContext);
};

interface IAppDrawerProviderProps {
    children: React.ReactNode;
}

export const AppDrawerProvider: React.FC<IAppDrawerProviderProps> = ({ children }) => {
    const [drawerOptions, setDrawerOptions] = useState<IDrawerOptionsProps[]>([]);
    const [isDrawerOpen, setIsDrawerOpen] = useState(false);

    const toggleDrawerOpen = useCallback(() => {
        setIsDrawerOpen(oldDrawerOpen => !oldDrawerOpen);
    }, []);

    const handleSetDrawerOptions = useCallback((newDrawerOptions: IDrawerOptionsProps[]) => {
        setDrawerOptions(newDrawerOptions);
    }, []);

    return (
        <DrawerContext.Provider value={{ isDrawerOpen, drawerOptions, toggleDrawerOpen, setDrawerOptions: handleSetDrawerOptions }}>
            { children }
        </DrawerContext.Provider>
    );
};