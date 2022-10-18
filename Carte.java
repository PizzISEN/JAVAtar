import java.util.ArrayList;

public class Carte {
    public int[] size;
    public ArrayList<ArrayList<Case>>carte;

    Carte(int[] s){
        this.size = s;
        carte = new ArrayList<ArrayList<Case>>();
    }

    public int caseDispo(){
        return 0;
    }

    public int[] allerSafeZone(){
        int [] arr = {0, 0};
        return arr;
    }
}
