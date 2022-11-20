package Personnage;

public class Royaume_de_la_terre extends Humain{
    // Appel du constructeur de Humain
    public Royaume_de_la_terre(int x, int y){
        super(0, 0);
    }

    // Getter de l'équipe de la terre
    @Override
    public String getEquipe() {
        return "T";
    }

    // Gestion des rencontres avec un autre humain
    @Override
    public int rencontre(Humain h){
        // Les messages sont partagés dans tous les cas
        // Soit l'autre humain est alliés et ils s'échangent cordialement leurs listes
        // Soit l'autre est ennemi et le vainqueur récupère les messages de l'autre sur son cadavre, ce qui revient à partager les messages
        this.partagerMessages(h);

        // Si les humains sont d'équipes différentes
        if(h.getEquipe() != this.getEquipe()) {
            // On lance les dés de combat, un pour chaque humain
            int dePerso = lancerDeDes();
            int deAdverse = lancerDeDes();

            // Si le lancé de dé personnel est supérieur au lancé adverse ou que l'ennemi fait partie de l'équipe de l'eau en cas d'égalité
            if(dePerso > deAdverse || (dePerso == deAdverse && h.getEquipe() == "E")) {
                // L'adversaire meurt
                h.mort();
                // Sa case devient un obstacle
                return 1;
            // Si le lancé de dé personnel est inférieur au lancé adverse ou que l'ennemi fait partie de l'équipe de l'air en cas d'égalité
            } else if(dePerso < deAdverse || (dePerso == deAdverse && h.getEquipe() == "A")) {
                // L'humain meurt
                this.mort();
                // Sa case devient un obstacle
                return 0;
            // Si l'adversaire fait partie de l'équipe du feu et qu'il y a égalité
            } else {
                // L'adverssaire meurt à cause de l'effet de surprise
                h.mort();
                // Sa case devient un obstacle
                return 1;
            }
        }

        // Si les deux humains sont de la même équipe, la case ne change pas
        return 2;
    }
}
