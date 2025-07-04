import { useEffect, useState, useContext } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import { AuthContext } from "../../contexts/AuthContext";

export default function PedidoDetalhes() {
  const { user } = useContext(AuthContext);
  const { id } = useParams();
  const navigate = useNavigate();
  const location = useLocation();

  const dataCriadoFromState = location.state?.dataCriado;
  const totalFromState = location.state?.total;

  const [itens, setItens] = useState([]);
  const [pedido, setPedido] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!user) return;

    async function fetchItensComChaves() {
      try {
        const resItens = await fetch(`/tatifurtando/Itenspedidos/byPedido/${id}`);
        if (!resItens.ok) throw new Error("Erro ao buscar itens do pedido");
        const itensData = await resItens.json();

        setPedido(itensData[0]?.pedido ?? null);

        const itensComChaves = await Promise.all(
          itensData.map(async (item) => {
            const resChaves = await fetch(`/tatifurtando/chavejogo/porItemPedido/${item.id}`);
            if (!resChaves.ok) return { ...item, chaves: [] };
            const chaves = await resChaves.json();
            return { ...item, chaves };
          })
        );

        setItens(itensComChaves);
      } catch (e) {
        setError(e.message);
      } finally {
        setLoading(false);
      }
    }

    fetchItensComChaves();
  }, [id, user]);

  if (!user) return <div>Você precisa estar logado.</div>;
  if (loading) return <div>Carregando detalhes do pedido...</div>;
  if (error) return <div style={{ color: "red" }}>Erro: {error}</div>;

  const dataParaExibir = pedido?.dataCriado ?? dataCriadoFromState ?? null;
  const totalParaExibir = pedido?.total ?? totalFromState ?? null;

  return (
    <div
      style={{
        maxWidth: 1100,
        margin: "20px auto",
        fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
        color: "#222",
        padding: 20,
        background: "#f9f9f9",
        borderRadius: 8,
        boxShadow: "0 0 12px rgba(0,0,0,0.1)",
      }}
    >
      <button
        onClick={() => navigate(-1)}
        style={{
          marginBottom: 20,
          background: "#eee",
          border: "none",
          borderRadius: 4,
          padding: "8px 14px",
          cursor: "pointer",
          fontWeight: "600",
          transition: "background-color 0.3s ease",
        }}
        onMouseEnter={(e) => (e.currentTarget.style.backgroundColor = "#ddd")}
        onMouseLeave={(e) => (e.currentTarget.style.backgroundColor = "#eee")}
        aria-label="Voltar"
      >
        ← Voltar
      </button>

      <div
        style={{
          marginBottom: 30,
          padding: 16,
          backgroundColor: "#e6f0ff",
          borderRadius: 8,
          boxShadow: "inset 0 0 8px rgba(0,71,171,0.15)",
        }}
      >
        <h2 style={{ margin: "0 0 8px 0", color: "#0047ab" }}>
          Pedido #{pedido?.id ?? id}
        </h2>
        <p style={{ margin: "4px 0" }}>
          <strong>Data:</strong>{" "}
          {dataParaExibir
            ? new Date(dataParaExibir).toLocaleDateString()
            : "N/A"}
        </p>
        <p style={{ margin: "4px 0" }}>
          <strong>Total:</strong>{" "}
          {totalParaExibir
            ? `R$ ${parseFloat(totalParaExibir).toFixed(2)}`
            : "N/A"}
        </p>
      </div>

      <h3 style={{ marginBottom: 16, color: "#0047ab" }}>Itens do pedido</h3>

      {itens.length === 0 && (
        <p style={{ fontStyle: "italic", color: "#666" }}>
          Nenhum item encontrado neste pedido.
        </p>
      )}

      <div style={{ display: "flex", flexDirection: "column", gap: 20 }}>
        {itens.map((item) => (
          <div
            key={item.id}
            style={{
              backgroundColor: "#fff",
              padding: 16,
              borderRadius: 8,
              boxShadow: "0 2px 6px rgba(0,0,0,0.1)",
            }}
          >
            <p style={{ margin: "0 0 8px 0", fontWeight: "600", fontSize: 16 }}>
              {item.jogo?.nome || "Sem nome"}
            </p>
            <p style={{ margin: "0 0 8px 0" }}>
              <strong>Quantidade:</strong> {item.quantidade}
            </p>
            <p style={{ margin: "0 0 8px 0" }}>
              <strong>Preço:</strong> R$ {parseFloat(item.precoItems).toFixed(2)}
            </p>
            <div>
              <strong>Chaves:</strong>
              {item.chaves && item.chaves.length > 0 ? (
                <div
                  style={{
                    marginTop: 10,
                    display: "flex",
                    flexDirection: "column",
                    gap: 12,
                  }}
                >
                  {item.chaves.map((chave, index) => (
                    <div key={chave.id}>
                      <div
                        style={{
                          fontSize: 13,
                          color: "#555",
                          fontWeight: "bold",
                          marginBottom: 4,
                        }}
                      >
                        Chave {index + 1}:
                      </div>
                      <div
                        style={{
                          overflowX: "auto",
                          backgroundColor: "#f0f4ff",
                          border: "1px solid #d0e0ff",
                          borderRadius: 4,
                          padding: "6px 8px",
                        }}
                      >
                        <code
                          style={{
                            fontFamily: "monospace",
                            whiteSpace: "nowrap",
                            color: "#0047ab",
                            fontSize: 14,
                          }}
                        >
                          {chave.chave}
                        </code>
                      </div>
                    </div>
                  ))}
                </div>
              ) : (
                <em style={{ display: "block", marginTop: 6, color: "#666" }}>
                  Sem chaves
                </em>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}