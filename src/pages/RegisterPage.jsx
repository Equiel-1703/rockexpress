import React, { useState } from "react";
import "../styles/RegisterPage.css";
import { Link } from "react-router-dom";

const RegisterPage = () => {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [confirmarSenha, setConfirmarSenha] = useState("");

  const handleRegister = (e) => {
    e.preventDefault();

    if (senha !== confirmarSenha) {
      alert("As senhas não coincidem.");
      return;
    }

    console.log("Criar conta:", { nome, email, senha });
    // Aqui você pode chamar sua API para criar conta
  };

  return (
    <div className="register-page">
      <div className="register-box">
        <h2>Crie sua conta</h2>
        <p className="register-subtitle">Cadastro com Email</p>

        <form onSubmit={handleRegister} className="register-form">
          <input
            type="text"
            placeholder="Nome"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            required
          />
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Senha"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Confirmar Senha"
            value={confirmarSenha}
            onChange={(e) => setConfirmarSenha(e.target.value)}
            required
          />

          <button type="submit" className="register-button">
            CRIAR CONTA
          </button>
        </form>

        <p className="login-link">
          <Link to="/login">Já tem uma conta? Faça login</Link>
        </p>
      </div>
    </div>
  );
};

export default RegisterPage;
