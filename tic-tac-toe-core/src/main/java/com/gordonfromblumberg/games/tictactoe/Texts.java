package com.gordonfromblumberg.games.tictactoe;

import java.util.Random;

public class Texts {
    public static final String GREETING = "GREETING";
    private static final String[] GREETINGS = {
            "Hello! Do you like tic tac toe? Let's play!",
            "Hi! Can you beat me in tic tac toe?",
            "Hey! How about to play tic tac toe?"
    };

    public static final String FIRST_OPP_MOVE = "FIRST_OPP_MOVE";
    private static final String[] START_OPP_MOVE = {
            "Ok, take the first move.",
            "Cool! Move first.",
            "Great, your move."
    };

    public static final String END_OPP_MOVE = "END_OPP_MOVE";
    private static final String[] END_OPP_MOVES = {
            "Hmm, interesting!",
            "What are you up to?",
            "Hey, I was going to go here!",
            "Good move! But mine will be better!"
    };

    public static final String START_MY_MOVE = "START_MY_MOVE";
    private static final String[] START_MY_MOVES = {
            "Just one moment...",
            "Give me a time to think...",
            "I will do it in a moment..."
    };

    public static final String END_MY_MOVE = "END_MY_MOVE";
    private static final String[] END_MY_MOVES = {
            "I've done! How do you answer?",
            "Ready to give up?",
            "What do think about it?"
    };

    public static final String MY_WIN_KEY = "MY_WIN";
    private static final String[] MY_WIN = {
            "You played well, but I'm better!",
            "I'm a champion!",
            "Ready for a rematch?"
    };

    public static final String DRAW = "DRAW";
    private static final String[] DRAWS = {
            "You are playing great!",
            "You was so close to victory!",
            "Nice match, it isn't? Let's play again?"
    };

    private static final Random RAND = new Random();

    public static String getText(String key) {
        switch (key) {
            case GREETING:
                return getRandom(GREETINGS);
            case FIRST_OPP_MOVE:
                return getRandom(START_OPP_MOVE);
            case END_OPP_MOVE:
                return getRandom(END_OPP_MOVES);
            case START_MY_MOVE:
                return getRandom(START_MY_MOVES);
            case END_MY_MOVE:
                return getRandom(END_MY_MOVES);
            case MY_WIN_KEY:
                return getRandom(MY_WIN);
            case DRAW:
                return getRandom(DRAWS);
            default:
                throw new IllegalArgumentException("Unknown key " + key);
        }
    }

    private static String getRandom(String[] array) {
        return array[RAND.nextInt(array.length)];
    }
}
