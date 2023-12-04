import { useField } from "@unform/core";
import { useEffect, useState } from "react";
import { Icon, InputAdornment, TextField, TextFieldProps,  } from "@mui/material";

type TVTextFieldProps = TextFieldProps & {
    name: string;
}

export const VTextFieldComment: React.FC<TVTextFieldProps> = ({ name, ...rest }) => {
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
            InputProps={{
              endAdornment: (
              <InputAdornment sx={{cursor: "pointer"}} position="end">
                  <Icon fontSize="large">send</Icon>
              </InputAdornment>
              ),
            }}
            defaultValue={defaultValue}
            value={value}
            onChange={e => {setValue(e.target.value); rest.onChange?.(e);}}
            onKeyDown={(e) => { () => error && clearError(); rest.onKeyDown?.(e); clearError()}}
        />
    );
};