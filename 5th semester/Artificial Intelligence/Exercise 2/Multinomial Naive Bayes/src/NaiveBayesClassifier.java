import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NaiveBayesClassifier
{
    private HashMap<String, Integer> globalDictionary;
    private HashMap<String, Integer> spamDictionary;
    private HashMap<String, Integer> hamDictionary;
    private HashMap<String, Integer> attributes;

    private ArrayList<HashMap<String, Integer>> spamMessages;
    private ArrayList<HashMap<String, Integer>> hamMessages;

    private int spamCounter;

    private double spamWords;
    private double hamWords;

    private double spamProbability;
    private double hamProbability;
    private double entropy;

    private double smoothingFactor = 0.001;
    private int numberOfAttributes = 1000;

    private int correct = 0;
    private int truePositive = 0;
    private int falsePositive = 0;
    private int falseNegative = 0;

    public NaiveBayesClassifier()
    {
        globalDictionary = new HashMap<>();
        spamDictionary = new HashMap<>();
        hamDictionary = new HashMap<>();
        attributes = new HashMap<>();

        spamMessages = new ArrayList<>();
        hamMessages = new ArrayList<>();

        spamCounter = 0;

        spamWords = 0;
        hamWords = 0;
    }

    public NaiveBayesClassifier(double smoothingFactor, int numberOfAttributes)
    {
        globalDictionary = new HashMap<>();
        spamDictionary = new HashMap<>();
        hamDictionary = new HashMap<>();
        attributes = new HashMap<>();

        spamMessages = new ArrayList<>();
        hamMessages = new ArrayList<>();

        spamCounter = 0;

        spamWords = 0;
        hamWords = 0;

        this.smoothingFactor = smoothingFactor;
        this.numberOfAttributes = numberOfAttributes;
    }

    public void Train(String[] dataset)
    {
        BufferedReader reader = null;

        for (String filepath : dataset)
        {
            try
            {
                reader = new BufferedReader(new FileReader(filepath));
                String line = null;

                if (filepath.contains("spmsg"))
                {
                    ++spamCounter;

                    HashMap<String, Integer> message = new HashMap<>();
                    while ((line = reader.readLine()) != null)
                    {
                        String[] values = line.split("\\s+");

                        if (values.length == 0) continue;

                        for (String word : values)
                        {
                            if (word.isEmpty() || word.equalsIgnoreCase("Subject:")) continue;

                            if (spamDictionary.containsKey(word))
                            {
                                spamDictionary.put(word, spamDictionary.get(word) + 1);
                            }
                            else
                            {
                                spamDictionary.put(word, 1);
                            }

                            globalDictionary.put(word, 1);
                            message.put(word, 1);
                        }
                    }

                    spamMessages.add(message);
                }
                else
                {
                    HashMap<String, Integer> message = new HashMap<>();
                    while ((line = reader.readLine()) != null)
                    {
                        String[] values = line.split("\\s+");

                        if (values.length == 0) continue;

                        for (String word : values)
                        {
                            if (word.isEmpty() || word.equalsIgnoreCase("Subject:")) continue;

                            if (hamDictionary.containsKey(word))
                            {
                                hamDictionary.put(word, hamDictionary.get(word) + 1);
                            }
                            else
                            {
                                hamDictionary.put(word, 1);
                            }

                            globalDictionary.put(word, 1);
                            message.put(word, 1);
                        }
                    }

                    hamMessages.add(message);
                }
            }
            catch (IOException e)
            {
                System.err.println("Error processing file: " + filepath);
                System.exit(-1);
            }
            finally
            {
                try
                {
                    reader.close();
                }
                catch (Exception e)
                {
                    System.err.println("Error closing file: " + filepath);
                    System.exit(-1);
                }
            }
        }

        spamProbability = spamCounter / (double)dataset.length;
        hamProbability = 1 - spamProbability;
        entropy = - spamProbability * (Math.log(spamProbability) / Math.log(2)) - (hamProbability) * (Math.log(hamProbability) / Math.log(2));
    }

    public void Optimize()
    {
        HashMap<String, Double> informationGain = new HashMap<>();

        for (Map.Entry<String, Integer> entry : globalDictionary.entrySet())
        {
            double spamExist = 0;
            double spamNoExist = 0;
            double hamExist = 0;
            double hamNoExist = 0;

            for (HashMap<String, Integer> message : spamMessages)
            {
                if (message.containsKey(entry.getKey()))
                {
                    ++spamExist;
                }
                else
                {
                    ++spamNoExist;
                }
            }

            for (HashMap<String, Integer> message : hamMessages)
            {
                if (message.containsKey(entry.getKey()))
                {
                    ++hamExist;
                }
                else
                {
                    ++hamNoExist;
                }
            }

            double entropySpamExist = spamExist / (spamExist + hamExist) * (Math.log(spamExist / (spamExist + hamExist)) / Math.log(2));
            double entropySpamNoExist = spamNoExist / (spamNoExist + hamNoExist) * (Math.log(spamNoExist / (spamNoExist + hamNoExist)) / Math.log(2));

            double entropyHamExist = hamExist / (spamExist + hamExist) * (Math.log(hamExist / (spamExist + hamExist)) / Math.log(2));
            double entropyHamNoExist = hamNoExist / (spamNoExist + hamNoExist) * (Math.log(hamNoExist / (spamNoExist + hamNoExist)) / Math.log(2));

            double probabilityExist = (hamExist + spamExist) / (hamExist + hamNoExist + spamExist + spamNoExist);
            double probabilityNoExist = (hamNoExist + spamNoExist) / (hamExist + hamNoExist + spamExist + spamNoExist);

            if (Double.isNaN(entropySpamExist)) entropySpamExist = 0;
            if (Double.isNaN(entropySpamNoExist)) entropySpamNoExist = 0;
            if (Double.isNaN(entropyHamExist)) entropyHamExist = 0;
            if (Double.isNaN(entropyHamNoExist)) entropyHamNoExist = 0;

            double infoGain = entropy - probabilityExist * (- entropySpamExist - entropyHamExist) - probabilityNoExist * (-entropySpamNoExist - entropyHamNoExist);

            informationGain.put(entry.getKey(), infoGain);
        }

        LinkedList<Map.Entry<String, Double>> list = new LinkedList<>(informationGain.entrySet());
        Collections.sort(list, (o1, o2) -> -(o1.getValue()).compareTo(o2.getValue()));

        attributes = new HashMap<>();

        for (int i = 0; i < numberOfAttributes && i < list.size(); ++i)
        {
            attributes.put(list.get(i).getKey(), 1);

            if (spamDictionary.containsKey(list.get(i).getKey()))
            {
                spamWords += spamDictionary.get(list.get(i).getKey());
            }

            if (hamDictionary.containsKey(list.get(i).getKey()))
            {
                hamWords += hamDictionary.get(list.get(i).getKey());
            }
        }

        System.out.println("Entropy: " + entropy);
    }

    public void Evaluate(String[] dataset)
    {
        BufferedReader reader = null;

        for (String filepath : dataset)
        {
            try
            {
                reader = new BufferedReader(new FileReader(filepath));
                String line = null;
                double spamPrediction = 0;
                double hamPrediction = 0;

                while ((line = reader.readLine()) != null)
                {
                    String[] values = line.split("\\s+");

                    if (values.length == 0) continue;

                    for (String word : values)
                    {
                        if (word.isEmpty() || word.equalsIgnoreCase("Subject:")) continue;
                        if (!attributes.containsKey(word)) continue;

                        double probabilityWordSpam;
                        double probabilityWordHam;

                        if (spamDictionary.containsKey(word))
                        {
                            probabilityWordSpam = (spamDictionary.get(word) + smoothingFactor) / (spamWords + smoothingFactor * spamDictionary.size());
                        }
                        else
                        {
                            probabilityWordSpam = smoothingFactor / (spamWords + smoothingFactor * spamDictionary.size());
                        }

                        if (hamDictionary.containsKey(word))
                        {
                            probabilityWordHam = (hamDictionary.get(word) + smoothingFactor) / (hamWords + smoothingFactor * hamDictionary.size());
                        }
                        else
                        {
                            probabilityWordHam = smoothingFactor / (hamWords + smoothingFactor * hamDictionary.size());
                        }

                        spamPrediction += Math.log(probabilityWordSpam);
                        hamPrediction += Math.log(probabilityWordHam);
                    }
                }

                spamPrediction += Math.log(spamProbability);
                hamPrediction += Math.log(hamProbability);

                if (spamPrediction > hamPrediction)
                {
                    if (filepath.contains("spmsg"))
                    {
                        ++correct;
                        ++truePositive;
                    }
                    else
                    {
                        ++falsePositive;
                    }
                }
                else
                {
                    if (filepath.contains("spmsg"))
                    {
                        ++falseNegative;
                    }
                    else
                    {
                        ++correct;
                    }
                }
            }
            catch (IOException e)
            {
                System.err.println("Error processing file: " + filepath);
                System.exit(-1);
            }
            finally
            {
                try
                {
                    reader.close();
                }
                catch (Exception e)
                {
                    System.err.println("Error closing file: " + filepath);
                    System.exit(-1);
                }
            }
        }

        System.out.println("Correct: " + correct);
        System.out.println("True positive: " + truePositive);
        System.out.println("False positive: " + falsePositive);
        System.out.println("False negative: " + falseNegative);
    }
}
