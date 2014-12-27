/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morseplayer;

/**MorseDictionary
 * The MorseDictionary class is used to decode single characters into dits and dahs
 * of morse code.  
 * 
 * The character is the key and the returned string is the dit, dah sequence.
 *
 * @author bill
 */
public class MorseDictionary {
//create map for character to morse code conversion
//Also create map class for different levels of character learning. level 1 5etar level 2 sln04 etc..
//whatever combos are according to TSART book.
//Map <String, String > decode_key = new HashMap<String, String>();
//copy dictionary from c++ project!

    public java.util.HashMap<Character, String> morseDictionary = new java.util.HashMap<>();

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
        morseDictionary.put('(', "--..--");
        morseDictionary.put(')', ".........");
        morseDictionary.put(' ', " ");
        morseDictionary.put('@', ".--.-.");
        morseDictionary.put('/', "-..-.");
//finish fixing dictionary
//add that one command to convert it to unchangeable.....whatever it was called...
    }
}
