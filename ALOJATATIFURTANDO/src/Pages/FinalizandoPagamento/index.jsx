import { useEffect, useState } from "react";
import { initMercadoPago, CardPayment } from "@mercadopago/sdk-react";
import { useLocation, useNavigate } from "react-router-dom";

export default function FinalizandoPagamento() {
  const location = useLocation();
  const navigate = useNavigate();
  const { total } = location.state || { total: 0 };
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    initMercadoPago("TEST-9af4072a-6e25-4a49-9367-5237e6a8491c");
  }, []);

  const onSubmit = async (formData) => {
    if (isSubmitting) return;
    setIsSubmitting(true);

    try {
      const response = await fetch("/tatifurtando/Itenspedidos/process_payment", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          token: formData.token,
          paymentMethodId: formData.payment_method_id,
          payerEmail: formData.payer.email,
          amount: total,
        }),
      });

      const result = await response.json();
      console.log("Resposta completa do pagamento:", result);

      const status = result.status || result.body?.status;

      if (status === "approved") {
        navigate("/pagamento-sucesso");
      } else {
        navigate("/pagamento-erro");
      }
    } catch (error) {
      console.error("Erro ao processar pagamento", error);
      navigate("/pagamento-erro");
    } finally {
      setIsSubmitting(false);
    }
  };

  const onError = (error) => {
    console.error("Erro no Brick:", error);
    alert("Erro no formulário de pagamento");
  };

  const onReady = () => {
    console.log("Brick pronto para usar");
  };

  return (
    <div style={{ padding: 30 }}>
      <h2>Finalizando pagamento</h2>
      <CardPayment
        initialization={{ amount: total }}
        onSubmit={onSubmit}
        onReady={onReady}
        onError={onError}
        disabled={isSubmitting}
      />
    </div>
  );
}