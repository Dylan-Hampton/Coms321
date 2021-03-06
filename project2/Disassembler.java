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

            //For R and D type instructions
            int rdReg = Integer.parseInt(tmp.substring(27), 2); //0-4
            int rnReg = Integer.parseInt(tmp.substring(22,27), 2); //5-9
            int rmReg = Integer.parseInt(tmp.substring(10,16), 2); //16-20
            int shamt = Integer.parseInt(tmp.substring(16,22), 2); //10-15
            int dtAddr = Integer.parseInt(tmp.substring(10,20), 2); //for D types, 20-12

            //For I type instructions
            int aluImmediate;//10-21
            if(tmp.charAt(10) == '1') //if first bit of immediate is on, find twos comp and negate
            {
                aluImmediate = Integer.parseInt(findTwoscomplement(new StringBuffer(tmp.substring(11,22))), 2);
                aluImmediate *= -1;
            }
            else
            {
                aluImmediate = Integer.parseInt(tmp.substring(11,22), 2);
            }

            //For B type instructions
            int brAddress;
            if(tmp.charAt(6) == '1')
            {
               brAddress = Integer.parseInt(findTwoscomplement(new StringBuffer(tmp.substring(7))), 2);
               brAddress *= -1;
            }
            else
            {
               brAddress = Integer.parseInt(tmp.substring(7), 2);
            }

            //for CB type instructions
            int condBrAddress;
            if(tmp.charAt(8) == '1')
            {
                condBrAddress = Integer.parseInt(findTwoscomplement(new StringBuffer(tmp.substring(9, 27))), 2);
                condBrAddress *= -1;
            }
            else
            {
                condBrAddress = Integer.parseInt(tmp.substring(9, 27), 2);
            }


            switch (sixBitOp)  //B type instructions
            {
                case "000101":
                    output = "B " + "Label" + (i+brAddress);
                    break;
                case "100101":
                    output = "BL " + "Label" + (i+brAddress);
                    break;
                default:
                    break;
            }


            switch (eightBitOp)  //CB type instructions
            {
                case "01010100":
                    switch(rdReg) //rt is located in same place as rd
                    {
                        case 0:
                            output = "B.EQ " + "Label" + (i+condBrAddress);
                            break;
                        case 1:
                            output = "B.NE " + "Label" + (i+condBrAddress);
                            break;
                        case 2:
                            output = "B.HS " + "Label" + (i+condBrAddress);
                            break;
                        case 3:
                            output = "B.LO " + "Label" + (i+condBrAddress);
                            break;
                        case 4:
                            output = "B.MI " + "Label" + (i+condBrAddress);
                            break;
                        case 5:
                            output = "B.PL " + "Label" + (i+condBrAddress);
                            break;
                        case 6:
                            output = "B.VS " + "Label" + (i+condBrAddress);
                            break;
                        case 7:
                            output = "B.VC " + "Label" + (i+condBrAddress);
                            break;
                        case 8:
                            output = "B.HI " + "Label" + (i+condBrAddress);
                            break;
                        case 9:
                            output = "B.LS " + "Label" + (i+condBrAddress);
                            break;
                        case 10:
                            output = "B.GE " + "Label" + (i+condBrAddress);
                            break;
                        case 11:
                            output = "B.LT " + "Label" + (i+condBrAddress);
                            break;
                        case 12:
                            output = "B.GT " + "Label" + (i+condBrAddress);
                            break;
                        case 13:
                            output = "B.LE " + "Label" + (i+condBrAddress);
                            break;
                    }
                    break;
                case "10110100":
                    output = "CBZ X" + rdReg + ", Label" + (i+condBrAddress); //rt == rd
                    break;
                case "10110101":
                    output = "CBNZ X" + rdReg + ", Label" + (i+condBrAddress); //rt == rd
                    break;
                default:
                    break;
            }


            switch (tenBitOp)  //I type instructions
            {
                case "1001000100":
                    output = "ADDI X" + rdReg + ", X" + rnReg + ", #" + aluImmediate;
                    break;
                case "1001001000":
                    output = "ANDI X" + rdReg + ", X" + rnReg + ", #" + aluImmediate;
                    break;
                case "1101000100":
                    output = "SUBI X" + rdReg + ", X" + rnReg + ", #" + aluImmediate;
                    break;
                case "1101001000":
                    output = "EORI X" + rdReg + ", X" + rnReg + ", #" + aluImmediate;
                    break;
                case "1011001000":
                    output = "ORRI X" + rdReg + ", X" + rnReg + ", #" + aluImmediate;
                    break;
                case "1111000100":
                    output = "SUBIS X" + rdReg + ", X" + rnReg + ", #" + aluImmediate;
                    break;
                default:
                    break;
            }


            switch (elvnBitOp)  //R and D type instructions
            {
                case "10001011000":
                    output = "ADD X" + rdReg + ", X" + rnReg + ", X" + rmReg;
                    break;
                case "10001010000":
                    output = "AND X" + rdReg + ", X" + rnReg + ", X" + rmReg;
                    break;
                case "11001010000":
                    output = "EOR X" + rdReg + ", X" + rnReg + ", X" + rmReg;
                    break;
                case "11001011000":
                    output = "SUB X" + rdReg + ", X" + rnReg + ", X" + rmReg;
                    break;
                case "11111000010":
                    output = "LDUR X" + rdReg + ", [X" + rnReg + ", #" + dtAddr + "]";
                    break;
                case "11111000000":
                    output = "STUR X" + rdReg + ", [X" + rnReg + ", #" + dtAddr + "]";
                    break;
                case "11010011011":
                    output = "LSL X" + rdReg + ", X" + rnReg + ", #" + shamt;
                    break;
                case "11010011010":
                    output = "LSR X" + rdReg + ", X" + rnReg + ", #" + shamt;
                    break;
                case "10101010000":
                    output = "ORR X" + rdReg + ", X" + rnReg + ", X" + rmReg;
                    break;
                case "11101011000":
                    output = "SUBS X" + rdReg + ", X" + rnReg + ", X" + rmReg;
                    break;
                case "11010110000":
                    output = "BR " + "X" + rnReg;
                    break;
                case "10011011000":
                    output = "MUL " + "X" + rdReg + ", X" + rnReg + ", X" + rmReg;
                    break;
                case "11111111101":
                    output = "PRNT X" + rdReg;
                    break;
                case "11111111100":
                    output = "PRNL";
                    break;
                case "11111111110":
                    output = "DUMP";
                    break;
                case "11111111111":
                    output = "HALT";
                    break;
                default:
                    break;
            }

            System.out.println("Label"+ (i) +":");
            System.out.println(output);
            output = "";
        }
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
                    count = 0;
                }
                else
                {
                    count++;
                }
            }

            inputStream.close();

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return instructions;
    }

    //This code was borrowed from https://www.geeksforgeeks.org/efficient-method-2s-complement-binary-string/
    static String findTwoscomplement(StringBuffer str)
    {
        int n = str.length();

        // Traverse the string to get first '1' from
        // the last of string
        int i;
        for (i = n-1 ; i >= 0 ; i--)
            if (str.charAt(i) == '1')
                break;

        // If there exists no '1' concat 1 at the
        // starting of string
        if (i == -1)
            return "1" + str;

        // Continue traversal after the position of
        // first '1'
        for (int k = i-1 ; k >= 0; k--)
        {
            //Just flip the values
            if (str.charAt(k) == '1')
                str.replace(k, k+1, "0");
            else
                str.replace(k, k+1, "1");
        }

        // return the modified string
        return str.toString();
    }

}
