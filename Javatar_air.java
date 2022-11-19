import java.util.*;

public class Javatar_air extends Nomades_de_l_air {
    private static Javatar_air INSTANCE;

    private ArrayList<String> messages = new ArrayList<String>();
    private int nbOfMessages;

    private Javatar_air(int x, int y) {
        super(x, y);
    };

    public void setPos(int x, int y) {
        this.pos.setCoord(x, y);
    }

    public static Javatar_air getInstance() {
        if(INSTANCE== null){
            // HAUT-GAUCHE -> x = 0, y = 0
            INSTANCE = new Javatar_air(0, 0);
        }
        return INSTANCE;
    }

    public String GetMessage(int i){ 
        return messages.get(i);
    }

    public int GetNbMessage(){ 
        return messages.size();
    }

    public void SetMessage(ArrayList<String> listmessage){
        for (String message : listmessage){
            if(messages.contains(message)){
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
