import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Maze {

    private static String maze [] [];
    private static ArrayList<Integer> dirList;
    private static final String closed = "[#]";
    private static final String open = "[ ]";
    //bounds of the maze
    private static int boundX;
    private static int boundY;
    // current player position
    private static int currPlayerPosX;
    private static int currPlayerPosY;
    // treasure position
    private static int treasurePosX;
    private static int treasurePosY;
    private static boolean found = false;
    private static Random rand;
    // initial player position
    private static int initPlayerPosX;
    private static int initPlayerPosY;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static Scanner sc = new Scanner(System.in);

    //Austin
    public static void main (String[] args) {
        start();
    }

    //Josia
    // initializes the board and starts the maze game
    private static void start() {
        int mazeSize, pathLen;
        do {
            System.out.print("Enter a size for the maze: ");
            mazeSize = sc.nextInt();
            if (mazeSize >= 2) {
                maze = new String [mazeSize] [mazeSize];
                boundX = mazeSize;
                boundY = mazeSize;
                break;
            } else {
                System.out.println(ANSI_RED + mazeSize + " is an invalid size for the maze. " +
                        "Please enter a value greater than or equal to 2." + ANSI_RESET);
            }

        } while (true);
        // initializes the maze
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = closed;
            }
        }

        do {
            System.out.print("Enter a length for the maze path: ");
            pathLen = sc.nextInt();
            if (pathLen >= 2 && pathLen < (pathLen * pathLen)) {
                break;
            } else {
                System.out.println(ANSI_RED + pathLen + " is an invalid length for the path. " +
                        "Please enter a value greater than or equal to 2 and less than the maze size." + ANSI_RESET);
            }

        } while (true);

        generatePlayerPos();
        generatePath(pathLen);
        printMaze();
        move();

    }

    // prints out the current board
    private static void printMaze() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    // Generates a path for the player who cannot go out of the bounds of the path or backwards
    private static void generatePath(int pathLen) {
        int counter = 0;
        dirList = new ArrayList<>(); // 1 - North | 2 - South | 3 - West | 4 - East
        do {
            int randDir = rand.nextInt(4);

            switch(randDir) {
                case 0 :
                    if  (currPlayerPosX-1 < 0 || maze[currPlayerPosX-1][currPlayerPosY].equals(open)) {

                    } else {
                        maze[currPlayerPosX][currPlayerPosY] = open;
                        maze[currPlayerPosX-1][currPlayerPosY] = open;
                        currPlayerPosX -= 1;
                        counter++;
                        dirList.add(1);
                        break;
                    }
                case 1 :
                    if (currPlayerPosX+1 > boundX-1 || maze[currPlayerPosX+1][currPlayerPosY].equals(open)) {

                    } else {
                        maze[currPlayerPosX][currPlayerPosY] = open;
                        maze[currPlayerPosX+1][currPlayerPosY] = open;
                        currPlayerPosX += 1;
                        counter++;
                        dirList.add(2);
                        break;
                    }
                case 2 :
                    if (currPlayerPosY-1 < 0 || maze[currPlayerPosX][currPlayerPosY-1].equals(open)) {

                    } else {
                        maze[currPlayerPosX][currPlayerPosY] = open;
                        maze[currPlayerPosX][currPlayerPosY-1] = open;
                        currPlayerPosY -= 1;
                        counter++;
                        dirList.add(3);
                        break;
                    }
                case 3 :
                    if (currPlayerPosY+1 > boundY-1 || maze[currPlayerPosX][currPlayerPosY+1].equals(open)) {

                    } else {
                        maze[currPlayerPosX][currPlayerPosY] = open;
                        maze[currPlayerPosX][currPlayerPosY+1] = open;
                        currPlayerPosY += 1;
                        counter++;
                        dirList.add(4);
                        break;
                    }
            }
        } while (counter < pathLen);
        maze[initPlayerPosX][initPlayerPosY] = "[X]";
        treasurePosX = currPlayerPosX;
        treasurePosY = currPlayerPosY;
        currPlayerPosX = initPlayerPosX;
        currPlayerPosY = initPlayerPosY;
    }

    //Austin
    private static void generatePlayerPos() {
        rand = new Random();
        // randomly assign initial player position within the array bounds
        initPlayerPosX = rand.nextInt(boundX);
        initPlayerPosY = rand.nextInt(boundY);
        // if the initial X and Y pos of the player and the treasure are the same
        //call random again until the the treasure and player are at different positions.

        rand = new Random();

        initPlayerPosX = rand.nextInt(boundX);
        initPlayerPosY = rand.nextInt(boundY);
        currPlayerPosX = initPlayerPosX;
        currPlayerPosY = initPlayerPosY;
    }

    private static void move() {
        String dir;
        sc.nextLine();
        int i = 0;

        while (!found) {
            System.out.print("Enter a direction [ N W S E ] or Q to exit: ");
            dir = sc.nextLine();
            switch (dir.toLowerCase()) {
                case "n":
                    if (dirList.get(i) == 1 && i < dirList.size()) {
                        moveNorth();
                        printMaze();
                        i++;
                    } else {
                        System.out.println(ANSI_RED + "You cannot move in that direction." + ANSI_RESET);
                    }
                    break;
                case "s":
                    if (dirList.get(i) == 2 && i < dirList.size()) {
                        moveSouth();
                        printMaze();
                        i++;
                    } else {
                        System.out.println(ANSI_RED + "You cannot move in that direction" + ANSI_RESET);
                    }
                    break;
                case "w":
                    if (dirList.get(i) == 3 && i < dirList.size()) {
                        moveWest();
                        printMaze();
                        i++;
                    } else {
                        System.out.println(ANSI_RED + "You cannot move in that direction." + ANSI_RESET);
                    }
                    break;
                case "e":
                    if (dirList.get(i) == 4 && i < dirList.size()) {
                        moveEast();
                        printMaze();
                        i++;
                    } else {
                        System.out.println(ANSI_RED + "You cannot move in that direction." + ANSI_RESET);
                    }
                    break;
                case "q":
                    exit();
                default:
                    System.out.println(ANSI_RED + "Invalid direction." + ANSI_RESET);
            }
            if (found) {
                System.out.println(ANSI_RED + "You have found the treasure!" + ANSI_RESET);
                exit();
            }
        }
    }

    //Bili
    // To shift upwards: (-1, 0);
    private static void moveNorth() {
        int currX = currPlayerPosX, currY = currPlayerPosY;

        if  (currPlayerPosX-1 < 0 || maze[currPlayerPosX-1][currPlayerPosY].equals(closed)) {
            System.out.println(ANSI_RED + "You cannot move outside the boundaries of the maze." + ANSI_RESET);
        } else {
            maze[currX][currY] = "[ ]";
            maze[currPlayerPosX-1][currPlayerPosY] = "[X]";
            currPlayerPosX -= 1;
        }
        foundTreasure();
    }

    //Austin - moveSouth
    // To shift downwards: 1, 0);
    private static void moveSouth() {
        int currX = currPlayerPosX, currY = currPlayerPosY;

        if (currPlayerPosX+1 > boundX-1 || maze[currPlayerPosX+1][currPlayerPosY].equals(closed)) {
            System.out.println(ANSI_RED + "You cannot move outside the boundaries of the maze." + ANSI_RESET);
        } else {
            maze[currX][currY] = "[ ]";
            maze[currPlayerPosX+1][currPlayerPosY] = "[X]";
            currPlayerPosX += 1;
        }
        foundTreasure();
    }

    //Austin - moveWest
    // To shift left: (0, -1);
    private static void moveWest() {
        int currX = currPlayerPosX, currY = currPlayerPosY;

        if (currPlayerPosY-1 < 0 || maze[currPlayerPosX][currPlayerPosY-1].equals(closed)) {
            System.out.println(ANSI_RED + "You cannot move outside the boundaries of the maze." + ANSI_RESET);
        } else {
            maze[currX][currY] = "[ ]";
            maze[currPlayerPosX][currPlayerPosY-1] = "[X]";
            currPlayerPosY -= 1;
        }
        foundTreasure();
    }

    //Austin - moveEast
    // To shift right: (0, 1);
    private static void moveEast() {
        int currX = currPlayerPosX, currY = currPlayerPosY;

        if (currPlayerPosY+1 > boundY-1 || maze[currPlayerPosX][currPlayerPosY+1].equals(closed)) {
            System.out.println(ANSI_RED + "You cannot move outside the boundaries of the maze." + ANSI_RESET);
        } else {
            maze[currX][currY] = "[ ]";
            maze[currPlayerPosX][currPlayerPosY+1] = "[X]";
            currPlayerPosY += 1;
        }
        foundTreasure();
    }

    // Returns true if the treasure is found and updates the [X] with [$]
    private static boolean foundTreasure() {
        if (currPlayerPosX == treasurePosX && currPlayerPosY == treasurePosY) {
            maze[currPlayerPosX][currPlayerPosY] = "[" + ANSI_YELLOW + "$" + ANSI_RESET + "]";
            found = true;
        }
        return found;
    }

    private static void exit() {
        System.exit(0);
    }

}
