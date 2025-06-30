import {BrowserRouter, Route, Routes} from "react-router-dom";
import Pages from "./Pages";

function App(){

    return (
        <BrowserRouter>
        <Routes>
            {
                Pages.map((page, index) => (
                    <Route path={} element={page.component} />
                ))
            }
        </Routes>
        </BrowserRouter>
    )
}