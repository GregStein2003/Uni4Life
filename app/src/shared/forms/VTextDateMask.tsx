import { useField, FormHandles } from "@unform/core";
import ReactInputMask from "react-input-mask";
import { useEffect, useState, useRef } from "react";
import { TextField, TextFieldProps } from "@mui/material";

type TVTextFieldProps = TextFieldProps & {
    name: string;
    formRef: FormHandles;
}

export const VTextDateMask: React.FC<TVTextFieldProps> = ({ name, formRef, ...rest }) => {
    const { fieldName, registerField, defaultValue, error, clearError } = useField(name);
    const [value, setValue] = useState(defaultValue || "");

    useEffect(() => {
      registerField({
        name: fieldName,
        getValue: () => value,
        setValue: (_, newValue) => setValue(newValue),
      });

    }, [fieldName, registerField, value]);

    return (
      <>
        <ReactInputMask
          mask="99/99/9999" 
          maskPlaceholder={null}
          placeholder={`Data de nascimento: `}
          ref={formRef}
          defaultValue={defaultValue}
          className={!!error ? "default-style__input default-style__input--error" : "default-style__input" }
          onChange={e => {setValue(e.target.value); rest.onChange?.(e);}}
          onKeyDown={(e) => { () => error && clearError(); rest.onKeyDown?.(e); clearError()}}
          value={value}
          {...rest}
        />

        {error && 
          <span className="default-style__error">{error}</span>
        }
      </>
    );
};