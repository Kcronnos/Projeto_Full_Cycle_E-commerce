import Home from "../Pages/Home";
import LoginPage from "../Pages/Login";
import Carrinho from "../Pages/Carrinho";
import Pedidos from "../Pages/Pedidos";
import PedidosDetalhes from "../Pages/PedidoDetalhes";
import FinalizandoPagamento from "../pages/FinalizandoPagamento";
import Cadastro from "./CadastroUsuario";
import PagamentoErro from "../Pages/PagamentoErro";
import PagamentoSucesso from "../Pages/PagamentoSucesso";
import ProtectedRoute from "@Components/ProtectedRoute";
import LoginRequired from "@Pages/LoginRequired";

const Pages = [
  {
    path: "/",
    component: Home
  },
  {
    path: "/finalizando-pagamento",
    component: FinalizandoPagamento
  },
  {
    path: "/pedido-detalhes/:id",
    component: PedidosDetalhes
  },
  {
    path: "/login",
    component: LoginPage
  },
  {
    path: "/cadastro",
    component: Cadastro
  },
  {
    path: "/meus-pedidos",
    component: () => (
      <ProtectedRoute>
        <Pedidos />
      </ProtectedRoute>
    )
  },
  {
  path: "/finalizando-pagamento",
  component: FinalizandoPagamento
  },
  {
    path: "/pagamento-erro",
    component: PagamentoErro
  },
  {
    path: "/pagamento-sucesso",
    component: PagamentoSucesso
  },
  {
    path: "/login-required",
    component: LoginRequired
  },
  {
    path: "/carrinho",
    component: () => (
      <ProtectedRoute>
        <Carrinho />
      </ProtectedRoute>
    )
  }
];

export default Pages;