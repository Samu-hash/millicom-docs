import { configureStore } from "@reduxjs/toolkit";
import cartReducer from './card';
import productReducer from './productSlice';
import userReducer from './user';

export const store = configureStore({
    reducer: {
        cart: cartReducer,
        products: productReducer,
        user: userReducer,
    }
})