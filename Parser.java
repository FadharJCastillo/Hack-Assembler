// Fadhar J. Castillo
// Hack Asssembler: Parser Module
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	public enum COMMAND_TYPE {A_COMMAND, C_COMMAND, L_COMMAND};
	private String filePath, instruction, symbol, dest, comp, jump;
	private COMMAND_TYPE instructionCommandType;
	private int instructionLineNumber;
	private ArrayList<String> assemblyCode;
	Parser(String filePath)
	{
		this.filePath=filePath;
		assemblyCode = new ArrayList<String>();
		try 
		{
			FileReader reader = new FileReader(filePath);
			BufferedReader buffReader = new BufferedReader(reader);
			String line;
			while((line = buffReader.readLine()) != null)
			{
				if(line.equals(""))
					continue;
				else
				{
					String lineNoWhiteSpace = line.replaceAll("\\s", "");
					if(lineNoWhiteSpace.contains("//"))
					{
						int indexOfComment = lineNoWhiteSpace.indexOf("//");
						if(indexOfComment == 0) continue;
						else assemblyCode.add(lineNoWhiteSpace.substring(0,indexOfComment));
					}
					else assemblyCode.add(lineNoWhiteSpace);
				}
			}
			instructionLineNumber = 0;
			buffReader.close();
			reader.close();
		} catch(FileNotFoundException e){
			System.out.println("File Not Found.");
		} catch (IOException e) {
			System.out.println("Error Reading File.");
		}
	}
	public boolean hasMoreCommands()
	{
		return (instructionLineNumber < assemblyCode.size());
	}
	public void advance()
	{
		instruction = assemblyCode.get(instructionLineNumber);
		instructionLineNumber++;
	}
	public COMMAND_TYPE commandType()
	{
		if(instruction != null || instruction.equals(""))
		{
			if(instruction.contains("@"))
			{
				instructionCommandType = COMMAND_TYPE.A_COMMAND;
				return COMMAND_TYPE.A_COMMAND;
			}
			else if(instruction.contains("(") || instruction.contains(")"))
			{
				instructionCommandType = COMMAND_TYPE.L_COMMAND;
				return COMMAND_TYPE.L_COMMAND;
			}
			else if(instruction.contains("=") || instruction.contains(";"))
			{
				instructionCommandType = COMMAND_TYPE.C_COMMAND;
				return COMMAND_TYPE.C_COMMAND;
			}
		}
		return COMMAND_TYPE.A_COMMAND;
	}
	public String symbol()
	{
		if(instructionCommandType == COMMAND_TYPE.A_COMMAND) {
			int start = instruction.indexOf("@")+1;
			int end = 0;
			boolean endOfString = false;
			for(int i = start; i < instruction.length(); i++)
			{
				char charAtI = instruction.charAt(i);
				if((charAtI != '.')&&(charAtI != '$')&&(charAtI != ':')&&(charAtI!='_')
						&&(!(charAtI >= 'a') && !(charAtI <= 'z'))&&(!(charAtI >= 'A') && !(charAtI <= 'Z'))
						&&(!(charAtI >= '0') && !(charAtI <= '9')))
				{end = i;}
				else if(i==instruction.length()-1)
				{endOfString = true;}
			}
			if(endOfString) {
			symbol = instruction.substring(start);}
			else
			{symbol = instruction.substring(start, end);}
			return symbol;
		}
		else if(instructionCommandType == COMMAND_TYPE.L_COMMAND)
		{
			int start = instruction.indexOf("(")+1;
			int end = instruction.indexOf(")");
			symbol = instruction.substring(start, end);
			return symbol;
		}
		else
			return "";
	}
	public String dest()
	{
		if(instructionCommandType == COMMAND_TYPE.C_COMMAND)
		{
			if(instruction.contains("="))
			{
				int end = instruction.indexOf("=");
				dest = instruction.substring(0, end);
				return dest;
			}
			else if(instruction.contains(";"))
			{
				dest = "null";
				return dest;
			}
		}
		return null;
	}
	public String comp()
	{
		if(instructionCommandType == COMMAND_TYPE.C_COMMAND)
		{
			if(instruction.contains("="))
				comp = instruction.substring(instruction.indexOf("=")+1);
			else if(instruction.contains(";"))
				comp = instruction.substring(0,instruction.indexOf(";"));
			return comp;
		}
			return null;
	}
	public String jump()
	{
		if(instructionCommandType == COMMAND_TYPE.C_COMMAND)
		{
			if(instruction.contains(";")) {
				jump = instruction.substring(instruction.indexOf(";")+1);
				return jump;
			}
			else if(instruction.contains("=")) 
				{jump = "null"; return jump;}
		}
		return null;
	}
	public ArrayList<String> getRawAssemblyCommands()
	{
		return new ArrayList<String>(assemblyCode);
	}
}
