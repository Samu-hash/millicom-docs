import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux';
import { addToCart } from '../store/card';
import imageMap from '../utils/imagesMap';

const Detail = () => {
    const { slug } = useParams();
    const products = useSelector(state => state.products.list);
    const [detail, setDetail] = useState([]);
    const [quantity, setQuantity] = useState(1);
    const dispatch = useDispatch();

    useEffect(() => {
        const found = products.find(product => product.slugDetail === slug);
        if (found) {
            setDetail(found);
        } else {
            window.location.href = '/';
        }
    }, [slug, products]);


    const handleMinusQuantity = () => {
        setQuantity(prev => Math.max(prev - 1, 1));
    };

    const handlePlusQuantity = () => {
        setQuantity(prev => prev + 1);
    };

    const handleAddToCart = () => {
        if (detail) {
            dispatch(addToCart(
                { productId: detail.idProduct, quantity, product: detail}));
        }
    };

    if (!detail) return null;

    const imageName = detail.urlProduct?.replace('./assets/images/', '') || 'default.png';
    const urlImage = imageMap[imageName] || imageMap['default.png'];

    return (
        <div>
            <h2 className='text-3xl text-center'>PRODUCT DETAIL</h2>
            <div className='grid grid-cols-2 gap-5 mt-5'>
                <div>
                    <img src={urlImage} alt="" className='w-full' />
                </div>
                <div className='flex flex-col gap-5'>
                    <h1 className='text-4xl uppercase font-bold'>{detail.title}</h1>
                    <p className='font-bold text-3xl'>
                        ${detail.priceTotal}
                    </p>
                    <div className='flex gap-5'>
                        <div className='flex gap-2 justify-center items-center'>
                            <button className='bg-gray-100 h-full w-10 font-bold text-xl rounded-xl flex justify-center items-center' onClick={handleMinusQuantity}>-</button>
                            <span className='bg-gray-200 h-full w-10 font-bold text-xl rounded-xl flex justify-center items-center'>{quantity}</span>
                            <button className='bg-gray-100 h-full w-10 font-bold text-xl rounded-xl flex justify-center items-center' onClick={handlePlusQuantity}>+</button>
                        </div>
                        <button className='bg-slate-900 text-white px-7 py-3 rounded-xl shadow-2xl' onClick={handleAddToCart}>
                            Add To Cart
                        </button>
                    </div>
                    <div className="product-details-container"
                        dangerouslySetInnerHTML={{ __html: detail.details }} />
                </div>
            </div>
        </div>
    )
}

export default Detail