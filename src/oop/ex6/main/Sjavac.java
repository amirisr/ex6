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
        String file = args[0];
        String[] lines = null;
        boolean success = true;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
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
        catch (FileNotFoundException error)
        {
            success = false;
        }
        catch (IOException exp)
        {
            success = false;
        }
        if (success) {
            GlobalScope global = new GlobalScope(lines);
            try  {
                global.testScope();
            } catch (CompileException cmp) {
            }

        }
    }
}
