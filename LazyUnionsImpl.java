package bigclustering;

import java.util.Comparator;
import java.util.PriorityQueue;


public class LazyUnionsImpl extends LazyUnions {
    
    /* I need to implement the full graph with edges */
    static Comparator cust = (Comparator<Edge>) (Edge o1, Edge o2) -> {
        Integer a = o1.weight;
        Integer b = o2.weight;
        
        return a.compareTo(b);
    };
    
    PriorityQueue<Edge> edges;
    
    
    public LazyUnionsImpl(int nVert) {
        super(nVert);
        
        this.edges = new PriorityQueue(10000,LazyUnionsImpl.cust);
    }
    
    /* new methods */
    
    // extend by find number of clusters
    public int getNumClusters(){
    
        int numCl = 0;
        
        for(int i = 0; i<this.leaders.length; i++ ){
        
            if(this.leaders[i]==i)
                numCl++;
        }
        
        return numCl;
    }
    
    // set edge
    
    public void setEdge(int a, int b, int weight){
    
        this.edges.add(new Edge( a, b, weight));
    }
    
    private Edge getTopEdge(){
    
        return this.edges.poll();
    }
    
    private boolean checkCycle(Edge edo){
        
        return this.find(edo.getA())==this.find(edo.getB());
    }
    
    // find clustering if I tell you to what distance I an searching 
    // dist is exclusive
    public void findClustering(int dist){
    
        Edge temp;
        
        while(true){
        
            temp = this.getTopEdge();
            
            
            if(temp!=null && !this.checkCycle(temp) && temp.getW()<dist){
                //System.out.println("you are in find function " + counter);
                //System.out.println("size of edge Priority queue " + this.edges.size());
                
                this.uniteEdge(temp);
            }
            else if(temp==null || temp.getW()>=dist){
                break;
            }
        }
    }
    
    public void findKclusters(int k){
    
        Edge temp;
        
        while(this.getNumClusters()>k){
        
            temp = this.getTopEdge();
            
            if(!this.checkCycle(temp)){
                this.uniteEdge(temp);
                
            }
        }
    }
    
    private void uniteEdge(Edge edo){
    
        this.union(edo.getA(), edo.getB());
    }
     
    
    public int maxDistance(){
    
        Edge temp;
        int distance = -1;
        
        while(true){
        
            temp = this.edges.poll();
            
            if(temp!=null && !this.checkCycle(temp)){
                distance = temp.getW();
                break;
            }
            else if(temp==null){
                break;
            }
        }
         
        return distance;
    }
    
    /* internal edge class */
    private class Edge{
    
        /* fields */
        // from = a, to = b
        int a;
        int b;
        private int weight;
        
        private Edge(int a, int b, int weight){
            this.a = a;
            this.b = b;
            this.weight = weight;
        }
        
        /* methods */
        
        private int getA(){
        
            return this.a;
        }
        
        private int getB(){
            
            return this.b;
        }
        
        private int getW(){
            return this.weight;
        }
    }
    
    
}
