public class Tribus_de_l_eau extends Humain{
    public Tribus_de_l_eau(int x, int y){
        super(0, 0);
    }

    @Override
    public String getEquipe() {
        return "E";
    }

    @Override
    public int rencontre(Humain h){
        this.partagerMessages(h);
        if(h.getEquipe() != this.getEquipe()) {
            int dePerso = lancerDeDes();
            int deAdverse = lancerDeDes();

            if(dePerso > deAdverse || (dePerso == deAdverse && h.getEquipe() == "F")) {
                h.mort();
                return 1;
            } else if(dePerso < deAdverse || (dePerso == deAdverse && h.getEquipe() == "T")) {
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
