import { Login } from "./shared/components";
import { AppThemeProvider } from "./shared/contexts/"
import { AppRoutes } from "./routes";

function App() {
  return (
      <AppThemeProvider>
        <Login>
            <AppRoutes />
        </Login>
      </AppThemeProvider>
  )
}

export default App
