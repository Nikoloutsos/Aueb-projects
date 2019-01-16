public class WordFreq implements Key<String>, Comparable<WordFreq> {
    private String string;
    int timesFound;

    public WordFreq(String string, int timesFound) {
        this.string = string;
        this.timesFound = timesFound;
    }

    public WordFreq(String string) {
        this.string = string;
        this.timesFound = 1;
    }

    @Override
    public String key() {
        return string;
    }


    @Override
    public String toString() {
        return '"' + key() + '"' + " found " + timesFound + " times";
    }

    //Getters and Setters
    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getTimesFound() {
        return timesFound;
    }

    public void setTimesFound(int timesFound) {
        this.timesFound = timesFound;
    }


    @Override
    public int compareTo(WordFreq o1) {

        int temp = getTimesFound() - o1.getTimesFound();
        if (temp > 0) return 1;
        else if (temp < 0) return -1;
        else return 0;
    }
}
