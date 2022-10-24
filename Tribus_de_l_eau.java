public class Tribus_de_l_eau extends Humain{
    public Tribus_de_l_eau(){
        super(0, 0);
    }

    @Override
    public String getEquipe() {
        return "eau";
    }

    @Override
    public void rencontre(Humain h){}
}
