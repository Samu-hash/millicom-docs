import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import iconCart from '../assets/images/iconCard.png'
import login from '../assets/images/login.png'
import user from '../assets/images/user.png'
import { useSelector, useDispatch } from 'react-redux'
import { toggleStatusTab } from '../store/card'
import { loginService } from "../service/login";
import { createService } from '../service/createAcount';

const Header = () => {
  const [totalQuantity, setTotalQuantity] = useState(0);
  const carts = useSelector(store => store.cart.items);
  const dispatch = useDispatch();

  const [isLogged, setIsLogged] = useState(false);
  const [showLoginModal, setShowLoginModal] = useState(false);
  const [showRegisterModal, setShowRegisterModal] = useState(false);
  const [showUserMenu, setShowUserMenu] = useState(false);
  const [showPurchasesModal, setShowPurchasesModal] = useState(false);
  const [userData, setUserData] = useState(null);
  const [showUserDataModal, setShowUserDataModal] = useState(false);

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const [registerData, setRegisterData] = useState({
    name: '',
    lastname: '',
    email: '',
    password: ''
  });
  const [registerError, setRegisterError] = useState('');
  const [registerSuccess, setRegisterSuccess] = useState('');

  useEffect(() => {
    let total = 0;
    carts.forEach(item => total += item.quantity);
    setTotalQuantity(total);
  }, [carts]);

  const handleOpenTabCart = () => {
    dispatch(toggleStatusTab());
  }

  const handleOpenPurchases = () => {
    const data = localStorage.getItem('dataUser');
    if (data) {
      try {
        const parsed = JSON.parse(data);
        setUserData(parsed);
        setShowPurchasesModal(true);
      } catch (e) {
        console.error("Error al parsear dataUser:", e);
      }
    }
  };

  const handleOpenUserData = () => {
    const data = localStorage.getItem('dataUser');
    if (data) {
      try {
        const parsed = JSON.parse(data);
        setUserData(parsed);
        setShowUserDataModal(true);
      } catch (e) {
        console.error("Error al parsear dataUser:", e);
      }
    }
  };

  const handleLoginButtonClick = () => {
    if (isLogged) {
      setShowUserMenu(!showUserMenu);
      setShowLoginModal(false);
    } else {
      setShowLoginModal(true);
      setShowUserMenu(false);
    }
  }

  const handleLogin = async () => {
    if (username.trim() === '' || password.trim() === '') {
      setError('Por favor ingresa usuario y contraseña');
      return;
    }

    try {
      const response = await loginService(username, password);
      if (response.code === 200 && response.data) {
        // Suponiendo que la respuesta contiene un token
        localStorage.setItem('dataUser', JSON.stringify(response.data));

        setIsLogged(true);
        setShowLoginModal(false);
        setUsername('');
        setPassword('');
        setError('');
      } else {
        setError('Credenciales incorrectas o respuesta inválida');
      }
    } catch (err) {
      console.error(err);
      setError('Error al iniciar sesión. Intenta de nuevo.');
    }
  }

  const handleLogout = () => {
    setIsLogged(false);
    setShowUserMenu(false);
    localStorage.removeItem('dataUser');
    setUsername(null);
  }

  const handleRegister = async () => {
    const { name, lastname, email, password } = registerData;

    if (!name || !lastname || !email || !password) {
      setRegisterError('Todos los campos son obligatorios');
      return;
    }

    try {
      const response = await createService(name, lastname, email, password);

      if (response.code === 200) {
        setRegisterSuccess('Usuario creado correctamente. Ahora inicia sesión.');
        setRegisterError('');
        setRegisterData({
          name: response.data.name,
          lastname: response.data.lastname,
          email: response.data.email,
          password: response.data.password
        });

        setTimeout(() => {
          setShowRegisterModal(false);
          setShowLoginModal(true);
          setRegisterSuccess('');
        }, 1500);
      } else {
        setRegisterError('No se pudo crear el usuario. Intenta más tarde.');
      }
    } catch (error) {
      console.error(error);
      setRegisterError('Error en el servidor. Intenta de nuevo.');
    }
  };

  return (
    <header className='flex justify-between items-center mb-5 relative z-50'>
      <Link to="/" className='text-xl font-semibold'>Home.</Link>
      <div className='flex items-center gap-4'>
        <div
          className='w-10 h-10 bg-gray-100 rounded-full flex justify-center items-center relative cursor-pointer'
          onClick={handleOpenTabCart}
        >
          <img src={iconCart} alt="cart" className='w-6' />
          <span className='absolute top-2/3 right-1/2 bg-red-500 text-white text-sm w-5 h-5 rounded-full flex justify-center items-center'>
            {totalQuantity}
          </span>
        </div>

        <button
          onClick={handleLoginButtonClick}
          className="w-10 h-10 bg-gray-100 rounded-full flex justify-center items-center relative cursor-pointer"
        >
          {isLogged ? <img src={user} alt="cart" className='w-6' /> : <img src={login} alt="cart" className='w-6' />}
        </button>
      </div>

      {/* Modal Login */}
      {showLoginModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
          <div className="bg-white p-6 rounded shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Iniciar sesión</h2>
            {error && <p className="text-red-600 text-sm mb-2">{error}</p>}
            <div className="mb-4">
              <label className="block text-gray-700 text-sm mb-1">Usuario</label>
              <input
                type="text"
                value={username}
                onChange={e => setUsername(e.target.value)}
                className="w-full px-3 py-2 border rounded"
                placeholder="usuario"
              />
            </div>
            <div className="mb-4">
              <label className="block text-gray-700 text-sm mb-1">Contraseña</label>
              <input
                type="password"
                value={password}
                onChange={e => setPassword(e.target.value)}
                className="w-full px-3 py-2 border rounded"
                placeholder="contraseña"
              />
            </div>
            <div className="flex justify-between items-center">
              <button
                onClick={handleLogin}
                className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
              >
                Iniciar sesión
              </button>
              <button
                onClick={() => setShowLoginModal(false)}
                className="px-4 py-2 border rounded hover:bg-gray-100"
              >
                Cancelar
              </button>
            </div>
            <p className="mt-4 text-sm text-center">
              ¿No tienes cuenta?{' '}
              <button
                className="text-blue-600 hover:underline"
                onClick={() => {
                  setShowLoginModal(false);
                  setShowRegisterModal(true);
                }}
              >
                Crear una cuenta
              </button>
            </p>
          </div>
        </div>
      )}

      {/* Modal Registro */}
      {showRegisterModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
          <div className="bg-white p-6 rounded shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Crear cuenta</h2>
            {registerError && <p className="text-red-600 text-sm mb-2">{registerError}</p>}
            {registerSuccess && <p className="text-green-600 text-sm mb-2">{registerSuccess}</p>}
            <div className="mb-3">
              <label className="block text-sm">Nombre</label>
              <input
                type="text"
                value={registerData.name}
                onChange={e => setRegisterData({ ...registerData, name: e.target.value })}
                className="w-full px-3 py-2 border rounded"
              />
            </div>
            <div className="mb-3">
              <label className="block text-sm">Apellido</label>
              <input
                type="text"
                value={registerData.lastname}
                onChange={e => setRegisterData({ ...registerData, lastname: e.target.value })}
                className="w-full px-3 py-2 border rounded"
              />
            </div>
            <div className="mb-3">
              <label className="block text-sm">Correo electrónico</label>
              <input
                type="email"
                value={registerData.email}
                onChange={e => setRegisterData({ ...registerData, email: e.target.value })}
                className="w-full px-3 py-2 border rounded"
              />
            </div>
            <div className="mb-4">
              <label className="block text-sm">Contraseña</label>
              <input
                type="password"
                value={registerData.password}
                onChange={e => setRegisterData({ ...registerData, password: e.target.value })}
                className="w-full px-3 py-2 border rounded"
              />
            </div>
            <div className="flex justify-between">
              <button
                onClick={handleRegister}
                className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
              >
                Registrarse
              </button>
              <button
                onClick={() => setShowRegisterModal(false)}
                className="px-4 py-2 border rounded hover:bg-gray-100"
              >
                Cancelar
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Menú Usuario */}
      {showUserMenu && (
        <div className="absolute right-0 top-full mt-2 mr-4 bg-white shadow rounded w-48 z-50">
          <ul>
            <li className="px-4 py-2 hover:bg-gray-100 cursor-pointer" onClick={handleOpenUserData}>
              Mis datos
            </li>
            <li className="px-4 py-2 hover:bg-gray-100 cursor-pointer" onClick={handleOpenPurchases}>
              Mis Compras
            </li>
            <li
              className="px-4 py-2 hover:bg-gray-100 cursor-pointer text-red-600 font-semibold"
              onClick={handleLogout}
            >
              Cerrar sesión
            </li>
          </ul>
        </div>
      )}

      {showPurchasesModal && userData && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
          <div className="bg-white p-6 rounded shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Mis Datos</h2>
            <p><strong>Nombre:</strong> {userData.name}</p>
            <p><strong>Apellido:</strong> {userData.lastname}</p>
            <p><strong>Email:</strong> {userData.email}</p>
            {/* Puedes agregar más info si tienes */}
            <div className="mt-4 flex justify-end">
              <button
                onClick={() => setShowPurchasesModal(false)}
                className="px-4 py-2 border rounded hover:bg-gray-100"
              >
                Cerrar
              </button>
            </div>
          </div>
        </div>
      )}

      {showUserDataModal && userData && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
          <div className="bg-white p-6 rounded shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Datos del Usuario</h2>
            <p><strong>Nombre:</strong> {userData.name}</p>
            <p><strong>Apellido:</strong> {userData.lastname}</p>
            <p><strong>Email:</strong> {userData.email}</p>
            {/* Agrega más campos si tienes */}
            <div className="mt-4 flex justify-end">
              <button
                onClick={() => setShowUserDataModal(false)}
                className="px-4 py-2 border rounded hover:bg-gray-100"
              >
                Cerrar
              </button>
            </div>
          </div>
        </div>
      )}


    </header>
  );
};

export default Header;
