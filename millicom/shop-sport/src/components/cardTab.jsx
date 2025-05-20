import React from 'react'
import { useSelector, useDispatch } from 'react-redux'
import CartItem from './cardItem';
import { toggleStatusTab } from '../store/card';
import { savePayment } from '../service/users'

const CartTab = () => {
    const carts = useSelector(store => store.cart.items);
    const statusTab = useSelector(store => store.cart.statusTab);
    const dispatch = useDispatch();
    const handleCloseTabCart = () => {
        dispatch(toggleStatusTab());
    }

    const [showPaymentModal, setShowPaymentModal] = React.useState(false);
    const [paymentInfo, setPaymentInfo] = React.useState({
        cardNumber: '',
        cardName: '',
        expiry: '',
        cvv: '',
        address: ''
    });
    const [paymentError, setPaymentError] = React.useState('');
    const [paymentSuccess, setPaymentSuccess] = React.useState('');

    const handleCheckout = () => {
        setShowPaymentModal(true);
    };

    const handlePaymentSubmit = async () => {
        const data = localStorage.getItem('dataUser');
        const userId = data ? JSON.parse(data).identity : null;

        const productDetails = carts.map(item => ({
            productId: item.productId,
            quantity: item.quantity,
            price: item.product.priceTotal,
        }));

        const { cardNumber, cardName, expiry, cvv, address } = paymentInfo;

        if (!cardNumber || !cardName || !expiry || !cvv || !address) {
            setPaymentError('Por favor llena todos los campos');
            return;
        }

        try {

            const body = {
                'idUser': userId,
                'address': address,
                'details': productDetails,
                'payment':{
                    'cardNumber':cardNumber,
                    'cardName':cardName,
                    'expiry':expiry,
                    'cvv':cvv,
                }
            };

            console.log(body)

            /*const response = await savePayment(body);
            if (response.code === 200 && response.data) {

                setPaymentSuccess('¡Pago realizado con éxito!');
                setPaymentError('');
            } else {
                setError('No se pudo procesar la transaccion.');
            }*/
        } catch (err) {
            console.error(err);
            setError('Error al procesar la transaccio');
        }



        setTimeout(() => {
            setShowPaymentModal(false);
            setPaymentInfo({ cardNumber: '', cardName: '', expiry: '', cvv: '', address:'' });
            setPaymentSuccess('');
            dispatch(toggleStatusTab());
        }, 2000);
    };


    return (
        <>
            {/* CARRITO LATERAL */}
            <div className={`fixed top-0 right-0 bg-gray-700 shadow-2xl w-96 h-full grid grid-rows-[60px_1fr_60px] 
      transform transition-transform duration-500
      ${statusTab === false ? "translate-x-full" : ""}
    `}>
                <h2 className='p-5 text-white text-2xl'>Shopping Sport</h2>
                <div className='p-5 overflow-y-auto'>
                    {carts.map((item, key) =>
                        <CartItem key={key} data={item} />
                    )}
                </div>
                <div className='grid grid-cols-2'>
                    <button className='bg-black text-white' onClick={handleCloseTabCart}>CLOSE</button>
                    <button onClick={handleCheckout} className='bg-amber-600 text-white'>CHECKOUT</button>
                </div>
            </div>

            {/* MODAL DE PAGO */}
            {showPaymentModal && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
                    <div className="bg-white p-6 rounded shadow-lg w-96">
                        <h2 className="text-xl font-bold mb-4">Pago con tarjeta</h2>

                        {paymentError && <p className="text-red-600 text-sm mb-2">{paymentError}</p>}
                        {paymentSuccess && <p className="text-green-600 text-sm mb-2">{paymentSuccess}</p>}

                        <div className="mb-3">
                            <label className="block text-sm">Número de tarjeta</label>
                            <input
                                type="text"
                                className="w-full px-3 py-2 border rounded"
                                value={paymentInfo.cardNumber}
                                onChange={(e) => setPaymentInfo({ ...paymentInfo, cardNumber: e.target.value })}
                            />
                        </div>
                        <div className="mb-3">
                            <label className="block text-sm">Nombre en la tarjeta</label>
                            <input
                                type="text"
                                className="w-full px-3 py-2 border rounded"
                                value={paymentInfo.cardName}
                                onChange={(e) => setPaymentInfo({ ...paymentInfo, cardName: e.target.value })}
                            />
                        </div>
                        <div className="mb-3 flex gap-2">
                            <div className="w-1/2">
                                <label className="block text-sm">Expira</label>
                                <input
                                    type="text"
                                    placeholder="MM/AA"
                                    className="w-full px-3 py-2 border rounded"
                                    value={paymentInfo.expiry}
                                    onChange={(e) => setPaymentInfo({ ...paymentInfo, expiry: e.target.value })}
                                />
                            </div>
                            <div className="w-1/2">
                                <label className="block text-sm">CVV</label>
                                <input
                                    type="text"
                                    className="w-full px-3 py-2 border rounded"
                                    value={paymentInfo.cvv}
                                    onChange={(e) => setPaymentInfo({ ...paymentInfo, cvv: e.target.value })}
                                />
                            </div>

                            <div className="mb-3">
                                <label className="block text-sm">Dirección de envío</label>
                                <input
                                    type="text"
                                    className="w-full px-3 py-2 border rounded"
                                    value={paymentInfo.address}
                                    onChange={(e) => setPaymentInfo({ ...paymentInfo, address: e.target.value })}
                                />
                            </div>

                        </div>

                        <div className="flex justify-between mt-4">
                            <button
                                onClick={handlePaymentSubmit}
                                className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
                            >
                                Pagar
                            </button>
                            <button
                                onClick={() => setShowPaymentModal(false)}
                                className="px-4 py-2 border rounded hover:bg-gray-100"
                            >
                                Cancelar
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </>
    );

}

export default CartTab