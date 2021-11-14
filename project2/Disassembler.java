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
        ArrayList<String> instructions = parseBinary(args[0]);
        disassemble(instructions);
    }

    public static void disassemble(ArrayList<String> instructions)
    {
        String output = "";
        for(int i = 0; i < instructions.size(); i++)
        {
            String tmp = instructions.get(i);
            String sixBitOp = tmp.substring(0,6);
            String eightBitOp = tmp.substring(0,8);
            String tenBitOp = tmp.substring(0,10);
            String elvnBitOp = tmp.substring(0,11);
            //System.out.println(sixBitOp);
            //System.out.println(eightBitOp);
            //System.out.println(tenBitOp);
            //System.out.println(tmp);
            //System.out.println(elvnBitOp);

            switch (sixBitOp)  //B type instructions
            {
                case "000101":
                    //find branch number somehow
                    output = "B " /* + branch*/;
                    break;
                default:
                    break;
            }


            switch (eightBitOp)  //CB type instructions
            {

                default:
                    break;
            }


            switch (tenBitOp)  //I type instructions
            {

                default:
                    break;
            }


            int rdReg = Integer.parseInt(tmp.substring(27), 2); //0-4
            int rnReg = Integer.parseInt(tmp.substring(22,27), 2); //5-9
            int rmReg = Integer.parseInt(tmp.substring(12,17), 2); //16-20
            int shamt = Integer.parseInt(tmp.substring(17,22), 2); //10-15
            int dtAddr = Integer.parseInt(tmp.substring(12,22), 2); //for D types
            switch (elvnBitOp)  //R and D type instructions
            {
                case "10001011000":
                    output = "ADD " + "X" + rdReg + ", X" + rnReg + ", X" + rmReg;
                    break;
                case "10001010000":
                    output = "AND " + "X" + rdReg + ", X" + rnReg + ", X" + rmReg;
                    break;
                case "11001010000":
                    output = "EOR " + "X" + rdReg + ", X" + rnReg + ", X" + rmReg;
                    break;
                case "11001011000":
                    output = "SUB " + "X" + rdReg + ", X" + rnReg + ", X" + rmReg;
                    break;
                case "11111000010":
                    output = "LDUR " + "X" + rdReg + ", [X" + rnReg + ", #" + dtAddr + "]";
                    break;
                case "11111000000":
                    output = "STUR " + "X" + rdReg + ", [X" + rnReg + ", #" + dtAddr + "]";
                    break;
                case "11010011011":
                    output = "LSL " + "X" + rdReg + ", X" + rnReg + ", #" + shamt;
                    break;
                case "11010011010":
                    output = "LSR " + "X" + rdReg + ", X" + rnReg + ", #" + shamt;
                    break;
                case "10101010000":
                    output = "ORR " + "X" + rdReg + ", X" + rnReg + ", X" + rmReg;
                    break;
                case "11101011000":
                    output = "SUBS " + "X" + rdReg + ", X" + rnReg + ", X" + rmReg;
                    break;
                case "11010110000": //not sure what to do but "The branch target is encoded in the Rn field."
                    output = "BR " + "X" + rdReg + ", X" + rnReg + ", X" + rmReg;
                    break;
                default:
                    break;
            }

            System.out.println(output);
            output = "";
        }

        //have switch cases for each length of opcode
        //compare the opcodes to find right instruction then parse string to get registers and numbers
        //print instruction
        //repeat until end of file
    }

    public static ArrayList<String> parseBinary(String inputFile)
    {
        ArrayList<String> instructions = new ArrayList<String>();
        int ch;
        try
        {
             FileInputStream inputStream = new FileInputStream(new File(inputFile));
             int count = 0;
             String line = "";
            while((ch = inputStream.read()) != -1)
            {
                //does some magic to convert from byte to binary string
                String tmp = String.format("%8s", Integer.toBinaryString(ch & 0xFF)).replace(' ', '0');
                line += tmp;
                if(count == 3)
                {
                    instructions.add(line); //add completed 32 bit instruction
                    line = "";
                    //line += tmp; //start new instruction
                    count = 0;
                }
                else
                {
                    count++;
                }
            }

            inputStream.close();

//            String tmp = "";
//            for(int i = 0; i < temp.size(); i += 4)
//            {
//                //not very graceful but it works
//                for(int j = 0; j < 4; j++)
//                {
//                    tmp += temp.get(i);
//                }
//                    instructions.add(tmp);
//                    tmp = "";
//            }
//
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
