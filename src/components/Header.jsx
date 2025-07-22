import '../styles/Header.css';

import { Link } from 'react-router-dom';
import ShoppingCartRoundedIcon from '@mui/icons-material/ShoppingCartRounded';
import TextField from '@mui/material/TextField';
import InputAdornment from '@mui/material/InputAdornment';
import SearchRoundedIcon from '@mui/icons-material/SearchRounded';

export default function Header() {
  return (
    <header className="header">
      <div className="header-upper">
        <div className="header-logo-text">
          <Link to="/">
            <img
              className='logo'
              src="/logo-rockexpress-semfundo.png"
              alt="Logo RockExpress"
            />
          </Link>
          <h1>Bem vindo(a) ao <br /> <strong>RockExpress</strong></h1>
        </div>

        <Link to="/carrinho">
          <ShoppingCartRoundedIcon className='header-shopping-cart' />
        </Link>
      </div>
      <nav className="nav">
        <Link to="/">HOME</Link>
        <Link to="/carrinho">CARRINHO</Link>
        <Link to="/checkout">CHECKOUT</Link>

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

        <Link to="/login">LOGIN/CADASTRO</Link>
      </nav>
    </header>
  );
}
