package com.gordonfromblumberg.games.tictactoecli;

import com.gordonfromblumberg.games.tictactoe.Texts;
import com.gordonfromblumberg.games.tictactoe.TicTacToe;

import java.util.*;

import static com.gordonfromblumberg.games.tictactoe.TicTacToe.*;

public class Main {
    private static final Random RAND = new Random();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Texts.getGreeting());

        Map<Integer, Integer> moves = new HashMap<>();
        int state = 0;
        printTip();

        int turn = 0, draws = 0, defeats = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (turn == 0)
                System.out.println(Texts.getFirstOppMove());

            int cell;
            boolean correctMove = false;
            int errors = 0;
            while (!correctMove) {
                String userInput = scanner.next();

                if ("tip".equalsIgnoreCase(userInput)) {
                    printTip();
                    continue;
                }

                try {
                    cell = Integer.parseInt(userInput);

                    if (cell < 0 || cell > 8 || !isEmpty(state, cell)) {
                        throw new NumberFormatException("Cell number out of range: " + cell);
                    }

                    correctMove = true;
                    state = move(state, cell);
                    System.out.println(stateToString(state));

                } catch (NumberFormatException e) {
                    System.out.println("Sorry, this is incorrect move.");
                    printPossibleMoves(state);
                    if (++errors == 3) {
                        printTip();
                        errors = 0;
                    }
                }
            }

            if (isDraw(state)) {
                System.out.println(Texts.getDraw());
                state = 0;
                turn = 0;
                draws++;
                printResult(draws, defeats);
                continue;
            }

            System.out.println(Texts.getEndOppMove());
            System.out.println(Texts.getStartMyMove());

            Thread.sleep(750 + RAND.nextInt(1000));

            if (!moves.containsKey(state))
                myMove(state, moves);

            state = moves.get(state);
            System.out.println(stateToString(state));

            if (checkMyWin(state)) {
                System.out.println(Texts.getMyWin());
                state = 0;
                turn = 0;
                defeats++;
                printResult(draws, defeats);
                continue;
            }

            if (isDraw(state)) {
                System.out.println(Texts.getDraw());
                state = 0;
                turn = 0;
                draws++;
                printResult(draws, defeats);
                continue;
            }

            turn++;
            System.out.println(Texts.getEndMyMove());
        }
    }

    private static void printTip() {
        System.out.println("Cell numbers:");
        System.out.println(TicTacToe.tip());
        System.out.println("If you forget cell numbers, print tip.");
    }

    private static void printPossibleMoves(int state) {
        StringBuilder sb = new StringBuilder("Possible moves: ");
        boolean first = true;
        for (int i = 0, n = CELL_MASK.length; i < n; i++) {
            if (isEmpty(state, i)) {
                if (!first)
                    sb.append(", ");
                else
                    first = false;

                sb.append(i);
            }
        }

        System.out.println(sb);
        System.out.println("If you forget cell numbers, print tip.");
    }

    private static void printResult(int draws, int defeats) {
        System.out.println("Current results:");
        System.out.println("Victories: 0");
        System.out.println("Draws: " + draws);
        System.out.println("Defeats: " + defeats);
    }
}
