import React from 'react';
import { useSelector } from 'react-redux';

const MisDatos = () => {
  const user = useSelector(store => store.user.value);

  if (!user) {
    return <p className="p-4">Debes iniciar sesión para ver tus datos.</p>;
  }

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-4">Mis Datos</h2>
      <p><strong>Usuario:</strong> {user.username}</p>
      <p><strong>Email:</strong> {user.email}</p>
    </div>
  );
};

export default MisDatos;
