import java.util.ArrayList;
import java.security.SecureRandom;

public abstract class Humain {
    private Coord pos = new Coord();
    private ArrayList<String> messages = new ArrayList<String>();
    private boolean vivant;

    public Humain(int x, int y) {
        this.pos.setCoord(x, y);
        this.vivant = true;
    }

    public ArrayList<String> getMessages() {
        return this.messages;
    }

    public Coord getPos() {
        return this.pos;
    }

    // TODO Décider si on garde setPos() ou seulement this.pos.setCoord()
    public void setPos(int x, int y) {
        this.pos.setCoord(x, y);
    }

    public void ajouterMessage(String m) {
        this.messages.add(m);
    }

    public void setMessages(ArrayList<String> m) {
        this.messages = m;
    }

    public void mort() {
        this.vivant = false;
    }

    public Boolean estVivant() {
        return this.vivant;
    }

    public void partagerMessages(Humain h) {
        ArrayList<String> additionMessages = h.getMessages();
            
        for (String message : this.getMessages()) {
            if(!additionMessages.contains(message)) {
                additionMessages.add(message);
            }
        }

        h.setMessages(additionMessages);
        this.setMessages(additionMessages);
    }
    
    public int lancerDeDes() {
        SecureRandom rand = new SecureRandom();
        return rand.nextInt(6);
    }

    // TODO: Ajouter la gestion d'obstacles
    public void seDeplacer(int h, int w) {
        Coord centre = new Coord(h/2, w/2);
        int direction;
        Boolean newPosFound = false;

        int x = this.pos.getX();
        int y = this.pos.getY();

        while (!newPosFound) {
            direction = lancerDeDes();

            if ((direction == 0 && x <= centre.getX()) || ((direction == 2 || direction == 3) && x > centre.getX())) {
                if (this.pos.getX() > 0) {
                    x -= 1;
                    newPosFound = true;
                }
            } else if ((direction == 0 && x > centre.getX()) || ((direction == 2 || direction == 3) && x <= centre.getX())) {
                if (this.pos.getX() < h-1) {
                    x += 1;
                    newPosFound = true;
                }
            } else if ((direction == 1 && y <= centre.getY()) || ((direction == 4 || direction == 5) && y > centre.getY())) {
                if (this.pos.getY() > 0 ) {
                    y -= 1;
                    newPosFound = true;
                }
            } else if ((direction == 1 && y > centre.getY()) || ((direction == 4 || direction == 5) && y <= centre.getY())) {
                if (this.pos.getY() < w-1) {
                    y += 1;
                    newPosFound = true;
                }
            }
        }
        
        this.setPos(x, y);
    }

    public abstract String getEquipe();
    public abstract void rencontre(Humain h);
}
