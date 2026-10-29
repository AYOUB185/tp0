package ht.fds.mbds.ayoubfariat.jsf.tp0.service;

import jakarta.enterprise.context.Dependent;

import java.io.Serializable;
import java.util.Locale;

/**
 * Génère la réponse à une question.
 * Portée Dependent : l'instance vit aussi longtemps que le backing bean dans lequel elle est injectée.
 * Serializable est nécessaire car le backing bean a la portée view.
 */
@Dependent
public class GenerateurReponse implements Serializable {

    public String genererReponse(String question, String roleSysteme, boolean premiereQuestion) {
        StringBuilder reponse = new StringBuilder();
        if (premiereQuestion && roleSysteme != null) {
            reponse.append(roleSysteme.toUpperCase(Locale.ROOT)).append("\n");
        }
        reponse.append("|| ").append(inverserCasse(question)).append(" ||");
        return reponse.toString();
    }

    private String inverserCasse(String texte) {
        StringBuilder sb = new StringBuilder();
        for (char c : texte.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(Character.toUpperCase(c));
            }
        }
        return sb.toString();
    }
}