package Map;

import Coordonnees.Coord;
import Personnage.*;


//Class Case qui est l'élément qui compose entièrement la Carte
public class Case {
    public String type;     //Renvoi le type de case de celle-ci (référentiels en dessous)
    private Coord coord = new Coord();      //Classe custom pour créer une paire de Coordonnée
    public int id;
    public Humain personnage;   // Va servir de référence pour le personnage situé sur la case

    //  Type des cases: Vide (0) - air(A) - eau(E) - terre(T) - feu(F) - obstacle(O)

    public Case (String t, int x, int y, int id){
        this.coord.setCoord(x, y);
        this.type = t;
        this.id = id;
        this.personnage = null;     //A l'initialisation les cases ne comportent pas de personnage sur elles
    }
    
    public Coord getCoord(){
        return coord;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String t){
        this.type = t;
    }

    public void setPersonnage(Humain p){
        this.personnage = p;
    }
}
