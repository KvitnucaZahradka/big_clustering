package bigclustering;

import java.util.Arrays;

/**
 *
 * @author Pipjak
 */
public abstract class LazyUnions {
    
    /* fields */
    
    int[] leaders;
    int[] rank;
    
    
    /* constructor */
    LazyUnions(int nVert){
        
        this.leaders = new int[nVert];
        this.rank = new int[nVert];
        
        Arrays.fill(this.rank, 0, nVert-1, 0);
        
        // filling up the leaders:
        for(int i = 0; i<nVert; i++)
            this.leaders[i] = i; 
    }
    
    /* merge */
    public void union(int a, int b){
        
        int headA = this.find(a);
        int headB = this.find(b);
        
        // only if ranks are equal increase the rank of b
        if(this.rank[headA]==this.rank[headB])
            this.rank[headB]++;
        
        // set parent of a node with smaller rank to a leader of a node with
        // bigger rank
        if(this.rank[headA]>this.rank[headB])
            this.leaders[headB]=this.leaders[headA];
        else
            this.leaders[headA]=this.leaders[headB];
    
    }
    
    /* find with possible rewire, remember true leader is selfleader */
    public int find(int a){
    
        int leader = a;
        
        // by this you will find a leader:
        while(this.leaders[leader]!=leader){
            
            leader = this.leaders[leader];
        }
        
        // rewire
        int temp = a;
        int next;
        
        while(this.leaders[temp]!=temp){
        
            next = this.leaders[temp]; 
            this.leaders[temp] = leader;
            temp = next;
        }
        
        return leader;
    }
    
    
}
