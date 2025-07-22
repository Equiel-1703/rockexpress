import "../styles/LoginPage.css";

import React, { useState } from "react";
import { Link } from "react-router-dom";

const LoginPage = () => {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");

  const handleLogin = (e) => {
    e.preventDefault();
    console.log("Login:", email, senha);
    // aqui você pode chamar a API ou redirecionar
  };

  return (
    <div className="login-page">
      <div className="login-box">
        <h2>Olá, novamente!</h2>
        <p className="login-subtitle">Login com Email</p>

        <form onSubmit={handleLogin} className="login-form">
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

          <button type="submit" className="login-button">
            LOGAR
          </button>
        </form>

        <p className="register-link">
          <Link to="/cadastro">Ou crie sua conta</Link>
        </p>
      </div>
    </div>
  );
};

export default LoginPage;
