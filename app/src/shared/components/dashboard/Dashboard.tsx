import "../../styles/Dashboard.css"
import Ops from "../../../images/ops.gif"
import { useEffect, useState } from "react";
import { DialogPost } from "../dialogPost/DialogPost";
import { BoxContent } from "../boxContent/BoxContent";
import { Icon, Button, Box, Paper, Typography } from "@mui/material"
import { IFormPostReturn, ContentService } from "../../services/api/formPost/FormPost";


export const Dashboard: React.FC = () => {
    const [open, setOpen] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    const [posts, setPosts] = useState<IFormPostReturn[]>([]);

    const handleClickOpen = () => { setOpen(true) };
  
    const handleClose = () => { setOpen(false) };

    useEffect(() => {
        setIsLoading(true);
            ContentService.getAll().then(result => {
                if(result instanceof Error){
                    setPosts([]);
                    console.log(result.message)
                }else {
                    setPosts(result.conteudo);
                }
                setIsLoading(false);
            });
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
        <>
        <Button 
            variant="contained" 
            sx={buttonSubmit} 
            endIcon=
            {<Icon>add</Icon>} 
            onClick={() => handleClickOpen()}>
        Novo
        </Button>

        {posts.map(post => ( 
            
            <BoxContent 
                key={post.id}
                id={post.id}
                autor={post.autor}
                imagem={post.imagem}
                data={post.dataCriacao}
                link={post.link}
                tipoConteudo={post.tipoConteudo}
                titulo={post.titulo}
                descricao={post.descricao}
            />
        ))}
         
         {posts.length == 0 && (
            <Box
                position="relative"
                gap={1} 
                paddingY={3}
                paddingX={3}
                component={Paper}
                marginBottom={2}
            >   
                <Typography variant="h3" textAlign="center">Sem conteúdos para listar.</Typography>
                <img src={Ops} alt="Ops... 404" style={{ maxWidth: "200px", margin: "20px auto", display: "block" }} />
            </Box>
         )}
 

        <DialogPost openState={open} eventOpenState={setOpen} handleClose={handleClose} />
    </>
    )
}