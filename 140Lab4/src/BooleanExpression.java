import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * @author Raymond
 *
 */
public class BooleanExpression {
	private List<Implicant> implicantList;
	private List<Implicant> dontcareList;
	private List<Long> mintermsNeededToCover;
	private static long tempMSB;
	private static long tempLSB;
	private static int bitCountMSB;
	private static int bitCountLSB;
	private int myNumVars;
	public static final long maxVal = -1;
	public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	
	private void initBooleanExpression(int numVars)
	{
		implicantList = new ArrayList<Implicant>();
		dontcareList = new ArrayList<Implicant>();
		myNumVars = numVars; 
		mintermsNeededToCover = new ArrayList<Long>();
	}
	
	public BooleanExpression(ArrayList<Long> minterms, ArrayList<Long> dontcares, int numVars)
	{
		initBooleanExpression(numVars);
		for (Long minterm : minterms)
		{
			implicantList.add(new Implicant(minterm, numVars));
			mintermsNeededToCover.add(minterm);
		}

		for (Long dontcare : dontcares)
		{
			dontcareList.add(new Implicant(dontcare, numVars));
		}
	}
	
	public List<Implicant> getImplicantList()
	{
		return implicantList;
	}
	
	public boolean differBySingleVariable(Implicant imp1,  Implicant imp2) {
		tempMSB = imp1.getMSB() ^ imp2.getMSB();
		tempLSB = imp1.getLSB() ^ imp2.getMSB();
		bitCountMSB = Long.bitCount(tempMSB);
		bitCountLSB = Long.bitCount(tempLSB);
		return (bitCountMSB == 1 && bitCountLSB == 1 && tempMSB == tempLSB);	
	}
	
	public void doTabulationMethod()
	{
		ArrayList<ArrayList<ArrayList<Implicant>>> tabulationList = new ArrayList<ArrayList<ArrayList<Implicant>>>(myNumVars);
		ArrayList<ArrayList<Implicant>> tempImplicantList = new ArrayList<ArrayList<Implicant>>(myNumVars);
		boolean completed = true;
		boolean prime = false;
		int difference = Long.SIZE - myNumVars;
		int bitCount;
		for (int i = 0; i < implicantList.size(); i++) {
			bitCount = Long.bitCount(implicantList.get(i).getMSB());
			bitCount = bitCount - difference;
			tempImplicantList.get(bitCount).add(implicantList.get(i));
		}
		while(true) {
			for (int i = 0; i < tabulationList.size(); i++ ) {
				completed = true;
				for (int j = 0; j < tabulationList.get(i).size();i++) {
					System.out.println("Size is " + tabulationList.get(i).size() + "for " + j + "0s");
				}
			}
		}
		
	}
	
	public void doQuineMcCluskey()
	{
		//TODO: Your code goes here
	}
	
	public void doPetricksMethod()
	{
		//TODO: Your code goes here
	}
	
	/*
	 * Parameters: fileName
	 * 
	 * Generates a verilog file with the same name as the fileName. Do not change
	 * the input and output port names, as we will need these for testing. The 
	 * interior of the module, however, can be implemented as you see fit. 
	 */
	public boolean genVerilog(String fileName)
	{
		try {
			PrintWriter outputStream = new PrintWriter(new FileWriter(fileName + ".v"));
			outputStream.println("module " + fileName + "(");
			for (int i = 0; i < myNumVars; i++)
			{
				outputStream.println("input " + alphabet.charAt(i) + ",");
			}
			outputStream.println("output out");
			outputStream.println(");");
			
			//Code will be given after the late deadline
			outputStream.write("\tassign out = ");
			for (int i = 0; i < implicantList.size(); i++)
			{
				outputStream.write(implicantList.get(i).getVerilogExpression());
				if (i < implicantList.size()-1)
					outputStream.write("|");
			}
			outputStream.println(";");
			
			outputStream.println("endmodule");
			outputStream.close();
			return true;
		} catch (Exception e){
			return false;
		}
		
	}
	
}
