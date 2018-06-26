package oop.ex6.main;

import oop.ex6.scopes.*;
import java.io.*;
import java.util.*;

/**
 * The main class of this exercise - Contains the main method.
 * @author Amir Israeli
 * @author Omer Binyamin.
 */
public class Sjavac {

    /**
     * The main method.
     * @param args arguments for this program.
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            String file = args[0];
            String[] lines = null;
            boolean success = true;
            try (BufferedReader br = new BufferedReader(new FileReader(file)))
            {
                ArrayList<String> tmp = new ArrayList<>();
                String lineTemp;
                lineTemp = br.readLine();
                while (lineTemp != null) {
                    tmp.add(lineTemp);
                    lineTemp = br.readLine();
                }
                lines = new String[tmp.size()];
                lines = tmp.toArray(lines);
            }
            catch (FileNotFoundException error) {
                success = false;
            }
            catch (IOException exp) {
                success = false;
            }
            if (success) {
                GlobalScope global = new GlobalScope(lines);
                try {
                    global.testScope();
                }
                catch (CompileException cmp) {
                    success = false;
                    System.out.println(cmp.getMessage());
                }
                if (success) {
                    System.out.println(0);
                }
                else {
                    System.out.println(1);
                }
            }
            else
            {
                System.out.println(2);
            }
        }
        else
        {
            System.out.println(2);
        }
    }
}
