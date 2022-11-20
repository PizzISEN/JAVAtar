package Personnage;
import java.util.ArrayList;
import java.security.SecureRandom;
import Coordonnees.Coord;

// Déclaration de la classe mère humain
public abstract class Humain {
    protected Coord pos = new Coord();
    protected int energie;
    private ArrayList<String> messages = new ArrayList<String>();
    private boolean vivant;

    // Constructeur commun à toutes les équipes
    public Humain(int x, int y) {
        // Setter des coordonnées de l'humain
        this.pos.setCoord(x, y);
        // Chaque humain est initialisé vivant et avec une énergie de 50
        this.energie = 30;
        this.vivant = true;
    }

    // Getter du tableau de messages
    public ArrayList<String> getMessages() {
        return this.messages;
    }

    // Getter de la position de l'humain (renvoie une Coord)
    public Coord getPos() {
        return this.pos;
    }

    // Setter de la position de l'humain
    public void setPos(int x, int y) {
        this.pos.setCoord(x, y);
    }

    // Getter de l'énergie
    public int getEnergie() {
        return this.energie;
    }

    // Setter de l'énergie
    public void setEnergie(int e) {
        this.energie = e;
    }

    // Ajoute un message à ceux que connait l'humain
    public void ajouterMessage(String m) {
        this.messages.add(m);
    }

    // Setter de tout le tableau de messagess
    public void setMessages(ArrayList<String> m) {
        this.messages = m;
    }

    // Change le statut de l'humain
    public void mort() {
        this.vivant = false;
    }

    // Getter du statut de l'humain
    public Boolean estVivant() {
        return this.vivant;
    }

    // Partage les messages entre deux humains
    public void partagerMessages(Humain h) {
        // On récupère d'abord tous les messages d'un des humains
        ArrayList<String> additionMessages = h.getMessages();
            
        // Pour tous les messages que connait l'autre humain
        for (String message : this.getMessages()) {
            // S'il n'est pas déjà dans la liste de messages générale
            if(!additionMessages.contains(message)) {
                // On ajoute le message
                additionMessages.add(message);
            }
        }

        // On remplace la liste des messages connues des deux humain par celle commune
        h.setMessages(additionMessages);
        this.setMessages(additionMessages);
    }
    
    // Fonction qui renvoie un nombre entre 0 et 5 inclus
    public int lancerDeDes() {
        SecureRandom rand = new SecureRandom();
        return rand.nextInt(6);
    }

    // Fonction qui gère le déplacement d'un humain, elle prend en paramètre le tableau des cases disponibles 
    public Coord seDeplacer(ArrayList<Coord> casesDispo) {
        // S'il y a au moins une case disponible
        if (casesDispo.size() != 0) {
            // On récupère aléatoirement une des cases disponibles
            int randomIndex=new SecureRandom().nextInt(casesDispo.size());

            // L'humain perd 1 d'énergie suite au déplacement
            this.energie -= 1;
            // On remplace les coordonnées de l'humain par les nouvelles
            this.pos.setCoord(casesDispo.get(randomIndex).getX(), casesDispo.get(randomIndex).getY());
        }

        // Renvoie la coordonnée générée pour modifier celle de la carte
        return this.getPos();
    }

    // Déclaration abstraite des fonction getEquipe() et rencontre() qui ont des comportement différent en fonction de l'équipe
    public abstract String getEquipe();
    public abstract int rencontre(Humain h);
}
