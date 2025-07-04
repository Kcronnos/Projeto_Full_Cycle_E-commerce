import { Link, useNavigate } from 'react-router-dom';
import carrinhoIcon from "../assets/carrinho.png";
import usuarioIcon from "../assets/usuario.png";
import { useContext, useState, useEffect } from 'react';
import { AuthContext } from '../contexts/AuthContext';

export default function Navbar() {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const [showLogoutModal, setShowLogoutModal] = useState(false);

  useEffect(() => {
    if (showLogoutModal) {
      // Bloqueia o scroll do body
      document.body.style.overflow = 'hidden';
    } else {
      // Restaura o scroll
      document.body.style.overflow = 'auto';
    }

    // Limpeza para caso o componente desmonte com modal aberto
    return () => {
      document.body.style.overflow = 'auto';
    };
  }, [showLogoutModal]);

  const handleLogout = () => {
    logout();
    setShowLogoutModal(false);
    navigate("/");
  };

  return (
    <>
      <nav className="flex justify-between items-center px-6 py-4 bg-blue-600 text-white shadow-md">
        <Link to="/">
          <h1 className="text-2xl font-bold hover:underline">TatiFurtando</h1>
        </Link>

        <div className="flex items-center gap-10">
          <Link to="/carrinho" className="flex items-center gap-2 hover:underline">
            <img src={carrinhoIcon} alt="Carrinho" title="Carrinho" className="w-6 h-6" />
          </Link>

          {user && (
            <Link to="/meus-pedidos" className="text-white hover:underline font-medium">
              Meus Jogos
            </Link>
          )}

          {!user && (
            <Link to="/login" className="flex items-center gap-2 hover:underline">
              <img src={usuarioIcon} alt="Usuário" title="Login/Cadastro" className="w-6 h-6" />
            </Link>
          )}

          {user && (
            <div className="flex items-center gap-2 ml-4">
              <span>Olá, {user.nome}!</span>
              <button
                onClick={() => setShowLogoutModal(true)}
                className="bg-white text-blue-600 px-2 py-1 rounded hover:bg-gray-200 transition"
              >
                Sair
              </button>
            </div>
          )}
        </div>
      </nav>

      {/* Modal de confirmação */}
      {showLogoutModal && (
        <div className="fixed inset-0 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-6 max-w-sm w-full shadow-lg text-center">
            <h2 className="text-lg font-semibold mb-4">Confirmação de Logout</h2>
            <p className="mb-6">Deseja realmente sair da conta?</p>
            <div className="flex justify-center gap-4">
              <button
                onClick={handleLogout}
                className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
              >
                Confirmar
              </button>
              <button
                onClick={() => setShowLogoutModal(false)}
                className="bg-gray-300 px-4 py-2 rounded hover:bg-gray-400 transition"
              >
                Cancelar
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}