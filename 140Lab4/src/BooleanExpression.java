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
	private static long tempMSB;
	private static long tempLSB;
	private static int bitCountMSB;
	private static int bitCountLSB;
	private int myNumVars;
	public static final long maxVal = -1;
	public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private ArrayList<BitVector> row;
	private ArrayList<BitVector> col;
	private ArrayList<Implicant> primeImplicant;
	private ArrayList<Implicant> petrickImplicant;
	
	private void initBooleanExpression(int numVars)
	{
		implicantList = new ArrayList<Implicant>();
		dontcareList = new ArrayList<Implicant>();
		myNumVars = numVars; 
		mintermsNeededToCover = new ArrayList<Long>();
		dontcaresList = new ArrayList<Long>();
		primeImplicant = new ArrayList<Implicant>();
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
		tempMSB = imp1.getMSB() | imp2.getMSB(); // XOR together MSB
		tempLSB = imp1.getLSB() | imp2.getLSB(); // XOR together LSB
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
//			System.out.println("Number of cubes = " + i);
			completed = true;
			for (int j = 0; j < tabulationList.get(i).size() - 1;j++) {
//					System.out.print(j + " ones : ");
//					System.out.print("There are " + tabulationList.get(i).get(j).size());
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
//							temp.printList();
							implicantList.remove(prev);
							implicantList.remove(next);
							if (!containsImplicant(tabulationList.get(i+1).get(j),temp)) {
								if (!temp.getMinterms().isEmpty()) {
									implicantList.add(temp);
								}
								tabulationList.get(i+1).get(j).add(temp);
							}
						}
					}
				}
//				System.out.println("");
			}
			if (completed)
				break;
		}
	}

	
	public void doQuineMcCluskey()
	{
		row = new ArrayList<BitVector>(implicantList.size());
		col = new ArrayList<BitVector>(mintermsNeededToCover.size());
		Implicant impTemp;
		BitVector bitVectorTemp;
		List<Long> minterms;
		for (int i = 0; i < implicantList.size(); i++) {
//			implicantList.get(i).printList();
			row.add(new BitVector(mintermsNeededToCover.size()));
		}
		for (int i = 0; i < mintermsNeededToCover.size(); i++) {
			col.add(new BitVector(implicantList.size()));
		}
		for (int i = 0; i < implicantList.size(); i++) {
			impTemp = implicantList.get(i);
			minterms = impTemp.getMinterms();
			for (int j = 0; j < minterms.size(); j++) {
				row.get(i).set(mintermsNeededToCover.indexOf(minterms.get(j)));
				col.get(mintermsNeededToCover.indexOf(minterms.get(j))).set(i);
			}
		}
//		System.out.println("Cols");
//		for (int i = 0; i < implicantList.size(); i++) {
//			System.out.println(row.get(i).toString() + " " + row.get(i).getCardinality());
//		}
//		System.out.println("Rows");
//		for (int i = 0; i < mintermsNeededToCover.size(); i++) {
//			System.out.println(col.get(i).toString() + " " + col.get(i).getCardinality());
//		}
		int index;
		int count = 0;
		int tempCount = 0;
		System.out.println("START");
		while (true) {
			tempCount++;
			count++;
			/*
			 * STEP 1!!!!!!!
			 */
//			System.out.println("step 1 = " + count);
			for (int i = 0; i < col.size(); i++) {
//				System.out.println("index = " + i + " hex = " + col.get(i).toString() + " altIndex = " + col.get(i).findNeededImplicant());
				if (col.get(i).getCardinality() == 1) {
//					System.out.print(col.get(i).toString());
					count = 0;
					index = col.get(i).findNeededImplicant();
					col.get(i).unset(index);
//					System.out.print(mintermsNeededToCover.size());
//					System.out.println("index = " + index + "," + i);
					if (!containsImplicant(primeImplicant, implicantList.get(index)))
						primeImplicant.add(implicantList.get(index));
					for (int j = 0; j < col.size(); j++) {
						if(row.get(index).exists(j)) {
							row.get(index).unset(j);
							for (int k = 0; k < row.size(); k++) {
								if (col.get(j).exists(k)) {
									col.get(j).unset(k);
									row.get(k).unset(j);
								}
							}
							
						}
					}
				}
			}
			if (count == 3)
				break;
			count++;
			/*
			 * STEP 2!!!!!!
			 */
//			System.out.println("step 2 = " + count);
			for (int i = 0; i < row.size() - 1; i++) {
				bitVectorTemp = row.get(i);
				for (int j = i + 1; j < row.size(); j++) {
					if (!row.get(j).isZero() && !row.get(i).isZero()) {
						if (bitVectorTemp.union(row.get(j)).equals(bitVectorTemp)) {
							count = 0;
							for (int k = 0; k < col.size(); k++) {
								if (row.get(j).exists(k)) {
//									System.out.println("index = " +i + "," + j + "," + k );
									row.get(j).unset(k);
									col.get(k).unset(j);
								}
							}
						}
						else if (bitVectorTemp.union(row.get(j)).equals(row.get(j))) {
//							System.out.println("DOMINATION");
							count = 0;
							for (int k = 0; k < col.size(); k++) {
								if (row.get(i).exists(k)) {
									row.get(i).unset(k);
									col.get(k).unset(i);
								}
							}
							
						}
					}

				}
			}
			if (count == 3)
				break;
			count++;
			/*
			 * STEP 3!!!!!!!!!
			 */
//			System.out.println("step 3 = " + count);
			for (int i = 0; i < col.size() - 1; i++) {
				bitVectorTemp = col.get(i);
				for (int j = i + 1; j < col.size(); j++) {
					if (!col.get(j).isZero() && !col.get(i).isZero()) {
						if (bitVectorTemp.union(col.get(j)).equals(col.get(j))) {
//							System.out.println("DOMINATION");
							count = 0;
							for (int k = 0; k < row.size(); k++) {
								if (col.get(j).exists(k)) {
									col.get(j).unset(k);
									row.get(k).unset(j);
								}
							}
						}
						else if (bitVectorTemp.union(col.get(j)).equals(bitVectorTemp)) {
//							System.out.println("DOMINATION");
							count = 0;
							for (int k = 0; k < row.size(); k++) {
								if (col.get(i).exists(k)) {
									col.get(i).unset(k);
									row.get(k).unset(i);
								}
							}
						}
					}

				}
			}
			if (count == 3)
				break;
			}
//		for (int i = 0; i < primeImplicant.size(); i++) {
//			primeImplicant.get(i).printList();
//		}
		/*
		 * Adding Stuff
		 */
		for (int i = 0; i < row.size(); i++) {
			if (!row.get(i).isZero()) {
				primeImplicant.add(implicantList.get(i));
			}
		}
		implicantList = primeImplicant;
//		for (int i = 0; i < implicantList.size(); i++) {
//			implicantList.get(i).printList();
//		}
		System.out.println(tempCount);
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
