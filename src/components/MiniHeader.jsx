import '../styles/Header.css';

import { Link } from 'react-router-dom';
import ShoppingCartRoundedIcon from '@mui/icons-material/ShoppingCartRounded';
import TextField from '@mui/material/TextField';
import InputAdornment from '@mui/material/InputAdornment';
import SearchRoundedIcon from '@mui/icons-material/SearchRounded';

export default function Header() {
  return (
    <header className="header">
      <div className="mini-header">
        <Link to="/">
          <img
            className='mini-header-logo'
            src="/logo-rockexpress-semfundo.png"
            alt="Logo RockExpress"
          />
        </Link>

        <nav className="nav mini-header-nav">
          <TextField
            id="searchbar"
            placeholder="Buscar produtos"
            sx={{
              width: '100%',
              '& .MuiInputBase-root': {
                color: '#fff',
              },
              '& .MuiInputLabel-root': {
                color: '#fff',
              },
              '& .MuiInput-underline:before': {
                borderBottomColor: '#fff',
              },
              '& .MuiInput-underline:after': {
                borderBottomColor: '#fff',
              },
              '& .MuiInputAdornment-root': {
                color: '#fff',
              },
            }}
            slotProps={{
              input: {
                startAdornment: (
                  <InputAdornment position="start">
                    <SearchRoundedIcon sx={{ color: '#fff' }} />
                  </InputAdornment>
                ),
              },
            }}
            variant="standard"
          />
          <Link to="/">HOME</Link>
          <Link to="/carrinho">CARRINHO</Link>
          <Link to="/checkout">CHECKOUT</Link>
          <Link to="/login">LOGIN/CADASTRO</Link>

          <Link to="/carrinho">
            <ShoppingCartRoundedIcon className='mini-header-shopping-cart' />
          </Link>
        </nav>
      </div>
    </header>
  );
}
