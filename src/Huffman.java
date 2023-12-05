import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

class HuffmanNode {
    int data;char c;
    HuffmanNode left;
    HuffmanNode right;
}
class ImplementComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y) {
        return x.data - y.data;
    }
}


public class Huffman {
    static HashMap<Character,String>ans=new HashMap<Character,String>();
    static HashMap<String,Character> table = new HashMap<String,Character>() ;
    public static void DFS(HuffmanNode root, String s) {
        if (root.left == null && root.right == null && Character.isLetter(root.c)) {
            System.out.println(root.c + " " + s);
            ans.put(root.c, s);
            table.put(s,root.c);
            return;
        }
        DFS(root.left, s + "0");
        DFS(root.right, s + "1");
    }
    public static String decompress( String code ){
        String temp = "" ;
        String word = "" ;
        for ( int i = 0 ; i < code.length() ; i++ ){
            temp += code.charAt(i) ;
            if ( table.containsKey(temp)  ){
                word += table.get(temp) ;
                temp = "" ;
            }
        }
        return word ;

    }

    public static void main(String[] args) {
        PrintStream out= null;
        try {
            out = new PrintStream(new FileOutputStream("output.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setOut(out);
        String test ="";
        try {
            File input = new File("data.txt");
            Scanner myReader = new Scanner(input);
            test = myReader.nextLine();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        HashMap<Character,Integer>map=new HashMap<Character,Integer>();
        for (int i=0;i<test.length();i++){
            if(!map.containsKey(test.charAt(i)))map.put(test.charAt(i),0);
            else map.put(test.charAt(i),map.get(test.charAt(i))+1);
        }
        PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(test.length(), new ImplementComparator());
        for (Map.Entry<Character,Integer> entry : map.entrySet()) {
            HuffmanNode node = new HuffmanNode();
            char ch= entry.getKey();
            int freq= entry.getValue();

            node.c = ch;
            node.data = freq;

            node.left = null;
            node.right = null;
            q.add(node);
        }
        HuffmanNode root = null;

        while (q.size() > 1) {

            HuffmanNode small1 = q.peek();
            q.poll();

            HuffmanNode small2 = q.peek();
            q.poll();

            HuffmanNode new_node = new HuffmanNode();

            new_node.data = small1.data + small2.data;
            new_node.c = '!';
            new_node.left = small1;
            new_node.right = small2;
            root = new_node;

            q.add(new_node);
        }

        DFS(root,"");
        String result="";
        for (int i=0;i<test.length();i++){
            result+=ans.get(test.charAt(i));
        }
        System.out.println("TABLE");
        System.out.println("Size before compress: "+(test.length()*8)+" bits");
        System.out.println("The encoded String: "+result);
        System.out.println("Size after compress: "+(result.length()+(table.size()*8))+" bits");
        String de=decompress(result);
        System.out.println("Decompressed string "+de);

    }


}
