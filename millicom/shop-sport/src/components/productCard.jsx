import React from 'react' 
import { Link } from 'react-router-dom';
import iconCart from '../assets/images/iconCard.png'
import { useSelector, useDispatch } from 'react-redux';
import { addToCart } from '../store/card';
import imageMap from '../utils/imagesMap';

const ProductCart = (props) => {
    const carts = useSelector(store => store.cart.items);
    const {idProduct, title, priceTotal, urlProduct, slugDetail} = props.data;

    const imageName = urlProduct?.replace('./assets/images/', '') || 'default.png';
    const imageSrc = imageMap[imageName] || imageMap['default.png'];
    const dispatch = useDispatch();
    const handleAddToCart = () => {
        dispatch(addToCart({
            productId: idProduct,
            quantity: 1,
            product: props.data
        }));
    }
    return (
    <div className='bg-white p-5 rounded-xl shadow-sm'>
        <Link to={slugDetail}>
            <img src={imageSrc} alt='' className='w-full h-80 object-cover object-top drop-shadow-[0_80px_30px_#0007]' />
        </Link>
        <h3 className='text-3xl py-3 text-center font-medium'>{title}</h3>
        <div className='flex justify-between items-center'>
            <p>
                $<span className='text-2xl font-medium'>{priceTotal}</span>
            </p>
            <button className='bg-gray-300 p-2 rounded-md text-sm hover:bg-gray-400 flex gap-2' onClick={handleAddToCart}>
                <img src={iconCart} alt="" className='w-5'/>
                Add To Cart
            </button>
        </div>
    </div>
  )
}

export default ProductCart