package Sentences;

// Fichier contenant les messages échangés
public class Sentences {

    private static Sentences INSTANCE;
    public String[] messages={
            "Katara a l'aide",
            "Soka aime Aang",
            "Aang est vraiment beau",
            "Katara maitrise le sang",
            "Soka ne sait pas se battre",
            "Toph est aveugle",
            "Toph est fetichiste des pieds",
            "Aang cache ses pets avec ses rafales de vent",
            "Soka est amoureux de la lune",
            "Azula est une haineuse",
            "Appa est un gros bison",
            "Momo est un ptit singe",
            "Appa sert d'appartement pour Aang",
            "Momo est la que pour mettre le bins",
            "Zuko a mange trop de piment",
            "Iroh est une montagne de muscle",
            "Iroh est un agent infiltre",
            "Katara peut se laver grace a ses pouvoirs",
            "Soka ne sait pas lancer ses boumerangs",
            "Aang est l'avatar",
            "toph maitrise le fer",
            "Azula est vraiment tres jolie",
            "Aang aime les choux de bruxelles",
            "Le vendeur de choux est venere ",
            "Iroh ne boit que du the",
            "Katara vend l'eau de son bain",
            "toph va au ski",
            "Soka aime les frites",
            "Zuko ne sait pas nager",
            "Katara 4.8",
            "Katara 3.2",
            "Katara 2.0",
            "Katara vend des armes",
            "je suis pas la",
            "je suis la",
            "Aang aime le chocolat",
            "Aang est plus forte que Pryhdzzaffzufgzky",
            "Aang pense qu'elle va mourir",
            "Aang va a la peche",
            "Aang ella appelle moi ce soir",
            "Aang va a la cantine",
            "Katara va a la cantine",
            "Katara va a la peche",
            "Katara se fait draguer",
            "Katara pense qu'elle va mourir",
            "Katara pense que tu vas mourir",
            "Katara aime boire l'eau de la riviere",
            "Katara mange indien",
            "Kataraahahah esteban zia tao les...",
            "Katara ah ah ah stay in alive",
            "Aang est plus forte que lui",
            "Iroh est plus forte que moi",
            "Zuko est plus forte que Pryhdzzaffzufgzky",
            "Azula est plus forte qu'ArthurV2",
            "Katara est plus forte que Valentin",
            "Katara est plus forte que Mouloud",
            "Katara a oublie son katana"
        };

    public static Sentences getInstance() {
        if(INSTANCE== null){
            // HAUT-DROITE -> x = 0, y = size[1] - 1
            INSTANCE = new Sentences();
        }
        return INSTANCE;
    }

    private Sentences() {
       
    }
}
