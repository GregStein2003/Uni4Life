import { BrowserRouter } from "react-router-dom";
import { Login } from "./shared/components";
import { AppRoutes } from "./routes";

function App() {
  return (
      <Login>
          <AppRoutes />
      </Login>
  )
}

export default App
