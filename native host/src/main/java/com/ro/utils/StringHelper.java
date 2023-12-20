package com.ro.utils;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import java.util.ArrayList;

public final class StringHelper {

    private StringHelper(){}

    public static String sanitize(String original) {
        if (original == null) {
            return null;
        }
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
        //PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS).and(Sanitizers.IMAGES).and(Sanitizers.LINKS).and(Sanitizers.STYLES);
        //PolicyFactory policy = Sanitizers.FORMATTING;
        return policy.sanitize(original);
    }

    public static String buildStrFromArr(String[] arr) {
        String out = "";
        boolean flag = false;
        for(String str : arr) {
            if(!flag) {
                flag = true;
                out += str;
            } else {
                out += ", " + str;
            }
        }
        return out;
    }

    public static String[] removeStrFromArray(String[] vals, String[] input) {
        ArrayList<String> strArr = new ArrayList<>();
        for(int i = 0; i < input.length; i++) {
            for(int j = 0; j < vals.length; j++) {
                if(input[i].equals(vals[j])) {
                    break;
                }
                if(j == vals.length - 1) {
                    strArr.add(input[i]);
                }
            }
        }

        String[] output = new String[strArr.size()];
        for(int i = 0; i < output.length; i++){
            output[i] = strArr.get(i);
        }

        return output;
    }
}
