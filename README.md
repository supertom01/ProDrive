# ProDrive
The puzzle from I/O Vivat volume 35 #1.
[Puzzle page](https://puzzle.prodrive-technologies.com/inter-actief)

## Puzzle
Requested is a matrix of `n*n` integers `(n >= 4)` filled with the integers `1..n*n`.
The goal is to maximize the number of times the equation `|a-2b+c=d|` is satisfied.
This can be done in all 8 directions, as long as a, b, c and d are continguous and on a single line.

For each `n`, you will get a subscore between 0 and 1. The subscore is calculated by dividing your best score by the best score of any contestant.
Assume you are the first to submit a solution for `n = 10` with a score of 30. Since this is the only submission for `n = 10`, it is also the best
score currently submitted for `n = 10`, resulting in a subscore of 1.0 points. Another contestant submits a solution for `n = 10` with a score of
34. Now your subscore is reduced to 0.88 `( = 30 / 34)`.

Your total score is the sum of all your subscores. This means your total score is between 0 and 25. The goal is to maximize your total score. Note
that your total score is not fixed! As other people submit solutions your total score might drop, so keep an eye on the submission page.

### Example
```              
 7  8 10 22 17    | 7  - 2 * 12 + 19 | ==  2
14 12  3 25  4    | 1  - 2 * 11 + 4  | == 17
13 15 19  9 11    | 5  - 2 * 16 + 15 | == 12
18 16  6  2  1    | 20 - 2 * 24 + 5  | == 23
23  5 24 20 21    | 20 - 2 * 2  + 9  | == 25
                  | 21 - 2 * 20 + 24 | ==  5
```   `
