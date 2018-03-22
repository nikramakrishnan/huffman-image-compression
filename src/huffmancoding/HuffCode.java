/*
 * Copyright Nikhil Ramakrishnan
 */
package huffmancoding;


public class HuffCode {
    Tree[] minheap;
    int TreeSize;
    int max_tree_ht;
    int[] huffman;
    
    /**
     * Generate Huffman Tree
     * @param mh minheap tree of frequency nodes
     */
    public void generate(Tree[] mh){
        minheap = mh;
        TreeSize = mh.length;
        max_tree_ht = 1;
        huffman = new int[256];
        
        int freqSum;
        while(TreeSize>2){
            Tree temp = new Tree();
            freqSum = 0;
            
            Tree min = minheap[1];
            freqSum += min.freq;
            //System.out.println("Freq of minheap top ="+freqSum);
            temp.left = min;
            //swap
            minheap[1] = minheap[TreeSize-1];
            TreeSize -= 1;
            percolate(1);
            
            Tree min2;
            /*int target;
            if(TreeSize>3 && minheap[3].freq < minheap[2].freq) {min2 = minheap[3]; target = 3;}
            else {min2 = minheap[2]; target = 2;}
            freqSum += min2.freq;
            */
            min2 = minheap[1];
            freqSum += min2.freq;
            temp.right = min2;
            //System.out.println("Freq of minheap second ="+min2.freq);
            //System.out.println("FreqSum = "+freqSum);
            
            //swap
            minheap[1] = minheap[TreeSize-1];
            TreeSize -= 1;
            percolate(1);
            
            //Put internal tree in the queue
            temp.freq = freqSum;
            insert(temp);
            max_tree_ht+=1;

        }
        Tree fin = minheap[1];
        int top = 0;
        int[] codes = new int[max_tree_ht];
        getCodes(fin,codes,top);
    }
    
    /**
     * Percolate the node down to satisfy min-heap property
     * @param index The node to percolate down and build heap
     */
    public void percolate(int index){
        while(2*index < TreeSize){
            int l = 2*index;
            int r = l+1;

            int val = minheap[index].freq;
            int target;
            if(r<TreeSize && minheap[r].freq<=minheap[l].freq) target = r;
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
     * Insert the supplied value in the minheap tree
     * @param node The node to insert in the priority queue
     */
    public void insert(Tree node){
        TreeSize += 1;
        minheap[TreeSize-1] = node;
        int hole = TreeSize-1;
        int val = node.freq;
        int parent = hole/2;
        //System.out.println("Current Hole = "+hole);
        //System.out.println("Current Parent = "+parent);
        while(hole>1 && val<minheap[parent].freq){
            
            
            Tree temp = minheap[hole];
            minheap[hole] = minheap[parent];
            minheap[parent] = temp;
            hole = parent;
            parent = hole/2;
        }
    }
    
    /**
     * Generate the Huffman code for each leaf and store it in an array
     * @param arr Generated Huffman code array
     * @param n Top of array
     * @param value Value of the code
     */
    public void printArr(int[] arr, int n, int value){
        huffman[value] = n;
        /*
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i]);
        }
        System.out.println("\n");
        */
    }
    
    /**
     * Assign 0 to left and 1 to right and traverse to generate codes
     * @param root root of tree
     * @param arr array to store codes in
     * @param top integer to store top of array
     */
    public void getCodes(Tree root, int[] arr, int top){
        if (root.left!=null){
            arr[top] = 0;
            getCodes(root.left, arr, top+1);
        }
        if(root.right!=null){
            arr[top] = 1;
            getCodes(root.right, arr, top+1);
        }
        
        if(root.right==null && root.left == null){
            //System.out.print(root.value + ": ");
            printArr(arr,top, root.value);
        }
    }
    
    /**
     * Return the Huffman codes array
     * @return Huffman codes array
     */
    public int[] getHuff(){
        return huffman;
    }
}
