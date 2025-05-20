import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import {findAllPays} from '../service/users'

const MisCompras = () => {
  const user = useSelector(store => store.user.value);
  const [compras, setCompras] = useState([]);

    useEffect(() => {
        findAllPays(user.identity)
          .then(pays => {
            dispatch(setCompras(pays.data));
          })
          .catch(error => {
            console.error("Error loading pays:", error);
          });
      }, [user]);

  if (!user) {
    return <p className="p-4">Debes iniciar sesión para ver tus compras.</p>;
  }

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-4">Mis Compras</h2>
      {compras.length === 0 ? (
        <p>No tienes compras registradas.</p>
      ) : (
        <ul className="list-disc pl-5">
          {compras.map((compra, idx) => (
            <li key={idx}>
              {compra.producto} - {compra.fecha} - ${compra.total}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default MisCompras;
