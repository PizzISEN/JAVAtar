import java.util.*;
public class Javatar_feu extends Nation_du_feu{
    private static Javatar_feu INSTANCE;

    private List<String> messages;

    private int nbOfMessages;

    private Javatar_feu(){};

    public static Javatar_feu getInstance() {
        if(INSTANCE== null){
            INSTANCE = new Javatar_feu();
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
