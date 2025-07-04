import { useNavigate } from "react-router-dom";

export default function PagamentoErro() {
  const navigate = useNavigate();

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="bg-white rounded-2xl shadow-md p-8 text-center max-w-sm w-full">
        <h2 className="text-2xl font-semibold text-red-600 mb-4">Erro ao processar pagamento</h2>
        <p className="text-gray-700 mb-6">
          Algo deu errado ao finalizar sua compra. Tente novamente ou verifique seu carrinho.
        </p>
        <button
          onClick={() => navigate("/carrinho")}
          className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
        >
          Retornar ao carrinho
        </button>
      </div>
    </div>
  );
}