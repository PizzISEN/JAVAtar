import java.util.*;
public class Javatar_air {
    private static Javatar_air INSTANCE;

    private List<String> messages;

    private int nbOfMessages;

    private Javatar_air(){};

    public static Javatar_air getInstance() {
        if(INSTANCE== null){
            INSTANCE = new Javatar_air();
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
