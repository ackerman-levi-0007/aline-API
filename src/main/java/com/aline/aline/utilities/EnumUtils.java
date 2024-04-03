package com.aline.aline.utilities;

public class EnumUtils {

    // Generic method to check if a value exists in an enum
    public static <T extends Enum<T>> boolean contains(Class<T> enumClass, String value){
        // Iterate over enum constants
        for (T constant : enumClass.getEnumConstants()){
            // Compare constant's value with the given value (case-insensitive)
            if(constant.name().equals(value)) return true;
        }
        return false;
    }
}
