package utilities;

import java.util.regex.Pattern;

/**
 *
 * @author Jorge Padilla
 */
public class Cedula {
    
    private Cedula() {
        throw new UnsupportedOperationException();
    }
    
    //https://www.alexastudillo.com/2020/10/validar-una-cedula-ecuatoriana-java.html
    public static boolean verificarCedula(String cedula) {
        if (cedula == null) {
            return false;
        }
        
        String regex = "[0-9]{10}";
        Pattern p = Pattern.compile(regex);
        if (!p.matcher(cedula).matches()) {
            return false;
        }
        
        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) {
            return false;
        }
        int total = 0;
        for (int i = 0; i < cedula.length() - 1; i++) { //No se toma en cuenta el último dígito porque solo es de verificación
            int digito = Integer.parseInt(String.valueOf(cedula.charAt(i)));
            if (i == 2 && digito > 6) { //Tercer dígito es un número no mayor a 6
                return false;
            }
            if (i % 2 == 0) { //Dígito impar (índices pares)
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }
            total += digito;
        }
        /*
        A la suma de todos los dígitos se le resta la decena superior.
        Ejemplo: si la suma sale 31 debemos restar 40 - 31 = 9
         */
        int decenaSup = total - (total % 10) + 10;
        int ultimoDigito = Integer.parseInt(String.valueOf(cedula.charAt(9)));
        //Si la resta es igual al último dígito de la cédula, el documento es válido
        return (decenaSup - total) == ultimoDigito;
    }

    public static String generarCedula() {
        String cedula;
        int provincia = randomInt(1, 24);
        int tercerDigito = randomInt(0, 6);
        cedula = String.format("%02d", provincia) + String.valueOf(tercerDigito);

        for (int i = 3; i < 9; i++) { //No se toma en cuenta el último dígito porque solo es de verificación
            int digito = randomInt(0, 9);
            cedula += String.valueOf(digito);
        }

        int total = 0;
        for (int i = 0; i < cedula.length(); i++) { //No se toma en cuenta el último dígito porque solo es de verificación
            int digito = Integer.parseInt(String.valueOf(cedula.charAt(i)));
            if (i % 2 == 0) { //Dígito impar (índices pares)
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }
            total += digito;
        }

        int decenaSup = total - (total % 10) + 10;
        cedula += String.valueOf((decenaSup - total));
        return cedula;
    }
    
    private static int randomInt(int min, int max) {
        int range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }
}
