import { BrowserRouter, Route, Routes } from "react-router-dom";
import Pages from "./Pages";
import Navbar from "@Components/Navbar";

function App() {
  return (
    <BrowserRouter>
    <Navbar />
      <Routes>
        {Pages.map(({ path, component: Component }, index) => (
          <Route key={index} path={path} element={<Component />} />
        ))}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
