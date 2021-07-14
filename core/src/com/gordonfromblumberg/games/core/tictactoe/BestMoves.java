package com.gordonfromblumberg.games.core.tictactoe;

import com.badlogic.gdx.utils.IntIntMap;

import static com.gordonfromblumberg.games.core.tictactoe.TicTacToe.*;

public class BestMoves {
    public static void main(String[] args) {
        int state = 0;
        final IntIntMap map = new IntIntMap();

        move(state, false, map);

        System.out.println("map size = " + map.size);
    }

    private static void move(int state, boolean myTurn, IntIntMap map) {
        if (map.containsKey(state))
            return;

        int sign = myTurn ? MY_CELL : OPPONENT_CELL;
        for (int cell : CELL_MASK) {
            if ((cell & state) == EMPTY_CELL) {
                int cellSign = sign & cell;
                int newState = state | cellSign;
                if (checkWin(newState, sign)) {

                    if (myTurn) {
                        map.put(state, newState);
                    } else {
                        return;
                    }

                } else {
                    move(newState, !myTurn, map);
                }
            }
        }
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
}
