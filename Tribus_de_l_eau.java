public class Tribus_de_l_eau extends Humain{
    public Tribus_de_l_eau(){
        super(0, 0);
    }

    @Override
    public String getEquipe() {
        return "eau";
    }

    @Override
    public void rencontre(Humain h){
        if(h.getEquipe() == this.getEquipe()) {
            System.out.println("Copain eau");
            this.partagerMessages(h);
        } else {
            int dePerso = lancerDeDes();
            int deAdverse = lancerDeDes();

            if(dePerso > deAdverse || (dePerso == deAdverse && h.getEquipe() == "feu")) {
                h.mort();
            } else if(dePerso < deAdverse || (dePerso == deAdverse && h.getEquipe() == "terre")) {
                this.mort();
            } else {
                h.mort();
            }
        }
    }
}
