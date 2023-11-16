import { Autocomplete, TextField } from "@mui/material"
import { useField } from "@unform/core";
import { useEffect, useMemo, useState } from "react";

interface IAutoCompleteProps {
    name: string;
    label: string;
    options: any;
}

export const AutoComplete: React.FC<IAutoCompleteProps> = ({ name, label, options }) => {
    const { fieldName, registerField, defaultValue, error, clearError } = useField(name);
    const [selectedType, setSelectedType] = useState<any>(defaultValue);

    useEffect(() => {
        registerField({
            name: fieldName, 
            getValue: () => selectedType,
            setValue: (_, newSelectedType) => setSelectedType(newSelectedType)
        })
    }, [registerField, selectedType])

    const autoCompleteSelectedOption = useMemo(() => {
        if(!selectedType) return null;

        return selectedType;

    }, [selectedType]);

    return (
        <Autocomplete 
            openText="Abrir"
            closeText="Fechar"
            noOptionsText="Sem Opcoes"
            loadingText="Carregando..."
            disablePortal
            value={autoCompleteSelectedOption}
            options={options}
            onChange={(_, newValue) => {
                setSelectedType(newValue);
                clearError();
            }}

            renderInput={(params) => (
                <TextField
                    {...params}
                    error={!!error}
                    helperText={error}
                    label={label}
                    required
                />
            )}
        />
    )
}