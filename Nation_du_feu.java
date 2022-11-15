public class Nation_du_feu extends Humain {
    public Nation_du_feu() {
        super();
    }

    public Nation_du_feu(int x, int y){
        super(0, 0);
    }

    @Override
    public String getEquipe() {
        return "feu";
    }

    @Override
    public void rencontre(Humain h){
        if(h.getEquipe() == this.getEquipe()) {
            this.partagerMessages(h);
        } else {
            int dePerso = lancerDeDes();
            int deAdverse = lancerDeDes();

            if(dePerso > deAdverse || (dePerso == deAdverse && h.getEquipe() == "air")) {
                h.mort();
            } else if(dePerso < deAdverse || (dePerso == deAdverse && h.getEquipe() == "eau")) {
                this.mort();
            } else {
                h.mort();
            }
        }
    }
}
