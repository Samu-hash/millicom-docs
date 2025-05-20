package com.milicom.code.products.context;

import com.milicom.code.products.payload.error.ErrorHandler;

import java.util.Objects;

public class ErrorContext {

    private static final InheritableThreadLocal<ErrorHandler> contextError = new InheritableThreadLocal<>();


    public static ErrorHandler getContextError(){
        return Objects.nonNull(contextError.get()) ? contextError.get() : null;
    }

    public static void setContextError(ErrorHandler e){
        if(Objects.isNull(e))
            contextError.set(new ErrorHandler());
        else
            contextError.set(e);
    }


    public static void remove(){
        if(Objects.nonNull(contextError.get()))
            contextError.remove();
    }
}
