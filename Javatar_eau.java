import java.util.*;

public class Javatar_eau extends Tribus_de_l_eau{
    private static Javatar_eau INSTANCE;

    private ArrayList<String> messages = new ArrayList<String>();
    private int nbOfMessages;

    private Javatar_eau(int x, int y) {
        super(x, y);
    };

    public void setPos(int x, int y) {
        this.pos.setCoord(x, y);
    }
    
    public static Javatar_eau getInstance() {
        if(INSTANCE== null){
            // HAUT-DROITE -> x = 0, y = size[1] - 1
            INSTANCE = new Javatar_eau(0, 14);
        }
        return INSTANCE;
    }
    
    public ArrayList<String> GetMessage(){ 
        return messages;
    }

    public int GetNbMessage(){ 
        return messages.size();
    }

    public void SetMessage(ArrayList<String> listmessage){
        for (String message : listmessage){
            if(!messages.contains(message)){
                messages.add(message);
            }
        }
        return ;
    }

    public void SetNbOfMessages(int i){ 
        this.nbOfMessages=i;
        return ;
    }

    public boolean win(){
        return nbOfMessages==messages.size();
    }
}
