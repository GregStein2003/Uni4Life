import { useEffect, useState } from "react";
import { LayoutBaseDefault } from "../../layouts/";
import { DialogProfile } from "../dialogProfile/DialogProfile";
import { Box, Button, Icon, Paper } from '@mui/material';
import { IFormPostReturn, ContentService } from '../../services/api/formPost/FormPost';
import { BoxContent } from '../boxContent/BoxContent';
import { UserItem } from "../user/UserItem";
import { ProfileService } from "../../services/api/profile/ProfileService";

export const Profile: React.FC = () => {
    const [open, setOpen] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [ShowNoContent, setShowNoContent] = useState(false);
    const [profile, setProfile] = useState(""); 
    const [posts, setPosts] = useState<IFormPostReturn[]>([]);
    const handleClickOpen = () => { setOpen(true) };
    const handleClose = () => { setOpen(false) };

    useEffect(() => {
        setIsLoading(true);
            ContentService.getByMyUser().then(result => {
                if(result instanceof Error){
                    setPosts([]);
                    console.log(result.message)
                }else {
                    setPosts(result.conteudo);
                }
                setIsLoading(false);
                setShowNoContent(true);
            });

            ProfileService.getUserById().then(result => {
                setProfile(result);
            })
    }, [open]);

    const buttonSubmit = {
        backgroundColor: "#262d63",
        display: "flex",
        justifyContent: "flex-end",
        marginLeft: "auto",
        marginBottom: 2,
        '&:hover': {
          backgroundColor: '#262d63',
        }
      };

    return (
        <LayoutBaseDefault>

            <Button 
                variant="contained" 
                sx={buttonSubmit} 
                endIcon=
                {<Icon>update</Icon>} 
                onClick={() => handleClickOpen()}
            >
                Meus Dados
            </Button>

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
                    myAccount={true}
                /> 
            </Box>

            <DialogProfile openState={open} eventOpenState={setOpen} handleClose={handleClose} />

            <Box marginTop={3}>
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
                        myPosts={true}
                    />
                ))}
            </Box>


        </LayoutBaseDefault>
    )
}