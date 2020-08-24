import java.io.*;
import java.util.*;

/**
 * The Puzzle class for the ProDrive puzzle from I/O VIVAT v. 35 no. 1.
 * Website <a href="https://puzzle.prodrive-technologies.com/">link</a>
 * @author Tom Meulenkamp
 * @version 1.0
 */
public class Puzzle {

    private final int n;
    private int score;
    private int counter;
    private Integer[] fixedIndexes;
    private Integer[] currentArray;

    /**
     * Create a puzzle object.
     * @param n The number of rows to create.
     */
    public Puzzle(int n) {
        this.score = 0;
        this.n = n;
        this.fixedIndexes = new Integer[n*n];
        this.currentArray = new Integer[n*n];
        for (int i = 0; i < n * n; i++) {
            currentArray[i] = i + 1;
        }
        this.counter = 0;
        writeToFile(System.lineSeparator() + "Starting test for matrix with n = " + n + System.lineSeparator());
        while (Arrays.asList(fixedIndexes).subList(0, n * n).contains(null) && counter < 1000) {
            counter++;
            if(counter % 100 == 0) {
                System.out.println(counter / 10 + "% has been processed.");
            }
            currentArray = shuffle();
            testMatrix(currentArray);
        }
    }

    /**
     * @deprecated
     *
     * Get all possible permutations of the matrix.
     * @param items         The set of integers which should form the matrix.
     * @param permutation   The stack which will contain all permutations.
     * @param size          The size of the permutations (the length of the matrix)
     */
    public void permutations(Set<Integer> items, Stack<Integer> permutation, int size) {

        /* permutation stack has become equal to size that we require */
        if(permutation.size() == size) {
            /* print the permutation */
            testMatrix(permutation.toArray(new Integer[0]));
        }

        /* items available for permutation */
        Integer[] availableItems = items.toArray(new Integer[0]);
        for(Integer i : availableItems) {
            /* add current item */
            permutation.push(i);

            /* remove item from available item set */
            items.remove(i);

            /* pass it on for next permutation */
            permutations(items, permutation, size);

            /* pop and put the removed item back */
            items.add(permutation.pop());
        }
    }

    /**
     * Shuffles the array. But it will keep the indexes which already contain solutions.
     * @return The shuffled array
     */
    public Integer[] shuffle() {
        List<Integer> toShuffle = new ArrayList<>();
        Integer[] toReturn = new Integer[n * n];
        for (int i = 0; i < fixedIndexes.length; i++) {
            if(fixedIndexes[i] == null) {
                toShuffle.add(currentArray[i]);
            }
        }
        Collections.shuffle(toShuffle);
        for (int i = 0; i < n * n; i++) {
            if(fixedIndexes[i] == null) {
                toReturn[i] = toShuffle.get(0);
                toShuffle.remove(0);
            } else {
                toReturn[i] = fixedIndexes[i];
            }
        }

        return toReturn;
    }

    /**
     * Check if the equation holds
     * @param a value for the variable a
     * @param b value for the variable b
     * @param c value for the variable c
     * @param d value for the variable d
     * @return  true if holds, otherwise false.
     */
    public boolean checkEquation(int a, int b, int c, int d) {
        return Math.abs(a - (2 * b) + c) == d;
    }

    /**
     * Checks if the given indexes are on one continues line.
     * @param i A tuple of [x,y] coordinates
     * @param j A tuple of [x,y] coordinates
     * @param k A tuple of [x,y] coordinates
     * @param l A tuple of [x,y] coordinates
     * @return  true if holds, otherwise false.
     */
    public static boolean isOneLine(int[] i, int[] j, int[] k, int[] l) {
        // Vertical or horizontal lines
        boolean sameX = i[0] == j[0] && j[0] == k[0] && k[0] == l[0];
        boolean sameY = i[1] == j[1] && j[1] == k[1] && k[1] == l[1];
        boolean countingLeft = i[0] - 1 == j[0] && j[0] - 1 == k[0] && k[0] - 1 == l[0];
        boolean countingRight = l[0] - 1 == k[0] && k[0] - 1 == j[0] && j[0] - 1 == i[0];
        boolean countingDown = l[1] - 1 == k[1] && k[1] - 1 == j[1] && j[1] - 1 == i[1];
        boolean countingUp = i[1] - 1 == j[1] && j[1] - 1 == k[1] && k[1] - 1 == l[1];

        // Diagonal lines
        boolean rightDown = countingRight && countingDown;
        boolean rightUp = countingRight && countingUp;
        boolean leftDown = countingLeft && countingDown;
        boolean leftUp = countingLeft&& countingUp;

        return (sameY && countingLeft) || (sameY && countingRight)
            || (sameX && countingDown) || (sameX && countingUp)
            || rightDown || rightUp || leftDown || leftUp;
    }

    /**
     * Converts an array to a two dimensional matrix.
     * @param array the array to convert.
     * @return  the 2d matrix
     */
    public int[][] arrayToMatrix(Integer[] array) {
        int[][] matrix = new int[n][n];
        int j = -1;
        int k = 0;
        for (int i = 0; i < n * n; i++) {
            if(i % n == 0) {
                j++;
                k = 0;
            }
            matrix[j][k] = array[i];
            k++;
        }
        return matrix;
    }

    public void writeToFile(String toWrite) {
        FileWriter fw;
        try {
            File file = new File("C:/Users/meule/IdeaProjects/ProDrive/src/results2.txt");
            fw = new FileWriter(file, true);
            fw.write(toWrite);
            fw.close();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to open file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Unable to write to file: " + e.getMessage());
        }
    }

    /**
     * Test the matrix and echo the array representation and score if it scores higher than the previous ones.
     * @param mat   the matrix to check.
     */
    public void testMatrix(Integer[] mat) {
        fixedIndexes = new Integer[n * n];
        int tempScore = 0;
        int[][] matrix = arrayToMatrix(mat);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                for (int k = 0; k < this.n; k++) {
                    for (int l = 0; l < this.n; l++) {
                        for (int m = 0; m < this.n; m++) {
                            for (int n = 0; n < this.n; n++) {
                                for (int o = 0; o < this.n; o++) {
                                    for (int p = 0; p < this.n; p++) {
                                        if(isOneLine(new int[]{i, j}, new int[]{k, l}, new int[]{m, n}, new int[]{o, p})
                                        && checkEquation(matrix[i][j], matrix[k][l], matrix[m][n], matrix[o][p])) {
                                            tempScore++;
                                            fixedIndexes[j + this.n*i] = matrix[i][j];
                                            fixedIndexes[l + this.n*k] = matrix[k][l];
                                            fixedIndexes[n + this.n*m] = matrix[m][n];
                                            fixedIndexes[p + this.n*o] = matrix[o][p];
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if(this.score < tempScore) {
            this.score = tempScore;
            System.out.println("New score: " + tempScore + "; Array: " + Arrays.toString(mat));
            writeToFile("New score: " + tempScore + "; Array: " + Arrays.toString(mat) + System.lineSeparator());
        }
    }

    public static void main(String[] args) {
        new Puzzle(25);
    }
}