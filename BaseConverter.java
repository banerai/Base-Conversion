/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package base.converter;

/**
 *
 * @author Arjun
 */
import java.util.*;
//In this class, I stack methods because it will make diagnosing each
//process of this class easier
//This is a relatively simple and fun project everyone does
public class BaseConverter {
    private Scanner sc;
    private int from, to = 0;
    private String number;
    private StringBuilder strb;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BaseConverter a = new BaseConverter(255, 16, "10");
        System.out.println(a.convertToNumbers()); //prints out "15/15"
        System.out.println(a.convertToUnicode()); //prints out "??"
        //finally to declare the inner stack methods private to prevent tampering
        
        // TODO code application logic here
    }
    
    public BaseConverter(String number) {
        //finding largest digit to guess the base from
        int largestInt = 0;
        for(int i = 0; i < number.length(); i++) {
            if((int) (number.charAt(i)) > largestInt) {
                largestInt = (int) (number.charAt(i));
            }
        }
        //finally declaring the variables
        this.from = largestInt + 1;
        //conversion to the decimal base
        this.to = 10;
        this.number = number;
    }
    
    public BaseConverter (int from, int to, String number) {
        this.from = from;
        this.to =  to;
        this.number = number;
    }
    
    //reverses number to be read from left to right
    private String reverse(String num) {
        String reversed = "";
        for(int i = 0; i < num.length(); i++) {
            reversed = reversed.concat(num.charAt(num.length() - i - 1) + "");
        }
        return reversed;
    }
    
    private int toDecimal() {
        //declaring a pointer to reversed of number
        String num = reverse(number);
        sc = new Scanner(num);
        int storeDigit = 0;
        int decimal = 0;
        for(int i = 0; i < num.length(); i++) {
            storeDigit = Character.getNumericValue(num.charAt(i));
            decimal += (storeDigit * Math.pow(from, i));
        }
        return decimal;
    }
    
    private String reverseByDelimiter(String num, String del) {
        String[] arr = num.split("/");
        strb = new StringBuilder(arr.length);
        String save = "";
        for(int i = 0; i < arr.length / 2; i++) {
            save = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = save;
        }
        //collecting all the ints
        for(int i = 0; i < arr.length; i++){
            strb.append(arr[i] + "/");
        }
        
        return strb.toString();
    }
    
    //the gives you the integers and their places in the new number
    public String convertToNumbers() {
        int decimal = toDecimal();
        String finalNumber = "";
        //this will reverse the digits because it will read from left to right
        while(decimal != 0) {
            //to seperate numbers that are > 1 digits, I use "/"
            //I also used this symbol because dilimeters recognize it
            //shown later
            finalNumber = finalNumber.concat((decimal % to) + "/");
            decimal /= to;
        }
        //this will correct the fencepost
        //you cannot concatinate \b because it will only mask the output
        String fn = "";
        for(int i = 0; i < finalNumber.length() - 1; i++) {
            fn = fn.concat(finalNumber.charAt(i) + "");
        }
        //this will reverse the final number by "/"
        fn = reverseByDelimiter(fn, "/");
        String rfn = "";
        for(int i = 0; i < fn.length() - 1; i++) {
            rfn = rfn.concat(fn.charAt(i) + "");
        }
        return rfn;
    }
    
    //converts the newNumber to unicode
    //this is optional
    public String convertToUnicode() {
        String num = convertToNumbers();
        char c;
        String[] arr = num.split("/");
        strb = new StringBuilder(arr.length);
        for(int i = 0; i < arr.length; i++) {
            c = '0';
            c += (char) ((int) Integer.parseInt(arr[i]));
            strb.append(c);
        }
        return strb.toString();
    }
}
