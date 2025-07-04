import { useNavigate } from "react-router-dom";
import { AuthContext } from "../contexts/AuthContext";
import { useState, useEffect, useContext} from "react";

export default function CardJogo({ jogo }) {
  const navigate = useNavigate();
  const { user } = useContext(AuthContext);
  const userId = user?.id;
  const [modalVerMaisAberto, setModalVerMaisAberto] = useState(false);
  const [modalComprarAberto, setModalComprarAberto] = useState(false);
  const [quantidade, setQuantidade] = useState(1);

  
  useEffect(() => {
    if (modalVerMaisAberto || modalComprarAberto) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "";
    }
    return () => {
      document.body.style.overflow = "";
    };
  }, [modalVerMaisAberto, modalComprarAberto]);

  function abrirComprar() {
    setModalComprarAberto(true);
    setModalVerMaisAberto(false);
  }
  function fecharComprar() {
    setModalComprarAberto(false);
    setQuantidade(1);
  }

  
  function mudarQuantidade(e) {
    const val = e.target.value;
    if (val === "") {
      setQuantidade("");
      return;
    }
    const num = Number(val);
    if (!isNaN(num) && num >= 1) {
      setQuantidade(num);
    }
    
  }

  async function pegarPedidoPendente(userId) {
    const response = await fetch(`/tatifurtando/pedidos/user/${userId}/pendentes`);
    if (!response.ok) {
      throw new Error("Erro ao buscar pedidos pendentes");
    }
    const pedidos = await response.json();
    
    return pedidos.length > 0 ? pedidos[0] : null;
  }

  async function adicionarItemPedido(pedido, jogo, quantidade) {
    const response = await fetch(`/tatifurtando/Itenspedidos/register`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        quantidade,
        precoItems: jogo.preco * quantidade,
        pedido: pedido,
        jogo: jogo
      })
    });

    if (!response.ok) {
      throw new Error("Erro ao adicionar item ao pedido");
    }

    const data = await response.json();
    return data;
  }

  async function adicionarAoCarrinho() {
    try {
      
      const pedidoPendente = await pegarPedidoPendente(user.id);

      if (!pedidoPendente) {
        alert("Nenhum pedido pendente encontrado.");
        return;
      }

      await adicionarItemPedido(pedidoPendente, jogo, quantidade === "" ? 1 : quantidade);

      alert(`Adicionado ${quantidade}x ${jogo.nome} ao carrinho!`);
      fecharComprar();
    } catch (error) {
      alert(error.message);
    }
  }

  return (
    <>
      {/* Card */}
      <div className="bg-white rounded-lg shadow-md overflow-hidden w-72">
        <img
          src={jogo.imagemurl}
          alt={jogo.nome}
          className="w-full h-48 object-cover"
        />
        <div className="p-4">
          <h2 className="text-xl font-semibold">{jogo.nome}</h2>
          <p className="text-green-700 font-bold">
            R$ {Number(jogo.preco).toFixed(2)}
          </p>

          <div className="flex gap-26 mt-3">
            <button
              onClick={() => setModalVerMaisAberto(true)}
              className="text-blue-600 hover:underline"
            >
              Ver mais
            </button>
            <button
              onClick={() => {if (!user) {
                    navigate("/login-required");
                  } else {
                    setModalComprarAberto(true)
                  }
              }}
              className="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700"
            >
              Comprar
            </button>
          </div>
        </div>
      </div>

      {/* Modal Ver Mais */}
      {modalVerMaisAberto && (
        <div className="fixed inset-0 backdrop-blur-sm bg-black/20 flex items-center justify-center z-50 p-4">
          <div className="bg-white p-6 rounded-xl shadow-lg max-w-md w-full max-h-[90vh] overflow-auto relative">
            {/* Botão Fechar */}
            <div className="relative rounded-lg overflow-hidden mb-4">
              <img
                src={jogo.imagemurl}
                alt={jogo.nome}
                className="w-full h-60 object-cover rounded-lg mb-4"
              />

              <button
                onClick={() => setModalVerMaisAberto(false)}
                className="absolute top-0 right-0 bg-white rounded-md w-8 h-8 flex items-center justify-center text-gray-600 hover:bg-red-600 hover:text-white transition-colors"
              >
                &times;
              </button>
            </div>
            <h2 className="text-xl font-bold mb-1">Descrição</h2>
            <div className="text-sm text-gray-700 max-h-40 overflow-y-auto pr-2 mb-4">
              {jogo.descricao}
            </div>

            <div className="flex items-center justify-between">
              <p className="text-green-700 font-semibold text-lg">
                R$ {Number(jogo.preco).toFixed(2)}
              </p>
              <button
                onClick={() => {
                  if (!user) {
                    
                    navigate("/login-required");
                  } else {
                    abrirComprar();
                  }
                }}
                className="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700"
              >
                Comprar
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Modal Comprar */}
      {modalComprarAberto && (
        <div className="fixed inset-0 backdrop-blur-sm bg-black/20 flex items-center justify-center z-50 p-4">
          <div className="bg-white p-6 rounded-xl shadow-lg max-w-md w-full max-h-[90vh] overflow-auto relative">
            {/* Botão Fechar */}
            <div className="relative rounded-lg overflow-hidden mb-4">
              <img
                src={jogo.imagemurl}
                alt={jogo.nome}
                className="w-full h-60 object-cover"
              />
              <button
                onClick={fecharComprar}
                className="absolute top-0 right-0 m-2 bg-white rounded-md w-8 h-8 flex items-center justify-center text-gray-600 hover:bg-red-600 hover:text-white transition-colors"
              >
                &times;
              </button>
            </div>
            {/* Nome do jogo separado */}
            <h2 className="text-xl font-bold mb-4">{jogo.nome}</h2>

            {/* Linha com preço e quantidade lado a lado */}
            <div className="flex items-center justify-between mb-4">
              <p className="text-green-700 font-semibold text-lg">
                R$ {Number(jogo.preco).toFixed(2)}
              </p>

              <div className="flex items-center gap-2">
                <label
                  htmlFor="quantidade"
                  className="font-medium whitespace-nowrap"
                >
                  Quantidade:
                </label>
                <input
                  id="quantidade"
                  type="number"
                  min="1"
                  value={quantidade}
                  onChange={mudarQuantidade}
                  className="w-16 text-center border border-gray-300 rounded outline-none"
                  style={{
                    MozAppearance: "textfield",
                    WebkitAppearance: "none",
                    appearance: "none",
                  }}
                  onWheel={(e) => e.target.blur()}
                />
              </div>
            </div>

            <button
              onClick={adicionarAoCarrinho}
              className="bg-green-600 text-white px-4 py-2 rounded w-full hover:bg-green-700"
            >
              Adicionar ao carrinho
            </button>
          </div>
        </div>
      )}
    </>
  );
}