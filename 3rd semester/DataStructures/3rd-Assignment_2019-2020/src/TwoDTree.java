import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a TwoDTree with point elements
 */
public class TwoDTree{

    /**
     * Represents a Tree Node
     */
    @SuppressWarnings("InnerClassMayBeStatic")
    private class TreeNode {
        // The data it holds
        private Point item;

        // Left subtree (child)
        private TreeNode l;

        // Right subtree (child)
        private TreeNode r;

        /**
         * TreeNode constructor accepting data
         * @param data the data inserted for this node
         */
        public TreeNode(Point data){
            this.item = data;
        }

        /**
         * @return The data it holds
         */
        public Point getData() {
            return this.item;
        }

        /**
         * @return left subtree
         */
        public TreeNode getLeft() {
            return l;
        }

        /**
         * @param left Set left subtree
         */
        public void setLeft(TreeNode left) {
            this.l = left;
        }

        /**
         * @return right subtree
         */
        public TreeNode getRight() {
            return r;
        }

        /**
         * @param right Set right subtree
         */
        public void setRight(TreeNode right) {
            this.r = right;
        }
    }

    // the root of the tree
    private TreeNode head;

    /**
     * Creates an empty tree
     */
    public TwoDTree(){
    }

    /**
     * Creates a tree with a root (only to be used in methods)
     * @param head the root node of the tree
     */
    private TwoDTree(TreeNode head) {
        this.head = head;
    }

    /**
     * @return the root of the tree
     */
    public TreeNode getHead() {
        return head;
    }

    /**
     * Checks if the tree is empty
     * @return true if the tree is empty, otherwise false
     */
    public boolean isEmpty(){
        return this.getHead()==null;
    }

    /**
     * Returns the number of points in the tree
     * @return the size of the tree
     */
    public int size(){
        if(isEmpty()){
            return 0;
        }
        return getNumberOfNodes(head);
    }

    /**
     * Returns the height of the tree
     * @return The height of the tree
     */
    public int getTreeHeight() {
        if (head == null){
            return 0;
        }
        return getNodeDepth(head);
    }

