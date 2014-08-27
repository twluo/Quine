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
	private List<Long> dontcaresList;
	private List<Implicant> finalList;
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
		dontcaresList = new ArrayList<Long>();
		finalList = new ArrayList<Implicant>();
	}
	
	public BooleanExpression(ArrayList<Long> minterms, ArrayList<Long> dontcares, int numVars)
	{
		initBooleanExpression(numVars);
		for (Long minterm : minterms)
		{
			implicantList.add(new Implicant(minterm, numVars, false));
			mintermsNeededToCover.add(minterm);
		}

		for (Long dontcare : dontcares)
		{
			dontcaresList.add(dontcare);
			dontcareList.add(new Implicant(dontcare, numVars, true));
		}
	}
	
	public List<Implicant> getImplicantList()
	{
		return implicantList;
	}
	
	public boolean differBySingleVariable(Implicant imp1,  Implicant imp2) {
		tempMSB = imp1.getMSB() ^ imp2.getMSB(); // XOR together MSB to get 1 at a certain place
		tempLSB = imp1.getLSB() ^ imp2.getLSB(); // XOR together LSB to get 1 at a certain place
		bitCountMSB = Long.bitCount(tempMSB); // Get number of 1's in MSB
		bitCountLSB = Long.bitCount(tempLSB); // Get number of 1's in LSB
//		System.out.println("tempMSB = " + tempMSB + " tempLSB = " + tempLSB + " bitCount = " + bitCountMSB + " ");
		return (bitCountMSB == 1 && bitCountLSB == 1 && tempMSB == tempLSB); //Compare and return that MSB and LSB only contain one 1	
	}
	public Implicant merge(Implicant imp1, Implicant imp2) {
		tempMSB = imp1.getMSB() ^ imp2.getMSB(); // XOR together MSB
		tempLSB = imp1.getLSB() ^ imp2.getLSB(); // XOR together LSB
		tempMSB = imp1.getMSB() | tempMSB; //Or together tempMSB and MSB of imp1 to keep everything same except for differing position
		tempLSB = imp2.getLSB() | tempLSB; //Or together tempLSB and LSB of imp2 to keep everything same except for differing position
		Implicant newImp = new Implicant(tempMSB, tempLSB, myNumVars); //create new Implicant list
		newImp.mergeMinterms(imp1.getMinterms(), imp2.getMinterms(), imp1.getdontcares(), imp2.getdontcares()); //create new implicant for next group using minterms and Don't cares
		return newImp;
	}
	
	/**
	 * Used to check if list contains implicant
	 * @param impList: List to look through
	 * @param imp: Variable to check if it's in list
	 */
	public boolean containsImplicant(ArrayList<Implicant> impList, Implicant imp) {
		for (int i = 0; i < impList.size(); i++) {
			if (imp.equals(impList.get(i))) 
				return true;
		}
		return false;
	}
	
	/**
	 * Method to replace implicants with prime implicants using minterms and don't cares
	 */
	public void doTabulationMethod()
	{
		ArrayList<ArrayList<ArrayList<Implicant>>> tabulationList = new ArrayList<ArrayList<ArrayList<Implicant>>>(myNumVars + 1);
		for (int i = 0; i < myNumVars + 1; i++) {
			tabulationList.add(new ArrayList<ArrayList<Implicant>>());
			for (int j = 0; j < myNumVars + 1; j++) {
				tabulationList.get(i).add(new ArrayList<Implicant>());
			}
		}
		boolean completed = true;
		Implicant prev;
		Implicant next;
		int bitCount;
//		for (int i = 0; i < dontcaresList.size(); i++) {
//			bitCount = Long.bitCount(dontcaresList.get(i));
//			tabulationList.get(0).get(bitCount).add(dontcareList.get(i));
//		}
		for (int i = 0; i < mintermsNeededToCover.size(); i++) {
			bitCount = Long.bitCount(mintermsNeededToCover.get(i));
			tabulationList.get(0).get(bitCount).add(implicantList.get(i));
		}
		Implicant temp;
		for (int i = 0; i < tabulationList.size() - 1; i++ ) {
			System.out.println("Number of squares = " + i);
			completed = true;
			for (int j = 0; j < tabulationList.get(i).size() - 1;j++) {
					System.out.print(j + " ones : ");
					System.out.print("There are " + tabulationList.get(i).get(j).size());
				for (int k = 0; k < tabulationList.get(i).get(j).size(); k++) {
//					tabulationList.get(i).get(j).get(k).printList();
					prev = tabulationList.get(i).get(j).get(k);
					for (int l = 0; l < tabulationList.get(i).get(j+1).size(); l++) {
						next = tabulationList.get(i).get(j+1).get(l);
//						System.out.print("Comparing " + prev.getLSB() + "with " + next.getLSB() + " ");
						if (differBySingleVariable(prev, next)) {
							completed = false;
//							System.out.println("match");
							temp = merge(prev,next);
							implicantList.remove(prev);
							implicantList.remove(next);
							if (!containsImplicant(tabulationList.get(i+1).get(j),temp)) {
								tabulationList.get(i+1).get(j).add(temp);
								implicantList.add(temp);
							}
						}
					}
				}
				System.out.println("");
			}
			if (completed)
				break;
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
