package typeqast.util;

public class PalindromChecker {

    private PalindromChecker(){
    }

    public static boolean check(String input) {
        String result = removeSymbolsLowercase(input);

        if (result.equals(reverse(result))) {
            return true;
        }

        return false;
    }

    private static String reverse(String input) {

        String reverse = "";

        for (int i = input.length() - 1; i >= 0; i--) {
            reverse += input.charAt(i);
        }

        return reverse;
    }

    private static String removeSymbolsLowercase(String input) {
        String result = input.replaceAll("[^a-zA-Z]", "");
        return result.toLowerCase();
    }

}
