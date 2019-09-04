package bigint;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	boolean negative;
	
	/**
	 * Number of digits in this integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 *    
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 *    5 --> 3 --> 2  (No zeros after the last 2)        
	 */
	DigitNode front;
	
	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12  345" will not parse as
	 * an integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
	 * constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) 
	throws IllegalArgumentException 
	{
		integer=integer.trim();
		BigInteger firstProject=new BigInteger();
		boolean firstDigit=false;
		boolean space=false;
		boolean negativeSign=false;
		boolean firstDigitIsZero=false;
		int startOfInteger=0;
		int endOfInteger=0;
		for(int i=0;i<integer.length();i++)
		{
			if(firstDigit==true)
			{
				if(integer.charAt(i)==' ')
				{	
					space=true;
					if(firstDigitIsZero==false)
					{
						endOfInteger=i;
					}
					else
					{
						endOfInteger=i;
						startOfInteger=i-1;
					}
				}
				else if(!Character.isDigit(integer.charAt(i)))
				{
					throw new IllegalArgumentException("Invalid value. Try again!");
				}
			}
			if(Character.isDigit(integer.charAt(i)))
			{
				if(firstDigit==false)
				{
					firstDigit=true;
					if(integer.charAt(i)!='0')
					{
						startOfInteger=i;
						firstProject.addToFront(Character.getNumericValue(integer.charAt(i)));
					}
					else
					{
						firstDigitIsZero=true;
					}
				}
				else if(space==false)
				{
					if(firstDigitIsZero==false)
					{
						firstProject.addToFront(Character.getNumericValue(integer.charAt(i)));
					}
					else if(integer.charAt(i)!='0')
					{
						if(startOfInteger==0)
						{
						startOfInteger=i;
						firstDigitIsZero=false;
						}
						firstProject.addToFront(Character.getNumericValue(integer.charAt(i)));
					}
				}
				else
				{
					throw new IllegalArgumentException("Invalid value. Try again!");
				}
			}
			else if(i==0 && integer.charAt(i)=='+')
			{
				negativeSign=false;
			}
			else if(i==0 && integer.charAt(i)=='-')
			{
				negativeSign=true;
			}
			else
			{
				throw new IllegalArgumentException("Invalid value. Try again!");
			}
			if(endOfInteger==0 && i==integer.length()-1)
			{
				endOfInteger=i;
			}
		}
		if(firstDigit==false)
		{
			throw new IllegalArgumentException("Invalid value. Try again!");
		}
		if(firstDigitIsZero==true)
		{
			firstProject.front=null;
			negativeSign=false;
		}
		String integerValue=integer.substring(startOfInteger,endOfInteger);
		int numberOfDigits=integerValue.length();
		firstProject.numDigits=numberOfDigits;
		firstProject.negative=negativeSign;
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		return firstProject;
	}
	private void addToBack (int newDigit)
	{
			if(front==null)
			{
					addToFront(newDigit);
			}
			else
			{
				DigitNode temp=front;
				while(temp.next!=null)
				{
					temp=temp.next;
				}
				temp.next=new DigitNode(newDigit, null);
			}
	}
	private void addToFront(int newDigit)
	{
		front=new DigitNode(newDigit, front);
	}
	private void removeBack()
	{
		DigitNode temp=front;
		if(!(front==null))
		{
			if(front.next!=null) 
			{
				while(front.next.next!=null)
				{
					front=front.next;
				}
				front.next=null;
				
			}
			else
			{
				front=null;
			}
			
		}
		front=temp;
	}
	
	/**
	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */
	public static BigInteger add(BigInteger first, BigInteger second) {
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		BigInteger sum= new BigInteger();
		DigitNode ptr1= first.front;
		DigitNode ptr2= second.front;
		if((first.negative && second.negative) || (!first.negative && !second.negative))
		{
			int digitCounter=0;
			if(first.numDigits>second.numDigits)
			{
		
				int carry=0;
				for(int i=0;i<=first.numDigits+1;i++)
				{
					if(ptr2==null && ptr1==null)
					{
						if(carry!=0)
						{
							sum.addToBack(carry/10);
							digitCounter++;
						}
					}
					else if(ptr2==null)
					{
						int smallSum=ptr1.digit+carry/10;
						carry=0;
						if(smallSum>9)
						{
							carry=smallSum-smallSum%10;
						}
						sum.addToBack(smallSum%10);
						digitCounter++;
						ptr1=ptr1.next;
					}
					else if(ptr1==null)
					{
						int smallSum=ptr2.digit+carry/10;
						carry=0;
						if(smallSum>9)
						{
							carry=smallSum-smallSum%10;
						}
						sum.addToBack(smallSum%10);
						digitCounter++;
						ptr2=ptr2.next;
					}
					else if(!(ptr1==null) && !(ptr2==null))
					{
						int smallSum=ptr1.digit+ptr2.digit+carry/10;
						carry=0;
						if(smallSum>9)
						{
							carry=smallSum-smallSum%10;
						}
						sum.addToBack(smallSum%10);
						digitCounter++;
						ptr1=ptr1.next;
						ptr2=ptr2.next;
					}
				}
			}
			else if(second.numDigits>first.numDigits)
			{
				int carry=0;
				for(int i=0;i<=second.numDigits+1;i++)
				{
					if(ptr1==null && ptr2==null)
					{
						if(carry!=0)
						{
							sum.addToBack(carry/10);
							digitCounter++;
						}
			
					}
					else if(ptr1==null)
					{
						int smallSum=ptr2.digit+carry/10;
						carry=0;
						if(smallSum>9)
						{
							carry=smallSum-smallSum%10;
						}
						sum.addToBack(smallSum%10);
						digitCounter++;
						ptr2=ptr2.next;
					}
					else
					{
						int smallSum=ptr2.digit+ptr1.digit+carry/10;
						carry=0;
						if(smallSum>9)
						{
							carry=smallSum-smallSum%10;
						}
						sum.addToBack(smallSum%10);
						digitCounter++;
						ptr2=ptr2.next;
						ptr1=ptr1.next;
					}
				}
			}
			else
			{
	
				int carry=0;
				for(int i=0;i<=second.numDigits+1;i++)
				{
					if(ptr1==null && ptr2==null)
					{
						if(carry!=0)
						{
							sum.addToBack(carry/10);
							digitCounter++;
						}
					}
					else if(ptr1==null && i!=second.numDigits+1)
					{
						int smallSum=ptr2.digit+carry/10;
						carry=0;
						if(smallSum>9)
						{
							carry=smallSum-smallSum%10;
						}
						sum.addToBack(smallSum%10);
						digitCounter++;
						ptr2=ptr2.next;
					}
					else if(ptr2==null && i!=second.numDigits+1)
					{
						int smallSum=ptr1.digit+carry/10;
						carry=0;
						if(smallSum>9)
						{
							carry=smallSum-smallSum%10;
						}
						sum.addToBack(smallSum%10);
						digitCounter++;
						ptr1=ptr1.next;
					}
					else if(i!=second.numDigits+1)
					{
						int smallSum=ptr2.digit+ptr1.digit+carry/10;
						carry=0;
						if(smallSum>9)
						{
							carry=smallSum-smallSum%10;
						}
						sum.addToBack(smallSum%10);
						digitCounter++;
						ptr2=ptr2.next;
						ptr1=ptr1.next;
					}
				}
			}
			if(first.negative && second.negative)
			{
				sum.negative=true;
			}
			if(!first.negative && !second.negative)
			{
				sum.negative=false;
			}
			if(sum.front==null || (sum.front.digit==0 && sum.front.next==null))
			{
				return new BigInteger();
			}
			sum.numDigits=digitCounter;
			return sum;
		}
		else
		{
			int digitCounter=0;	
			if(first.numDigits>second.numDigits)
			{
				BigInteger copy1=new BigInteger();
				DigitNode head=new DigitNode(0, null);
				DigitNode copy1ptr=head;
				for(DigitNode node=ptr1;node!=null;node=node.next)
				{
					copy1ptr.next=node;
					copy1ptr=copy1ptr.next;
				}
				copy1.front=head.next;
				copy1ptr=copy1.front;
				
				for(int i=0;i<first.numDigits+1;i++)
				{
					if(ptr2==null)
					{
						sum.addToBack(copy1ptr.digit);
						digitCounter++;
						copy1ptr=copy1ptr.next;
					}
					else
					{
						int smallSub=copy1ptr.digit-ptr2.digit;
						if(smallSub>0)
						{
							sum.addToBack(smallSub);
							digitCounter++;
						}
						else if(smallSub==0 && !(copy1ptr.next==null))
						{
							sum.addToBack(smallSub);
							digitCounter++;
						}
						else
						{
							DigitNode temp=copy1ptr;
							while(copy1ptr!=null)
							{
								if(copy1ptr.next.digit!=0)
								{
									copy1ptr.next.digit-=1;
									sum.addToBack(smallSub+10);
									digitCounter++;
									break;
								}
								if(copy1ptr.next.digit==0)
								{
									copy1ptr.next.digit=9;
								}
								copy1ptr=copy1ptr.next;
							}
							copy1ptr=temp;
					
						}
						copy1ptr=copy1ptr.next;
						ptr2=ptr2.next;
					}
				}
				for(int zeroTracker=digitCounter;zeroTracker>0;zeroTracker--)
				{
				for(DigitNode traverseptr=sum.front;traverseptr!=null;traverseptr=traverseptr.next)
				{
						if(traverseptr.next==null)
						{
							if(traverseptr.digit==0)
							{
								sum.removeBack();
								digitCounter--;
							}
						}
				}
				}
				if(first.negative && !second.negative)
				{
					sum.negative=true;
				}
				else 
				{
					sum.negative=false;
				}
				sum.numDigits=digitCounter;
				if(sum.front==null || sum.front.digit==0 && sum.front.next==null)
				{
					return new BigInteger();
				}
				return sum;
			}
			else if(second.numDigits>first.numDigits)
			{
				
				BigInteger copy2=new BigInteger();
				DigitNode head=new DigitNode(0, null);
				DigitNode copy2ptr=head;
				for(DigitNode node=ptr2;node!=null;node=node.next)
				{
					copy2ptr.next=node;
					copy2ptr=copy2ptr.next;
				}
				copy2.front=head.next;
				copy2ptr=copy2.front;
				
				for(int i=0;i<second.numDigits+1;i++)
				{
					if(ptr1==null)
					{
						sum.addToBack(copy2ptr.digit);
						digitCounter++;
						copy2ptr=copy2ptr.next;
					}
					else
					{
						int smallSub=copy2ptr.digit-ptr1.digit;
						if(smallSub>0)
						{
							sum.addToBack(smallSub);
							digitCounter++;
						}
						else if(!(copy2ptr.next==null) && smallSub==0)
						{
							sum.addToBack(smallSub);
							digitCounter++;
						}
						else
						{
							DigitNode temp=copy2ptr;
							while(copy2ptr!=null)
							{
								if(copy2ptr.next.digit!=0)
								{
									copy2ptr.next.digit-=1;
									sum.addToBack(smallSub+10);
									digitCounter++;
									break;
								}
								if(copy2ptr.next.digit==0)
								{
									copy2ptr.next.digit=9;
								}
								copy2ptr=copy2ptr.next;
							}
							copy2ptr=temp;
						}
						ptr1=ptr1.next;
						copy2ptr=copy2ptr.next;
					}
				}
				for(int zeroTracker=digitCounter;zeroTracker>0;zeroTracker--)
				{
				for(DigitNode traverseptr=sum.front;traverseptr!=null;traverseptr=traverseptr.next)
				{
						if(traverseptr.next==null)
						{
							if(traverseptr.digit==0)
							{
								sum.removeBack();
								digitCounter--;
							}
						}
				}
				}
				if(first.negative && !second.negative)
				{
					sum.negative=false;
				}
				else 
				{
					sum.negative=true;
				}
				if(sum.front==null || sum.front.digit==0 && sum.front.next==null)
				{
					return new BigInteger();
				}
				sum.numDigits=digitCounter;
				return sum;
			}
			else
			{
			
				boolean firstIsLarger=true;
				int last=second.numDigits+1;
				boolean decision=false;
				for(int i=0;i<second.numDigits+1;i++)
				{
					DigitNode temp1=ptr1;
					DigitNode temp2=ptr2;
					for(int k=0;k<last;k++)
					{
						if(k==last-1)
						{
							if(ptr2.digit>ptr1.digit)
							{
								firstIsLarger=false;
								decision=true;
							}
							else if(ptr2.digit<ptr1.digit)
							{
								firstIsLarger=true;
								decision=true;
							}
							break;
						}
						ptr1=ptr1.next;
						ptr2=ptr2.next;
					}
					ptr1=temp1;
					ptr2=temp2;
					if(decision)
					{
						break;
					}
					last--;
				}
				if(!decision)
				{
					return new BigInteger();
				}
				else if(firstIsLarger)
				{
					BigInteger copy1=new BigInteger();
					DigitNode head=new DigitNode(0, null);
					DigitNode copy1ptr=head;
					for(DigitNode node=ptr1;node!=null;node=node.next)
					{
						copy1ptr.next=node;
						copy1ptr=copy1ptr.next;
					}
					copy1.front=head.next;
					copy1ptr=copy1.front;
					
					for(int i=0;i<first.numDigits+1;i++)
					{
							int smallSub=copy1ptr.digit-ptr2.digit;
							if(smallSub>0)
							{
								sum.addToBack(smallSub);
								digitCounter++;
							}
							else if(smallSub==0 && !(copy1ptr.next==null))
							{
								sum.addToBack(smallSub);
								digitCounter++;
							}
							else
							{
								DigitNode temp=copy1ptr;
								while(copy1ptr.next!=null)
								{
									if(copy1ptr.next.digit!=0)
									{
										copy1ptr.next.digit-=1;
										sum.addToBack(smallSub+10);
										digitCounter++;
										break;
									}
									if(copy1ptr.next.digit==0)
									{
										copy1ptr.next.digit=9;
									}
									copy1ptr=copy1ptr.next;
								}
								copy1ptr=temp;
							}
							copy1ptr=copy1ptr.next;
							ptr2=ptr2.next;
					}
					boolean removed=false;
					for(int zeroTracker=digitCounter;zeroTracker>0;zeroTracker--)
					{
					for(DigitNode traverseptr=sum.front;traverseptr!=null;traverseptr=traverseptr.next)
					{
						if(removed==true || zeroTracker==digitCounter)
						{
							if(traverseptr.next==null)
							{
								if(traverseptr.digit==0)
								{
									sum.removeBack();
									digitCounter--;
									removed=true;
								}
								else
								{
									removed=false;
									break;
								}
							}
						}
					}
					if(removed==false)
					{
						break;
					}
					}
					if(first.negative && !second.negative)
					{
						sum.negative=true;
					}
					else if(!first.negative && second.negative)
					{
						sum.negative=false;
					}
					if(sum.front==null || sum.front.digit==0 && sum.front.next==null)
					{
						return new BigInteger();
					}
					sum.numDigits=digitCounter;
					return sum;
				}
				else
				{
					BigInteger copy2=new BigInteger();
					DigitNode head=new DigitNode(0, null);
					DigitNode copy2ptr=head;
					for(DigitNode node=ptr2;node!=null;node=node.next)
					{
						copy2ptr.next=node;
						copy2ptr=copy2ptr.next;
					}
					copy2.front=head.next;
					copy2ptr=copy2.front;
					
					for(int i=0;i<second.numDigits+1;i++)
					{
						if(ptr1==null)
						{
							sum.addToBack(copy2ptr.digit);
							digitCounter++;
							copy2ptr=copy2ptr.next;
						}
						else{
							int smallSub=copy2ptr.digit-ptr1.digit;
							if(smallSub>0)
							{
								sum.addToBack(smallSub);
								digitCounter++;
							}
							else if(smallSub==0 && !(copy2ptr.next==null))
							{
								sum.addToBack(smallSub);
								digitCounter++;
							}
							
							else
							{
								DigitNode temp=copy2ptr;
								while(copy2ptr.next!=null)
								{
									if(copy2ptr.next.digit!=0)
									{
										copy2ptr.next.digit-=1;
										sum.addToBack(smallSub+10);
										digitCounter++;
										break;
									}
									if(copy2ptr.next.digit==0)
									{
										copy2ptr.next.digit=9;
									}
									copy2ptr=copy2ptr.next;
								}
								copy2ptr=temp;
							}
							ptr1=ptr1.next;
							copy2ptr=copy2ptr.next;
						}
					}
					boolean removed=false;
					for(int zeroTracker=digitCounter;zeroTracker>0;zeroTracker--)
					{
					for(DigitNode traverseptr=sum.front;traverseptr!=null;traverseptr=traverseptr.next)
					{
						if(removed==true || zeroTracker==digitCounter)
						{
							if(traverseptr.next==null)
							{
								if(traverseptr.digit==0)
								{
									sum.removeBack();
									digitCounter--;
									removed=true;
								}
								else
								{
									removed=false;
								}
							}
						}
					}
					}
					if(first.negative && !second.negative)
					{
						sum.negative=false;
					}
					else if(!first.negative && second.negative)
					{
						sum.negative=true;
					}
					if(sum.front==null || sum.front.digit==0 && sum.front.next==null)
					{
						return new BigInteger();
					}
					sum.numDigits=digitCounter;
					return sum;
				}
			}
		}
	}
	
	/**
	 * Returns the BigInteger obtained by multiplying the first big integer
	 * with the second big integer
	 * 
	 * This method DOES NOT MODIFY either of the input big integers
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return A new BigInteger which is the product of the first and second big integers
	 */
	public static BigInteger multiply(BigInteger first, BigInteger second) {
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		DigitNode ptr1=first.front;
		DigitNode ptr2=second.front;
		if(first.numDigits>second.numDigits)
		{
			int numberOfZeroes=0;
			BigInteger base=new BigInteger();
			for(int i=0;i<second.numDigits+1;i++)
			{	
				BigInteger smallProduct=new BigInteger();
				int carry=0;
				for(int j=0;j<numberOfZeroes;j++)
				{
					smallProduct.addToBack(0);
					smallProduct.numDigits++;
				}
				for(int k=0;k<=first.numDigits+1;k++)
				{
					if(ptr1==null)
					{
						if(carry!=0)
						{
							smallProduct.addToBack(carry/10);
							smallProduct.numDigits++;
						}
					}
					else if(!(ptr2==null))
					{
						int smallMult=ptr1.digit*ptr2.digit+carry/10;
						carry=0;
						if(smallMult>9)
						{
							carry=smallMult-smallMult%10;
						}
						smallProduct.addToBack(smallMult%10);
						smallProduct.numDigits++;
						ptr1=ptr1.next;
					}
				}
				for(int zeroTracker=smallProduct.numDigits;zeroTracker>0;zeroTracker--)
				{
					for(DigitNode traverseptr=smallProduct.front;traverseptr!=null;traverseptr=traverseptr.next)
					{
						if(traverseptr.next==null)
						{
							if(traverseptr.digit==0)
							{
								smallProduct.removeBack();
								smallProduct.numDigits--;
							}
						}
					}
				}
				base=BigInteger.add(base,smallProduct);
				numberOfZeroes++;
				if(ptr2!=null) {ptr2=ptr2.next;}
				ptr1=first.front;
				
			}
			if((first.negative && second.negative) || (!first.negative && !second.negative))
			{
				base.negative=false;
			}
			if((!first.negative && second.negative) || (first.negative && !second.negative))
			{
				base.negative=true;
			}
			if(base.front==null || base.front.digit==0 && base.front.next==null)
			{
				return new BigInteger();
			}
			return base;
		}
		else if(second.numDigits>first.numDigits)
		{
			int numberOfZeroes=0;
			BigInteger base=new BigInteger();
			for(int i=0;i<first.numDigits+1;i++)
			{	
				if(ptr1==null)
				{
					base.front=null;
					return base;
				}
				BigInteger smallProduct=new BigInteger();
				int carry=0;
				for(int j=0;j<numberOfZeroes;j++)
				{
					smallProduct.addToBack(0);
					smallProduct.numDigits++;
				}
				for(int k=0;k<=second.numDigits+1;k++)
				{
					if(ptr2==null)
					{
						if(carry!=0)
						{
							smallProduct.addToBack(carry/10);
							smallProduct.numDigits++;
						}
					}
					else if(!(ptr1==null))
					{
						int smallMult=ptr2.digit*ptr1.digit+carry/10;
						carry=0;
						if(smallMult>9)
						{
							carry=smallMult-smallMult%10;
						}
						smallProduct.addToBack(smallMult%10);
						smallProduct.numDigits++;
						ptr2=ptr2.next;
					}
				}
				for(int zeroTracker=smallProduct.numDigits;zeroTracker>0;zeroTracker--)
				{
				for(DigitNode traverseptr=smallProduct.front;traverseptr!=null;traverseptr=traverseptr.next)
				{
						if(traverseptr.next==null)
						{
							if(traverseptr.digit==0)
							{
								smallProduct.removeBack();
								smallProduct.numDigits--;
							}
						}
				}
				}
				ptr2=second.front;
				if(ptr1!=null) {ptr1=ptr1.next;}
				base=BigInteger.add(base,smallProduct);
				numberOfZeroes++;
			}
			if((first.negative && second.negative) || (!first.negative && !second.negative))
			{
				base.negative=false;
			}
			if((!first.negative && second.negative) || (first.negative && !second.negative))
			{
				base.negative=true;
			}
			if(base.front==null || base.front.digit==0 && base.front.next==null)
			{
				return new BigInteger();
			}
			return base;
		}
		else
		{
				int numberOfZeroes=0;
				BigInteger base=new BigInteger();
				for(int i=0;i<second.numDigits+1;i++)
				{	
					BigInteger smallProduct=new BigInteger();
					int carry=0;
					for(int j=0;j<numberOfZeroes;j++)
					{
						smallProduct.addToBack(0);
						smallProduct.numDigits++;
					}
					for(int k=0;k<=first.numDigits+1;k++)
					{
						if(ptr1==null)
						{
							if(carry!=0)
							{
								smallProduct.addToBack(carry/10);
								smallProduct.numDigits++;
							}
						}
						else if(ptr2!=null)
						{
							int smallMult=ptr1.digit*ptr2.digit+carry/10;
							carry=0;
							if(smallMult>9)
							{
								carry=smallMult-smallMult%10;
							}
							smallProduct.addToBack(smallMult%10);
							smallProduct.numDigits++;
							ptr1=ptr1.next;
						}
					}
					for(int zeroTracker=smallProduct.numDigits;zeroTracker>0;zeroTracker--)
					{
					for(DigitNode traverseptr=smallProduct.front;traverseptr!=null;traverseptr=traverseptr.next)
					{
							if(traverseptr.next==null)
							{
								if(traverseptr.digit==0)
								{
									System.out.println("hi");
									smallProduct.removeBack();
									smallProduct.numDigits--;
								}
							}
					}
					}
					ptr1=first.front;
					if(ptr2!=null) {ptr2=ptr2.next;}
					base=BigInteger.add(base,smallProduct);
					numberOfZeroes++;
				}
				if((first.negative && second.negative) || (!first.negative && !second.negative))
				{
					base.negative=false;
				}
				if((!first.negative && second.negative) || (first.negative && !second.negative))
				{
					base.negative=true;
				}
				if(base.front==null || base.front.digit==0 && base.front.next==null)
				{
					return new BigInteger();
				}
				return base;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}
	
}
