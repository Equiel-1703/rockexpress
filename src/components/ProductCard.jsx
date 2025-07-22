import '../styles/ProductCard.css';

import { useNavigate, Link } from 'react-router-dom';

export default function ProductCard({ product }) {
  const navigate = useNavigate();

  const handleAddToCart = () => {
    navigate('/carrinho');
  };

  return (
    <div className="product-card">
      <Link to={`/produtos/${product.id}`} className="unstyled-link">
        <img src={product.image} alt={product.name} className="product-img" />
        <h3>{product.name}</h3>
      </Link>
      <p>R$ {product.price}</p>
      <button onClick={handleAddToCart}>Adicionar ao carrinho</button>
    </div>
  );
}
