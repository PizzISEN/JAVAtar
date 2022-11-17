import java.util.ArrayList;
import java.security.SecureRandom;

public abstract class Humain {
    protected Coord pos = new Coord();
    protected int energie;
    private ArrayList<String> messages = new ArrayList<String>();
    private boolean vivant;

    public Humain(int x, int y) {
        this.pos.setCoord(x, y);
        this.energie = 100;
        this.vivant = true;
    }

    public ArrayList<String> getMessages() {
        return this.messages;
    }

    public Coord getPos() {
        return this.pos;
    }

    public int getEnergie() {
        return this.energie;
    }

    public void setEnergie(int e) {
        this.energie = e;
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

    // TODO fix cette merde
    public Coord seDeplacer(ArrayList<Coord> casesDispo) {
        System.out.println(casesDispo);
        if (casesDispo.size() != 0) {
            int directionX, directionY; 
            Boolean newPosFound = false;

            int x = this.pos.getX();
            int y = this.pos.getY();
            System.out.println("AVANT LE WHILE SE DEPLACER");

            System.out.println(casesDispo.contains(new Coord(x+1, y)));
            System.out.println(casesDispo.contains(new Coord(x+1, y+1)));
            System.out.println(casesDispo.contains(new Coord(x+1, y-1)));
            System.out.println(casesDispo.contains(new Coord(x-1, y+1)));
            System.out.println(casesDispo.contains(new Coord(x-1, y)));
            System.out.println(casesDispo.contains(new Coord(x-1, y-1)));

            while (!newPosFound) {
                directionX = new SecureRandom().nextInt(3) - 1;
                directionY = new SecureRandom().nextInt(3) - 1;

                if (casesDispo.contains(new Coord(directionX, directionY))) {
                    x += directionX;
                    y += directionY;
                    System.out.println("Moved to coords: " + directionX + " " + directionY);
                    this.energie -= 1;
                    newPosFound = true;
                }
            }
            System.out.println(x+" "+y+" "+this.pos.getX()+" "+this.pos.getY()+" ");
            this.pos.setCoord(x, y);
        }

        return this.getPos();
    }

    public abstract String getEquipe();
    public abstract void rencontre(Humain h);
}
