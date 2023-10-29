import { useEffect } from "react";
import { Home } from "../shared/components";
import { useAppDrawerContext } from "../shared/contexts";
import { Routes, Route, Navigate } from "react-router-dom";

export const AppRoutes = () => {

    const { setDrawerOptions } = useAppDrawerContext();
    

    useEffect(() => {
        setDrawerOptions([
            {
                label: "Pagina Inicial",
                icon: "home",
                path: "/"
            },
            {
                label: "Pessoas",
                icon: "people",
                path: "/pessoas"
            },
            {
                label: "Cidades",
                icon: "location_city",
                path: "/cidades"
            }
        ]);
    }, []);

    return (
        <Routes>
            <Route path="/" element={<Home />}/>
            <Route path="*" element={<Navigate to="/"/>}/>
        </Routes>
    );
};
