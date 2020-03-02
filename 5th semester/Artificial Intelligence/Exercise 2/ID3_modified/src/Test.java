import java.io.File;
import java.util.*;

public class Test {

    public static HashMap<String, Integer> wordsInSpam = new HashMap<>();
    public static HashMap<String, Integer> wordsInHam = new HashMap<>();

    public static int k = 660; // from development
    public static int filesforTraining = 8;
    public static int hamMails = 0;
    public static int spamMails = 0;

    public static void main(String[] args) {

        ArrayList<String> hamFiles = new ArrayList<>();
        ArrayList<String> spamFiles = new ArrayList<>();

        String path_ham = "src\\dataSets\\enron1\\ham\\ham_";
        String path_spam = "src\\dataSets\\enron1\\spam\\spam_";
        for (int i = 1; i <= 10; ++i) {
            hamFiles.add(path_ham + i);
            spamFiles.add(path_spam + i);
        }

        //all ham files directories
        File[] fileH = new File[10];
        for (int i = 0; i < 10; ++i) {
            fileH[i] = new File(hamFiles.get(i));
        }
        //all spam files directories
        File[] fileS = new File[10];
        for (int i = 0; i < 10; ++i) {
            fileS[i] = new File(spamFiles.get(i));
        }

        //reads mails and fills hash maps with words
        ReadFiles rf = new ReadFiles();
        rf.readHam(fileH);
        rf.readSpam(fileS);

//--------------------------------------------------------------------------------------------------------------------------

/*find igs for each word, because of a kind of pruning implemented no need to do it again. just calculate igs once.
Another version of ID3 which is faster than the usual*/
        HashSet<String> allWords = new HashSet<>();
        for (Map.Entry<String, Integer> stringIntegerEntry : wordsInSpam.entrySet()) {
            allWords.add(stringIntegerEntry.getKey());
        }

        for (Map.Entry<String, Integer> stringIntegerEntry : wordsInHam.entrySet()) {
            allWords.add(stringIntegerEntry.getKey());
        }


        Double[] ig = new Double[allWords.size()];

        double p1 = (spamMails * 1.0) / (spamMails + hamMails);
        double entropy = -p1 * log_2(p1) - (1 - p1) * log_2(1 - p1);

        int index = 0;
        for (String word : allWords) {
            int sp = 0;
            int hp = 0;
            if (wordsInSpam.get(word) != null) sp = wordsInSpam.get(word);
            if (wordsInHam.get(word) != null) hp = wordsInHam.get(word);

            double p_contains = (sp + hp * 1.0) / (sp + hp);
            double pk1 = 1.0 * sp / (sp + hp);
            double pk2 = 1.0 * hp / (hp + sp);
            Double entropyOfWord = new Double(-pk1 * log_2(pk1) - (pk2) * log_2(pk2));
            ig[index] = new Double(entropy - entropyOfWord);
            if (entropyOfWord.isNaN()) {
                if (sp == 0) {
                    if (hp >= 15.0 / 100 * hamMails) {
                        entropyOfWord = 1.0 * hp / (0.15 * hamMails);
                        ig[index] = new Double(entropyOfWord);
                    }

                } else {
                    if (sp >= 15.0 / 100 * spamMails) {
                        entropyOfWord = 1.0 * sp / (0.15 * spamMails);
                        ig[index] = new Double(entropyOfWord);
                    }

                }
            }

            ++index;
        }

        for (int i = 0; i < ig.length; ++i) {
            if (ig[i].isNaN()) ig[i] = new Double(-1);
        }


//----------------------------------------------------------------------------------------------------------------------
//development
//find a good k(number of nodes of decision tree)
        /*double[] resultAccurate = new double[3001];
        for (int i = 100; i <= 3000; i += 10) {

            k = i;
            ID3 idOb = new ID3();
            idOb.createTree(ig, allWords);


            //calculate accuracy with dev data
            File f = new File(hamFiles.get(0));
            File[] files = f.listFiles();
            int countFiles = 0;
            int good = 0;
            for (File ff : files) {
                if (idOb.predict(ff, "HAM") == true) {
                    ++good;
                }
                ++countFiles;
            }

            f = new File(spamFiles.get(0));
            files = f.listFiles();
            for (File ff : files) {
                if (idOb.predict(ff, "SPAM") == true) {
                    ++good;
                }
                ++countFiles;
            }
            resultAccurate[i] = 1.0 * good / countFiles * 100;
            //System.out.println("nodes->" +  i + " : " + "accuracy->" + resultAccurate[i]);
        }

        k = 100;
        double maxPrecentage = resultAccurate[100];
        for (int i = 110; i <= 3000; i += 10) {
            if (resultAccurate[i] > maxPrecentage) {
                maxPrecentage = resultAccurate[i];
                k = i;
            }
        }

        System.out.println("\nBest accuracy given for " + k + " nodes/words");*/

//----------------------------------------------------------------------------------------------------------------------
        //create decision tree
        ID3 idOb = new ID3();
        idOb.createTree(ig, allWords);

//----------------------------------------------------------------------------------------------------------------------
        //test
        //calculate accuracy with test data


        double truePos = 0;
        double falsePos = 0;
        double falseNeg = 0;
        double precision;
        double recall;
        double f1;

        File f = new File(hamFiles.get(9));
        File[] files = f.listFiles();
        int countFiles = 0;
        int good = 0;
        for (File ff : files) {
            if (idOb.predict(ff, "HAM") == true) {
                ++truePos;
                ++good;
            } else {
                ++falseNeg;
            }
            ++countFiles;
        }

        f = new File(spamFiles.get(9));
        files = f.listFiles();
        for (File ff : files) {
            if (idOb.predict(ff, "SPAM") == true) {
                ++good;
            } else {
                ++falsePos;
            }
            ++countFiles;
        }

        precision = truePos / (truePos + falsePos);
        recall = truePos / (truePos + falseNeg);
        f1 = 2 * ( (precision*recall) / (precision+recall) );

        System.out.println("----------------------------------------");
        System.out.println("The accuracy of the test data is: " + (good * 1.0 / countFiles) * 100 + "%");
        System.out.println("----------------------------------------");
        System.out.println("precision -> " + precision);
        System.out.println("recall -> " + recall);
        System.out.println("F1 -> " + f1);

    }

    //----------------------------------------------------------------------------------------------------------------------
    public static double log_2(double n) {
        return Math.log10(n) / Math.log10(2);
    }
}
