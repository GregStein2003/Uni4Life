import "./shared/forms/TraducoesYup";
import { Welcome } from "./shared/components";
import { AppThemeProvider } from "./shared/contexts/"
import { AppRoutes } from "./routes";

function App() {
  return (
      <AppThemeProvider>
        <Welcome>
            <AppRoutes />
        </Welcome>
      </AppThemeProvider>
  )
}

export default App
