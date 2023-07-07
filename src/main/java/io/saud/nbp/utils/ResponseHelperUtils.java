package io.saud.nbp.utils;

import java.util.Map;

public class ResponseHelperUtils {


    public static Map<String, Object> ErrorResponseHelper(String message){
        return Map.of("success", false, "message", message);
    }
}
