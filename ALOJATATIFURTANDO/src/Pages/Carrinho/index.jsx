import { useContext, useState, useEffect } from "react";
import { Navigate, useNavigate} from "react-router-dom";
import { AuthContext } from "../../contexts/AuthContext";

export default function Carrinho() {
  const { user } = useContext(AuthContext);
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  if (!user) {
    return <Navigate to="/login-required" replace />;
  }

  async function continuarCompra() {
    if(cartItems.length === 0) {
      alert("Seu carrinho está vazio!");
      return;
    }
    
    // Redireciona para a tela de finalização passando os dados
    navigate("/finalizando-pagamento", { state: { cartItems, total: totalValor } });
  }

  useEffect(() => {
    async function fetchPedidosPendentes() {
        try {
        const response = await fetch(`/tatifurtando/pedidos/user/${user.id}/pendentes`);
        if (!response.ok) throw new Error("Erro ao carregar pedidos");

        const pedidos = await response.json();
        if (pedidos.length === 0) {
            setCartItems([]);
            setLoading(false);
            return;
        }

        const pedido = pedidos[0]; // pega o pedido pendente
        const itensResponse = await fetch(`/tatifurtando/Itenspedidos/byPedido/${pedido.id}`);
        if (!itensResponse.ok) throw new Error("Erro ao carregar itens");

        const itens = await itensResponse.json();

        const itensFormatados = itens.map(item => {
        console.log(itens);
            return {
                id: item.id,
                nome: item.jogo.nome,
                valor: parseFloat(item.precoItems),
                quantidade: item.quantidade,
                idPedido: pedido.id,
                jogo: item.jogo
            };
        });

        setCartItems(itensFormatados);
        setLoading(false);
        } catch (error) {
        console.error(error);
        setLoading(false);
        }
    }

    fetchPedidosPendentes();
    }, [user.id]);


  if (loading) return <div>Carregando carrinho...</div>;

  const totalValor = cartItems.reduce((acc, item) => acc + item.valor, 0);

  async function removerItem(idItem) {
        try {
            const response = await fetch(`/tatifurtando/Itenspedidos/delete/${idItem}`, {
            method: "DELETE",
            });

            if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Erro ao remover item");
            }

            // Se deu certo no back-end, removemos localmente
            setCartItems(prevItems => prevItems.filter(item => item.id !== idItem));
        } catch (error) {
            console.error("Erro ao remover item do pedido:", error.message);
            alert("Erro ao remover item do pedido.");
        }
    }

  return (
    <div style={{
      display: "flex",
      maxWidth: 900,
      margin: "20px auto",
      fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
      color: "#222",
    }}>
      {/* Lista de itens */}
      <div style={{
        flex: 2,
        background: "#f9f9f9",
        padding: 20,
        borderRadius: 8,
        marginRight: 20,
        boxShadow: "0 0 10px rgb(0 0 0 / 0.1)"
      }}>
        <h2 style={{marginBottom: 15, color: "#0047ab"}}>Meu carrinho</h2>

        <table style={{width: "100%", borderCollapse: "collapse"}}>
          <thead>
            <tr style={{textAlign: "left", borderBottom: "2px solid #ddd"}}>
              <th>Item</th>
              <th style={{width: 100}}>Valor</th>
            </tr>
          </thead>
          <tbody>
            {cartItems.map(item => (
              <tr key={item.id} style={{borderBottom: "1px solid #eee"}}>
                <td style={{padding: "10px 5px", verticalAlign: "middle", display: "flex", alignItems: "center", gap: 10}}>
                  <div>
                    <strong>{item.nome}</strong><br />
                    <small style={{color: "#888", fontSize: 12}}>QUANTIDADE: {item.quantidade}</small><br />
                    <button
                      onClick={() => removerItem(item.id)}
                      style={{
                        marginTop: 5,
                        background: "none",
                        border: "none",
                        color: "#d11a2a",
                        cursor: "pointer",
                        fontSize: 14,
                        padding: 0,
                        textDecoration: "underline",
                      }}
                    >
                      Remover
                    </button>
                  </div>
                </td>
                <td style={{textAlign: "right", fontWeight: "bold"}}>R$ {item.valor.toFixed(2)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Resumo do carrinho */}
      <div style={{
        flex: 1,
        background: "#f9f9f9",
        padding: 20,
        borderRadius: 8,
        boxShadow: "0 0 10px rgb(0 0 0 / 0.1)",
        height: "fit-content"
      }}>
        <h3 style={{color: "#0047ab", marginBottom: 20}}>Resumo do carrinho</h3>
        <div style={{marginBottom: 10, fontSize: 16}}>
          <strong>Itens:</strong> {cartItems.length}
        </div>
        <div style={{marginBottom: 30, fontSize: 16}}>
          <strong>Total a pagar:</strong> <span style={{fontWeight: "bold"}}>R$ {totalValor.toFixed(2)}</span>
        </div>
        <button onClick={continuarCompra} style={{
          width: "100%",
          backgroundColor: "#d11a2a",
          border: "none",
          padding: "12px 0",
          color: "white",
          fontWeight: "bold",
          borderRadius: 6,
          cursor: "pointer",
          fontSize: 16,
          boxShadow: "0 4px 6px rgb(209 26 42 / 0.5)",
          transition: "background-color 0.3s ease",
        }}
        onMouseEnter={e => e.currentTarget.style.backgroundColor = "#b01020"}
        onMouseLeave={e => e.currentTarget.style.backgroundColor = "#d11a2a"}
        >
          Continuar compra →
        </button>
        <div style={{marginTop: 10, fontSize: 12, color: "#666", display: "flex", alignItems: "center", gap: 5}}>
          <svg xmlns="http://www.w3.org/2000/svg" fill="#666" width="16" height="16" viewBox="0 0 24 24"><path d="M12 0C5.371 0 0 5.371 0 12s5.371 12 12 12 12-5.371 12-12S18.629 0 12 0zm0 21.818A9.818 9.818 0 1 1 21.818 12 9.83 9.83 0 0 1 12 21.818zM12 5.455a6.545 6.545 0 0 0-6.545 6.545A6.555 6.555 0 0 0 12 18.545a6.545 6.545 0 0 0 6.545-6.545A6.555 6.555 0 0 0 12 5.455zm0 11.455a4.909 4.909 0 1 1 4.909-4.909A4.915 4.915 0 0 1 12 16.91z"/></svg>
          Compra segura e protegida
        </div>
      </div>
    </div>
  );
}
