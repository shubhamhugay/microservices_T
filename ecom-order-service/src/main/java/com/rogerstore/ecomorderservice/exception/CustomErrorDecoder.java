package com.rogerstore.ecomorderservice.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        if(response.status()== 404)
        {
            return new RuntimeException("Product not Found");
        }
        return  new RuntimeException("generic Error:" +response.status());
    }
}
