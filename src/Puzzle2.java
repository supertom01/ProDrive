import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class Puzzle2 {

    private int n;
    private int score;
    private long timeScoreFound;

    private String fileName;

    private Integer[] fixedIndexes;
    private Integer[] currentMatrix;
    private Integer[] bestMatrix;

    /**
     * All 8 possible directions in a matrix.
     */
    public enum Direction {
        LEFT,
        UP,
        RIGHT,
        DOWN,
        LEFT_UP,
        LEFT_DOWN,
        RIGHT_UP,
        RIGHT_DOWN
    }

    /**
     * Create the puzzle object with a custom value of n.
     * @param n The size of the matrix with n * n entries.
     */
    public Puzzle2(int n) {
        this.score = 0;
        this.n = n;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH-mm-ss");
        this.fileName = sdf.format(new Timestamp(System.currentTimeMillis())) + "_" + n;

        fixedIndexes = new Integer[n * n];
        currentMatrix = new Integer[n * n];

        for (int i = 0; i < currentMatrix.length; i++) {
            currentMatrix[i] = i + 1;
        }

        writeToFile(System.lineSeparator() + "Starting test for n = " + this.n + System.lineSeparator());
    }

    public int getN() {
        return n;
    }

    public int getScore() {
        return score;
    }

    public Integer[] getBestMatrix() {
        return bestMatrix;
    }

    public void setN(int n) {
        this.n = n;
    }

    /**
     * Shuffle the matrix. But only the values which are not included into a solution.
     */
    public void shuffle() {
        List<Integer> toShuffle = new ArrayList<>();

        // Only shuffle the indexes which do not belong to a solution.
        for (int i = 0; i < (n * n); i++) {
            if(fixedIndexes[i] == null) {
                toShuffle.add(currentMatrix[i]);
            }
        }

        // Shuffle the list.
        Collections.shuffle(toShuffle);

        // Merge the shuffles values with the fixed ones.
        for (int i = 0; i < (n * n); i++) {
            if(fixedIndexes[i] == null) {
                currentMatrix[i] = toShuffle.get(0);
                toShuffle.remove(0);
            } else {
                currentMatrix[i] = fixedIndexes[i];
            }
        }
    }

    /**
     * Store indexes which should not be shuffled.
     * @param i index
     * @param j index
     * @param k index
     * @param l index
     * @param a value
     * @param b value
     * @param c value
     * @param d value
     */
    public void fixIndexes(int i, int j, int k, int l, int a, int b, int c, int d) {
        fixedIndexes[i] = a;
        fixedIndexes[j] = b;
        fixedIndexes[k] = c;
        fixedIndexes[l] = d;
    }

    /**
     * Check the equation.
     * @param a
     * @param b
     * @param c
     * @param d
     * @return boolean
     */
    public boolean checkEquation(int a, int b, int c, int d) {
        return (a - (2 * b) + c == d) || (a - (2 * b) + c == -d);
    }

    /**
     * Check one direction and index.
     * Not the cleanest solution, but I guess it works?
     * @param matrix        The matrix to check
     * @param startIndex    The index to start counting from
     * @param direction     The direction to look for
     */
    public boolean checkLine(Integer[] matrix, int startIndex, Direction direction) {
        int size = this.n;
        switch (direction) {
            case UP:
                if(!(startIndex < size * 3) && checkEquation(matrix[startIndex], matrix[startIndex - size],
                        matrix[startIndex - 2 * size], matrix[startIndex - 3 * size])) {
                    fixIndexes(startIndex, startIndex - size, startIndex - 2 * size, startIndex - 3 * size,
                            matrix[startIndex], matrix[startIndex - size], matrix[startIndex - 2 * size],
                            matrix[startIndex - 3 * size]);
                    return true;
                }
                break;
            case DOWN:
                if(!(startIndex > (Math.pow(size, 2) - (3 * size) - 1)) && checkEquation(matrix[startIndex],
                        matrix[startIndex + size], matrix[startIndex + 2 * size], matrix[startIndex + 3 * size])) {
                    fixIndexes(startIndex, startIndex + size, startIndex + 2 * size, startIndex + 3 * size,
                            matrix[startIndex], matrix[startIndex + size], matrix[startIndex + 2 * size],
                            matrix[startIndex + 3 * size]);
                    return true;
                }
                break;
            case LEFT:
                if(!(startIndex % size < 3) && checkEquation(matrix[startIndex],
                        matrix[startIndex - 1], matrix[startIndex - 2], matrix[startIndex - 3])) {
                    fixIndexes(startIndex, startIndex - 1, startIndex - 2, startIndex - 3,
                            matrix[startIndex], matrix[startIndex - 1], matrix[startIndex - 2], matrix[startIndex - 3]);
                    return true;
                }
                break;
            case RIGHT:
                if(!(startIndex % size > size - 4) && checkEquation(matrix[startIndex],
                        matrix[startIndex + 1], matrix[startIndex + 2], matrix[startIndex + 3])) {
                    fixIndexes(startIndex, startIndex + 1, startIndex + 2, startIndex + 3,
                            matrix[startIndex], matrix[startIndex + 1], matrix[startIndex + 2], matrix[startIndex + 3]);
                    return true;
                }
                break;
            case LEFT_UP:
                if(!((startIndex % size < 3) || (startIndex < (size + 1) * 3)) && checkEquation(matrix[startIndex],
                        matrix[startIndex - (size + 1)], matrix[startIndex - 2 * (size + 1)],
                        matrix[startIndex - 3 * (size + 1)])) {
                    fixIndexes(startIndex, startIndex - (size + 1), startIndex - 2 * (size + 1),
                            startIndex - 3 * (size + 1), matrix[startIndex], matrix[startIndex - (size + 1)],
                            matrix[startIndex - 2 * (size + 1)], matrix[startIndex - 3 * (size + 1)]);
                    return true;
                }
                break;
            case RIGHT_UP:
                if(!((startIndex % size > size - 4) || (startIndex < (size - 1) * 3)) && checkEquation(matrix[startIndex],
                        matrix[startIndex - (size - 1)], matrix[startIndex - 2 * (size - 1)],
                        matrix[startIndex - 3 * (size - 1)])) {
                    fixIndexes(startIndex, startIndex - (size - 1), startIndex - 2 * (size - 1),
                            startIndex - 3 * (size - 1), matrix[startIndex], matrix[startIndex - (size - 1)],
                            matrix[startIndex - 2 * (size - 1)], matrix[startIndex - 3 * (size - 1)]);
                    return true;
                }
                break;
            case LEFT_DOWN:
                if(!((startIndex % size < 3) || (startIndex > (Math.pow(size, 2) - (3 * size)))) &&
                        checkEquation(matrix[startIndex], matrix[startIndex + (size - 1)],
                                matrix[startIndex + 2 * (size - 1)], matrix[startIndex + 3 * (size - 1)])) {
                    fixIndexes(startIndex, startIndex + (size - 1), startIndex + 2 * (size - 1),
                            startIndex + 3 * (size - 1), matrix[startIndex], matrix[startIndex + (size - 1)],
                            matrix[startIndex + 2 * (size - 1)], matrix[startIndex + 3 * (size - 1)]);
                    return true;
                }
                break;
            case RIGHT_DOWN:
                if(!((startIndex % size > size - 4) || (startIndex > (Math.pow(size, 2) - (3 * size) - 1))) &&
                        checkEquation(matrix[startIndex], matrix[startIndex + (size + 1)],
                                matrix[startIndex + 2 * (size + 1)], matrix[startIndex + 3 * (size + 1)])) {
                    fixIndexes(startIndex, startIndex + (size + 1), startIndex + 2 * (size + 1),
                            startIndex + 3 * (size + 1), matrix[startIndex], matrix[startIndex + (size + 1)],
                            matrix[startIndex + 2 * (size + 1)], matrix[startIndex + 3 * (size + 1)]);
                    return true;
                }
                break;
            default:
                System.err.println("INVALID DIRECTION!");
        }
        return false;
    }

    /**
     * Checks all directions and starting values for the current matrix.
     */
    public void checkMatrix() throws TimeoutException {
        this.fixedIndexes = new Integer[n * n];
        int tempScore = 0;

        for(Direction direction: Direction.values()) {
            for (int i = 0; i < (n * n); i++) {
                if(checkLine(currentMatrix, i, direction)) {
                    tempScore++;
                }
            }
        }

        if(this.score < tempScore) {
            this.score = tempScore;
            this.bestMatrix = currentMatrix;

            int nrOfFixedIndexes = 0;

            for (int i = 0; i < (n * n); i++) {
                if(fixedIndexes[i] != null) {
                    nrOfFixedIndexes++;
                }
            }

            // Store the time a solution was found.
            timeScoreFound = System.currentTimeMillis();

            writeToFile("New score: " + this.score + "; Array: " + Arrays.toString(this.currentMatrix) + System.lineSeparator());
            if(nrOfFixedIndexes == n * n) {
                System.out.println("All indexes are fixed!");
                throw new TimeoutException("All indexes are fixed!");
            }
            System.out.println("Only " + ((n * n) - nrOfFixedIndexes) + " indexes left to shuffle.");
        } else {
            if(System.currentTimeMillis() - timeScoreFound > (1000 * 60)) {
                System.out.println("[TIMEOUT] No new solutions found within 1 minute.");
                throw new TimeoutException("No solutions found withing 1 minute. Loading next matrix series");
            }
        }
    }

    public void writeToFile(String message) {
        try {
            File file = new File("C:/Users/meule/IdeaProjects/ProDrive/results/" + this.fileName + ".txt");
            FileWriter fw = new FileWriter(file, true);
            fw.write(message);
            fw.close();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to open file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Unable to write to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Puzzle2 puzzle = new Puzzle2(5);
        try {
            while(true) {
                puzzle.shuffle();
                puzzle.checkMatrix();
            }
        } catch (TimeoutException e) {
            System.exit(0);
        }
    }
}
