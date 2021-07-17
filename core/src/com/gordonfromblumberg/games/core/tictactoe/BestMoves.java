package com.gordonfromblumberg.games.core.tictactoe;

import com.badlogic.gdx.utils.IntIntMap;

import static com.gordonfromblumberg.games.core.tictactoe.TicTacToe.*;

public class BestMoves {
    public static void main(String[] args) {
        int state = 0;
        final IntIntMap map = new IntIntMap();

        oppMove(state, map);

        System.out.println("map size = " + map.size);
        state = 0b000000000000000010;
        System.out.println(stateToString(state));
        while (map.containsKey(state)) {
            state = map.get(state, -1);
            System.out.println(stateToString(state));
        }
    }

    private static int myMove(int state, IntIntMap map) {
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
                    if ((newState & 0b000000000100000010) == 0b000000000100000010) {
                        System.out.println("newState:" + stateToString(newState));
                        System.out.println("result = " + result);
                    }

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

    private static int oppMove(int state, IntIntMap map) {
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

    private static boolean checkWin(int state, int sign) {
        for (int line : ALL_LINES) {
            // mask with bits of the particular sign on the one line
            int winMask = line & sign;
            if ((winMask & state) == winMask) {
                return true;
            }
        }
        return false;
    }

    private static String stateToString(int state) {
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
}
