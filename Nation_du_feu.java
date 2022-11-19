public class Nation_du_feu extends Humain {
    public Nation_du_feu(int x, int y){
        super(0, 0);
    }

    @Override
    public String getEquipe() {
        return "F";
    }

    @Override
    public int rencontre(Humain h){
        this.partagerMessages(h);
        if(h.getEquipe() != this.getEquipe()) {
            int dePerso = lancerDeDes();
            int deAdverse = lancerDeDes();

            if(dePerso > deAdverse || (dePerso == deAdverse && h.getEquipe() == "A")) {
                h.mort();
                return 1;
            } else if(dePerso < deAdverse || (dePerso == deAdverse && h.getEquipe() == "E")) {
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
