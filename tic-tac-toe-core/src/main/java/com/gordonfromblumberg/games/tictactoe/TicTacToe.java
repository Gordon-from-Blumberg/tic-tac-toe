package com.gordonfromblumberg.games.tictactoe;

import java.util.Map;

/**
 * Game field:<br>
 * |---|---|---|<br>
 * | 0 | 1 | 2 |<br>
 * |---|---|---|<br>
 * | 3 | 4 | 5 |<br>
 * |---|---|---|<br>
 * | 6 | 7 | 8 |<br>
 * |---|---|---|<br>
 * <br>
 * Bits:
 * <ul>
 *   <li>00 - empty cell</li>
 *   <li>01 - my cell</li>
 *   <li>10 - opponent cell</li>
 * </ul>
 * <br>
 */
public class TicTacToe {
    public static final int[] CELL_MASK = {
            0b11,
            0b11 << 2,
            0b11 << 4,
            0b11 << 6,
            0b11 << 8,
            0b11 << 10,
            0b11 << 12,
            0b11 << 14,
            0b11 << 16
    };
    private static final int EMPTY_CELL = 0b00;
    public static final int MY_CELL = 0b010101010101010101;
    private static final int OPPONENT_CELL = 0b101010101010101010;
    private static final int[] ALL_LINES = {
            // horizontal
            CELL_MASK[0] | CELL_MASK[1] | CELL_MASK[2],
            CELL_MASK[3] | CELL_MASK[4] | CELL_MASK[5],
            CELL_MASK[6] | CELL_MASK[7] | CELL_MASK[8],
            // vertical
            CELL_MASK[0] | CELL_MASK[3] | CELL_MASK[6],
            CELL_MASK[1] | CELL_MASK[4] | CELL_MASK[7],
            CELL_MASK[2] | CELL_MASK[5] | CELL_MASK[8],
            // diagonal
            CELL_MASK[0] | CELL_MASK[4] | CELL_MASK[8],
            CELL_MASK[2] | CELL_MASK[4] | CELL_MASK[6]
    };

    public static int myMove(int state, Map<Integer, Integer> map) {
        if (map.containsKey(state))
            return 1;

        int bestMove = -1, bestResult = -1;
        int moveCount = 0;
        for (int cell : CELL_MASK) {
            if ((cell & state) == EMPTY_CELL) {
                moveCount++;
                int cellSign = MY_CELL & cell;
                int newState = state | cellSign;

                if (checkWin(newState, MY_CELL)) {

                    map.put(state, newState);
                    return 1;

                } else {
                    int result = oppMove(newState, map);

                    if (result > bestResult) {
                        bestResult = result;
                        bestMove = newState;
                    }
                }
            }
        }

        if (bestMove != -1)
            map.put(state, bestMove);

        return moveCount == 0 ? 0 : bestResult;
    }

    /**
     * Does not check is cell empty!
     * @param state Current state
     * @param cell Cell of new move
     * @return New state
     */
    public static int move(int state, int cell) {
        int cellMask = CELL_MASK[cell];
        int cellSign = OPPONENT_CELL & cellMask;
        return state | cellSign;
    }

    private static int oppMove(int state, Map<Integer, Integer> map) {
        for (int cell : CELL_MASK) {
            if ((cell & state) == EMPTY_CELL) {
                int cellSign = OPPONENT_CELL & cell;
                int newState = state | cellSign;

                if (checkWin(newState, OPPONENT_CELL)) {

                    return -1;

                } else {
                    int result = myMove(newState, map);
                    if (result == -1)
                        return -1;
                }
            }
        }

        return 0;
    }

    public static String stateToString(int state) {
        StringBuilder sb = new StringBuilder("\n");
        for (int i = 0, n = CELL_MASK.length; i < n; i++) {
            if (i != 0 && i % 3 == 0)
                sb.append("\n");
            int cell = CELL_MASK[i] & state;
            sb.append("|").append(cell == EMPTY_CELL ? ' ' : cell == (CELL_MASK[i] & MY_CELL) ? "o" : "x");
            if (i % 3 == 2)
                sb.append("|");
        }
        return sb.toString();
    }

    public static boolean isEmpty(int state, int cell) {
        return (CELL_MASK[cell] & state) == EMPTY_CELL;
    }

    public static String tip() {
        return "|0|1|2|\n" +
                "|3|4|5|\n" +
                "|6|7|8|";
    }

    public static boolean checkWin(int state, int sign) {
        for (int line : ALL_LINES) {
            // mask with bits of the particular sign on the one line
            int winMask = line & sign;
            if ((winMask & state) == winMask) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDraw(int state) {
        for (int cell : CELL_MASK) {
            if ((cell & state) == EMPTY_CELL)
                return false;
        }
        return true;
    }
}
