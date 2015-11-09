package com.gof.scut.androidcourse;

import java.util.Random;

/**
 * Created by gjz on 11/9/15.
 */
public class CardColor {

    public int backgroundColor;
    public int textColor;

    CardColor(int backgroundColor, int textColor) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public static CardColor  getCardColor() {
        CardColor[] cardColors = {new CardColor(R.color.blue, R.color.white),
                                new CardColor(R.color.green, R.color.white),
                                new CardColor(R.color.pink, R.color.white),
                                new CardColor(R.color.white, R.color.black),
                                new CardColor(R.color.yellow, R.color.black)};

        return cardColors[new Random().nextInt(cardColors.length)];
    }



}
