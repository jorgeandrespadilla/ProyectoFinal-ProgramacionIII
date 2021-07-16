package utilities;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 *
 * @author Jorge Padilla
 */
public class FilterTools {
    
    private FilterTools() {
        throw new UnsupportedOperationException();
    }

    public static boolean startsWith(String value, String searchValue) {
        return normalize(value).startsWith(normalize(searchValue));
    }

    public static boolean contains(String value, String searchValue) {
        return normalize(value).contains(normalize(searchValue));
    }

    public static boolean equals(String value, String searchValue) {
        return normalize(value).compareTo(normalize(searchValue)) == 0;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern p = Pattern.compile(regex);
        if (email == null) {
            return false;
        }
        return p.matcher(email).matches();
    }

    public static boolean isValidClave(String clave) {
        return clave.length() >= 8;
    }

    public static boolean isValidCedula(String cedula) {
        return Cedula.verificarCedula(cedula);
    }

    public static String normalize(String value) {
        return removeAccents(value.toLowerCase().trim());
    }

    public static String capitalize(String name) {
        name = name.trim();
        StringBuilder sb = new StringBuilder();
        String[] words = name.split(" ");
        for (int i = 0; i < words.length; i++) {
            String word = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(word);
        }
        return sb.toString();
    }

    public static String normalizeEmail(String email) {
        return email.toLowerCase().trim();
    }

    public static String removeAccents(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}
