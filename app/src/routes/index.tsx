import { Routes, Route, Navigate } from "react-router-dom";
import { Login } from "../pages";

export const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<p>Pagina Inicial</p>}/>
            <Route path="*" element={<Navigate to="/"/>}/>
        </Routes>
    );
};
