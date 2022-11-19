public class Royaume_de_la_terre extends Humain{
    public Royaume_de_la_terre(int x, int y){
        super(0, 0);
    }

    @Override
    public String getEquipe() {
        return "T";
    }

    @Override
    public int rencontre(Humain h){
        this.partagerMessages(h);
        if(h.getEquipe() != this.getEquipe()) {
            int dePerso = lancerDeDes();
            int deAdverse = lancerDeDes();

            if(dePerso > deAdverse || (dePerso == deAdverse && h.getEquipe() == "E")) {
                h.mort();
                return 1;
            } else if(dePerso < deAdverse || (dePerso == deAdverse && h.getEquipe() == "A")) {
                this.mort();
                return 0;
            } else {
                h.mort();
                return 1;
            }
        }
        return 2;
    }
}
