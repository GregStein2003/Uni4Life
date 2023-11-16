import { useField } from "@unform/core";
import { useEffect, useRef, useState } from "react";
import {  Avatar, Grid, Icon, TextField, TextFieldProps} from "@mui/material";

type TVTextFieldProps = TextFieldProps & {
    name: string;
}

export const VTextUpload: React.FC<TVTextFieldProps> = ({ name, ...rest }) => {
    const inputRef = useRef(null);
    const { fieldName, registerField, defaultValue, error, clearError } = useField(name);
    const [value, setValue] = useState(defaultValue || "");
    const [imgValue, setImgValue] = useState("");
    const [showPreview, setShowPreview] = useState(false);

    useEffect(() => {
        registerField({
          name: fieldName,
          getValue: () => value,
          setValue: (_, newValue) => setValue(newValue),
        });
      }, [registerField, fieldName, value]);

    const onChange = (e) => {
        const files = e.target.files;
        const file = files[0]
        getBase64(file);
    }

    const onLoad = (fileString) => {
        setValue(fileString);
        setImgValue(fileString);
        setShowPreview(true)
    }

    const getBase64 = (file) => {
        let reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
            onLoad(reader.result);
        }
    }

    return (
        <>
            <Grid container item direction="row" spacing={2}>
                <Grid item xs={12} sm={9}>
                <TextField
                    {...rest}
                    defaultValue={defaultValue}
                    onKeyDown={(e) => { () => error && clearError(); rest.onKeyDown?.(e); clearError()}}
                    error={!!error}
                    helperText={error}
                    InputProps={{
                        readOnly: true,
                        endAdornment: (
                            <>
                                <input
                                type="file"
                                accept="image/png, image/gif, image/jpeg"
                                onChange={onChange}
                                style={{
                                    position: 'absolute',
                                    top: 0,
                                    right: 0,
                                    bottom: 0,
                                    left: 0,
                                    opacity: 0,
                                }}
                                />
                                <Icon sx={{ fontSize: "3rem", color: "#262d63"}}>cloud_upload</Icon>
                            </>
                        ),
                    }}
                />                           
                </Grid>
                {showPreview && (
                    <Grid item xs={12} sm={3} display="flex" alignItems="center" justifyContent="center">
                        <Avatar alt="Imagem" src={imgValue} sx={{ width: 56, height: 56 }} />
                    </Grid>
                )}
            </Grid>


        </>
    );
};