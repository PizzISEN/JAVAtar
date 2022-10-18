public class Humain {
    private int posX = 0;
    private int posY = 0;
    private String[] messages;
    private int futurX = 0;
    private int futurY = 0;

    public boolean vivant;

    public Humain(int x, int y) {
        this.posX = x;
        this.posY = y;
        this.vivant = true;
    }

    public void changerPosition(int x, int y) {
        this.posX = x;
        this.posY = y;
    }
    
    public void rencontre() {}

    public void mort() {
        this.vivant = false;
    }
}
