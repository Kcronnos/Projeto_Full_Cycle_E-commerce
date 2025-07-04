import React, { useState, useContext, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { AuthContext } from '../../contexts/AuthContext'

export default function LoginPage() {
  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('')
  const [modalMessage, setModalMessage] = useState(null) // texto do modal
  const [modalType, setModalType] = useState(null) // 'success' ou 'error'
  const navigate = useNavigate()

  const { login } = useContext(AuthContext)

  // Bloqueia scroll quando modal está aberto
  useEffect(() => {
    if (modalMessage) {
      document.body.style.overflow = 'hidden'
    } else {
      document.body.style.overflow = 'auto'
    }
    return () => {
      document.body.style.overflow = 'auto'
    }
  }, [modalMessage])

  const handleLogin = async (e) => {
    e.preventDefault()

    try {
      const response = await fetch('/tatifurtando/users/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, senha }),
      })

      const data = await response.json()

      if (response.ok) {
        setModalType('success')
        setModalMessage('Login bem-sucedido!')
        login(data)
      } else {
        setModalType('error')
        setModalMessage(data.message || 'Email ou senha incorretos.')
      }
    } catch (error) {
      console.error('Erro na requisição:', error)
      setModalType('error')
      setModalMessage('Erro ao tentar fazer login. Tente novamente mais tarde.')
    }
  }

  // Fecha modal e navega para / quando sucesso
  const closeModal = () => {
    setModalMessage(null)
    if (modalType === 'success') {
      navigate('/')
    }
  }

  return (
    <>
      <div className="min-h-screen bg-gradient-to-br from-blue-500 to-indigo-700 flex items-center justify-center">
        <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-md">
          <h2 className="text-2xl font-bold mb-6 text-center text-gray-800">Login</h2>
          <form onSubmit={handleLogin} className="space-y-5">
            <div>
              <label className="block mb-1 text-gray-600">Email</label>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full border border-gray-300 px-4 py-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                required
              />
            </div>
            <div>
              <label className="block mb-1 text-gray-600">Senha</label>
              <input
                type="password"
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
                className="w-full border border-gray-300 px-4 py-2 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                required
              />
            </div>
            <button
              type="submit"
              className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700 transition"
            >
              Entrar
            </button>
          </form>

          {/* Área de cadastro */}
          <div className="mt-6 text-center">
            <p className="text-sm text-gray-600 mb-2">Não é cadastrado ainda?</p>
            <button
              onClick={() => navigate('/cadastro')}
              className="text-blue-600 hover:underline font-medium"
            >
              Cadastre-se
            </button>
          </div>
        </div>
      </div>

      {/* Modal de mensagem */}
      {modalMessage && (
        <div
          className="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-20 backdrop-blur-sm"
          style={{ backgroundColor: 'rgba(0, 0, 0, 0.15)' }}
        >
          <div
            className={`p-6 rounded-lg max-w-sm w-full text-center shadow-lg ${
              modalType === 'success' ? 'bg-green-100 text-green-900' : 'bg-red-100 text-red-900'
            }`}
          >
            <h2 className="text-xl font-semibold mb-4">
              {modalType === 'success' ? 'Sucesso' : 'Erro'}
            </h2>
            <p className="mb-6">{modalMessage}</p>
            <button
              onClick={closeModal}
              className={`px-6 py-2 rounded font-semibold ${
                modalType === 'success'
                  ? 'bg-green-600 text-white hover:bg-green-700'
                  : 'bg-red-600 text-white hover:bg-red-700'
              } transition`}
            >
              Fechar
            </button>
          </div>
        </div>
      )}
    </>
  )
}