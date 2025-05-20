package com.millicom.customer.infocustomer.utils.validations;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum MessagesEnume {

    CODE_NOT_FOUND(1000, "Not found code"),
    GENERIC_CODE(1002, "%s"),
    VALIDATE_FIELD_NOT_NULL(1100, "The field %s is required"),
    VALIDATE_FIELD_NOT_BLANK(1101, "The field %s is not empty"),
    VALIDATE_FIELD_EMAIL(1103, "The field %s is not valid for a email");

    private final int code;
    private final String message;

    public static MessagesEnume findCode(Integer code){
        return Stream.of(MessagesEnume.values())
                .filter(en -> en.getCode() == code).findAny().orElse(CODE_NOT_FOUND);
    }
}
