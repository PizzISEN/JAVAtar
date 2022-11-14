class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
<<<<<<< HEAD
         int[] s= {4,4};
        // System.out.println("Constructeur");
         Carte c = new Carte(s);
         System.out.println("Case Dispo");
         for (int i = 0; i < s[0]; i++) {
            for (int y = 0; y < s[1]; y++) {
                System.out.println(c.carte.get(i).get(y).type);
            }
        }
        // System.out.println("\n\n CASE 2:2");
        // for (int index = 0; index <c.caseDispo(2, 2).size(); index++) {

        //     System.out.println( "N" + index+ " : " + c.caseDispo(2,2).get(index).x + c.caseDispo(2,2).get(index).y);
        // }
        c.matrice(new Coord(1,1),new Coord(3,2));
=======
        int[] s= {30,30};
        System.out.println("Constructeur");
        Carte c = new Carte(s);
>>>>>>> e911f7fcff84d4334e5418d7d56ed7b5207b4813
        
    }
}
