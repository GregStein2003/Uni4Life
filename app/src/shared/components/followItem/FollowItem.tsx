import { useEffect, useState } from "react";
import Loader from "../../../images/loader.gif"
import { Avatar, Box, Button, Icon, Typography } from "@mui/material"
import { TFollowTotalCount, IFollowReturn, FollowService } from "../../services/api/follow/FollowService";


interface IFollowItemProps {
    dataNascimento?: Date;
    dataRelacionamento?: Date;
    email?: string;
    id: string;
    nome: string;
    registroAcademico: string;
    segmento?: string;
    seguidores: number;
    telefone?: string;
    tipoConta?: string;
    seguido: string;
    imagem?: string;
    updateList: (() => void) | undefined;
}

const ListItem: React.FC<IFollowItemProps> = ({ id, nome, registroAcademico, seguidores, seguido, imagem, updateList }) => {
  const [isFollowed, setIsFollowed] = useState<any>(seguido);
  const nameUser = nome.split(" ");
  const abrevName = nameUser[0] + " " + nameUser[1];
  const avatarImage = "data:image/png;base64,"+imagem;

  const addFollow = (id: string ) => {
    FollowService.add(id).then(result => {
      setIsFollowed(true)
      updateList(`true ${id}`);
    }).catch(error => {
        console.log("Error: ", error)
    })
}

  const removeFollow = (id: string ) => {
    FollowService.remove(id).then(result => {
      setIsFollowed(false);
      updateList(`false ${id}`);
    }).catch(error => {
        console.log("Error: ", error)
    })
  }

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
    <Box display="flex" alignItems="center" justifyContent="space-between" sx={{ cursor: "pointer" }} rowGap={1}>
      <Box display="flex" alignItems="center" gap={2} flexWrap="wrap">
        <Avatar alt="Remy Sharp" src={avatarImage} sx={{ width: 56, height: 56 }}/>
        <Box>
          <Typography variant="h5" fontWeight="700">{abrevName}</Typography>
          <Typography variant="body2">@{registroAcademico}</Typography>
          <Typography variant="caption">Seguidores: <strong>{seguidores}</strong></Typography>
        </Box>
      </Box>
      
        {isFollowed && (
          <Button variant="contained" sx={buttonSubmit} onClick={() => removeFollow(id)} endIcon={<Icon>bookmark</Icon>}>
            Seguido
          </Button>
        )}

        {!isFollowed && (
          <Button variant="contained" sx={buttonSubmit} onClick={() => addFollow(id)} endIcon={<Icon>bookmark_border</Icon>}>
            Seguir
          </Button>
        )}
    </Box>
  )
}

export const FollowItem: React.FC = () => {
  const [followers, setFollowers] = useState<TFollowTotalCount[] | IFollowReturn | any>([]);
  const [updateList, setUpdateList] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
      setIsLoading(true);
      FollowService.getAll().then(result => {
          if(result instanceof Error){
              setFollowers([]);
          }else {
              setFollowers(result.conteudo);
          }
          setIsLoading(false);
      });
  }, [updateList]);

  const triggerUpdateList = (value) => {
    setUpdateList(value)
  }

    return (
      <>
          <Box display="flex" flexDirection="column" gap={1} marginTop={1}>
            {followers.map((follow: { id: string; seguido: string; nome: string; imagem: string; registroAcademico: string; seguidores: number }) => ( 
              <ListItem
                key={follow.id}
                id={follow.id}
                seguido={follow.seguido}
                nome={follow.nome}
                imagem={follow.imagem}
                registroAcademico={follow.registroAcademico}
                seguidores={follow.seguidores}
                updateList={triggerUpdateList}
              />
            ))}
          </Box>
          {isLoading && (
            <Box sx={{ width: "100vw", height: "100vh", bgcolor: "white", position: "fixed", opacity: ".7", zIndex: 99999, top: 0, left: 0, display: "grid", placeItems: "center" }}>
                <img src={Loader} alt="Loader" />
            </Box>
          )}
      </>
    )
}