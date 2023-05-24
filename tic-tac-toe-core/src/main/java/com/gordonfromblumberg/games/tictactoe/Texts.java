package com.gordonfromblumberg.games.tictactoe;

import java.util.Random;

public class Texts {
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

    private static final String[] END_OPP_MOVES = {
            "Hmm, interesting!",
            "What are you up to?",
            "Hey, I was going to go here!",
            "Good move! But mine will be better!"
    };

    private static final String[] START_MY_MOVES = {
            "Just one moment...",
            "Give me a time to think...",
            "I will do it in a moment..."
    };

    private static final String[] END_MY_MOVES = {
            "I've done! How do you answer?",
            "Ready to give up?",
            "What do think about it?"
    };

    private static final String[] MY_WIN = {
            "You played well, but I'm better!",
            "I'm a champion!",
            "Ready for a rematch?"
    };

    private static final String[] DRAWS = {
            "You are playing great!",
            "You was so close to victory!",
            "Nice match, it isn't? Let's play again?"
    };

    private static final Random RAND = new Random();

    public static String getGreeting() {
        return getRandom(GREETINGS);
    }

    public static String getFirstOppMove() {
        return getRandom(START_OPP_MOVE);
    }

    public static String getEndOppMove() {
        return getRandom(END_OPP_MOVES);
    }

    public static String getStartMyMove() {
        return getRandom(START_MY_MOVES);
    }

    public static String getEndMyMove() {
        return getRandom(END_MY_MOVES);
    }

    public static String getMyWin() {
        return getRandom(MY_WIN);
    }

    public static String getDraw() {
        return getRandom(DRAWS);
    }

    private static String getRandom(String[] array) {
        return array[RAND.nextInt(array.length)];
    }
}
