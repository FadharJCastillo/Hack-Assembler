// Author: Fadhar J. Castillo
// Hack Asssembler: Parser Module

import java.util.HashMap;
public class Code {
	private HashMap<String, String> hashMap;
	private HashMap<String, String> destHashMap;
	Code()
	{
		destHashMap = new HashMap<String, String>();
		hashMap = new HashMap<String, String>();
		destHashMap.put("null","000");
		destHashMap.put("M","001");
		destHashMap.put("D","010");
		destHashMap.put("MD","011");
		destHashMap.put("A","100");
		destHashMap.put("AM","101");
		destHashMap.put("AD","110");
		destHashMap.put("AMD","111");
		hashMap.put("0", "0101010");
		hashMap.put("1","0111111");
		hashMap.put("-1","0111010");
		hashMap.put("D","0001100");
		hashMap.put("M","1110000");
		hashMap.put("A","0110000");
		hashMap.put("!D","0001101");
		hashMap.put("!M","1110001");
		hashMap.put("!A","0110001");
		hashMap.put("-D","0001111");
		hashMap.put("-M","1110011");
		hashMap.put("-A","0110011");
		hashMap.put("D+1","0011111");
		hashMap.put("M+1","1110111");
		hashMap.put("A+1","0110111");
		hashMap.put("D-1","0001110");
		hashMap.put("M-1","1110010");
		hashMap.put("A-1","0110010");
		hashMap.put("D+M","1000010");
		hashMap.put("D+A","0000010");
		hashMap.put("D-M","1010011");
		hashMap.put("D-A","0010011");
		hashMap.put("M-D","1000111");
		hashMap.put("A-D","0000111");
		hashMap.put("D&M","1000000");
		hashMap.put("D&A","0000000");
		hashMap.put("D|M","1010101");
		hashMap.put("D|A","0010101");
		hashMap.put("null","000");
		hashMap.put("JGT","001");
		hashMap.put("JEQ","010");
		hashMap.put("JGE","011");
		hashMap.put("JLT","100");
		hashMap.put("JNE","101");
		hashMap.put("JLE","110");
		hashMap.put("JMP","111");
		}
	
	public String dest(String mnemonic)
	{
		return destHashMap.get(mnemonic);
	}
	public String comp(String mnemonic)
	{
		return hashMap.get(mnemonic);
	}
	public String jump(String mnemonic)
	{
		return hashMap.get(mnemonic);
	}
}
