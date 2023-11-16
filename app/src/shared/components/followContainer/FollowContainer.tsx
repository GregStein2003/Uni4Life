import { Box, Paper } from "@mui/material"
import { useEffect, useState } from "react";
import { useMediaQuery, useTheme } from "@mui/material";
import { FollowItem } from "../followItem/FollowItem";

export const FollowContainer: React.FC = () => {
    const theme = useTheme();
    const mdDown = useMediaQuery(theme.breakpoints.down("md"));

    return (
        <>
            <Box
                padding={2}
                width={mdDown ? "100%" : "75%"}
                component={Paper}
                elevation={3}
            >
                <FollowItem  />
            </Box>
        </>
        )
} 