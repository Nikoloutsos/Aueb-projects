import java.io.FileNotFoundException;

/*
This is not part of the assignment but it is necessary for testing our ST Data Structure.
 */
public class Test {

    public static void main(String[] args) {
        STInterface myObject = new ST();
        try {
            myObject.load(args[0]);
        } catch (Exception e) {
            if (e instanceof FileNotFoundException) {
                System.err.println("Problem with finding the file ");
            } else {
                System.err.println("A problem has occurred at loading the file");
            }
            System.exit(1);
        }

        System.out.println("||This is a simple test for illustrating ST Data Structure||\n\n");

        System.out.println("Calling printTreeByFrequency() method: ");
        myObject.printΤreeByFrequency(System.out);

        System.out.println("--------------------------");

        System.out.println("Calling printTreeAlphabeticaly() method: ");
        myObject.printΤreeAlphabetically(System.out);

        System.out.println();

        System.out.println("Total words are: " + myObject.getTotalWords());
        System.out.println();
        System.out.println("Distinct words are: " + myObject.getDistinctWords());
        System.out.println();

        System.out.print("Mean frequency is: ");
        System.out.printf("%.5f", myObject.getMeanFrequency());


        System.out.println();
        System.out.print("The object with max frequency is : ");
        System.out.print(myObject.getMaximumFrequency());


    }
}
