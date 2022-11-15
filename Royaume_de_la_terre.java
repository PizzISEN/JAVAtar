public class Royaume_de_la_terre extends Humain{
    public Royaume_de_la_terre() {
        super();
    }
    
    public Royaume_de_la_terre(int x, int y){
        super(0, 0);
    }

    @Override
    public String getEquipe() {
        return "terre";
    }

    @Override
    public void rencontre(Humain h){
        if(h.getEquipe() == this.getEquipe()) {
            this.partagerMessages(h);
        } else {
            int dePerso = lancerDeDes();
            int deAdverse = lancerDeDes();

            if(dePerso > deAdverse || (dePerso == deAdverse && h.getEquipe() == "eau")) {
                h.mort();
            } else if(dePerso < deAdverse || (dePerso == deAdverse && h.getEquipe() == "air")) {
                this.mort();
            } else {
                h.mort();
            }
        }
    }
}
