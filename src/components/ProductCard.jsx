import { useNavigate } from 'react-router-dom';
import './ProductCard.css';

export default function ProductCard({ product }) {
  const navigate = useNavigate();

  const handleAddToCart = () => {
    navigate('/carrinho');
  };

  return (
    <div className="product-card">
      <img src={product.image} alt={product.name} className="product-img" />
      <h3>{product.name}</h3>
      <p>R$ {product.price}</p>
      <button onClick={handleAddToCart}>Adicionar ao carrinho</button>
    </div>
  );
}
