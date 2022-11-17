import java.util.*;

public class Javatar_terre extends Royaume_de_la_terre {
    private static Javatar_terre INSTANCE;

    private ArrayList<String> messages = new ArrayList<String>();
    private int nbOfMessages;

    private Javatar_terre(int x, int y) {
        super(x, y);
    };

    public void setPos(int x, int y) {
        this.pos.setCoord(x, y);
    }
    
    public static Javatar_terre getInstance() {
        if(INSTANCE== null){
            // BAS-GAUCHE -> x = size[0] - 1, y = 0
            INSTANCE = new Javatar_terre(14, 14);
        }
        return INSTANCE;
    }

    public String GetMessage(int i){ 
        return messages.get(i);
    }

    public void SetMessage(String message){ 
        if(messages.contains(message)){
            messages.add(message);
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
