import { useTheme } from "@mui/material";
import { Box, Icon, InputAdornment, Paper, TextField } from "@mui/material"

export const SearchContainer: React.FC = () => {
    const theme = useTheme();

    return (
        <Box
            marginTop={2}
            display="flex"
            alignItems="center"
            justifyContent="flex-end"
            padding={2}
            width="75%"
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
        </Box>


    )
} 