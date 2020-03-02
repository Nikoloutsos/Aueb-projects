import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ReadFiles {

    public void readHam(File[] ham)
    {
        int count = 0;
        for(int i = 0; i < Test.filesforTraining; ++i)
        {
            File[] files = ham[i].listFiles();
            for(File f: files){
                count++;
                //read file
                HashMap<String, Integer> filewords = new HashMap<>();
                try {
                    Scanner input = new Scanner(f);
                    while (input.hasNext()) {
                        String word  = input.next();
                        //put word found in ham mail only once
                        if(filewords.get(word) == null){
                            filewords.put(word, 1);
                            if(Test.wordsInHam.get(word) == null)
                            {
                                Test.wordsInHam.put(word, 1);
                            }else {
                                Test.wordsInHam.put(word, Test.wordsInHam.get(word) + 1);
                            }
                        }
                       // System.out.println(word);
                    }
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        Test.hamMails = count;
    }


    public void readSpam(File[] spam)
    {
        int count = 0;
        for(int i = 0; i <  Test.filesforTraining; ++i)
        {
            File[] files = spam[i].listFiles();
            for(File f: files){
                //read file
                count ++;
                HashMap<String, Integer> filewords = new HashMap<>();
                try {
                    Scanner input = new Scanner(f);
                    while (input.hasNext()) {
                        String word  = input.next();
                        //put word found in ham mail only once
                        if(filewords.get(word) == null){
                            filewords.put(word, 1);
                            if(Test.wordsInSpam.get(word) == null)
                            {
                                Test.wordsInSpam.put(word, 1);
                            }else {
                                Test.wordsInSpam.put(word, Test.wordsInSpam.get(word) + 1);
                            }
                        }
                       // System.out.println(word);
                    }
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        Test.spamMails = count;
    }

}
