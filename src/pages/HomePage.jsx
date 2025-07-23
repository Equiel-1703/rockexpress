import '../styles/HomePage.css';

// Importing mock data for products
import products from '../placeholders/products';

import ProductCard from '../components/ProductCard';

export default function HomePage() {
  return (
    <main className="home">
      <h1>Exibindo {products.length} Produtos</h1>
      <div className="product-grid">
        {products.map((p) => (
          <ProductCard key={p.id} product={p} />
        ))}
      </div>
    </main>
  );
}
