import java.util.*;
public class Javatar_eau {
    private static Javatar_eau INSTANCE;

    private List<String> messages;

    private int nbOfMessages;

    private Javatar_eau(){};

    public static Javatar_eau getInstance() {
        if(INSTANCE== null){
            INSTANCE = new Javatar_eau();
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
