package Personnage;
import java.util.*;

public class Javatar_air extends Nomades_de_l_air {
    // Déclaration de l'instance du singleton de l'air
    private static Javatar_air INSTANCE;

    private ArrayList<String> messages = new ArrayList<String>();
    private int nbOfMessages;

    // Appel du constructeur de Humain
    private Javatar_air(int x, int y) {
        super(x, y);
    };

    // Getter de l'instance
    public static Javatar_air getInstance() {
        // Si l'instance n'est pas initialisée
        if(INSTANCE== null){
            // Création de l'instance
            INSTANCE = new Javatar_air(0, 0);
        }

        // Renvoie l'instance
        return INSTANCE;
    }

    // Getter du tableau de messages
    public ArrayList<String> GetMessage(){ 
        return messages;
    }

    // Getter du nombre de messages dans le tableau
    public int GetNbMessage(){ 
        return messages.size();
    }

    // Setter du tableau de messages en entier
    public void SetMessage(ArrayList<String> listmessage) {
        // Pour tous les messages de la liste donnée en paramètre
        for (String message : listmessage) {
            // Si le message n'est pas dans le tableau
            if (!messages.contains(message)) {
                // On l'ajoute à ce dernier
                messages.add(message);
            }
        }
    }

    // Setter du nombre de messages nécessaire pour gagner
    public void SetNbOfMessages(int i){ 
        this.nbOfMessages=i;
    }

    // Fonction de vérification de victoire
    public boolean win(){
        // Si une équipe atteint le nombre de messages suffisant, elle gagne
        return nbOfMessages == messages.size();
    }
}
