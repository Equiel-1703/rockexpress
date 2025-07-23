import "../styles/ProductPage.css";

import { useState } from "react";
import { useParams } from 'react-router-dom';
import NumberFormattter from "../utils/NumberFormatter";

// Importing mock data for products just for testing purposes
import products from '../placeholders/products';

const ProductPage = () => {
  const [selectedSize, setSelectedSize] = useState("");
  const [selectedColor, setSelectedColor] = useState("");
  const [quantity, setQuantity] = useState(1);

  const urlParams = useParams();
  const product = products.find(p => p.id === parseInt(urlParams.id));

  return (
    <div className="product-page">
      <h2 className="breadcrumb">DETALHE PRODUTO</h2>
      <div className="product-container">
        <div className="product-images">
          {
            product.images
              .map(
                (image, index) => (
                  <img key={index} src={image} alt={`Produto ${index + 1}`} />
                ))
          }
          {
            Array
              .from({ length: 4 - product.images.length })
              .map((_, index) => (
                <div key={index} className="placeholder"></div>
              ))
          }

          {/* <img src="/produtos/img1.png" alt="Produto" />
          <img src="/produtos/img2.png" alt="Produto" />
          <div className="placeholder"></div>
          <div className="placeholder"></div> */}
        </div>

        <div className="product-details">
          <h1 className="product-title">
            {product.name}
            <span className="external-icon">↗</span>
          </h1>
          <p className="product-price">R$ {NumberFormattter.format(product.price)}</p>
          <p className="product-description">{product.description}</p>

          {/* <div className="section">
            <p className="label">Cores</p>
            <div className="color-options">
              <div
                className={`color black ${selectedColor === "black" ? "selected" : ""
                  }`}
                onClick={() => setSelectedColor("black")}
              ></div>
              <div
                className={`color green ${selectedColor === "green" ? "selected" : ""
                  }`}
                onClick={() => setSelectedColor("green")}
              ></div>
            </div>
          </div> */}

          <div className="section">
            <p className="label">Tamanho</p>
            <div className="size-options">
              {["PP", "P", "M", "G", "GG", "XG"].map((size) => (
                <button
                  key={size}
                  className={`size-button ${selectedSize === size ? "active" : ""
                    }`}
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
              <button onClick={() => setQuantity((q) => Math.max(1, q - 1))}>
                −
              </button>
              <span>{quantity}</span>
              <button onClick={() => setQuantity((q) => q + 1)}>+</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductPage;
