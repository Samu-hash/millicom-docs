import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getAllProducts } from "../service/products";
import ProductCart from '../components/productCard'
import { setProducts } from '../store/productSlice';

const Home = () => {
  const dispatch = useDispatch();
  const products = useSelector(state => state.products.list);

  useEffect(() => {
    getAllProducts()
      .then(pro => {
        dispatch(setProducts(pro.data));
      })
      .catch(error => {
        console.error("Error loading products:", error);
      });
  }, [dispatch]);
  
  return (
    <div>
        <h1 className='text-3xl my-5'>List Products</h1>
        <div className='grid lg:grid-cols- md:grid-cols-3 sm:grid-cols-2 gap-5'>
            {products.map((product, key) => 
                <ProductCart key={key} data={product}/>
            )}
        </div>
    </div>
  )
}

export default Home