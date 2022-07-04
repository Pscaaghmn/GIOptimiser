import java.io.*;

public class FileHandler {

    public static String fileRead(String fileName, int start, int length) {
        //gets String from point

        try (RandomAccessFile rf = new RandomAccessFile(fileName, "rws")) {
            rf.seek(start);

            if (length == 0) {
                //Whole line
                String read = rf.readLine();
                return read;
            } else {
                //Specific length, using a string builder to omit carriage returns
                StringBuilder output = new StringBuilder();

                for (int i = 0; i < length; i++) {
                    char nextC = (char) rf.read();

                    if (Character.toString(nextC).equals("\r")) {
                        i++;
                    } else {
                        output.append(nextC);
                    }
                }

                System.out.print(output);
                return output.toString();
            }
        } catch (IOException e) {
            System.out.println("Random read error!");
            return null;
        }
    }

    public static void fileWrite(String fileName, int start, String text) {
        //writes string from point

        try (RandomAccessFile rf = new RandomAccessFile(fileName, "rws")) {
            rf.seek(start == -1 ? FileHandler.getFileLength(fileName) : start);
            for (int i = 0; i < text.length(); i++) {
                rf.write(text.charAt(i));
            }

        } catch (IOException e) {
            System.out.println("Random write error!");
        }
    }

    public static int getFileLength(String fileName) {
        //Returns length of a file

        try (RandomAccessFile rf = new RandomAccessFile(fileName, "rws")) {
            return (int)rf.length();
        } catch (IOException e) {
            System.out.println("Random length error!");
            return 0;
        }
    }

}