import { Link } from 'react-router-dom';
import './Header.css';

export default function Header() {
  return (
    <header className="header">
      <h1 className="logo">RockExpress</h1>
      <nav className="nav">
        <Link to="/">Home</Link>
        <Link to="/carrinho">Carrinho</Link>
        <Link to="/checkout">Checkout</Link>
        <Link to="/login">Login</Link>
      </nav>
    </header>
  );
}
