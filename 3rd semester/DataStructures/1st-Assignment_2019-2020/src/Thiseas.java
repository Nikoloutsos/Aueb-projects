import java.io.*;
import java.util.Arrays;


/**
 * Checks for maze solution
 */
public class Thiseas {
    public static void main(String[] args) throws Exception {
        boolean checker = true; //Terminates while loop in certain conditions
        File file = new File(args[0]);
        try {
            String temp; //Serves during reading the maze and storing it in 2d array
            String[] str; //Holds the values of a row in a maze
            int counter = 0; //Keeps count on which line we are
            StringStackImpl stringStack = new StringStackImpl();
            BufferedReader read = new BufferedReader(new FileReader(file));
            int[] dim = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); //Dimensios of maze
            int[] start = Arrays.stream(read.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); //Start of maze
            String[][] maze = new String[dim[0]][dim[1]]; //2d array to hold the maze of size dim
            while((temp = read.readLine())!=null) { //While loop for saving maze to 2d array by reading each line and spliting the elemts of each line
                str = temp.split(" ");
                maze[counter] = str;
                counter++;
            }
            //Checks if there is no E in the given start location
            if(maze[start[0]][start[1]].equals("E")) {
                throw new Exception("Entrance not in desired location");
            }
            String holder; //Holds the coordinates that we are on
            stringStack.push(start[0]+ " " + start[1]); //First element of the stack is the start
            while(checker){
                holder = stringStack.peek(); //Holds the coordinates againt which we check the right, left, top and bottom
                //Check left
                //If the left cell is not out of bounds and it is a "0" then we keep this location in the stack
                if(OutOfBounds(Integer.valueOf(holder.split(" ")[0]), Integer.valueOf(holder.split(" ")[1])-1, dim )){
                    if(maze[Integer.valueOf(holder.split(" ")[0])][Integer.valueOf(holder.split(" ")[1])-1].equals("0")) {
                        stringStack.push(Integer.valueOf(holder.split(" ")[0]) + " " + (Integer.valueOf(holder.split(" ")[1]) - 1));
                        //If it is on the wall, we found solution
                        if(IsWall(Integer.valueOf(stringStack.peek().split(" ")[0]), Integer.valueOf(stringStack.peek().split(" ")[1]), dim)){
                            break;
                        }
                    }
                }
                //Check right
                //If the right cell is not out of bounds and it is a "0" then we keep this location in the stack
                if(OutOfBounds(Integer.valueOf(holder.split(" ")[0]), Integer.valueOf(holder.split(" ")[1])+1, dim )){
                    if(maze[Integer.valueOf(holder.split(" ")[0])][Integer.valueOf(holder.split(" ")[1])+1].equals("0")) {
                        stringStack.push(Integer.valueOf(holder.split(" ")[0]) + " " + (Integer.valueOf(holder.split(" ")[1])+1));
                        //If it is on the wall, we found solution
                        if(IsWall(Integer.valueOf(stringStack.peek().split(" ")[0]), Integer.valueOf(stringStack.peek().split(" ")[1]), dim)){
                            break;
                        }
                    }
                }
                //Check bottom
                //If the bottom cell is not out of bounds and it is a "0" then we keep this location in the stack
                if(OutOfBounds(Integer.valueOf(holder.split(" ")[0])+1, Integer.valueOf(holder.split(" ")[1]), dim )) {
                    if (maze[Integer.valueOf(holder.split(" ")[0]) + 1][Integer.valueOf(holder.split(" ")[1])].equals("0")) {
                        stringStack.push(Integer.valueOf(holder.split(" ")[0]) + 1 + " " + (Integer.valueOf(holder.split(" ")[1])));
                        //If it is on the wall, we found solution
                        if(IsWall(Integer.valueOf(stringStack.peek().split(" ")[0]), Integer.valueOf(stringStack.peek().split(" ")[1]), dim)){
                            break;
                        }
                    }
                }
                //Check top
                //If the top cell is not out of bounds and it is a "0" then we keep this location in the stack
                if(OutOfBounds(Integer.valueOf(holder.split(" ")[0])-1, Integer.valueOf(holder.split(" ")[1]), dim )){
                    if(maze[Integer.valueOf(holder.split(" ")[0])-1][Integer.valueOf(holder.split(" ")[1])].equals("0")) {
                        stringStack.push(Integer.valueOf(holder.split(" ")[0]) - 1 + " " + (Integer.valueOf(holder.split(" ")[1])));
                        //If it is on the wall, we found solution
                        if(IsWall(Integer.valueOf(stringStack.peek().split(" ")[0]), Integer.valueOf(stringStack.peek().split(" ")[1]), dim)){
                            break;
                        }
                    }
                }
                //If we dont have any moves left we break the while loop
                if(stringStack.peek().equals(holder)){
                    checker = false;
                }
                maze[Integer.valueOf(holder.split(" ")[0])][Integer.valueOf(holder.split(" ")[1])]="X"; //We visited this location and we are done with possible solutions
                //We check if the first elemen of the stack is on the wall, if yes we break
                if(IsWall(Integer.valueOf(stringStack.peek().split(" ")[0]), Integer.valueOf(stringStack.peek().split(" ")[1]), dim)){
                    break;
                }
            }
            //Print solution
            System.out.println("The way out is in coordinates: (" + stringStack.peek().split(" ")[0]+ "," + stringStack.peek().split(" ")[1] +").");


        } catch (FileNotFoundException filenotfoundexception) {
            throw new FileNotFoundException("File not found, consider using another type of path");
        } catch (IOException ioexception) {
            throw new IOException(ioexception + " occurred");
        } catch (Exception exception) {
            throw new Exception(exception + " occurred");
        }

    }

    /**
     * *
     * @param x is the coordinate x of the input
     * @param y is the coordinate y of the input
     * @param dimensions are the dimensions of the maze ix n m format
     * @return false if out of bounds, true if inbound
     */
    private static boolean OutOfBounds(int x, int y, int[] dimensions){
        return (x >= 0) && (x < dimensions[0]) && (y >= 0) && (y < dimensions[1]);
    }

    /**
     *
     * @param x is the coordinate x of the input
     * @param y is the coordinate y of the input
     * @param dimensions are the dimensions of the maze ix x y format
     * @return false if x and y are not on the sides of the maze, true if one of x y is one the side of the maze
     */
    private static boolean IsWall(int x, int y, int[] dimensions){
        return(x==0 || x==dimensions[0]-1 || y==0 || y==dimensions[1]-1);
    }
}
