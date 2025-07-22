import React, { useState } from 'react';
import './ProductPage.css';

const ProductPage = () => {
  const [selectedSize, setSelectedSize] = useState('');
  const [selectedColor, setSelectedColor] = useState('');
  const [quantity, setQuantity] = useState(2);

  return (
    <div className="product-page">
      <h2 className="breadcrumb">DETALHE PRODUTO</h2>

      <div className="product-container">
        <div className="product-images">
          <img src="/public/produtos/img1.png" alt="Produto" />
          <img src="/public/produtos/img2.png" alt="Produto" />
          <div className="placeholder"></div>
          <div className="placeholder"></div>
        </div>

        <div className="product-details">
          <h1 className="product-title">
            Camiseta Cleiton Rasta <span className="external-icon">↗</span>
          </h1>
          <p className="product-price">R$ 79,99</p>
          <p className="product-description">
            Chama Chama Chama Chama Chama Chama Chama
          </p>

          <div className="section">
            <p className="label">Cores</p>
            <div className="color-options">
              <div
                className={`color green ${selectedColor === 'green' ? 'selected' : ''}`}
                onClick={() => setSelectedColor('green')}
              ></div>
              <div
                className={`color black ${selectedColor === 'black' ? 'selected' : ''}`}
                onClick={() => setSelectedColor('black')}
              ></div>
            </div>
          </div>

          <div className="section">
            <p className="label">Tamanho</p>
            <div className="size-options">
              {['PP', 'P', 'M', 'G', 'GG', 'XG'].map((size) => (
                <button
                  key={size}
                  className={`size-button ${selectedSize === size ? 'active' : ''}`}
                  onClick={() => setSelectedSize(size)}
                >
                  {size}
                </button>
              ))}
            </div>
          </div>

          <div className="action-row">
            <button className="add-to-cart">Adicionar ao carrinho</button>
            <div className="quantity-control">
              <button onClick={() => setQuantity(q => Math.max(1, q - 1))}>−</button>
              <span>{quantity}</span>
              <button onClick={() => setQuantity(q => q + 1)}>+</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductPage;
