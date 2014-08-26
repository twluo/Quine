import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Stores implicants in the form where 
 */
public class Implicant {
	
	private long myMSB;
	private long myLSB;
	private int myNumVars;
	private List<Long> minterms;
	private List<Long> dontcares;

	public long getMSB()
	{
		return myMSB;
	}
	
	public long getLSB()
	{
		return myLSB;
	}
	
	public int getNumVars()
	{
		return myNumVars;
	}
	
	public List<Long> getMinterms()
	{
		return minterms;
	}
	
	public Implicant(long newMSB, long newLSB, int numVars)
	{
		myMSB = newMSB;
		myLSB = newLSB;
		myNumVars = numVars;
		minterms = new ArrayList<Long>();
	}
	
	public Implicant(long minterm, int numVars)
	{
		myMSB = minterm ^ BooleanExpression.maxVal;
		myLSB = BooleanExpression.maxVal & (minterm | (BooleanExpression.maxVal << numVars));
		myNumVars = numVars;
		minterms = new ArrayList<Long>();
		minterms.add(minterm);
	}

	public boolean equals(Implicant imp)
	{
		return 	(imp.getLSB() == this.myLSB) && 
				(imp.getNumVars() == this.myNumVars) &&
				(imp.getMSB() == this.myMSB);
	}
	
	public String getVerilogExpression()
	{
		StringBuilder expr = new StringBuilder("");
		
		expr.append("(");
		
		boolean first = true; 
		for (int i = 0; i < myNumVars; i++)
		{
			long tempMSB = myMSB & (1 << i);
			long tempLSB = myLSB & (1 << i);
			char alphabetVal = BooleanExpression.alphabet.charAt(i);
			
			
			if (Long.bitCount(tempMSB) == 1 && Long.bitCount(tempLSB) == 0)
			{
				if (first)
				{
					first = false;
				}
				else
				{
					expr.append("&");
				}
				expr.append("(~" + alphabetVal + ")");
			}
			if (Long.bitCount(tempMSB) == 0 && Long.bitCount(tempLSB) == 1)
			{
				if (first)
				{
					first = false;
				}
				else
				{
					expr.append("&");
				}
				expr.append(alphabetVal);
			}
		}
		expr.append(")");
		return expr.toString();
	}
}
