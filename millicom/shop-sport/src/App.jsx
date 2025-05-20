import React, { useState, useEffect } from 'react';
import './App.css';
import { tokenService } from './service/token';
import Layout from './components/layout';
import Home from './pages/home';
import Detail from './pages/detail';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MisDatos from './pages/MisDatos';
import MisCompras from './pages/MisCompras';

function App() {
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchAndStoreToken = async () => {
      try {
        const response = await tokenService();
        if (response?.data) {
          localStorage.setItem('token', response.data);
          console.log('Token actualizado');
        }
      } catch (error) {
        console.error('Error al refrescar token', error);
      } finally {
        setLoading(false); // ya cargó, renderiza la app
      }
    };

    fetchAndStoreToken();

    // Refrescar cada 2 minutos (120000 ms)
    const interval = setInterval(fetchAndStoreToken, 120000);

    return () => clearInterval(interval);
  }, []);

  if (loading) {
    return <div className="text-center mt-10">Cargando aplicación...</div>;
  }

  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Layout />}>
          <Route index element={<Home />} />
          <Route path='/:slug' element={<Detail />} />
          <Route path="/mis-datos" element={<MisDatos />} />
          <Route path="/mis-compras" element={<MisCompras />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
