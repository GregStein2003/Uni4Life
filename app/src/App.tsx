import "./shared/forms/TraducoesYup";
import { Welcome } from "./shared/components";
import { BrowserRouter } from "react-router-dom";
import { AppThemeProvider, AppDrawerProvider } from "./shared/contexts/"
import { AppRoutes } from "./routes";

function App() {
  return (
      <AppThemeProvider>
        <Welcome>
          <AppDrawerProvider>
            <BrowserRouter>
              <AppRoutes />
            </BrowserRouter>
          </AppDrawerProvider>
        </Welcome>
      </AppThemeProvider>
  )
}

export default App
