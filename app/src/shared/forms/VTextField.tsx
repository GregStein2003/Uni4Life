import { useField } from "@unform/core";
import { useEffect, useState } from "react";
import { Icon, InputAdornment, TextField, TextFieldProps } from "@mui/material";

type TVTextFieldProps = TextFieldProps & {
    name: string;
}

export const VTextField: React.FC<TVTextFieldProps> = ({ name, ...rest }) => {
    const { fieldName, registerField, defaultValue, error, clearError } = useField(name);
    const [value, setValue] = useState(defaultValue || "");

    useEffect(() => {
        registerField({
          name: fieldName,
          getValue: () => value,
          setValue: (_, newValue) => setValue(newValue),
        });
      }, [registerField, fieldName, value]);

    return (
        <TextField 
            {...rest}
            error={!!error}
            helperText={error}
            InputProps={!!error ? {
                startAdornment: (
                  <InputAdornment position="end" sx={{ position: "absolute", right: "10px" }}>
                    <Icon fontSize="large" color="error">error</Icon>
                  </InputAdornment>
                ),
            } : undefined}
            defaultValue={defaultValue}
            value={value}
            onChange={e => {setValue(e.target.value); rest.onChange?.(e);}}
            onKeyDown={(e) => { () => error && clearError(); rest.onKeyDown?.(e); clearError()}}
        />
    );
};