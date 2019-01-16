import java.io.FileReader;
import java.io.PrintStream;


public class ST implements STInterface {

    protected TreeNode head;
    protected List stopWords;
    private int totalWords;
    private WordFreq maxFreqWord;

    private boolean isAddedAsLeaf;
    private int arrayOffSet;
    private boolean flagTotalWordsGuard;


    public ST() {
        maxFreqWord = new WordFreq("fakeWord", 0);
        stopWords = new List();
    }


    /* Tree related methods */
    @Override
    public WordFreq search(String w) {
        WordFreq searchedItem = naiveSearch(w);
        if(searchedItem == null) return null;
        if( searchedItem.timesFound > getMeanFrequency()){
            TreeNode newHead = searchR(head, w);
            if (newHead != null) {
                head = newHead;
                return head.item;
            }
        }
        return searchedItem;

    }

    @Override
    public void insert(WordFreq item) {
        if (!stopWords.isInList(item.key())) {
            totalWords++;
            head = insertR(head, item);
            if (maxFreqWord.getTimesFound() < item.getTimesFound()) maxFreqWord = item;
        }
    }

    @Override
    public void update(String w) {
        if (!stopWords.isInList(w)) {
            totalWords++;
            isAddedAsLeaf = false;
            head = updateR(head, new WordFreq(w));
        }
    }

    @Override
    public void remove(String w) {
        if (treeHasWordFreq(w)) {
            flagTotalWordsGuard = true;
            deleteR(head, w);
            if (maxFreqWord.key().equalsIgnoreCase(w)) updateMaxFreqWord();
        }
    }

    @Override
    public int getTotalWords() {
        return totalWords;
    }

    @Override
    public int getDistinctWords() {
        if(totalWords == 0) return 0;
        return head.number + 1;
    }

    @Override
    public int getFrequency(String w) {
        WordFreq wordObj = search(w);
        if (wordObj == null) return 0;
        return wordObj.timesFound;
    }

    @Override
    public WordFreq getMaximumFrequency() {
        if(totalWords == 0) return null;
        return maxFreqWord;
    }

    @Override
    public double getMeanFrequency() {
        return (float) getTotalWords() / getDistinctWords();
    }

    /* StopWords methods */
    @Override
    public void addStopWord(String w) {
        stopWords.add(w);
    }

    @Override
    public void removeStopWord(String w) {
        stopWords.deleteString(w);
    }

    /* Print methods */
    @Override
    public void printΤreeAlphabetically(PrintStream stream) { //e.x "a" "b" "c" "w" "z"
        printInorderR(head, stream);
    }


    @Override
    public void printΤreeByFrequency(PrintStream stream) {
        if(totalWords == 0) return;
        WordFreq[] tempArray = new WordFreq[getDistinctWords()];
        arrayOffSet = 0;
        loadWordFreqToArray(head, tempArray);
        SortUtil.mergeSort(tempArray, 0, getDistinctWords() - 1);
        for (int i = 0; i < getDistinctWords(); i++) {
            stream.println(tempArray[i]);
        }

    }

    private void loadWordFreqToArray(TreeNode head, WordFreq[] array) {
        if (head == null) return;
        loadWordFreqToArray(head.l, array);
        array[arrayOffSet] = head.item;
        arrayOffSet++;
        loadWordFreqToArray(head.r, array);
    }

