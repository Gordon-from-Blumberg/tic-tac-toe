package com.gordonfromblumberg.games.tictactoe;

public class IncorrectMoveException extends Exception {
    public IncorrectMoveException(String message) {
        super(message);
    }
}
