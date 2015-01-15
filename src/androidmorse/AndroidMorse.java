/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package androidmorse;
//import java.io.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 
 * @author bill
 */
public class AndroidMorse {

    private double dit_length;
    private double dah_length;
    private double interElementSpacing;
    private double interCharacterSpacing_normal;
    private double interCharacterSpacing_farnsworth;
    private double interWordSpacing;
    private boolean farnsworthSpacing;//= false;
    
    @SuppressWarnings("FieldMayBeFinal")
    private double farnsworthWPM = 13;

    private byte[] ditElementPCM;
    private byte[] dahElementPCM;
    private byte[] interCharacterPCM;
    private byte[] interCharacterFarnsworthPCM;
    private byte[] interWordSpacingPCM;
    
    public byte [] morseWaveByteArray;
    
    MorseContainer mc = new MorseContainer();

    @SuppressWarnings("FieldMayBeFinal")
    private int mWordsPerMinute = 18;
    @SuppressWarnings("FieldMayBeFinal")
    
    private short mfreqInHz = 600;
    //private int sample_rate = 44100;
    final private int sample_rate = 16000;
    @SuppressWarnings("FieldMayBeFinal")
    private int mFarnsWPM = 12;
    private byte[] constructionOnePCM;
    private byte[] constructionTwoPCM;

    
    @SuppressWarnings("FieldMayBeFinal")
    public java.util.HashMap<Integer, String> levelSets = new java.util.HashMap<>();

