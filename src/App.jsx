import './App.css';
import { Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import CartPage from './pages/CartPage';
import CheckoutPage from './pages/CheckoutPage';
import LoginPage from './pages/LoginPage';
import ProductPage from './pages/ProductPage';
import ReviewsPage from './pages/ReviewsPage';
import SellerDashboardPage from './pages/SellerDashboardPage';
import Header from './components/Header';
import RegisterPage from './pages/RegisterPage';

function App() {
  return (
    <div className="app">
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/produto/:id" element={<ProductPage />} />
        <Route path="/carrinho" element={<CartPage />} />
        <Route path="/checkout" element={<CheckoutPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/reviews" element={<ReviewsPage />} />
        <Route path="/minha-loja" element={<SellerDashboardPage />} />
        <Route path="/cadastro" element={<RegisterPage />} />
      </Routes>
    </div>
  );
}

export default App;