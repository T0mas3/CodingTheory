package lt.tomas.bareikis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInputHandler {

    public String readInputAsString() {

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String input = null;

        try {
            input = bufferedReader.readLine();
        } catch (IOException ex) {
            System.out.println("IO error");
        }

        return input;
    }

    public int readInputAsInt() throws InputMismatchException {

        Scanner scanner = new Scanner(System.in);
        int inputInteger = scanner.nextInt();

        return inputInteger;
    }

    public float readInputAsFloat() throws InputMismatchException, ParseException {

        String inputString = readInputAsString();
        // Pakeičiame kablelius taškais, kad išvengtume klaidų verčiant į slankaus kablelio skaičių
        inputString = inputString.replace(",", ".");

        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        // Slankaus kablelio skaičiaus sveikoji dalis nuo trupmeninės bus atskiriama tašku
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        Number parsedNumber = decimalFormat.parse(inputString);

        return parsedNumber.floatValue();
    }
}
