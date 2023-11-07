import { Avatar, Button, ListItemButton, ListItemIcon, ListItemText, Typography, useMediaQuery, useTheme } from "@mui/material";
import { Box, Icon, InputAdornment, Paper, TextField } from "@mui/material"

export const SearchContainer: React.FC = () => {
    const theme = useTheme();
    const mdDown = useMediaQuery(theme.breakpoints.down("md"));

    return (
        <Box
            padding={2}
            width={mdDown ? "100%" : "75%"}
            component={Paper}
            elevation={3}
        >
            <TextField 
                size="medium"
                fullWidth
                variant="outlined"
                placeholder="Busca..."
                value=""
                InputProps={{
                    endAdornment: (
                      <InputAdornment sx={{cursor: "pointer"}} position="end">
                        <Icon fontSize="large" color="primary" >search</Icon>
                      </InputAdornment>
                    ),
                  }}
                onChange={(e) => console.log(e)}
            />

            <Box marginTop={3} display="flex" alignItems="center" justifyContent="space-between" sx={{ cursor: "pointer" }} flexWrap="wrap" rowGap={1}>
              <Box display="flex" alignItems="center" gap={2} >
                <Avatar alt="Remy Sharp" src="/static/images/avatar/1.jpg" />
                <Box>
                  <Typography variant="h5" fontWeight="700">Gregory Stein</Typography>
                  <Typography variant="body2">@gregoryste</Typography>
                </Box>
              </Box>
              <Button variant="contained" color="primary" size="medium" sx={{ borderRadius: "25px" }} endIcon={<Icon>bookmark_border</Icon>}>Seguir</Button>
            </Box>
        </Box>

    )
} 