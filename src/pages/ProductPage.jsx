import { useParams } from 'react-router-dom';

export default function ProductPage() {
  const { id } = useParams();

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold">Produto #{id}</h1>
      <p className="mt-4">Informações do produto aqui.</p>
      <button className="mt-4 px-4 py-2 bg-black text-white rounded">
        Adicionar ao Carrinho
      </button>
    </div>
  );
}
