package lt.tomas.bareikis;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World");

        DataStream inputStream = new DataStream();

        inputStream.appendToStreamEnd(new Bit(0));
        inputStream.appendToStreamEnd(new Bit(0));
        inputStream.appendToStreamEnd(new Bit(1));
        inputStream.appendToStreamEnd(new Bit(0));
        inputStream.appendToStreamEnd(new Bit(0));
        inputStream.appendToStreamEnd(new Bit(0));
        inputStream.appendToStreamEnd(new Bit(0));

        System.out.println(inputStream);

        Encoder encoder = new Encoder();
        DataStream result = encoder.encode(inputStream);

        System.out.println(result);


    }
}
