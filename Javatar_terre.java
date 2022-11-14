import java.util.*;
public class Javatar_terre {
    private static Javatar_terre INSTANCE;

    private List<String> messages;

    private int nbOfMessages;

    private Javatar_terre(){};

    public static Javatar_terre getInstance() {
        if(INSTANCE== null){
            INSTANCE = new Javatar_terre();
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