    {
        levelSets.put(1,"50ETAR");
        levelSets.put(2,"SLUQJH");
        levelSets.put(3,"ONCVIB");
        levelSets.put(4,"50ETARSLUQJHONCVIB"); //REVIEW LEVEL
        levelSets.put(5,"YPWKZM");
        levelSets.put(6,"DFXG?1");
        levelSets.put(7,"/34678");
        levelSets.put(8,"YPWKZMDFG?1/34678");//REVIEW LEVEL
        levelSets.put(9,"&$\'\";:");
        levelSets.put(10, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789/?&$;:\'\"");
    }
   
    @SuppressWarnings("FieldMayBeFinal")
    private java.util.HashMap<Character, String> morseDictionary = new java.util.HashMap<>();

    {
        morseDictionary.put('a', ".-");
        morseDictionary.put('b', "-...");
        morseDictionary.put('c', "-.-.");
        morseDictionary.put('d', "-..");
        morseDictionary.put('e', ".");
        morseDictionary.put('f', "..-.");
        morseDictionary.put('g', "--.");
        morseDictionary.put('h', "....");
        morseDictionary.put('i', "..");
        morseDictionary.put('j', ".---");
        morseDictionary.put('k', "-.-");
        morseDictionary.put('l', ".-..");
        morseDictionary.put('m', "--");
        morseDictionary.put('n', "-.");
        morseDictionary.put('o', "---");
        morseDictionary.put('p', ".--.");
        morseDictionary.put('q', "--.-");
        morseDictionary.put('r', ".-.");
        morseDictionary.put('s', "...");
        morseDictionary.put('t', "-");
        morseDictionary.put('u', "..-");
        morseDictionary.put('v', "...-");
        morseDictionary.put('w', ".--");
        morseDictionary.put('x', "-..-");
        morseDictionary.put('y', "-.--");
        morseDictionary.put('z', "--..");
        morseDictionary.put('0', "-----");
        morseDictionary.put('1', ".----");
        morseDictionary.put('2', "..---");
        morseDictionary.put('3', "...--");
        morseDictionary.put('4', "....-");
        morseDictionary.put('5', ".....");
        morseDictionary.put('6', "-....");
        morseDictionary.put('7', "--...");
        morseDictionary.put('8', "---..");
        morseDictionary.put('9', "----.");
        morseDictionary.put('?', "..--..");
        morseDictionary.put('!', ".-.-");
        morseDictionary.put('(', "-.--.");
        morseDictionary.put(')', "-.--.-");
        morseDictionary.put(' ', " ");
        morseDictionary.put('@', ".--.-.");
        morseDictionary.put('/', "-..-.");
        morseDictionary.put('.', ".-.-.-");
        morseDictionary.put(',', "--..--");
        morseDictionary.put('&',"._...");
        morseDictionary.put(':',"---..." );
        morseDictionary.put(';',"-.-.-." );
        morseDictionary.put('=',"-...-");
        morseDictionary.put('+',".-.-.");
        morseDictionary.put('-',"-....-");
        morseDictionary.put('\'', ".----.");
        morseDictionary.put('\"', ".-..-.");
        //TODO need " and ' characters added to dictionary
        
//finish fixing dictionary
//add that one command to convert it to unchangeable.....whatever it was called...
    }

    MorseContainer mContainer = new MorseContainer();

    public void setString(String mStringToPlay) {
        mContainer.stringToPlay = mStringToPlay;
        //MorseWave mw = new MorseWave(mContainer);

    }
/**
 * 
 * @param WPM
 * @param stringToAudio 
 */
    public AndroidMorse(int WPM, String stringToAudio) {
        MorseElements(WPM, 13, false, 800);
        this.mWordsPerMinute = WPM;
        mc.mWPM= WPM;
        //mc.mFarnsEnabled = FarnsworthEnabled;
       // mc.mFarnsWPM= FarnsWorthWPM;
        mc.stringToPlay = stringToAudio;
        mc.waveByteArray = byteWaveMorse(mc);
        this.morseWaveByteArray = this.mc.waveByteArray;
    }
/**
 * 
 * @param WPM
 * @param FarnsworthEnabled
 * @param FarnsWorthWPM
 * @param stringToAudio 
 */
    public AndroidMorse(int WPM, boolean FarnsworthEnabled, int FarnsWorthWPM, String stringToAudio) {
        //TODO elements need to be intialized first!
        this.mWordsPerMinute = WPM;
        this.farnsworthSpacing = FarnsworthEnabled;
        this.mFarnsWPM = FarnsWorthWPM;
        MorseElements(WPM, FarnsWorthWPM, FarnsworthEnabled, 800);
        mc.mWPM= WPM;
        mc.mFarnsEnabled = FarnsworthEnabled;
        mc.mFarnsWPM= FarnsWorthWPM;
        mc.stringToPlay = stringToAudio;
        mc.waveByteArray = byteWaveMorse(mc);
        this.morseWaveByteArray = this.mc.waveByteArray;
    }
   /**
    * 
    * @param WPM Speed of morse code in desired audio in words per minute
    * @param FarnsworthEnabled Farnsworth spacing enabled true or false.
    * @param FarnsWorthWPM Spacing of desired farnsworth speed
    * @param freqHz Tone in Hertz of morse code
    * @param stringToAudio String to convert to morse code audio.
    */ 
    public AndroidMorse(int WPM, boolean FarnsworthEnabled, int FarnsWorthWPM, int freqHz,  String stringToAudio) {
        //TODO elements need to be intialized first!
        this.mfreqInHz = (short)freqHz;
        this.mWordsPerMinute = WPM;
        this.farnsworthSpacing = FarnsworthEnabled;
        this.mFarnsWPM = FarnsWorthWPM;
        MorseElements(WPM, FarnsWorthWPM, FarnsworthEnabled, 800);
        mc.mWPM= WPM;
        mc.mFarnsEnabled = FarnsworthEnabled;
        mc.mFarnsWPM= FarnsWorthWPM;
        mc.stringToPlay = stringToAudio;
        mc.waveByteArray = byteWaveMorse(mc);
        this.morseWaveByteArray = this.mc.waveByteArray;
    }

    /**
     * combineByteArray simply combines the first and the second byte array as
     * one.
     *
     * @param firstByte First byte array desired
     * @param secondByte Second byte array to follow first
     * @return Returns firstByte plus secondByte
     */
    private static byte[] combineByteArray(byte[] firstByte, byte[] secondByte) {
        byte[] combinedArray = new byte[(firstByte.length + secondByte.length)];
        ByteBuffer bb = ByteBuffer.wrap(combinedArray);

        bb.position(0);
        bb.put(firstByte);
        bb.put(secondByte);

        return combinedArray;
    }

    private static void writeInt(ByteBuffer buffer, int intToUnsign) {

        buffer.put((byte) (intToUnsign));
        buffer.put((byte) (intToUnsign >> 8));
        buffer.put((byte) (intToUnsign >> 16));
        buffer.put((byte) (intToUnsign >> 24));
    }

    /**
     * This method accepts a byte array and creates and attaches a wave header
     * to the data.
     *
     * @param pcmData Pure PCM data as a byte []
     * @param sampleRate Sample rate of PCM data. Usually 44100
     * @param bitsPerSample Bit rate of PCM data. Usually 16
     * @return Returns Byte Array with newly created wave header and PCM wave
     * data attached.
     */
    private static byte[] createWaveHeaderForPcm(byte[] pcmData, int sampleRate, short bitsPerSample) {

        short numberChannels = 1;  // number of channels
        int byteRate = sampleRate * numberChannels * bitsPerSample / 8;
        short blockAlign = (short) (numberChannels * bitsPerSample / 8);
        int subChunk2Size = pcmData.length;
        //
        int pcmLength = pcmData.length;
        int waveLength = pcmData.length + 44;
        waveLength++;
        //conduct check of wave file length
        if (waveLength % 2 != 0) {
            waveLength++;
            pcmLength++;
            pcmLength++;
        }  //wave files must have an even number of bytes!
        //System.out.println("waveLength Value : " + waveLength);
        ByteBuffer waveBuffer = ByteBuffer.allocate(pcmLength + 44);
        waveBuffer.position(0);
        //Set to Big Endian for RIFF
        waveBuffer.order(ByteOrder.BIG_ENDIAN);
        //write ASCII 'RIFF' to Byte Buffer
        waveBuffer.put((byte) 0x52);
        waveBuffer.put((byte) 0x49);
        waveBuffer.put((byte) 0x46);
        waveBuffer.put((byte) 0x46);
        //Set to Little Endian for ChunkSize
        waveBuffer.order(ByteOrder.LITTLE_ENDIAN);
        //write Int Subchunk size
        waveBuffer.putInt(36 + subChunk2Size); 
        //Set to Big Endian for WAVE
        waveBuffer.order(ByteOrder.BIG_ENDIAN);
        //write ASCII 'WAVE' to Byte Buffer
        //0x57415645 big-endian form
        byte[] asciiwave = {0x57, 0x41, 0x56, 0x45}; //wave in hex ASCII
        waveBuffer.put(asciiwave);
        byte[] asciifmt = {0x66, 0x6d, 0x74, 0x20};//  fmt in ASCII 0x666d7420 big-endian form).
        waveBuffer.put(asciifmt);
        //sub chunk size also bitrate = 16
        waveBuffer.order(ByteOrder.LITTLE_ENDIAN);
        waveBuffer.putInt(bitsPerSample);  //This is 16 for the bit-rate  ***WATCH FOR ERRORS HERE
        waveBuffer.putShort((short) 1);  //1 is for PCM 2   AudioFormat      PCM = 1 (i.e. Linear quantization)
        waveBuffer.putShort(numberChannels); //2   NumChannels      Mono = 1, Stereo = 2, etc.
        writeInt(waveBuffer, sampleRate);
        writeInt(waveBuffer, byteRate);
        waveBuffer.putShort(blockAlign);//2   BlockAlign       == NumChannels * BitsPerSample/8
        waveBuffer.putShort(bitsPerSample);//2   BitsPerSample    8 bits = 8, 16 bits = 16, etc.
        //switch to big endian again
        waveBuffer.order(ByteOrder.BIG_ENDIAN);
        //Subchunk2ID      Contains the letters "data"
        byte[] asciidata = {0x64, 0x61, 0x74, 0x61};
        waveBuffer.put(asciidata);
        //switch back to little Endian
        waveBuffer.order(ByteOrder.LITTLE_ENDIAN);
        waveBuffer.putInt(subChunk2Size);
        // ADD PCM DATA TO HEADER
        waveBuffer.put(pcmData);
        //convert waveBuffer to byte[]
        byte[] completeWave = waveBuffer.array();
        return completeWave;
    }

    /**
     * Creates a smooth volume effect to a wave to prevent pops on play back.
     *
     * @param pcmData Byte Array of PCM data (16-bit only!)
     * @param fadeTime Fade Time is seconds
     * @param sampleRate Sample rate of PCM data Byte Array
     */
    private static void createHannWindow(byte[] pcmData, float fadeTime, int sampleRate) {

        ByteBuffer bb = ByteBuffer.wrap(pcmData);

        bb.asShortBuffer();
        bb.order(ByteOrder.LITTLE_ENDIAN);

        // Calculate duration, in samples, of fade time
        double numFadeSamples = fadeTime * sampleRate;
        short sliceValue;

        for (int s = 0; s < numFadeSamples; s++) {
            // Calculate weight based on Hann 'raised cosine' window
            float weight = 0.5f * ((float) ((1 - ((float) Math.cos((float) Math.PI * (float) s / (float) (numFadeSamples))))));
            //Fade In
            sliceValue = bb.getShort(s * 2);
            bb.putShort((s * 2), (short) (weight * sliceValue));                       // Fade In
            //Fade Out
            sliceValue = bb.getShort((pcmData.length - 2 - (2 * s)));
            bb.putShort((pcmData.length - (s * 2) - 2), (short) (sliceValue * weight));  // Fade Out

        }

        //This code is in C for a hann window
        //http://www.labbookpages.co.uk/audio/wavGenFunc.html#tone
    }

    /**
     * Creates a sine wave PCM byte array
     *
     * @param freq Desired frequency in hertz
     * @param volume_percent percent of desired volume 0 to 100
     * @param duration_ms Duration of wave in milliseconds
     * @param sampleRate Sample rate of PCM Data
     * @return
     */
    private static byte[] createSinePCM(short freq, short duration_ms, short volume_percent, int sampleRate) //@TODO all these parameters should be changed to doubles.
    {

       // double maxAmplitude_16bit = 32767;
        short waveAmptude_16bit = 28800; // test value of volume! 0 to 32767 value
        double calculate;
        double calcSlices = (double) duration_ms / 1000D * (double) sampleRate;
        int numberSlices = (int) calcSlices;
        //set up ByteBuffer
        ByteBuffer bb = ByteBuffer.allocate((numberSlices * 2));
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.asShortBuffer();
        bb.position(0);
        //Loop for sine wave
        for (int step = 0; step < numberSlices; step++) {
            calculate = (Math.sin(freq * Math.PI * 2 * step / sampleRate) * waveAmptude_16bit);
            bb.putShort((short) (calculate));
        }
        byte[] bytePCMsine = bb.array();

        return bytePCMsine;

    }

    /**
     * Creates a wave format of silence for specified duration and sample rate
     *
     * @param duration_ms
     * @param sampleRate
     * @return An Output Stream is returned
     */
    private static byte[] createSilencePCM(double duration_ms, double sampleRate) {

        ByteBuffer bb = ByteBuffer.allocate((int) (duration_ms / 1000 * sampleRate) * 2);
        bb.asShortBuffer();
        bb.position(0);

        for (int slice = 0; slice < ((int) (duration_ms / 1000 * sampleRate)) - 2; slice++) {
            bb.putShort((short) 0);
        }
        byte[] bytePCMsilence;
        bytePCMsilence = bb.array();

        return bytePCMsilence;
    }

    private void calculateSpacing(int wpm) {
        //routine to calc WPM dit and dah lengths
        this.dit_length = 1200 / wpm;
        this.dah_length = this.dit_length * 3;
        this.interElementSpacing = this.dit_length;
        this.interWordSpacing = (this.dit_length * 6);
        this.interCharacterSpacing_normal = (this.dah_length - this.dit_length);
        this.interCharacterSpacing_farnsworth = ((1200 / this.farnsworthWPM) * 7) - this.dit_length;
    }

    private void MorseElements(int wordsPerMinute, int farnsWPM, boolean boolSpacing, int freqHz) {

//@TODO possibly make these multithreaded in the future!
        calculateSpacing(mWordsPerMinute);
        constructionOnePCM = createSinePCM((short) mfreqInHz, (short) dit_length, (short) 0, sample_rate);
        createHannWindow(constructionOnePCM, 0.004F, sample_rate);
        constructionTwoPCM = createSilencePCM(interElementSpacing, sample_rate);
        ditElementPCM = combineByteArray(constructionOnePCM, constructionTwoPCM);

        constructionOnePCM = createSinePCM((short) mfreqInHz, (short) dah_length, (short) 0, sample_rate);

        createHannWindow(constructionOnePCM, 0.004F, sample_rate);

        constructionTwoPCM = createSilencePCM(interElementSpacing, sample_rate);
        dahElementPCM = combineByteArray(constructionOnePCM, constructionTwoPCM);

        interCharacterPCM = createSilencePCM(interCharacterSpacing_normal, sample_rate);

        interCharacterFarnsworthPCM = createSilencePCM(interCharacterSpacing_farnsworth, sample_rate);

        interWordSpacingPCM = createSilencePCM(interWordSpacing, sample_rate);

        //remeber to add code to dispose of unused byte arrays.
        System.gc();

    }

    private byte[] byteWaveMorse(MorseContainer mc) {

        ByteArrayOutputStream bbout = new ByteArrayOutputStream();
        String playString = mc.stringToPlay;
        char charToPlay;
        int sLength = playString.length();
        byte[] playWave = new byte[32];

        for (int step = 0; step < sLength; step++) {
            charToPlay = playString.charAt(step);
            if (charToPlay == ' ') {
                bbout.write(combineByteArray(playWave, interWordSpacingPCM), 0, interWordSpacingPCM.length);

            } else {
                if (farnsworthSpacing) {

                    bbout.write(combineByteArray(playWave, interCharacterFarnsworthPCM), 0, interCharacterFarnsworthPCM.length);
                } else {
                    try {
                        bbout.write(combineByteArray(playWave, interCharacterPCM));
                    } catch (IOException e) {
                        System.err.println("e");
                    }
                }
            }
            char toLower = Character.toLowerCase(charToPlay);
            String morseString = morseDictionary.get(toLower);

            for (int x = 0; x < (morseString.length()); x++) {
                char ditOrDah = morseString.charAt(x);
                if (ditOrDah == '.') {
                    try {

                        bbout.write(combineByteArray(playWave, ditElementPCM));
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }
                
                
                if (ditOrDah == '-') {
                    try {
                        bbout.write(combineByteArray(playWave, dahElementPCM));
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }
            }
        }

        playWave = bbout.toByteArray();
        byte[] header = createWaveHeaderForPcm(playWave, 16000, (short) 16);
        mc.waveByteArray = header;
        return header;
    }
    
    public static void main(String[] args) {
       // AndroidMorse morse = new AndroidMorse(18, true, 13, "poop test");
        //AndroidMorse tester = new AndroidMorse(12,"test string");
        //byte tested [] = tester.morseWaveByteArray;
    }
}
