package com.milicom.code.products.context;

import com.milicom.code.products.payload.token.AuthModelToken;

import java.util.Objects;

public class AuthDataContext {

    private static ThreadLocal<AuthModelToken> dataStringContext = new ThreadLocal<>();

    public static void setDataStringContext(AuthModelToken data){
        dataStringContext.set(data);
    }

    public static AuthModelToken getDataStringContext(){
        return Objects.isNull(dataStringContext.get()) ? createAuthNull() : dataStringContext.get();
    }

    public static AuthModelToken createAuthNull(){
        return new AuthModelToken();
    }

    public static void remove(){
        if(!Objects.isNull(dataStringContext.get()))
            dataStringContext.remove();
    }
}
