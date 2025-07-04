import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function CadastroUsuario() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    nome: "",
    email: "",
    senha: "",
  });

  const [errors, setErrors] = useState({});
  const [success, setSuccess] = useState(false);
  const [loading, setLoading] = useState(false);

  // Valida os campos do formulário
  function validar() {
    const newErrors = {};
    if (!formData.nome.trim()) newErrors.nome = "Nome é obrigatório";
    if (!formData.email.trim()) newErrors.email = "Email é obrigatório";
    else if (!/\S+@\S+\.\S+/.test(formData.email)) newErrors.email = "Email inválido";
    if (!formData.senha.trim()) newErrors.senha = "Senha é obrigatória";
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  }

  async function handleSubmit(e) {
    e.preventDefault();
    if (!validar()) return;

    setLoading(true);

    try {
      // Verifica se email já existe
      const checkResponse = await fetch(`/tatifurtando/users/check-email?email=${encodeURIComponent(formData.email)}`);
      const emailExists = await checkResponse.json();

      if (emailExists) {
        setErrors(prev => ({ ...prev, email: "Este email já está cadastrado." }));
        setLoading(false);
        return;
      }

      // Se email não existe, faz o cadastro
      const response = await fetch("/tatifurtando/users/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        const msg = await response.text();
        alert("Erro no cadastro: " + msg);
        setLoading(false);
        return;
      }

      setSuccess(true);
    } catch (error) {
      alert("Erro ao cadastrar: " + error.message);
    } finally {
      setLoading(false);
    }
  }

  if (success) {
    return (
      <div style={styles.container}>
        <div style={styles.successBox}>
          <h2>Cadastro concluído com sucesso! 🎉</h2>
          <button style={styles.button} onClick={() => navigate("/login")}>
            Ok
          </button>
        </div>
      </div>
    );
  }

  return (
    <div style={styles.container}>
      <form onSubmit={handleSubmit} style={styles.form}>
        <h2>Cadastro de Usuário</h2>

        <label style={styles.label}>
          Nome:
          <input
            style={errors.nome ? { ...styles.input, borderColor: "red" } : styles.input}
            type="text"
            value={formData.nome}
            onChange={(e) => setFormData({ ...formData, nome: e.target.value })}
          />
          {errors.nome && <span style={styles.error}>{errors.nome}</span>}
        </label>

        <label style={styles.label}>
          Email:
          <input
            style={errors.email ? { ...styles.input, borderColor: "red" } : styles.input}
            type="email"
            value={formData.email}
            onChange={(e) => setFormData({ ...formData, email: e.target.value })}
          />
          {errors.email && <span style={styles.error}>{errors.email}</span>}
        </label>

        <label style={styles.label}>
          Senha:
          <input
            style={errors.senha ? { ...styles.input, borderColor: "red" } : styles.input}
            type="password"
            value={formData.senha}
            onChange={(e) => setFormData({ ...formData, senha: e.target.value })}
          />
          {errors.senha && <span style={styles.error}>{errors.senha}</span>}
        </label>

        <button style={styles.button} type="submit" disabled={loading}>
          {loading ? "Cadastrando..." : "Cadastrar"}
        </button>
      </form>
    </div>
  );
}

const styles = {
  container: {
    maxWidth: 400,
    margin: "50px auto",
    padding: 20,
    borderRadius: 8,
    boxShadow: "0 0 15px rgba(0,0,0,0.2)",
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    backgroundColor: "#fff",
  },
  form: {
    display: "flex",
    flexDirection: "column",
  },
  label: {
    marginBottom: 15,
    fontWeight: "bold",
    fontSize: 14,
    display: "flex",
    flexDirection: "column",
  },
  input: {
    padding: 8,
    fontSize: 14,
    borderRadius: 4,
    border: "1px solid #ccc",
    marginTop: 5,
  },
  error: {
    color: "red",
    fontSize: 12,
    marginTop: 3,
  },
  button: {
    marginTop: 20,
    padding: 10,
    fontSize: 16,
    backgroundColor: "#0047ab",
    color: "white",
    border: "none",
    borderRadius: 6,
    cursor: "pointer",
    fontWeight: "bold",
  },
  successBox: {
    textAlign: "center",
    padding: 30,
    borderRadius: 8,
    backgroundColor: "#d4edda",
    color: "#155724",
    boxShadow: "0 0 15px rgba(0, 128, 0, 0.3)",
  },
};