import { useContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../contexts/AuthContext";

export default function PedidosPagos() {
  const { user } = useContext(AuthContext);
  const [pedidosPagos, setPedidosPagos] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    if (!user) return;

    async function fetchPedidos() {
      try {
        const res = await fetch(`/tatifurtando/pedidos/user/${user.id}/pagos`);
        if (!res.ok) throw new Error("Erro ao carregar pedidos pagos");
        const data = await res.json();
        setPedidosPagos(data);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    }

    fetchPedidos();
  }, [user]);

  if (!user) return <div style={{ textAlign: "center", marginTop: 40 }}>Você precisa estar logado.</div>;
  if (loading) return <div style={{ textAlign: "center", marginTop: 40 }}>Carregando pedidos pagos...</div>;

  return (
    <div
      style={{
        display: "flex",
        maxWidth: 900,
        margin: "20px auto",
        fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
        color: "#222",
        gap: 20,
      }}
    >
      {/* Lista de pedidos */}
      <div
        style={{
          flex: 2,
          background: "#f9f9f9",
          padding: 20,
          borderRadius: 8,
          boxShadow: "0 0 10px rgb(0 0 0 / 0.1)",
        }}
      >
        <h2 style={{ marginBottom: 15, color: "#0047ab" }}>Meus Pedidos Pagos</h2>

        {pedidosPagos.length === 0 ? (
          <p style={{ color: "#666", fontStyle: "italic" }}>Nenhum pedido pago encontrado.</p>
        ) : (
          <table style={{ width: "100%", borderCollapse: "collapse" }}>
            <thead>
              <tr
                style={{
                  borderBottom: "2px solid #ddd",
                  textAlign: "left",
                  backgroundColor: "#e6f0ff",
                }}
              >
                <th style={{ padding: "8px" }}>Pedido</th>
                <th style={{ padding: "8px" }}>Data</th>
                <th style={{ padding: "8px" }}>Total</th>
                <th style={{ padding: "8px", width: 130 }}></th>
              </tr>
            </thead>
            <tbody>
              {pedidosPagos.map((pedido) => (
                <tr key={pedido.id} style={{ borderBottom: "1px solid #eee" }}>
                  <td style={{ padding: "10px 8px", verticalAlign: "middle" }}>{pedido.id}</td>
                  <td style={{ padding: "10px 8px", verticalAlign: "middle" }}>
                    {new Date(pedido.dataCridado || pedido.dataCriado).toLocaleDateString()}
                  </td>
                  <td style={{ padding: "10px 8px", verticalAlign: "middle", fontWeight: "bold" }}>
                    R$ {parseFloat(pedido.total).toFixed(2)}
                  </td>
                  <td style={{ padding: "10px 8px", verticalAlign: "middle" }}>
                    <button
                      onClick={() =>
                        navigate(`/pedido-detalhes/${pedido.id}`, {
                          state: {
                            dataCriado: pedido.dataCriado,
                            total: pedido.total,
                          },
                        })
                      }
                      style={{
                        background: "#0047ab",
                        color: "white",
                        border: "none",
                        borderRadius: 6,
                        padding: "8px 14px",
                        cursor: "pointer",
                        fontSize: 14,
                        fontWeight: "600",
                        transition: "background-color 0.3s ease",
                      }}
                      onMouseEnter={(e) => (e.currentTarget.style.backgroundColor = "#003080")}
                      onMouseLeave={(e) => (e.currentTarget.style.backgroundColor = "#0047ab")}
                      aria-label={`Ver detalhes do pedido ${pedido.id}`}
                    >
                      Ver detalhes
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      {/* Resumo do usuário */}
      <aside
        style={{
          flex: 1,
          background: "#f9f9f9",
          padding: 20,
          borderRadius: 8,
          boxShadow: "0 0 10px rgb(0 0 0 / 0.1)",
          height: "fit-content",
        }}
      >
        <h3 style={{ color: "#0047ab", marginBottom: 20 }}>Usuário</h3>
        <div style={{ marginBottom: 10, fontSize: 16 }}>
          <strong>Nome:</strong> {user.nome}
        </div>
        <div style={{ fontSize: 16 }}>
          <strong>E-mail:</strong> {user.email}
        </div>
      </aside>
    </div>
  );
}