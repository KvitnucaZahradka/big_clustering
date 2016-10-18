package bigclustering;

import java.util.HashMap;
import java.util.HashSet;


/**
 *
 * @author Pipjak
 */
public class BinGraph {
    
    /* fields */
    static int id = 0;
    
    HashMap<Integer,HashSet<Integer>> mapNid;
    HashMap<Integer,Node> mapIdnode;
    
    LazyUnionsImpl lazyIons;
    
    /* constructor */
    public BinGraph(int nodes){
    
        this.mapNid = new HashMap();
        this.mapIdnode = new HashMap();
        
        this.lazyIons = new LazyUnionsImpl(nodes);
    }
   
    
    
    /* methods */
    
    // I guerantee that I am putting correct string in, ideally I should throw 
    // some exception there
    public void add(String s){
    
        // create node with struct of length 24
        Node nd = new Node(24);
        nd.add(s);
        
        this.mapIdnode.put(BinGraph.id, nd);
        
        if(this.mapNid.containsKey(nd.getN())){
            this.mapNid.get(nd.getN()).add(BinGraph.id);
        }
        else{
        
            HashSet<Integer> temp = new HashSet();
            temp.add(BinGraph.id);
            this.mapNid.put(nd.getN(), temp);
        }
        
        BinGraph.id++;
        
    }
    
    // calculate maximal number of clusters, argument is min separating distance
    // in our case its gonna be 3
    
    private void calculate3edges(int id){
        
        System.out.println("now is id " + id);
        int twoN = this.mapIdnode.get(id).getN();
        
        int temp;
        int dist;
        HashSet<Integer> tempSet;
        
        // scanning through the possible 2-vicinity of a node:
        for(int i = 0; i<5; i++){
        
            // this is the 2 or 1 bit change vicinity:
            temp = twoN + (i-2);
            //System.out.println("n is " + temp);
            tempSet = this.mapNid.get(temp);
            
            // if not Null calc hamD 
            if(tempSet!=null){
            
                for(Integer tempId : tempSet){
                    
                        if(tempId>id){
                        dist = this.mapIdnode.get(id).hamD(this.mapIdnode.get(tempId));
                        
                        if(dist!=0 && dist<=3){
                            
                            this.lazyIons.setEdge(id, tempId, dist);
                        }
                    }
                }
            }
        }
    }
    
    // calculate pruned graph:
    private void claculatePrunedGraph(){
        
        this.mapIdnode.keySet().stream().forEach((Integer idD) -> {
            BinGraph.this.calculate3edges(idD);
        });
    
    }
    
    // calculate max Number of clusters
    public int maxNumClusters(int distance){
    
        this.claculatePrunedGraph();
        
        this.lazyIons.findClustering(distance);
        return this.lazyIons.getNumClusters();
        
    }
    
    
    
    /* internal class Node*/
    private class Node{
    
        /* fields */
        boolean[] struct;
        int n;
        
        /* constructor */
        Node(int length){
            
            this.struct = new boolean[length];
        
        }
        
        
        /* methods */
        private void add(String s){
        
            int nN = 0;
            
            String[] arg = s.split("\\s+");
        
            // find number of ones in arg:
            for (int i=0; i<arg.length; i++) {
                if (Integer.parseInt(arg[i])==1) {
                    
                    this.struct[i]=true;
                    nN++;
                }
                else
                    this.struct[i]=false;
            }
            
            this.n=nN;
        }
        
        // calculate hamming distance
        private int hamD(Node nd){
        
            int ham = 0;
            
            for(int i=0; i<this.struct.length; i++){
            
                if((this.struct[i]^nd.getMember(i)))
                    ham++;
            }
            
            return ham;    
        }
        
        // get struct member:
        private boolean getMember(int i){
            return this.struct[i];
        }
        
        // get N
        public int getN(){ 
            return this.n;
        }
    } 
}



















