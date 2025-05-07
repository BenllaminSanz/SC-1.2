package Functions;

import java.util.Properties;

public class Niveles_Salarios {
    public static String determinarNivel(double salario, Properties properties) {
        String mejorNivel = "";
        double mejorSalario = Double.NEGATIVE_INFINITY;

        for (String key : properties.stringPropertyNames()) {
            String salarioStr = properties.getProperty(key);
            if (salarioStr != null && !salarioStr.isEmpty()) {
                try {
                    double nivelSalario = Double.parseDouble(salarioStr);
                    if (salario == nivelSalario && nivelSalario > mejorSalario) {
                        mejorSalario = nivelSalario;
                        mejorNivel = key.replace("nivel.", "");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Formato inválido en: " + key + " → " + salarioStr);
                }
            }
        }
        return mejorNivel.isEmpty() ? "" : mejorNivel;
    }
}
