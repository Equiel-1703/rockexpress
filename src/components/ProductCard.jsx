import '../styles/ProductCard.css';

import { useNavigate, Link } from 'react-router-dom';
import NumberFormatter from '../utils/NumberFormatter';

export default function ProductCard({ product }) {
  const navigate = useNavigate();

  const handleAddToCart = () => {
    navigate('/carrinho');
  };

  return (
    <div className="product-card">
      <Link to={`/produto/${product.id}`} className="unstyled-link">
        <img src={product.images[0]} alt={product.name} className="product-img" />
        <h3>{product.name}</h3>
      </Link>
      <p>R$ {NumberFormatter.format(product.price)}</p>
      <button onClick={handleAddToCart}>Adicionar ao carrinho</button>
    </div>
  );
}
