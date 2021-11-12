import java.io.*;
import java.util.ArrayList;

public class Disassembler {

    public static void main(String args[])
    {
        if(args.length < 1)
        {
            System.out.println("Please provide input file");
            System.exit(0);
        }
        parseBinary(args[0]);
    }

    public static void disassemble()
    {

        //have switch cases for each length of opcode
        //compare the opcodes to find right instruction then parse string to get registers and numbers
        //print instruction
        //repeat until end of file
    }

    public static String[] parseBinary(String inputFile)
    {
        ArrayList<String> instructions = new ArrayList<String>();
        int ch;
        try
        {
             FileInputStream inputStream = new FileInputStream(new File(inputFile));

            while((ch = inputStream.read()) != -1)
            {
                System.out.println((char) ch);
            }

            inputStream.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

}
