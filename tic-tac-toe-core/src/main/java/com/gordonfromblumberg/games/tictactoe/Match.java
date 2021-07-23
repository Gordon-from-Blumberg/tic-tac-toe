package com.gordonfromblumberg.games.tictactoe;

public class Match {
    private int state;

    public int move(int cell) throws IncorrectMoveException {
        if (cell < 0 || cell > 8 || !TicTacToe.isEmpty(state, cell)) {
            throw new IncorrectMoveException("Incorrect move " + cell);
        }

        return TicTacToe.move(state, cell);
    }

    public void reset() {
        state = 0;
    }


}
