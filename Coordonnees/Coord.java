package Coordonnees;

// Classe créée pour simplifier la gestion de coordonnées
public class Coord {
    private int x;
    private int y;

    // Constructeur par défaut
    public Coord() {
        this.x = 0;
        this.y = 0;
    }

    // Constructeur avec paramètres
    public Coord(int x,int y) {
        this.x = x;
        this.y = y;
    }

    // Setter de coordonnées
    public void setCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getter de la variable x
    public int getX() {
        return this.x;
    }

    // Getter de la variable y
    public int getY() {
        return this.y;
    }
}
