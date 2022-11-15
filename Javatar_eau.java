import java.util.*;

public class Javatar_eau extends Tribus_de_l_eau{
    private static Javatar_eau INSTANCE;

    private List<String> messages;
    private int nbOfMessages;

    private Javatar_eau(int x, int y) {
        super(x, y);
    };

    public static Javatar_eau getInstance() {
        if(INSTANCE== null){
            // HAUT-DROITE -> x = 0, y = size[1] - 1
            INSTANCE = new Javatar_eau(0, 14);
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
