import { useEffect, useState } from "react";
import axios from "axios";
import CardJogo from "../../Components/CardJogo";
import { Banner } from "flowbite-react";
import InicioBanner from "../../Components/Banner";

export default function Home() {
  const [jogos, setJogos] = useState([]);

  useEffect(() => {
    axios.get("/tatifurtando/jogos/showAll")
      .then(res => setJogos(res.data))
      .catch(err => console.error("Erro ao buscar jogos:", err));
  }, []);

  return (
    <div>
      <InicioBanner />
      <div className="p-6 grid gap-6 grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 justify-items-center">
        {jogos.map((jogo) => (
          <CardJogo key={jogo.id} jogo={jogo} />
        ))}
      </div>
    </div>
  );
}