    /**
     * Recursive function to get nodes depth
     * @param node The node to get depth
     * @return The depth of the node
     */
    private int getNodeDepth(TreeNode node) {
        if (node == null)
            return 0;

        // get left and right depth
        int leftDepth = getNodeDepth(node.getLeft());
        int rightDepth = getNodeDepth(node.getRight());

        // return max depth + 1 for current node
        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * Recursive function to get the number of nodes
     * @param node The node to get depth
     * @return The number of nodes existing
     */
    private int getNumberOfNodes(TreeNode node) {
        if (node == null)
            return 0;

        // get left and right depth
        int leftDepth = getNumberOfNodes(node.getLeft());
        int rightDepth = getNumberOfNodes(node.getRight());

        // return max depth + 1 for current node
        return leftDepth + rightDepth + 1;
    }

    /**
     * Inserts a point in the tree
     * @param p a point to be insert
     */
    public void insert(Point p){
        //Inserts point
        this.head = insertP(this.head, p,true);
    }

    /**
     * Helper function for recursive insert
     * @param n a node to be filled/checked
     * @param p a point to be added
     * @return a node
     */
    @NotNull
    @Contract("null, _, _ -> new")
    private TreeNode insertP(TreeNode n, Point p, Boolean first) {
        //Branch is null
        if (n==null) {
            return new TreeNode(p);
        }
        //Checks if point is already in
        else if(n.getData().equals(p)){
            System.out.println("\nPoint already in the tree. Please try with a different point");
            return n;
        }

        //Checking x
        if (first) {
            //Left case
            if (n.getData().getX() > p.getX()) {
                n.setLeft(insertP(n.getLeft(), p,false));
            }
            //Right case
            else {
                n.setRight(insertP(n.getRight(), p,false));
            }
        //Checking y
        } else {
            //Left case
            if (n.getData().getY() > p.getY()) {
                n.setLeft(insertP(n.getLeft(), p,true));
            }
            //Right case
            else {
                n.setRight(insertP(n.getRight(), p,true));
            }
        }
        return n;
    }

    /**
     * Searches for a point in a tree
     * @param p a point to search for
     * @return true if point is found in the tree, false otherwise
     */
    public boolean search(Point p){
        return searchH(this.getHead(),p,true);
    }

    /**
     * Helper function that searches for a point in a point tree
     * @param p The point that we are searching for
     * @param first Boolean to check if we are in x or y axis
     * @return True if the point is found in the tree, false otherwise
     */
    private boolean searchH(TreeNode head,Point p, boolean first) {
        //Empty case
        if(this.isEmpty()){
            return false;
        }
        TwoDTree xyCase;
        //Node case
        if(p.equals(head.getData())) {
            return true;
        }else {
            if (first) {
                if (head.getData().getX() > p.getX()) {
                    xyCase = getLeftSubTree(head);
                }
                else {
                    xyCase = getRightSubTree(head);
                }
                return xyCase.searchH(xyCase.getHead(),p,false);
            }
            else {
                if (head.getData().getY() > p.getY()) {
                    xyCase = getLeftSubTree(head);
                }
                else {
                    xyCase = getRightSubTree(head);
                }
                return xyCase.searchH(xyCase.getHead(),p,true);
            }
        }
    }


    /**
     * Searches for the nearest neighbor of a point
     * @param p a point of which we want to find the nearest neighbor
     * @return the nearestNeighbor point
     */
    public Point nearestNeighbor(Point p){
        //Empty tree
        if(this.isEmpty()){
            return null;
        }
        return this.nearestNeighborH(this.head.getData(),p,new Rectangle(0,100,0,100),true);
    }

    /**
     *
     * @param closestPoint the closest point that we have found so far
     * @param searchPoint the point of which we are searching the nearest neighbor
     * @param rectangle the rectangle representation of the node
     * @param first boolean to check if we are on x or y axis
     * @return the nearestNeighbor found so far
     */
    private Point nearestNeighborH(Point closestPoint, Point searchPoint, Rectangle rectangle, boolean first){
        //Case of empty tree branch
        if(this.isEmpty()){
            return closestPoint;
        }

        //Checks if the head is a new closest point
        if(this.head.getData().distanceTo(searchPoint)<closestPoint.distanceTo(searchPoint)){
            closestPoint = this.getHead().getData();
        }

        //Checks if the distance of the closest point with the searchpoint is greater that
        // the distance of the rectangle representation of a node with the searchpoint
        if(closestPoint.distanceTo(searchPoint)>=rectangle.distanceTo(searchPoint)){
            //x case
            if(first){
                closestPoint = getLeftSubTree(head).nearestNeighborH(closestPoint,searchPoint,new Rectangle(rectangle.getMinx(),head.getData().getX(),rectangle.getMiny(),rectangle.getMaxy()),false);
                closestPoint = getRightSubTree(head).nearestNeighborH(closestPoint,searchPoint,new Rectangle(head.getData().getX(),rectangle.getMaxx(),rectangle.getMiny(),rectangle.getMaxy()),false);
            }
            //y case
            else{
                closestPoint = getLeftSubTree(head).nearestNeighborH(closestPoint,searchPoint,new Rectangle(rectangle.getMinx(),rectangle.getMaxx(),rectangle.getMiny(),head.getData().getY()),true);
                closestPoint = getRightSubTree(head).nearestNeighborH(closestPoint,searchPoint,new Rectangle(rectangle.getMinx(), rectangle.getMaxx(), head.getData().getY(), rectangle.getMaxy()),true);
            }
        }
        return closestPoint;
    }

    /**
     * Findings the points contained in a given rectangle
     * @param rectangle the rectangle in which we are searching
     * @return a PointQueue with all the points in the given rectangle
     */
    public PointQueue rangeSearch(Rectangle rectangle){
        PointQueue pointQueue = new PointQueue();
        //Empty tree
        if(this.isEmpty()){
            return pointQueue;
        }
        return this.rangeSearchH(pointQueue,rectangle,new Rectangle(0,100,0,100),true);
    }

    /**
     * Helper function to find the points contained in a given rectangle
     * @param pointQueue a queue in which we save the nodes that are in the given rectangle
     * @param rectangle the rectangle in which we are searching
     * @param pointRectangle the rectangle representation of a node
     * @param first boolean to check if we are in x or y axis
     * @return the PointQueue containing the points found in the rectangle
     */
    private PointQueue rangeSearchH(PointQueue pointQueue, Rectangle rectangle,Rectangle pointRectangle, boolean first){
        //Empty branch case
        if(this.isEmpty()){
            return pointQueue;
        }

        //Checking if this node is contained in the rectangle
        if(rectangle.contains(this.getHead().getData())){
            pointQueue.put(this.getHead().getData());
        }

        //If the rectangle representation of a node does not intersects the rectangle in which we
        //are looking for then we don't search the subtrees
        if(!(rectangle.intersects(pointRectangle))){
            return pointQueue;
        }
        else{
            //x case
            if(first){
                getLeftSubTree(head).rangeSearchH(pointQueue,rectangle,new Rectangle(pointRectangle.getMinx(),head.getData().getX(),pointRectangle.getMiny(),pointRectangle.getMaxy()),false);
                getRightSubTree(head).rangeSearchH(pointQueue,rectangle,new Rectangle(head.getData().getX(),pointRectangle.getMaxx(),pointRectangle.getMiny(),pointRectangle.getMaxy()),false);
            }
            //y case
            else{
                getLeftSubTree(head).rangeSearchH(pointQueue,rectangle,new Rectangle(pointRectangle.getMinx(),pointRectangle.getMaxx(),pointRectangle.getMiny(),head.getData().getY()),true);
                getRightSubTree(head).rangeSearchH(pointQueue,rectangle,new Rectangle(pointRectangle.getMinx(), pointRectangle.getMaxx(), head.getData().getY(), pointRectangle.getMaxy()),true);
            }
        }
        return pointQueue;
    }

    /**
     * @param node The node to get left subtree
     * @return left child as binary tree
     */
    public TwoDTree getLeftSubTree(TreeNode node) {
        //Empty
        if (head == null)
            return null;

        return new TwoDTree(node.getLeft());
    }

    /**
     * @param node The node to get right subtree
     * @return right child as binary tree
     */
    public TwoDTree getRightSubTree(TreeNode node) {
        //Empty tree
        if (head == null)
            return null;

        return new TwoDTree(node.getRight());
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws Exception {
        int choice;
        Point p,a;
        Rectangle rectangle;
        PointQueue pntq;
        String inp = args[0];
        TwoDTree twoDTree = readInput(new File(inp));
        while(true){
            choice=printMenu();
            switch (choice){
                case 1:
                    System.out.println("\nThe number of nodes in the tree is: " + twoDTree.size());
                    System.out.println("The height (depth) of the tree is: " + twoDTree.getTreeHeight());
                    break;
                case 2:
                    p = strToPoint(pointInput("2"));
                    if(!twoDTree.search(p)){
                        System.out.println("\nPoint " + p + " inserted successfully");
                    }
                    twoDTree.insert(p);
                    break;
                case 3:
                    if(twoDTree.search(strToPoint(pointInput("3")))){
                        System.out.println("\nPoint exists in the tree");
                    }else{
                        System.out.println("\nPoint not found in the tree");
                    }
                    break;
                case 4:
                    rectangle = strToRectangle(pointInput("4.1"),pointInput("4.2"));
                    pntq = twoDTree.rangeSearch(rectangle);
                    pntq.printQueue(System.out);
                    break;
                case 5:
                    p = strToPoint(pointInput("5"));
                    System.out.print("\nThe nearest point of " + p.toString() + " is the point ");
                    a = twoDTree.nearestNeighbor(p);
                    System.out.println(a.toString()+".");
                    System.out.println("The distance between the two points is " + p.distanceTo(a) + ".");
                    break;
                case 6:
                    print(twoDTree.getHead());
                    break;
                default:
                    System.out.println("\nChoice number not recognized, please try again.");
            }
        }
    }

    /**
     * Static method to read from a file some points and return a twoDTree
     * @param file a file from which we want to search
     * @return a twoDTree containing the points presented in the file
     * @throws Exception in case that something goes wrong during reading the file
     */
    @NotNull
    private static TwoDTree readInput(File file) throws Exception{
        try{
            TwoDTree twoDTree = new TwoDTree(); //To be returned a TwoDTree
            int n; //To hold the number of lines given
            String temp; //To hold each line
            BufferedReader read = new BufferedReader(new FileReader(file));
            n = Integer.parseInt(read.readLine());

            //Checking if # of lines is correct
            while(read.readLine()!=null){
                n--;
            }
            if(n!=0){
                throw new Exception("Number of lines given on the header does not match the number of lines (points) given in the file");
            }

            //Resetting buffer
            read = new BufferedReader(new FileReader(file));

            //Reading file and creating points, inserting them in TwoDTree
            read.readLine();
            while((temp = read.readLine())!=null){
                Point p = strToPoint(temp);
                if(p.getY()>100 || p.getY()<0 || p.getX()<0 || p.getX()>100){
                    throw new Exception("x or y values must be between 0 and 100. Please check the input and try again");
                }
                twoDTree.insert(p);
            }
            return twoDTree;
        }
        catch (NumberFormatException e){
            throw new NumberFormatException("Please check the header and try again");
        }
        catch(FileNotFoundException e){
            throw  new FileNotFoundException("File not found, consider using another type of path");
        }
        catch (IOException e){
            throw new IOException(e + " occurred");
        }
        catch (Exception e){
            throw new Exception(e + " occurred");
        }
    }

    /**
     * Static method to print menu and read choice
     * @return the int number of user choice
     */
    private static int printMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nMenu Options:\n1. Compute the size of the tree\n2. Insert a new point");
        System.out.println("3. Search if a given point exists in the tree\n4. Provide a query rectangle");
        System.out.println("5. Provide a query point");
        System.out.print("6. Print a tree diagram\nPlease enter the number of your choice: ");
        return scanner.nextInt();
    }

    /**
     * Static method that takes integer from user and creates the represtantion in string
     * @param str a string input representing the user choice
     * @return a string representation of a point
     */
    private static String pointInput(@NotNull String str){
        Scanner scanner = new Scanner(System.in);
        switch (str) {
            case "2":
                System.out.print("\nPlease give give the coordinates of the point that you want bo be inserted in the tree");
                System.out.print(" in 'x y' format (e.g. 100 30): ");
                break;
            case "3":
                System.out.print("\nPlease give give the coordinates of the point that you want to search in the tree for");
                System.out.print(" in 'x y' format (e.g. 100 30): ");
                break;
            case "4.1":
                System.out.print("\nPlease give the min and max x of the rectangle that you want to search in");
                System.out.print(" in 'xmin xmax' format (e.g. 100 30): ");
                break;
            case "4.2":
                System.out.print("Please give the min and max y of the rectangle that you want to search in");
                System.out.print(" in 'ymin ymax' format (e.g. 100 30): ");
                break;
            case "5":
                System.out.print("\nPlease give give the coordinates of the point that of which you want the");
                System.out.print(" nearest neighbor, in 'x y' format (e.g. 100 30): ");
                break;
        }
        return scanner.nextLine();
    }

    /**
     * Static method creating a point from a string
     * @param str a string with a point input
     * @return a point extracted from a string
     */
    @NotNull
    @Contract("_ -> new")
    private static Point strToPoint (@NotNull String str){
        String[] strTbl = str.split(" ");
        return new Point(Integer.parseInt(strTbl[0]),Integer.parseInt(strTbl[1]));
    }

    /**
     * Static method that creates a rectangle from two strings containing xmin and xmax and ymin and ymax
     * @param xminmax the string representation of xmin and xmax
     * @param yminmax the string representation of ymin and ymax
     * @return a rectangle
     */
    @NotNull
    @Contract("_, _ -> new")
    private static Rectangle strToRectangle(@NotNull String xminmax, @NotNull String yminmax){
        String[] strTblx = xminmax.split(" ");
        String[] strTbly = yminmax.split(" ");
        return new Rectangle(Integer.parseInt(strTblx[0]),Integer.parseInt(strTblx[1]),Integer.parseInt(strTbly[0]),Integer.parseInt(strTbly[1]));
    }

    // ------------------------------------- EXTRA FUNCTIONALITY --------------------------------------------
    /***************************************************************************************
     *    Title: Binary Tree Printing
     *    Author: MightyPork
     *    Date: April 17, 2015
     *    Code version: unknown
     *    Availability: https://stackoverflow.com/a/29704252
     *    Print a twoDTree in a tree representation
     *    @param root a tree node root
     ***************************************************************************************/
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void print(TreeNode root)
    {
        List<List<String>> lines = new ArrayList();

        List<TreeNode> level = new ArrayList();
        List<TreeNode> next = new ArrayList();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList();

            nn = 0;

            for (TreeNode n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.getData().toString();
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeft());
                    next.add(n.getRight());

                    if (n.getLeft() != null) nn++;
                    if (n.getRight() != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<TreeNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (line.get(j) != null) {
                                c = '└';
                            }
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (String f : line) {

                if (f == null) f = "";
                float a = perpiece / 2f - f.length() / 2f;
                int gap1 = (int) Math.ceil(a);
                int gap2 = (int) Math.floor(a);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }
}
