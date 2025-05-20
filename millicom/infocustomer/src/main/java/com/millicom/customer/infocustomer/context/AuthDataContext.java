package com.millicom.customer.infocustomer.context;

import com.millicom.customer.infocustomer.payload.jwt.AuthModel;

import java.util.Objects;

public class AuthDataContext {

    private static ThreadLocal<AuthModel> dataStringContext = new ThreadLocal<>();

    public static void setDataStringContext(AuthModel data){
        dataStringContext.set(data);
    }

    public static AuthModel getDataStringContext(){
        return Objects.isNull(dataStringContext.get()) ? createAuthNull() : dataStringContext.get();
    }

    public static AuthModel createAuthNull(){
        return new AuthModel("", "");
    }

    public static void remove(){
        if(!Objects.isNull(dataStringContext.get()))
            dataStringContext.remove();
    }
}
