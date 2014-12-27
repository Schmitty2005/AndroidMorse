/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package androidmorse;

/**
 *
 * @author bill
 */
public class MorseCodePlayer {

    @SuppressWarnings("FieldMayBeFinal")
    private int mWPM = 18;
    @SuppressWarnings("FieldMayBeFinal")
    private int mFreqHz = 800;
    @SuppressWarnings("FieldMayBeFinal")
    private boolean mUseFarnsworth = false;
    @SuppressWarnings("FieldMayBeFinal")
    private int mFarnsworthWPM = 12;

    private class Elements {

        int mDitLength;
        int mDahLength;
        int mInterElement;
        int mInterCharacter;
        int mInterWord;
        int mInterWordFarnsworth;

        Elements(int WordsPerMinute, int FarnsworthWordsPerMinute) {
            this.mDitLength = 1200 / WordsPerMinute;
            this.mDahLength = this.mDitLength * 3;
            this.mInterElement = this.mDitLength;
            this.mInterWord = (this.mDitLength * 6);
            this.mInterCharacter = (this.mDahLength - this.mDitLength);
            this.mInterWordFarnsworth = ((1200 / FarnsworthWordsPerMinute) * 7) - this.mDitLength;

        }

    }

    MorseCodePlayer() {
        Elements el = new Elements (18, 12);
        
    }

    MorseCodePlayer(int WPM, int FarsnworthWPM, boolean useFarnsWorth, int FreqHz, String stringToPlay) {
        
        
    }
}
