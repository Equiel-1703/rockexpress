import './styles/App.css';

import { Routes, Route } from 'react-router-dom';

import Header from './components/Header';
import MiniHeaderLayout from './components/MiniHeaderLayout';

import HomePage from './pages/HomePage';
import CartPage from './pages/CartPage';
import CheckoutPage from './pages/CheckoutPage';
import LoginPage from './pages/LoginPage';
import ProductPage from './pages/ProductPage';
import ReviewsPage from './pages/ReviewsPage';
import SellerDashboardPage from './pages/SellerDashboardPage';
import RegisterPage from './pages/RegisterPage';

function App() {
  return (
    <div className="app">
      <Routes>
        <Route path="/" element={
          <>
            <Header />
            <HomePage />
          </>
        }
        />

        <Route element={<MiniHeaderLayout />}>
          <Route path="/produto/:id" element={<ProductPage />} />
          <Route path="/carrinho" element={<CartPage />} />
          <Route path="/checkout" element={<CheckoutPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/reviews" element={<ReviewsPage />} />
          <Route path="/minha-loja" element={<SellerDashboardPage />} />
          <Route path="/cadastro" element={<RegisterPage />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;