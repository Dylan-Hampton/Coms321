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
        ArrayList<String> instructions = new ArrayList<String>();
        instructions = parseBinary(args[0]);
        disassemble(instructions);
    }

    public static void disassemble(ArrayList<String> instructions)
    {


        //have switch cases for each length of opcode
        //compare the opcodes to find right instruction then parse string to get registers and numbers
        //print instruction
        //repeat until end of file
    }

    public static ArrayList<String> parseBinary(String inputFile)
    {
        ArrayList<String> temp = new ArrayList<String>();
        ArrayList<String> instructions = new ArrayList<String>();
        int ch;
        try
        {
             FileInputStream inputStream = new FileInputStream(new File(inputFile));

            while((ch = inputStream.read()) != -1)
            {
                String tmp = String.format("%8s", Integer.toBinaryString(ch & 0xFF)).replace(' ', '0');
                temp.add(tmp);
            }

            inputStream.close();

            String tmp = "";
            for(int i = 0; i < temp.size(); i += 4)
            {
                //not very graceful but it works
                for(int j = 0; j < 4; j++)
                {
                    tmp += temp.get(i);
                }
                    instructions.add(tmp);
                    tmp = "";
            }

//            for(int j = 0; j < instructions.size(); j++)
//            {
//                System.out.println(instructions.get(j));
//            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return instructions;
    }

}
