package ht.fds.mbds.ayoubfariat.jsf.tp0.jsf;

import ht.fds.mbds.ayoubfariat.jsf.tp0.service.GenerateurReponse;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Backing bean de index.xhtml.
 * Portée view : l'état de la conversation est conservé entre les requêtes POST de la même vue.
 */
@Named
@ViewScoped
public class Bb implements Serializable {

    private String roleSysteme;
    private boolean roleSystemeChangeable = true;
    private List<SelectItem> rolesSysteme;

    private String question;
    private String reponse;
    private final StringBuilder conversation = new StringBuilder();

    @Inject
    private GenerateurReponse generateurReponse;

    public String getRoleSysteme() { return roleSysteme; }
    public void setRoleSysteme(String roleSysteme) { this.roleSysteme = roleSysteme; }

    public boolean isRoleSystemeChangeable() { return roleSystemeChangeable; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getReponse() { return reponse; }
    public void setReponse(String reponse) { this.reponse = reponse; }

    public String getConversation() { return conversation.toString(); }
    public void setConversation(String conversation) { /* zone en lecture seule */ }

    public List<SelectItem> getRolesSysteme() {
        if (rolesSysteme == null) {
            rolesSysteme = new ArrayList<>();
            rolesSysteme.add(new SelectItem("Assistant"));
            rolesSysteme.add(new SelectItem("Traducteur français-anglais"));
            rolesSysteme.add(new SelectItem("Guide touristique"));
        }
        return rolesSysteme;
    }

    /** Action du bouton "Envoyer la question". Retourne null : on reste dans la même vue. */
    public String envoyer() {
        if (question == null || question.isBlank()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Texte question vide", "Il manque le texte de la question");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
        boolean premiereQuestion = conversation.isEmpty();
        reponse = generateurReponse.genererReponse(question, roleSysteme, premiereQuestion);
        conversation.append("== User:\n").append(question)
                .append("\n== Serveur:\n").append(reponse).append("\n");
        roleSystemeChangeable = false;
        return null;
    }

    /** Action du bouton "Nouveau chat". La valeur retournée n'est pas null : la portée view se termine. */
    public String nouveauChat() {
        return "index?faces-redirect=true";
    }
}