public class Nation_du_feu extends Humain {
    public Nation_du_feu(int x, int y){
        super(0, 0);
    }

    @Override
    public String getEquipe() {
        return "F";
    }

    @Override
    public void rencontre(Humain h){
        if(h.getEquipe() == this.getEquipe()) {
            this.partagerMessages(h);
        } else {
            int dePerso = lancerDeDes();
            int deAdverse = lancerDeDes();

            if(dePerso > deAdverse || (dePerso == deAdverse && h.getEquipe() == "A")) {
                h.mort();
            } else if(dePerso < deAdverse || (dePerso == deAdverse && h.getEquipe() == "E")) {
                this.mort();
            } else {
                h.mort();
            }
        }
    }
}
