public class Nomades_de_l_air extends Humain{
    public Nomades_de_l_air() {
        super();
    }

    public Nomades_de_l_air(int x, int y){
        super(0, 0);
    }

    @Override
    public String getEquipe() {
        return "air";
    }

    @Override
    public void rencontre(Humain h){
        if(h.getEquipe() == this.getEquipe()) {
            this.partagerMessages(h);
        } else {
            int dePerso = lancerDeDes();
            int deAdverse = lancerDeDes();

            if(dePerso > deAdverse || (dePerso == deAdverse && h.getEquipe() == "terre")) {
                h.mort();
            } else if(dePerso < deAdverse || (dePerso == deAdverse && h.getEquipe() == "feu")) {
                this.mort();
            } else {
                h.mort();
            }
        }
    }
}
