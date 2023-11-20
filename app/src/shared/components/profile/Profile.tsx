import * as yup from 'yup';
import { useEffect, useState } from "react";
import { IVFormErrors, useVForm } from "../../forms";
import { LayoutBaseDefault } from "../../layouts/";
import { DialogProfile } from "../dialogProfile/DialogProfile";
import { ProfileService } from "../../services/api/profile/ProfileService";
import { useNavigate } from 'react-router-dom';
import { Box, Button, Icon } from '@mui/material';
import { IFormPostReturn, ContentService } from '../../services/api/formPost/FormPost';
import { BoxContent } from '..';



export const Profile: React.FC = () => {
    const [open, setOpen] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [ShowNoContent, setShowNoContent] = useState(false);
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
    }, [open]);

    const buttonSubmit = {
        backgroundColor: "#262d63",
        display: "flex",
        justifyContent: "flex-end",
        marginLeft: "auto",
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
                    />
                ))}
            </Box>


        </LayoutBaseDefault>
    )
}