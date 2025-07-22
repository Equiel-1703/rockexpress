import ProductCard from '../components/ProductCard';
import './HomePage.css';

const products = [
  { id: 1, name: 'Camiseta Taylor Swift', price: 99, image: '/produtos/camiseta-taylor-swift-xuxa.jfif' },
  { id: 2, name: 'Pipoca MC Pipokinha', price: 10, image: '/produtos/pipoca-mc-pipokinha.png' },
  { id: 3, name: 'Quadro Kanye West (Doação)', price: 0, image: '/produtos/ye.png' },
  { id: 4, name: 'Funko Pop Xuxa', price: 59, image: '/produtos/funko-ts.jfif' },
  { id: 5, name: 'CD CPM-22', price: 49, image: '/produtos/cd-cpm-22.png' },
  { id: 6, name: 'Quadro Jungkook BTS', price: 1199, image: '/produtos/jungkook copy.jpg' },
];


export default function HomePage() {
  return (
    <div className="home">
      <h1>Exibindo {products.length} Produtos</h1>
      <div className="product-grid">
        {products.map((p) => (
          <ProductCard key={p.id} product={p} />
        ))}
      </div>
    </div>
  );
}
