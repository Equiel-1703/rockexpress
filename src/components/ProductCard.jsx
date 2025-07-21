import './ProductCard.css';

export default function ProductCard({ product }) {
  return (
    <div className="product-card">
      <h2>{product.name}</h2>
      <p>R$ {product.price}</p>
      <button>Adicionar ao carrinho</button>
    </div>
  );
}
