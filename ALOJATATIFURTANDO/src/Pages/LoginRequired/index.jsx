import { Link } from "react-router-dom";

export default function LoginRequired() {
  return (
    <div className="flex flex-col items-center justify-center h-screen text-center p-4">
      <h2 className="text-2xl font-bold mb-4">Login Necessário</h2>
      <p className="mb-6">Você precisa estar logado para acessar essa página.</p>
      <Link
        to="/login"
        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
      >
        Ir para Login
      </Link>
    </div>
  );
}