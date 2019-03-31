// Author: Fadhar J. Castillo
// Hack Assembler implemented in Java according to
// The Elements of Computing Systems Specifications

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class AssemblerDriver {
	
	public static void main(String[] args) {
		
		final int MAX_BINARY_DIGIT_SIZE = 15;
		int nextAvailableRAMAddress = 16;
		String filePath;
		if(args.length == 1)
			filePath = args[0];
		else filePath = "C:\\Users\\thedr_000\\Downloads\\nand2tetris\\nand2tetris\\projects\\06\\rect\\Rect.asm";
		Parser parser = new Parser(filePath);
		Code code = new Code();
		SymbolTable symbolTable = new SymbolTable();
	
		//Buid the symbol table
		ArrayList<String> assemblyCommands = parser.getRawAssemblyCommands();
		int count = 0;
		for(int i = 0; i < assemblyCommands.size(); i++) {
			String command = assemblyCommands.get(i);
			if(command.contains("(") && command.contains(")"))
			{
				int start = command.indexOf("(")+1;
				int end = command.indexOf(")");
				symbolTable.addEntry(command.substring(start, end), count);
				continue;
			}
			count++;
		}
		
		BufferedWriter writer = null;
		try {
			File fileToWrite = new File(filePath.replaceAll(".asm", ".hack"));
			writer = new BufferedWriter(new FileWriter(fileToWrite));
		} catch (Exception e) {
			System.out.println("Unable to create output file");
			System.exit(0);
		}
		// Translate assembly commands to machine language
		while(parser.hasMoreCommands())
		{
			parser.advance();
			Parser.COMMAND_TYPE command = parser.commandType();
			String machineCode = "";
			if(command==Parser.COMMAND_TYPE.C_COMMAND)
			{
				machineCode+= "111";
				machineCode+= code.comp(parser.comp());
				machineCode+= code.dest(parser.dest());
				machineCode+= code.jump(parser.jump());
			}
			else if(command.equals(Parser.COMMAND_TYPE.A_COMMAND))
			{
				machineCode+= "0";
				String symbol = parser.symbol();
				//Check the symbol table, first, less performance hit
				// otherwise check if its a constant value
				if(symbolTable.contains(symbol))
				{machineCode+= symbolTable.getAddress(symbol);}
				else
				{
					try
					{
						int constant = Integer.parseInt(symbol);
						String binaryRep = Integer.toBinaryString(constant);
						if(constant >= 0 && binaryRep.length() <= MAX_BINARY_DIGIT_SIZE)
						{
							for(int i = binaryRep.length(); i < MAX_BINARY_DIGIT_SIZE; i++)
							{binaryRep = "0"+binaryRep;}
							machineCode+=binaryRep;
						}
						else
						{
							System.out.println(symbol +" cannot be negative value.");
							System.exit(0);
						}
					}catch(NumberFormatException nfe)
					{
						//If the program gets to this point, the symbol must be a user-defined variable.
						//Add it to the symbol table and concatenate the binary address
						symbolTable.addEntry(symbol, nextAvailableRAMAddress);
						machineCode+=symbolTable.getAddress(symbol);
						nextAvailableRAMAddress++;
					}
				}
			}
			else
			{
				//The command must be an L_COMMAND, ignore it
				continue;
			}
			//Write the machine instruction to file
			try {
				writer.write(machineCode + "\n");
			} catch (IOException e) {
				System.out.println("Unable to write instruction");
			}
		}
		//Close the file writer when Assembling is finished
		try {
		    writer.close();
		} catch (Exception e) {
		}
	}
}
