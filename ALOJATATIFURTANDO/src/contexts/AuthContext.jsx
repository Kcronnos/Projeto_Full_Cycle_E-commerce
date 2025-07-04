import React, { createContext, useState, useEffect } from 'react'

// Cria o contexto
export const AuthContext = createContext()

// Provedor do contexto
export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)

  // Tenta carregar usuário do localStorage ao iniciar
  useEffect(() => {
    const storedUser = localStorage.getItem('usuario')
    if (storedUser) {
      setUser(JSON.parse(storedUser))
    }
  }, [])

  // Função para logar usuário e salvar no localStorage
  function login(userData) {
    setUser(userData)
    localStorage.setItem('usuario', JSON.stringify(userData))
  }

  // Função para deslogar usuário
  function logout() {
    setUser(null)
    localStorage.removeItem('usuario')
  }

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}
