public class Royaume_de_la_terre extends Humain{
    public Royaume_de_la_terre(int x, int y){
        super(0, 0);
    }

    @Override
    public String getEquipe() {
        return "T";
    }

    @Override
    public void rencontre(Humain h){
        if(h.getEquipe() == this.getEquipe()) {
            this.partagerMessages(h);
        } else {
            int dePerso = lancerDeDes();
            int deAdverse = lancerDeDes();

            if(dePerso > deAdverse || (dePerso == deAdverse && h.getEquipe() == "E")) {
                h.mort();
            } else if(dePerso < deAdverse || (dePerso == deAdverse && h.getEquipe() == "A")) {
                this.mort();
            } else {
                h.mort();
            }
        }
    }
}
