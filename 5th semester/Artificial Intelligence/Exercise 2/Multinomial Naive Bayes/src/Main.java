import java.io.File;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        for (int i = 0; i < 8; ++i)
        {
            System.out.println("Training size " + (i + 1));
            int trainingSize = 0;

            NaiveBayesClassifier b = new NaiveBayesClassifier(0.001, 1000);
            ArrayList<String> trainingFiles = new ArrayList<>();
            ArrayList<String> evaluationFiles = new ArrayList<>();

            File folder = new File(args[0]);

            for (File fileEntry : folder.listFiles())
            {
                if (fileEntry.isDirectory() && trainingSize <= i)
                {
                    for (File file : fileEntry.listFiles())
                    {
                        if (file.isFile())
                        {
                            trainingFiles.add(file.getAbsolutePath());
                        }
                    }

                    ++trainingSize;
                }
            }

            b.Train(trainingFiles.toArray(String[]::new));

            folder = new File(args[1]);

            for (File fileEntry : folder.listFiles())
            {
                if (fileEntry.isDirectory())
                {
                    for (File file : fileEntry.listFiles())
                    {
                        if (file.isFile())
                        {
                            evaluationFiles.add(file.getAbsolutePath());
                        }
                    }
                }
            }

            b.Optimize();
            b.Evaluate(trainingFiles.toArray(String[]::new));
            System.out.println("-------------------------------");
        }
    }
}