    /*Parsing methods */
    @Override
    public void load(String filename) throws Exception {
        //Parsing using state machine logic FSA.
        boolean idleState = true;
        boolean badIdleState = false;
        boolean stringBuilderState = false;
        int i;
        char currentChar;
        StringBuilder stringBuilder = new StringBuilder();

        FileReader fr = new FileReader(filename);

        while ((i = fr.read()) != -1) { //For every character in the file.
            currentChar = (char) i;

            if (idleState) {
                if (currentChar == ' ' || isPunctuationChar(currentChar)) {
                    continue;
                }
                if (isNumber(currentChar)) {
                    idleState = false;
                    badIdleState = true;
                    continue;
                }
                if (isEnglishChar(currentChar)) {
                    idleState = false;
                    stringBuilderState = true;
                }
            }

            if (badIdleState) {
                if (isEnglishChar(currentChar) || isNumber(currentChar)) {
                    continue;
                }
                if (currentChar == ' ' || isPunctuationChar(currentChar)) {
                    badIdleState = false;
                    idleState = true;
                    continue;
                }
            }

            if (stringBuilderState) {
                if (isEnglishChar(currentChar)) {
                    stringBuilder.append(currentChar);
                    continue;
                }
                if (isNumber(currentChar)) {
                    stringBuilder.setLength(0); //Clears the sb
                    stringBuilderState = false;
                    badIdleState = true;
                    continue;
                }
                if (currentChar == ' ' || isPunctuationChar(currentChar)) {
                    //Do the gist here.
                    update(stringBuilder.toString());
                    stringBuilder.setLength(0);
                    stringBuilderState = false;
                    idleState = true;
                }
            }
        }
    }

    private boolean isPunctuationChar(char currentChar) {
        String punctuationString = "!@#$%^&*(),./?+-*^<>=" + '"' + "\n";
        return punctuationString.indexOf(currentChar) != -1;
    }

    private boolean isNumber(char currentChar) {
        String numberString = "0123456789";
        return numberString.indexOf(currentChar) != -1;
    }

    private boolean isEnglishChar(char currentChar) {
        if ((currentChar >= 'a' && currentChar <= 'z') || (currentChar >= 'A' && currentChar <= 'Z') || currentChar == '\'') {
            return true;
        }
        return false;
    }

    /*Extra helping methods (R stands for recursive) */

    private TreeNode updateR(TreeNode head, WordFreq item) {
        if (head == null) { //Exit condition from recursive
            isAddedAsLeaf = true;
            return new TreeNode(item);
        }
        if (head.item.key().equalsIgnoreCase(item.key())) {
            head.item.timesFound++;
            if (head.item.timesFound > maxFreqWord.timesFound) maxFreqWord = head.item;
        } else if (head.item.key().compareToIgnoreCase(item.key()) < 0) {

            head.r = updateR(head.r, item);
            if (isAddedAsLeaf) head.number++;
        } else {

            head.l = updateR(head.l, item);
            if (isAddedAsLeaf) head.number++;
        }
        return head;
    }

    private TreeNode insertR(TreeNode head, WordFreq item) {
        if (head == null) { //Exit condition from recursive
            return new TreeNode(item);
        }

        if (head.item.key().compareToIgnoreCase(item.key()) < 0) {
            head.r = insertR(head.r, item);
            head.number++;
        } else {

            head.l = insertR(head.l, item);
            head.number++;
        }

        return head;
    }

    private void printInorderR(TreeNode head, PrintStream stream) {
        if (head == null) return;

        printInorderR(head.l, stream);

        stream.println('"' + head.item.key() + '"');

        printInorderR(head.r, stream);
    }

    private TreeNode searchR(TreeNode head, String item) {
        TreeNode temp;
        if (head == null) return null;
        String currentString = head.item.key();
        if (currentString.equalsIgnoreCase(item)) return head;

        if (currentString.compareToIgnoreCase(item) > 0) {
            temp = searchR(head.l, item);

            if (temp != null && getMeanFrequency() <= temp.item.getTimesFound()) {
                head.l = temp;
                rotateRight(head);
            }
        } else {
            temp = searchR(head.r, item);
            if (temp != null && getMeanFrequency() <= temp.item.getTimesFound()) {
                head.r = temp;
                rotateLeft(head);
            }

        }
        return temp;

    }

