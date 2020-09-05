import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.StandardSocketOptions;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunPuzzle {

    private List<Puzzle2> puzzles;

    public RunPuzzle() {
        puzzles = new ArrayList<>();
        for (int i = 1; i < 26; i++) {
            puzzles.add(new Puzzle2(i * 5));
        }
        writeToFile(System.lineSeparator() + "ProDrive Puzzle test at " + new Timestamp(System.currentTimeMillis()) + System.lineSeparator());
    }

    public List<Puzzle2> getPuzzles() {
        return puzzles;
    }

    public Integer[] startNextPuzzle() {
        Puzzle2 puzzle = puzzles.get(0);
        puzzles.remove(0);
        try {
            while(true) {
                puzzle.shuffle();
                puzzle.checkMatrix();
            }
        } catch (TimeoutException e) {
            System.out.println("For n = " + puzzle.getN() + " score = " + puzzle.getScore() + " with matrix = " +
                    Arrays.toString(puzzle.getBestMatrix()));
            writeToFile("For n = " + puzzle.getN() + " score = " + puzzle.getScore() + " with matrix = " +
                    Arrays.toString(puzzle.getBestMatrix()) + System.lineSeparator());
            return puzzle.getBestMatrix();
        }
    }

    public void writeToFile(String message) {
        try {
            File file = new File("C:/Users/meule/IdeaProjects/ProDrive/results/bestScores.txt");
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
        RunPuzzle runPuzzle = new RunPuzzle();
        while(runPuzzle.getPuzzles().size() > 0) {
            runPuzzle.startNextPuzzle();
        }
    }

}
