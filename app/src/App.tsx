import "./shared/forms/TraducoesYup";
import { Welcome } from "./shared/components";
import { BrowserRouter } from "react-router-dom";
import { AppThemeProvider, AppDrawerProvider, AuthProvider } from "./shared/contexts/"
import { AppRoutes } from "./routes";

function App() {
  return (
    <AuthProvider>
        <AppThemeProvider>
          <Welcome>
            <AppDrawerProvider>
              <BrowserRouter>
                <AppRoutes />
              </BrowserRouter>
            </AppDrawerProvider>
          </Welcome>
        </AppThemeProvider>
      </AuthProvider>
  )
}

export default App
