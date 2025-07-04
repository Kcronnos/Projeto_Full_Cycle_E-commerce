import { useEffect, useContext, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../contexts/AuthContext";

export default function PagamentoSucesso() {
  const navigate = useNavigate();
  const { user } = useContext(AuthContext);
  const isConfirmedRef = useRef(false);

  useEffect(() => {
    if (!user?.id) {
      console.warn("Usuário não autenticado - redirecionando para login");
      navigate("/login");
      return;
    }

    if (isConfirmedRef.current) return;

    const controller = new AbortController();

    async function confirmarPagamento() {
      try {
        const response = await fetch(`/tatifurtando/pedidos/${user.id}/finalizar`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({}),
          signal: controller.signal,
        });

        if (!response.ok) throw new Error("Erro ao confirmar pagamento");

        isConfirmedRef.current = true;

        navigate("/meus-pedidos");
      } catch (err) {
        if (err.name === "AbortError") {
          console.log("Requisição abortada");
        } else {
          console.error("Erro ao finalizar pagamento:", err);
          alert("Erro ao processar confirmação de pagamento.");
          navigate("/meus-pedidos");
        }
      }
    }

    confirmarPagamento();

    return () => {
      controller.abort();
    };
  }, [navigate, user]);

  return (
    <div style={{ textAlign: "center", padding: 20 }}>
      <p>Processando pagamento, aguarde...</p>
    </div>
  );
}