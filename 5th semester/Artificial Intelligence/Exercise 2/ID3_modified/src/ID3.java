import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class ID3 {
    Node head;


    public void createTree(Double[] ig, HashSet<String> words)
    {
        ArrayList<String> allWords = new ArrayList<>();
        for (String word : words) {
            allWords.add(word);
        }
        ArrayList<SortItem> s = new ArrayList<>();
        for(int i = 0; i < ig.length; ++i)
        {
            s.add(new SortItem(ig[i], allWords.get(i) ));
        }

        Collections.sort(s);
        int kNodes = Test.k;

        Node currentNode = new Node();
        while(kNodes > 0)
        {
            SortItem item = s.remove(0);
            int timesFoundInSpam = 0;
            int timesFoundInHam = 0;
            if(Test.wordsInSpam.get(item.value) != null)
            {
                timesFoundInSpam = Test.wordsInSpam.get(item.value);
            }

            if(Test.wordsInHam.get(item.value) != null)
            {
                timesFoundInHam = Test.wordsInHam.get(item.value);
            }


            if(head ==null )
            {
                head = new Node();
                head.value = item.value;
                Node help = new Node();
                if(timesFoundInHam > timesFoundInSpam)
                {
                    help.value = "H_A_M";
                }
                else {
                    help.value = "S_P_A_M";
                }
                head.right = help;
                Node n = new Node();
                head.left = n;
                currentNode = n;

            }else {
                currentNode.value = item.value;
                Node help = new Node();
                if(timesFoundInHam > timesFoundInSpam)
                {
                    help.value = "H_A_M";
                }
                else {
                    help.value = "S_P_A_M";
                }
               currentNode.right = help;

                Node n = new Node();
                currentNode.left = n;
                currentNode = n;
            }
            --kNodes;
        }

        //could be done by giving random value(spam ir ham) by using random generator
        currentNode.value = "S_P_A_M";

    }

    public  boolean predict(File f, String mailType)
    {
        ArrayList<String> words = new ArrayList<>();
        try {
            Scanner input = new Scanner(f);
            while (input.hasNext()) {
                String word  = input.next();
                //put word found in ham mail only once
                words.add(word);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int theEstimate = 0;
        for(int i = 0; i < words.size(); ++i)
        {
            if(check(words.get(i) ).equals("H_A_M"))
            {
                theEstimate = 1;
                break;
            } else if(check(words.get(i) ).equals("S_P_A_M")){
                theEstimate = -1;
                break;
            }
        }

       if(theEstimate == 1 && mailType == "HAM")
       {
           return true;
       }else if(theEstimate == -1 && mailType == "SPAM")
       {
           return true;
       }else{
           return false;
       }
    }

    public String check(String word)
    {
        Node current = head;
        while(current.left != null)
        {
            if(current.value.equals(word)) return current.right.value;
            current = current.left;
        }

        return "none";
    }


    class SortItem implements Comparable<SortItem>
    {
        SortItem(){}
        SortItem(Double d, String value)
        {
            this.d = d;
            this.value = value;
        }
        Double d;
        String value;

        @Override
        public int compareTo(SortItem o) {
            return (int) (o.d*10000 - this.d*10000);
        }
    }
}
