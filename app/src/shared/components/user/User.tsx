import "../../styles/Profile.css"
import { Box, Paper} from "@mui/material";
import { LayoutBaseDefault } from "../../layouts";
import { UserItem } from "../user/UserItem"
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { ProfileService } from "../../services/api/profile/ProfileService";
import { ContentService, IFormPostReturn } from '../../services/api/formPost/FormPost';
import { BoxContent } from '..';


export const User: React.FC = () => {
    const { id } = useParams<"id">();
    const [profile, setProfile] = useState(""); 
    const [posts, setPosts] = useState<IFormPostReturn[]>([]);

    useEffect(() => {
        ProfileService.getUserById(id).then(result => {
            console.log(result)
            setProfile(result);
        })

        ContentService.getById(id).then(result => {
            if(result instanceof Error){
                setPosts([]);
                console.log(result.message)
            }else {
                console.log(result)
                setPosts(result.conteudo);
            }
        });
    }, [])

    return (
        <LayoutBaseDefault>
            <Box
                position="relative"
                gap={1} 
                paddingY={3}
                paddingX={3}
                component={Paper}
                marginBottom={2}
            >  
                <UserItem
                    key={profile.id}
                    dataNascimento={profile.dataNascimento}
                    email={profile.email}
                    id={profile.id}
                    imagem={profile.imagem}
                    nome={profile.nome}
                    registroAcademico={profile.registroAcademico}
                    segmento={profile.segmento}
                    seguido={profile.seguido}
                    seguidores={profile.seguidores}
                    telefone={profile.telefone}
                    tipoConta={profile.tipoConta}
                /> 
            </Box>

            {posts.map(post => ( 
                <BoxContent
                    key={post.id}
                    id={post.id}
                    autor={post.autor}
                    imagemAutor={post.imagemAutor}
                    imagem={post.imagem}
                    data={post.dataCriacao}
                    link={post.link}
                    tipoConteudo={post.tipoConteudo}
                    titulo={post.titulo}
                    descricao={post.descricao}
                    comentarios={post.comentarios}
                />
            ))}

        </LayoutBaseDefault>
    )
}