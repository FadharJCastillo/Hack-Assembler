// Author: Fadhar J. Castillo
// Hack Assembler: Symbol Table Module
import java.util.HashMap;

public class SymbolTable {
	//Store the address as the String of binary digits
	private HashMap<String, String> symbolTable;
	final int MAX_BINARY_DIGIT_SIZE = 15;
	SymbolTable()
	{
		symbolTable = new HashMap<String, String>();
		symbolTable.put("SP","000000000000000");
		symbolTable.put("LCL","000000000000001");
		symbolTable.put("ARG","000000000000010");
		symbolTable.put("THIS","000000000000011");
		symbolTable.put("THAT","000000000000100");
		symbolTable.put("R0","000000000000000");
		symbolTable.put("R1","000000000000001");
		symbolTable.put("R2","000000000000010");
		symbolTable.put("R3","000000000000011");
		symbolTable.put("R4","000000000000100");
		symbolTable.put("R5","000000000000101");
		symbolTable.put("R6","000000000000110");
		symbolTable.put("R7","000000000000111");
		symbolTable.put("R8","000000000001000");
		symbolTable.put("R9","000000000001001");
		symbolTable.put("R10","000000000001010");
		symbolTable.put("R11","000000000001011");
		symbolTable.put("R12","000000000001100");
		symbolTable.put("R13","000000000001101");
		symbolTable.put("R14","000000000001110");
		symbolTable.put("R15","000000000001111");
		symbolTable.put("SCREEN", "100000000000000");
		symbolTable.put("KBD", "110000000000000");
	}
	public void addEntry(String symbol, int address)
	{
		symbolTable.put(symbol, integerAddressToBinary(address));
	}
	public boolean contains(String symbol)
	{
		return symbolTable.containsKey(symbol);
	}
	public String getAddress(String symbol)
	{
		return symbolTable.get(symbol);
	}
	private String integerAddressToBinary(int address)
	{
		String binaryRep = Integer.toBinaryString(address);
		for(int i = binaryRep.length(); i < MAX_BINARY_DIGIT_SIZE; i++)
		{binaryRep = "0"+binaryRep;}
		return binaryRep;
	}
}
