import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.CREATE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class DataSaver {
    public static void main(String[] args) {
        // ----- INITIALIZATIONS ------------
        Scanner scan = new Scanner(System.in);
        boolean cont = false; // used to determine if user wants to continue adding things
        ArrayList<String> records = new ArrayList<>();

        // -------- MAIN PROGRAM ------------
        try {
            // OUTER WHILE LOOP - continues as long as user still wants to input data
            do {
                // ------- GET + ADD INFO TO ARRAYLIST -------
                // Format should be: FName, LName, ID (six digits), Email, Year of Birth (four digits)
                String fName = SafeInput.getNonZeroLenString(scan, "Enter your first name");
                String lName = SafeInput.getNonZeroLenString(scan, "Enter your last name");
                String id = SafeInput.getRegExString(scan, "Enter your ID #", "^[0-9]{6,6}$");
                String email = SafeInput.getRegExString(scan, "Enter your email", "[a-zA-Z0-9]+@[a-zA-Z0-9.]+");
                // Must be four-digit integer. People born in really early years or later years (ex.) 1000 or anything past 2024) wouldn't be alive, but 1000-9999 is the range so that all four digit integers can be included
                int year = SafeInput.getRangedInt(scan, "Enter your year of birth", 1000, 9999);
                scan.nextLine(); // Clear the scanner so it doesn't mess up during the YN confirmation
                // Adds all items to arrayList and makes it into a String[] so that it can be read as one line instead of inputting data for one person on multiple lines
                // the .replace gets rid of the [ and ] at the start/end of the list -- otherwise the first and last values in the .csv file would have those attached (ex.) "[fName" instead of "fName", "year]" instead of "year")
                records.add(Arrays.toString(new String[]{fName, lName, id, email, String.valueOf(year)}).replace("[", "").replace("]", ""));
                cont = SafeInput.getYNConfirm(scan, "Do you want to continue? [Y/N]");
            }
            while (cont); // while continue is true, keep going through the loop

            // ----- PROMPT FOR FILE TO WRITE TO -----
            Path target = new File(System.getProperty("user.dir")).toPath(); // look through user directory
            String path = SafeInput.getNonZeroLenString(scan,"What is the name of the file you want to write to? [Give the name of the file you want to create if not already made] ");
            target = target.resolve("src\\" + path + ".csv"); // create path to src\\[filename].csv
            System.out.println("Path is " + target); // confirms path
            // (CREATE) means that if file does not already exist, create it
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(target, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));


            for (String record : records) { // for each record within records
                writer.write(record, 0, record.length()); // write the record with its proper length
                writer.newLine(); // add new line after each record
            }
            writer.close(); // close the writer when finished
        }
        catch(IOException e){ // catch if something goes wrong
            e.printStackTrace();
        }

    }
}
