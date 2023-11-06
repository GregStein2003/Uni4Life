import { Box, Icon, IconButton, InputAdornment, ListItemButton, ListItemIcon, ListItemText, Paper, TextField, Typography } from "@mui/material"
import "../../styles/Dashboard.css"

export const Dashboard: React.FC = () => {
    return (
        <Box
            gap={1} 
            marginX={1}
            paddingY={3}
            paddingX={3}
            component={Paper}
            bgcolor="#F9F9F9"
        >
            <Box display="flex" justifyContent="space-between" alignContent="flex-start" marginBottom={2}>
                <Box>
                    <span className="dashboard__name">Gregory Stein</span>
                    <span className="dashboard__data">02 de Outubro 14:03</span>
                </Box>
                <Box>
                    <IconButton onClick={() => console.log("teste")}>
                        <Icon fontSize="large" color="error">favorite</Icon>
                    </IconButton>
                    <IconButton onClick={() => console.log("teste")}>
                        <Icon fontSize="large">favorite_border</Icon>
                    </IconButton>
                    <IconButton onClick={() => console.log("teste")}>
                        <Icon fontSize="large">timeline</Icon>
                    </IconButton>
                </Box>
            </Box>

            <Box marginBottom={2}>
                <Typography variant="body1">
                    Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia,
                    molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum
                    numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium
                    optio, eaque rerum! Provident similique accusantium nemo autem. Veritatis
                    obcaecati tenetur iure eius earum ut molestias architecto voluptate aliquam
                    nihil, eveniet aliquid culpa officia aut! Impedit sit sunt quaerat, odit,
                    tenetur error, harum nesciunt ipsum debitis quas aliquid. Reprehenderit,
                    quia. Quo neque error repudiandae fuga? Ipsa laudantium molestias
                </Typography>
            </Box>

            <Box className="dashboard__icons-container">
                <Box className="dashboard__icon-item">
                    <Box>
                        <Icon fontSize="large">thumb_up_off_alt</Icon>
                        <Icon fontSize="large" sx={{display: "none"}}>thumb_up_alt</Icon>
                    </Box>
                    <span className="dashboard__icon-text">Curtir</span>  
                </Box>

                <Box className="dashboard__icon-item">
                    <Box>
                        <Icon fontSize="large">chat</Icon>
                    </Box>
                    <span className="dashboard__icon-text">Comentário</span>  
                </Box>

                <Box className="dashboard__icon-item">
                    <Box>
                        <Icon fontSize="large">reply</Icon>
                    </Box>
                    <span className="dashboard__icon-text">Compartilhar</span>  
                </Box>
            </Box>

        <Box
            marginTop={2}
            display="flex"
            alignItems="center"
            justifyContent="space-between"
            borderRadius={4}
        >
            <TextField 
                size="small"
                fullWidth
                placeholder="Insira um comentário"
                value=""
                InputProps={{
                    endAdornment: (
                      <InputAdornment sx={{cursor: "pointer"}} position="end">
                        <Icon fontSize="large">send</Icon>
                      </InputAdornment>
                    ),
                  }}
                onChange={(e) => console.log(e)}/>
        </Box>


        </Box>
    )
}