import java.util.*;

public class Javatar_feu extends Nation_du_feu{
    private static Javatar_feu INSTANCE;

    private List<String> messages;
    private int nbOfMessages;

    private Javatar_feu(int x, int y) {
        super(x, y);
    };

    public static Javatar_feu getInstance() {
        if(INSTANCE== null){
            // BAS-DROITE -> x = size[0] - 1, y = size[1] - 1 
            INSTANCE = new Javatar_feu(14, 14);
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
