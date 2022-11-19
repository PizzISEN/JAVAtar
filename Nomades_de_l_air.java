public class Nomades_de_l_air extends Humain{
    public Nomades_de_l_air(int x, int y){
        super(0, 0);
    }

    @Override
    public String getEquipe() {
        return "A";
    }

    @Override
    public int rencontre(Humain h){
        this.partagerMessages(h);
        if(h.getEquipe() != this.getEquipe()) {
            int dePerso = lancerDeDes();
            int deAdverse = lancerDeDes();

            if(dePerso > deAdverse || (dePerso == deAdverse && h.getEquipe() == "T")) { 
                h.mort();
                return 1;
            } else if(dePerso < deAdverse || (dePerso == deAdverse && h.getEquipe() == "F")) {
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
