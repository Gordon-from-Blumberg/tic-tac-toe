package com.gordonfromblumberg.games.tictactoecli;

import com.gordonfromblumberg.games.tictactoe.TicTacToe;

import java.util.*;

import static com.gordonfromblumberg.games.tictactoe.TicTacToe.*;

public class Main {
    private static final String[] GREETINGS = {
            "Hello! Do you like tic tac toe? Let's play!",
            "Hi! Can you beat me in tic tac toe?",
            "Hey! How about to play tic tac toe?"
    };
    private static final String[] START_OPP_MOVE = {
            "Ok, take the first move.",
            "Cool! Move first.",
            "Great, your move."
    };
    private static final String[] END_OPP_MOVE = {
            "Hmm, interesting!",
            "What are you up to?",
            "Hey, I was going to go here!",
            "Good move! But mine will be better!"
    };
    private static final String[] START_MY_MOVE = {
            "Just one moment...",
            "Give me a time to think...",
            "I will do it in a moment..."
    };
    private static final String[] END_MY_MOVE = {
            "I've done! How do you answer?",
            "Ready to give up?",
            "What do think about it?"
    };
    private static final String[] MY_WIN = {
            "You played well, but I'm better!",
            "I'm a champion!",
            "Ready for a rematch?"
    };
    private static final String[] DRAW = {
            "You are playing great!",
            "You was so close to victory!",
            "Nice match, it isn't? Let's play again?"
    };

    private static final Random RAND = new Random();

    public static void main(String[] args) {
        System.out.println(getRandom(GREETINGS));

        Map<Integer, Integer> moves = new HashMap<>();
        int state = 0;
        printTip();

        int turn = 0, draws = 0, defeats = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (turn == 0)
                System.out.println(getRandom(START_OPP_MOVE));

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
                System.out.println(getRandom(DRAW));
                state = 0;
                turn = 0;
                draws++;
                printResult(draws, defeats);
                continue;
            }

            System.out.println(getRandom(END_OPP_MOVE));
            System.out.println(getRandom(START_MY_MOVE));

            if (!moves.containsKey(state))
                myMove(state, moves);

            state = moves.get(state);
            System.out.println(stateToString(state));

            if (checkWin(state, MY_CELL)) {
                System.out.println(getRandom(MY_WIN));
                state = 0;
                turn = 0;
                defeats++;
                printResult(draws, defeats);
                continue;
            }

            if (isDraw(state)) {
                System.out.println(getRandom(DRAW));
                state = 0;
                turn = 0;
                draws++;
                printResult(draws, defeats);
                continue;
            }

            turn++;
            System.out.println(getRandom(END_MY_MOVE));
        }
    }

    private static void printTip() {
        System.out.println("Cell numbers:");
        System.out.println(TicTacToe.tip());
        System.out.println("If you forget cell numbers, print tip.");
    }

    private static String getRandom(String[] array) {
        return array[RAND.nextInt(array.length)];
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
        System.out.println("Your victories: 0");
        System.out.println("Draws: " + draws);
        System.out.println("Defeats: " + defeats);
    }
}
