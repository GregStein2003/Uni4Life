import { useField } from "@unform/core";
import ReactInputMask from "react-input-mask";
import { useEffect, useState, useRef } from "react";
import { Icon, InputAdornment, TextField, TextFieldProps } from "@mui/material";

type TVTextFieldProps = TextFieldProps & {
    name: string;
}

export const VTextTelMask: React.FC<TVTextFieldProps> = ({ name, ...rest }) => {
    const inputRef = useRef(null);
    const { fieldName, registerField, defaultValue, error, clearError } = useField(name);
    const [value, setValue] = useState(defaultValue || "");

    useEffect(() => {
      registerField({
        name: fieldName,
        ref: inputRef.current,
        path: "value",
        setValue(ref, value) {
          ref.setInputValue(value);
        },
        clearValue(ref) {
          ref.setInputValue("");
        }
      });
    }, [fieldName, registerField, value]);

    return (
      <>
        <ReactInputMask
          mask="(99)99999-9999"
          maskPlaceholder={null}
          placeholder={`Telefone: `}
          ref={inputRef}
          defaultValue={defaultValue}
          className={!!error ? "default-style__input default-style__input--error" : "default-style__input" }
          onChange={e => {setValue(e.target.value); rest.onChange?.(e);}}
          onKeyDown={(e) => { () => error && clearError(); rest.onKeyDown?.(e); clearError()}}
          {...rest}
        />

        {error && 
          <span className="default-style__error">{error}</span>
        }
      </>
    );
};