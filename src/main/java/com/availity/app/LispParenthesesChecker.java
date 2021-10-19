package com.avility.app;

public class LispParenthesesChecker {

    
    /** 
     * Entry point for main application.  Feed it strings.
     * @param args Strings with parentheses in them, 
     * if they match it prints "String [your string ] is good" to stdio
     * if they do not match it prints "String [your string ] is bad" to stdio
     * If called with null it throws an NPE (expected behavior), there is a test
     * specifically for this.  Of course I could have safeguarded it with a wrapper method...
     * Object object : Optional.ofNullable(args).orElse(Collections.emptyList())
     */
    public static void main(final String[] args) {
        int x = 1;
        for (final String str : args) {
            System.out.println("Checking:" + str);
            final String msg = lispCheckValue(str) == 0 ? "String " + x++ + " is good" : "String " + x++ + " is bad";
            System.out.println(msg);
        }
    }

    
    /** 
     * @param str Checks the lisp for matching braces, short circuits if ever right occurs before left.
     * @return int 0 if everything is ok, a number if there are excess right braces.
     */
    public static int lispCheckValue(final String str) {
        int value = 0;
        final char[] c_array = str.toCharArray();
        for (final char c : c_array) {
            value = c == '(' ? ++value : value;
            value = c == ')' ? --value : value;
            System.out.println(c + " " + value);
            if (value < 0)
                break;
        }
        return value;
    }

}
