package bigclustering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Pipjak
 */
public class BigClustering {
    
    /* fields */
    static int numNodes;
    
    BinGraph g;
    
    
    /* constructors */
    
    BigClustering(int n){
    
        this.g = new BinGraph(n);
    }
    
    /* methods */
    
    // claculate num of clusters, with distance @most distance:
    private int numClusters(int distance){
    
        return this.g.maxNumClusters(distance);
    }
    
    // read in number of vertices
    private static void readNumNodes() {
    
    try{
        File file = new File("clustering_big_small_II.txt");
        FileReader fileReader = new FileReader(file);
            
        BufferedReader bufferedReader = new BufferedReader(fileReader);    
        StringBuffer stringBuffer = new StringBuffer();
            
        String line;
            
        line = bufferedReader.readLine();
        String[] arg = line.split("\\s+");
        
        // fillint the number of vertices;
        BigClustering.numNodes = Integer.parseInt(arg[0]);
        
        // closing the fileReader:
        fileReader.close();
    
    
    }catch (IOException e) {
             e.printStackTrace();
        }
    
    
    }
    
    // read all the other lines;
    public void run() throws IOException {
        
    try {
        
        File file = new File("clustering_big_small_II.txt");
        FileReader fileReader = new FileReader(file);
            
        BufferedReader bufferedReader = new BufferedReader(fileReader);    
        StringBuffer stringBuffer = new StringBuffer();
            
        String line;
            
        // skip first line 
        line = bufferedReader.readLine();
       
        // read a next line:
        while ((line = bufferedReader.readLine()) != null ) {
                
            this.g.add(line);
        }
            
        // closing the fileReader:
        fileReader.close();
            
        }catch (IOException e) {
             e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) throws IOException {
        
        // reading num of nodes
        BigClustering.readNumNodes();
        
        // creating an instance
        BigClustering g = new BigClustering(BigClustering.numNodes);
        g.run();
        
        // we want distance=3
        System.out.println("Maximal number of clusters is " + g.numClusters(3));
        
    }
    
    
    
}