    private TreeNode deleteR(TreeNode head, String item) {
        //Complexity of this is o(N)
        //1. If node is a leaf --> just remove it
        //2. If node has only one child --> link parent to its child.
        //3. If node has 2 children --> find the maximum from left sub-tree save and delete it,
        // then replace it with the node you want to delete.
        //Keep in mind that you have to update some fields while you delete nodes.
        if (head == null) return null;
        else if (head.item.key().compareToIgnoreCase(item) > 0) {
            head.l = deleteR(head.l, item);
            head.number--;
        } else if (head.item.key().compareToIgnoreCase(item) < 0) {
            head.r = deleteR(head.r, item);
            head.number--;
        } else {
            if (flagTotalWordsGuard) totalWords -= head.item.getTimesFound();
            if (head.r == null && head.l == null) { // Leaf removal
                return null;
            } else if (head.r == null) { // One-child removal
                return head.l;

            } else if (head.l == null) {
                return head.r;
            } else { // two-child removal
                TreeNode minimumElementInRightSubTree = head.r;
                while (minimumElementInRightSubTree.l != null) {
                    minimumElementInRightSubTree = minimumElementInRightSubTree.l;
                }

                head.item = minimumElementInRightSubTree.item;
                head.number = head.number - 1;
                flagTotalWordsGuard = false;
                head.r = deleteR(head.r, minimumElementInRightSubTree.item.key());
                return head;
            }
        }
        return head;
    }

    private void rotateRight(TreeNode head) {
        head.l.number = head.number;
        boolean check1 = head.l.r != null;  // Doing this is necessary for avoiding NullPointer Exception!
        boolean check2 = head.r != null;
        if (check1 && check2) {
            head.number = head.r.number + 1 + head.l.r.number + 1;
        } else {
            if (check1) head.number = head.l.r.number + 1;
            if (check2) head.number = head.r.number + 1;
        }

        TreeNode newHead = head.l;
        head.l = newHead.r;
        newHead.r = head;


    }

    private void rotateLeft(TreeNode head) {

        head.r.number = head.number;
        boolean check1 = head.r.l != null;  // Doing this is necessary for avoiding NullPointer Exception!
        boolean check2 = head.l != null;
        if (check1 && check2) {
            head.number = head.l.number + 1 + head.r.l.number + 1;
        } else {
            if (check1) head.number = head.r.l.number + 1;
            if (check2) head.number = head.l.number + 1;
        }

        TreeNode newHead = head.r;
        head.r = newHead.l;
        newHead.l = head;
    }

    private boolean treeHasWordFreq(String item) {
        TreeNode currentNode = head;
        while (currentNode != null) {
            if (currentNode.item.key().compareToIgnoreCase(item) > 0) {
                currentNode = currentNode.l;
            } else if (currentNode.item.key().compareToIgnoreCase(item) < 0) {
                currentNode = currentNode.r;
            } else {
                return true;
            }
        }
        return false;
    }

    private void updateMaxFreqWord() {
        maxFreqWord = head.item;
        updateMaxFreqWordR(head);

    }

    private void updateMaxFreqWordR(TreeNode head) {
        if (head == null) return;
        updateMaxFreqWordR(head.l);
        if (maxFreqWord.getTimesFound() < head.item.getTimesFound()) maxFreqWord = head.item;
        updateMaxFreqWordR(head.r);
    }

    private WordFreq naiveSearch(String w){
        TreeNode current = head;
        while(current!=null){
            if(current.item.key().equalsIgnoreCase(w)){
                return current.item;
            }else if(current.item.key().compareToIgnoreCase(w) < 0 ){
                current = current.r;
            }else{
                current = current.l;
            }
        }
        return null;
    }

    private class TreeNode {
        WordFreq item;
        TreeNode l;
        TreeNode r;
        int number;

        public TreeNode() {
        }

        public TreeNode(WordFreq item) {
            this.item = item;
        }

        public TreeNode(WordFreq item, TreeNode l, TreeNode r, int number) {
            this.item = item;
            this.l = l;
            this.r = r;
            this.number = l.number + r.number + 2;
        }
    }
}
