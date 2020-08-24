import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class PuzzleTest {

    @Test
    void isOneLine() {
        // Vertical or horizontal lines
        assertTrue(Puzzle.isOneLine(new int[]{1, 1}, new int[]{1, 2}, new int[]{1, 3}, new int[]{1, 4}));
        assertTrue(Puzzle.isOneLine(new int[]{1, 4}, new int[]{1, 3}, new int[]{1, 2}, new int[]{1, 1}));
        assertTrue(Puzzle.isOneLine(new int[]{1, 1}, new int[]{2, 1}, new int[]{3, 1}, new int[]{4, 1}));
        assertTrue(Puzzle.isOneLine(new int[]{4, 1}, new int[]{3, 1}, new int[]{2, 1}, new int[]{1, 1}));

        // Diagonal lines
        assertTrue(Puzzle.isOneLine(new int[]{1, 1}, new int[]{2, 2}, new int[]{3, 3}, new int[]{4, 4}));
        assertTrue(Puzzle.isOneLine(new int[]{1, 2}, new int[]{2, 3}, new int[]{3, 4}, new int[]{4, 5}));
        assertTrue(Puzzle.isOneLine(new int[]{4, 4}, new int[]{3, 3}, new int[]{2, 2}, new int[]{1, 1}));
        assertTrue(Puzzle.isOneLine(new int[]{4, 5}, new int[]{3, 4}, new int[]{2, 3}, new int[]{1, 2}));
        assertTrue(Puzzle.isOneLine(new int[]{4, 1}, new int[]{3, 2}, new int[]{2, 3}, new int[]{1, 4}));
        assertTrue(Puzzle.isOneLine(new int[]{1, 4}, new int[]{2, 3}, new int[]{3, 2}, new int[]{4, 1}));
    }
}