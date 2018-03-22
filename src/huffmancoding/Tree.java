/*
 * Copyright Nikhil Ramakrishnan
 */
package huffmancoding;

public class Tree {
    Integer value;
    Integer freq;
    Tree left, right;
    Tree(){
        left = null;
        right = null;
    }
    Tree(Integer val, Integer fre){
        value = val;
        freq = fre;
    }
}
