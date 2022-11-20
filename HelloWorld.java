class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        int[] s= {10,10};
        System.out.println("Constructeur");
        Carte c = new Carte(s);
        c.matrice(new Coord(0,6),new Coord(0,3),"A");
    }
}
