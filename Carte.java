import java.util.ArrayList;
// Importing utility classes
import java.util.*;

public class Carte {                        // Fonctionnelle
    public int[] size;
    public ArrayList<ArrayList<Case>>carte;

    private int dist[];
    private Set<Integer> settled;
    private PriorityQueue<Node> pq;
    // Number of vertices
    private int V;
    List<List<Node> > adj;

    Carte(int[] s){
        this.size = s;
        carte = new ArrayList<ArrayList<Case>>();
        for (int i = 0; i < s[0]; i++) {
            carte.add(new ArrayList<Case>());
            for (int y = 0; y < s[1]; y++) {
                carte.get(i).add(0,new Case(0, i, y));
            }
        }
        carte.get(0).get(0).setType(1);         //Air
        carte.get(s[0]-1).get(0).setType(2);      //Eau
        carte.get(0).get(s[1]-1).setType(3);      //Terre
        carte.get(s[0]-1).get(s[1]-1).setType(4);   //Feu
        System.out.println(carte);
    }

    public ArrayList<Coord> caseDispo(int x, int y){        //Fonctionnelle
        Coord coord=new Coord(x,y);                         //Renvoi les coord des cases dispo
        ArrayList<Coord> caseDispos = new ArrayList<Coord>();
        if(coord.x<size[0]-1 && carte.get(coord.x+1).get(coord.y).type==0){
            caseDispos.add(new Coord(coord.x+1,coord.y));
        }
        if(coord.x<size[0]-1 && coord.y<size[1]-1 && carte.get(coord.x+1).get(coord.y+1).type==0){
            caseDispos.add(new Coord(coord.x+1,coord.y+1));
        }
        if(coord.x>0 && carte.get(coord.x-1).get(coord.y).type==0){
            caseDispos.add(new Coord(coord.x-1,coord.y));
        }
        if(coord.x>0 &&  coord.y<size[1]-1 && carte.get(coord.x-1).get(coord.y+1).type==0){
            caseDispos.add(new Coord(coord.x-1,coord.y+1));
        }
        if(coord.y<size[1]-1 && carte.get(coord.x).get(coord.y+1).type==0){
            caseDispos.add(new Coord(coord.x,coord.y+1));
        }
        if(coord.y>0 && coord.x<size[0]-1 && carte.get(coord.x+1).get(coord.y-1).type==0){
            caseDispos.add(new Coord(coord.x+1,coord.y-1));
        }
        if(coord.y>0 && carte.get(coord.x).get(coord.y-1).type==0){
            caseDispos.add(new Coord(coord.x,coord.y-1));
        }
        if(coord.y>0 && coord.x>0 && carte.get(coord.x-1).get(coord.y-1).type==0){
            caseDispos.add(new Coord(coord.x-1,coord.y-1));
        }
        return caseDispos;
    }
        
        // Class 2
    // Helper class implementing Comparator interface
    // Representing a node in the graph
    class Node implements Comparator<Node> {
    
        // Member variables of this class
        public int node;
        public int cost;
    
        // Constructors of this class
    
        // Constructor 1
        public Node() {}
    
        // Constructor 2
        public Node(int node, int cost)
        {
    
            // This keyword refers to current instance itself
            this.node = node;
            this.cost = cost;
        }
    
        // Method 1
        @Override public int compare(Node node1, Node node2)
        {
    
            if (node1.cost < node2.cost)
                return -1;
    
            if (node1.cost > node2.cost)
                return 1;
    
            return 0;
        }
        }

    // Method 1
    // Dijkstra's Algorithm
    public void dijkstra(List<List<Node> > adj, int src)
    {
        this.adj = adj;
 
        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;
 
        // Add source node to the priority queue
        pq.add(new Node(src, 0));
 
        // Distance to the source is 0
        dist[src] = 0;
 
        while (settled.size() != V) {
 
            // Terminating condition check when
            // the priority queue is empty, return
            if (pq.isEmpty())
                return;
 
            // Removing the minimum distance node
            // from the priority queue
            int u = pq.remove().node;
 
            // Adding the node whose distance is
            // finalized
            if (settled.contains(u))
 
                // Continue keyword skips execution for
                // following check
                continue;
 
            // We don't have to call e_Neighbors(u)
            // if u is already present in the settled set.
            settled.add(u);
 
            e_Neighbours(u);
        }
    }
 
    // Method 2
    // To process all the neighbours
    // of the passed node
    private void e_Neighbours(int u)
    {
 
        int edgeDistance = -1;
        int newDistance = -1;
 
        // All the neighbors of v
        for (int i = 0; i < adj.get(u).size(); i++) {
            Node v = adj.get(u).get(i);
 
            // If current node hasn't already been processed
            if (!settled.contains(v.node)) {
                edgeDistance = v.cost;
                newDistance = dist[u] + edgeDistance;
 
                // If new distance is cheaper in cost
                if (newDistance < dist[v.node])
                    dist[v.node] = newDistance;
 
                // Add the current node to the queue
                pq.add(new Node(v.node, dist[v.node]));
            }
        }
    }
 
    public void matrice(Coord depart,Coord dest)
    {
        List<List<Node> > adj = new ArrayList<List<Node> >();
        ArrayList<Coord> caseDispos = new ArrayList<Coord>();    
        int dep=0;
        int des=0;
        int it=0;
        int itNode=0;
       this.adj
            = new ArrayList<List<Node> >();
        for (int i = 0; i < size[0]; i++) {
            for (int y = 0; y < size[1]; y++) {
                if(depart.x==i && depart.y==y){
                    dep=it;
                }
                if(dest.x==i && dest.y==y){
                    des=it;
                }
                if(carte.get(i).get(y).type== 0){
                    adj.add(new ArrayList<Node>());
                    caseDispos=caseDispo(i, y);
                    for (int j = 0; j < caseDispos.size(); j++) {
                        adj.get(itNode).add(new Node(caseDispos.get(j).x+(caseDispos.get(j).y*size[0]),1));
                    }
                    itNode++;
                }
                else{
                    adj.add(new ArrayList<Node>());
                    caseDispos=caseDispo(i, y);
                    for (int j = 0; j < caseDispos.size(); j++) {
                        adj.get(itNode).add(new Node(caseDispos.get(j).x+(caseDispos.get(j).y*size[0]),99));
                    }
                    itNode++;
                }
                it++;
            }
        }
        this.V = itNode;
        this.dist = new int[V];
        this.settled = new HashSet<Integer>();
        this.pq = new PriorityQueue<Node>(V, new Node());
         dijkstra(adj, dep);
  
         // Printing the shortest path to all the nodes
         // from the source node
         System.out.println("The shorted path from node :");
  
         for (int i = 0; i < dist.length; i++)
             System.out.println(dep + " to " + i + " is "
                                + dist[i]);

        System.out.println(dep + " to " + des + " is "
        + dist[des]);
        return;

    }
}
