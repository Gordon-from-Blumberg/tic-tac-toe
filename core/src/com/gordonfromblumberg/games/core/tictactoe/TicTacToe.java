package com.gordonfromblumberg.games.core.tictactoe;

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
    static final int[] CELL_MASK = {
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
    static final int EMPTY_CELL = 0b00;
    static final int MY_CELL = 0b010101010101010101;
    static final int OPPONENT_CELL = 0b101010101010101010;
    static final int[] HOR_LINES = {
            CELL_MASK[0] | CELL_MASK[1] | CELL_MASK[2],
            CELL_MASK[3] | CELL_MASK[4] | CELL_MASK[5],
            CELL_MASK[6] | CELL_MASK[7] | CELL_MASK[8]
    };
    static final int[] VER_LINES = {
            CELL_MASK[0] | CELL_MASK[3] | CELL_MASK[6],
            CELL_MASK[1] | CELL_MASK[4] | CELL_MASK[7],
            CELL_MASK[2] | CELL_MASK[5] | CELL_MASK[8]
    };
    static final int[] DIAG_LINES = {
            CELL_MASK[0] | CELL_MASK[4] | CELL_MASK[8],
            CELL_MASK[2] | CELL_MASK[4] | CELL_MASK[6]
    };
    static final int[] ALL_LINES = {
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

}
