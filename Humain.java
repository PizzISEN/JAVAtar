import java.util.ArrayList;
import java.security.SecureRandom;

public abstract class Humain {
    protected Coord pos = new Coord();
    protected int energie;
    private ArrayList<String> messages = new ArrayList<String>();
    private boolean vivant;

    public Humain(int x, int y) {
        this.pos.setCoord(x, y);
        this.energie = 50;
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

    public Coord seDeplacer(ArrayList<Coord> casesDispo) {
        if (casesDispo.size() != 0) {
           
        
            int x = this.pos.getX();
            int y = this.pos.getY();
            int randomIndex=new SecureRandom().nextInt(casesDispo.size());
            
            x = casesDispo.get(randomIndex).getX();
            y = casesDispo.get(randomIndex).getY();;
            //System.out.println("Moved to coords: " + x + " " + y);
            this.energie -= 1;
            //System.out.println(x+" "+y+" "+this.pos.getX()+" "+this.pos.getY()+" ");
            this.pos.setCoord(x, y);
        }

        return this.getPos();
    }

    public abstract String getEquipe();
    public abstract int rencontre(Humain h);
}
