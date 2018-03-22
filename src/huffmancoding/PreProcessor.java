/*
 * Copyright Nikhil Ramakrishnan
 */
package huffmancoding;


public class PreProcessor {
    Tree[] minheap;
    int[][] img;
    int[] freq;
    int lossyFreq[];
    int treeSize;
    int lossyTreeSize;
    
    
    /**
     * Initializes all variables, and handles the process flow of the class
     * 
     * @param img_array The image matrix
     * @param width Width of the supplied image matrix
     * @param height Height of the supplied image matrix 
     * @return minheap priority queue
     */
    public Tree[] process(int[][] img_array, int width, int height){
        img = img_array;
        freq = new int[256];
        treeSize = 1;
        /*Calculate the frequency */
        calcFreq(width,height);
        /* Make nodes for the pixels */
        makeNodes();
        /* Heapify the minheap */
        for(int i=treeSize/2; i>0; i--){
            percolate(i);
        }
        
//        System.out.println("Array heapified");
        return minheap;
    }
    
    /**
     * Initializes all variables, and handles the process flow of the class for lossy compression part
     * @return lossy minheap priority queue
     */
    public Tree[] processLossy(){
        /* Do the loss part */
        lossyCompress();
        treeSize = lossyTreeSize;
        /* Make nodes for the pixels */
        makeNodes();
        /* Heapify the minheap */
        for(int i=treeSize/2; i>0; i--){
            percolate(i);
        }
//        System.out.println("Lossy Array heapified");
        return minheap;
    }
    
    
    
    
    
    /**
     * Calculates the frequency of each pixel value [0,255] in the image matrix
     * 
     * @param width Width of the supplied image matrix
     * @param height Height of the supplied image matrix
     */
    public void calcFreq(int width, int height){
        int value;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
               value = img[j][i];
               freq[value]+=1;
               if(freq[value]==1) treeSize+=1;
            }
        }
    }
    
    /**
     * Creates Nodes from the frequency array
     */
    public void makeNodes(){
        minheap = new Tree[treeSize];
        int index = 1;
        for (int i = 0; i < 256; i++) {
            if(freq[i]!=0){
                Tree pixel = new Tree(i,freq[i]);
                minheap[index] = pixel;
                index+=1;
            }
        }
    }
    
    /**
     * Percolate the node down to satisfy min-heap property
     * @param index The node to percolate down and build heap
     */
    public void percolate(int index){
        while(2*index < treeSize){
            int l = 2*index;
            int r = l+1;

            int val = minheap[index].freq;
            int target;
            if(r<treeSize && minheap[r].freq<minheap[l].freq) target = r;
            else target = l;
            
            if(minheap[target].freq < val) {
                /* Swap the two */
                Tree temp = minheap[index];
                minheap[index] = minheap[target];
                minheap[target] = temp;
                index = target;
            }
            else break;
        }
    }
    
    /**
     * Get the frequency array
     * @return Frequency array
     */
    public int[] getFreq(){
        return freq;
    }
    
    /**
     * Populate array lossyFreq with lossy image frequency data
     */
    public void lossyCompress(){
        lossyTreeSize = 1;
        for(int i=0;i<256;i+=10){
            int max = i;
            int sum = 0;
            for(int j=i;j<i+10;j++){
                if(j<256){
                    if(freq[j]>freq[max]) max = j;
                    sum += freq[j];
                    freq[j] = 0;
                }
            }
            freq[max] = sum;
            if(sum>0) lossyTreeSize+=1;
        }
        
    }
}
