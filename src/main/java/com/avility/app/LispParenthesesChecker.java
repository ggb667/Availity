package com.avility.app;

public class LispParenthesesChecker {
    
    public static void main(String[] args){

        int x=1;
        for(String str: args){
            System.out.println("Checking:" + str);
            String msg = lispCheckValue(str)==0 ? "String " + x++ + " is good" : "String " + x++ + " is bad";
            System.out.println(msg);
        }
    }

    public static int lispCheckValue(String str){
        int value = 0;
        char[] c_array = str.toCharArray();
        for (char c : c_array) {
            value = c == '(' ? ++value : value;
            value = c == ')' ? --value : value;
            System.out.println(c + " " + value);
            if ( value < 0) break;
        }
        return value;
    }


}
