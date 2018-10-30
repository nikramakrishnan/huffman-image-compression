/*
 * Copyright Nikhil Ramakrishnan
 */
package huffmancoding;

public class HuffmanCoding {
    
    
    /**
     * Main class to initiate and control the program
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        		
        String imgpath = args.length>0? args[0]:"digital_image_processing.jpg";
        
        Tree[] minheap;
        // Input
        System.out.println("Welcome to the Huffman Lossless Compressor");
        System.out.println("Reading "+imgpath+"...");
        ImageToMatrix  itm = new ImageToMatrix();
        int[][] img_arr = itm.convert(imgpath);
        int width = itm.getWidth();
        int height = itm.getHeight();
        
        PreProcessor pp = new PreProcessor();
        minheap = pp.process(img_arr, width, height);
        
        HuffCode hf = new HuffCode();
        hf.generate(minheap);
        
        int oldsize = 8*width*height;
        
        int[] freq = pp.getFreq();
        int[] codeLen = hf.getHuff();
        int newsize = 0;
        for(int i=0;i<256;i++){
            if(freq[i]!=0){
            //System.out.println(i+";"+freq[i]);
            newsize += freq[i]*codeLen[i];
            }
        }
        
        //System.out.println("Old size =" + oldsize + " bits");
        //System.out.println("New size =" + newsize + " bits");
        double ratio1 = oldsize/(newsize*1.0);
        ratio1 = Math.round(ratio1 * 100.0) / 100.0;
        System.out.println("\n\n");
        System.out.println("Lossless: "+ratio1);
        
        
        Tree[] minheap2;
        minheap2 = pp.processLossy();

        hf.generate(minheap2);
        
        int[] freq2 = pp.getFreq();
        int[] codeLen2 = hf.getHuff();
        int newsize2 = 0;
        for(int i=0;i<256;i++){
            if(freq2[i]!=0){
            //System.out.println(i+";"+freq2[i]);
            newsize2 += freq2[i]*codeLen2[i];
            }
        }
        
        //System.out.println("Old size =" + oldsize + " bits");
        //System.out.println("New size =" + newsize2 + " bits");
        double ratio2 = oldsize/(newsize2*1.0);
        ratio2 = Math.round(ratio2 * 100.0) / 100.0;
        System.out.println("Lossy: "+ratio2);
        
    }
    /*
        System.out.println("Please enter the width and height separated by space:\n");
        Scanner s = new Scanner(System.in);
        String in = s.nextLine();
        String[] dim = in.split(" ");
        int width = Integer.parseInt(dim[0]);
        int height = Integer.parseInt(dim[1]);
        
        int[][] img_arr = new int[width][height];
        
        System.out.println("Please enter the matrix:\n");
        for (int i = 0; i < height; i++) {
            String line = s.nextLine();
            String[] vals = line.split(" ");
            for (int j = 0; j < width; j++) {
                img_arr[j][i] = Integer.parseInt(vals[j]);
            }
        }
    */
}
