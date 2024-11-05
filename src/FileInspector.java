import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileInspector {
    public static void main(String[] args) {

        // --------- INITIALIZATIONS ------------
        int lineCount = 0; // use for counting lines
        int wordCount = 0; // use for counting words
        int charCount = 0; // use for counting characters
        JFileChooser chooser = new JFileChooser(); // create JFileChooser
        Path readFile = new File(System.getProperty("user.dir")).toPath(); // look through user directory
        readFile = readFile.resolve("src"); // look through java source, not looking at a specific file
        chooser.setCurrentDirectory(readFile.toFile());

        // ------- MAIN PROGRAM --------
        try{
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { // if the user has chosen a file, then do the following
                readFile = chooser.getSelectedFile().toPath();
                // (CREATE) means that if file does not already exist, create it
                InputStream in = new BufferedInputStream(Files.newInputStream(readFile, CREATE));
                // what we use to actually read the file, but it needs an input stream before reading (grabs file location)
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                // read line by line
                while (reader.ready()) {
                    String line = reader.readLine(); // each line is a string -- do this so that it's easy to split the line for word counting
                    System.out.println(line); // output each line to the user
                    charCount+=line.length(); // count the length of each line and add to charCount - this includes white spaces on lines, so "Hi there!" is nine characters, not eight
                    lineCount +=1; // increments by one each time a new line occurs
                    String[] words = line.split(" "); // split each time a space happens
                    for (String word : words){ // for each piece in the words array...
                        wordCount+=1; //... add one
                    }
                }
                reader.close(); // Closes the reader once finished

                // ------------- OUTPUT INFO -------------------
                System.out.println("Your file is " + lineCount + " line(s).");
                System.out.println("Your file has " + wordCount + " word(s).");
                System.out.println("Your file has " + charCount + " character(s).");
            }
            else{ // user didn't pick a file, close the chooser
                System.out.println("You must select a file.  Terminating...");
                System.exit(0);
            }
        }
        catch(IOException e) { // catch if something goes wrong
            e.printStackTrace();
        }
    }
}
