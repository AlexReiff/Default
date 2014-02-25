package a1posted;

/*
 *   STUDENT NAME      : Alexander Reiff
 *   STUDENT ID        : 260504962
 *      
 */

import java.util.LinkedList;

public class NaturalNumber  {
	
	int	base;       

	LinkedList<Integer>  coefficients;

	//   For any base and any positive integer, the representation of that positive 
	//   integer as a sum of powers of that base is unique.  
	//   Moreover,  we require that the "last" coefficient (namely, the coefficient
	//   of the largest power)  is non-zero.  
	//   For example,  350 is a valid representation (which we call "three hundred fifty") 
	//   but 0353 is not.  
	
	//  Constructors

	//  This constructor acts as a helper.  It is not called from the Tester class.
	
	NaturalNumber(int base){
		this.base = base;
		coefficients = new LinkedList<Integer>();
	}

	//  This constructor acts as a helper.  It is not called from the Tester class.

	NaturalNumber(int base, int i) throws Exception{
		this.base = base;
		coefficients = new LinkedList<Integer>();
		
		if ((i >= 0) && (i < base))
			coefficients.addFirst( new Integer(i) );
		else {
			System.out.println("constructor error: all coefficients should be non-negative and less than base");
			throw new Exception();
		}
	}

	NaturalNumber(int base, int[] intarray) throws Exception{
		this.base = base;
		coefficients = new LinkedList<Integer>();
		for (int i=0; i < intarray.length; i++){
			if ((i >= 0) && (intarray[i] < base))
				coefficients.addFirst( new Integer( intarray[i] ) );
			else{
				System.out.println("constructor error:  all coefficients should be non-negative and less than base");
				throw new Exception();
			}
		}
	}
		
	public NaturalNumber add(NaturalNumber second){
				
		//  initialize the sum as an empty list of coefficients
		
		NaturalNumber sum = new NaturalNumber( this.base );
		
		//initializes the carry variable, which is the 10s digit of the sum of the single digit addition
		int c = 0;
		//determines how many digits can be added before and IndexOutOfBoundsException gets thrown
		int N = Math.min(coefficients.size(), second.coefficients.size());
		//adds each digit until only one number still has digits left to carry over into the sum
		for(int i = 0; i < N; i++){
			//calculates the last digit of the sum of the two numbers plus anything left over from the last iteration
			int num = (coefficients.get(i) + second.coefficients.get(i) + c) % base;
			//adds that number to the sum
			sum.coefficients.add(num);
			//calculates the new carry
			c = (coefficients.get(i) + second.coefficients.get(i) + c) / base;
		}
		//carries over all extra digits from the larger term to the sum list
		if(coefficients.size() > second.coefficients.size())
			//goes through the rest of the LinkedList and transfers each term to the sum, accounting for the carry
			for(int j = N; j < coefficients.size(); j++){
				//does the actual adding
				sum.coefficients.add(j, (coefficients.get(j) + c) % base);
				//redefines the carry
				c = (coefficients.get(j) + c) / base;
			}
		//same thing, but only if the second number was bigger
		if(coefficients.size() < second.coefficients.size())
			for(int j = N; j < second.coefficients.size(); j++){
				sum.coefficients.add(j, (second.coefficients.get(j) + c) % base);
				c = (second.coefficients.get(j) + c) / base;
			}
		//adds the non-zero carry as the first term in the sum
		if(c != 0){
			sum.coefficients.add(c);	
		}
		return sum;		
	}
	
	/*
	 *   The subtract method computes a.subtract(b) where a>b.
	 *   If a<b, then it throws an exception.
	 */
	
	public NaturalNumber subtract(NaturalNumber second) throws Exception{

		//  initialize difference as an empty list of coefficients
		
		NaturalNumber  difference = new NaturalNumber(this.base);

		//   The subtract method shouldn't affect the number itself. 
		//   But the grade school algorithm sometimes requires us to "borrow" 
		//   from a higher coefficient to a lower one.   So let's just work
		//   with a copy (a clone) of 'this' so that we don't modify 'this'.   		

		NaturalNumber  first = this.clone();
		if (this.compareTo(second) < 0){
			System.out.println("Error:  subtract a-b requires that a > b");
			throw new Exception();
		}
		
		//for: every digit in the second number
		for(int i = 0; i < second.coefficients.size(); i++){
			//if: nothing needs to be borrowed
			if(first.coefficients.get(i) >= second.coefficients.get(i)){
				//add the difference to the final difference
				difference.coefficients.add((first.coefficients.get(i) - second.coefficients.get(i)) % base);
			}
			//else: AKA if something needs to be borrowed
			else{
				//while: the next digit to the left is a zero AKA unable to be decremented
				int j = 1;
				while(first.coefficients.get(i+j) == 0){	
					//adjusts each zero to base - 1, which is what it would be after the borrowing chain
					first.coefficients.set(i+j, base - 1);
					j++;
				}
				//decrement the digit that is being borrowed from
				first.coefficients.set(i+j, first.coefficients.get(i+1) - 1);
				//do the subtraction with the adjusted top term (old term + base)
				difference.coefficients.add((first.coefficients.get(i) - second.coefficients.get(i) + base) % base);
			}					
		}
		//for: every digit more in the top number than the bottom 
		for(int x = second.coefficients.size(); x < first.coefficients.size(); x++){
			//carry that digit into the final difference
			difference.coefficients.add(x, first.coefficients.get(x));
		}
		//removes any zero at the end due to borrowing
		if(difference.coefficients.get(difference.coefficients.size() - 1) == 0){
			difference.coefficients.remove(difference.coefficients.size() - 1);
		}
		return difference;	
	}

	
	
	//   The multiply method should NOT be the same as what you learned in
	//   grade school since that method requires space proportional to the
	//   square of the number of coefficients in the number.   Instead,
	//   you must write a method that uses space that is proportional to
	//   the number of coefficients.    This can be done by basically 
	//   changing the order of loops, as was sketched in class.  
	//
	//  You are not allowed to simply perform addition repeatedly. 
	//  Such a method would be correct, but way too slow to be useful.

	public NaturalNumber multiply(NaturalNumber second) throws Exception{
		
		//  initialize product as an empty list of coefficients
		
		NaturalNumber product	= new NaturalNumber( this.base );
		
		//initialize the carry
		int c = 0;
		//this is the result of the multiplication of one digit of the bottom number
		NaturalNumber temp = new NaturalNumber(this.base);
		//for: each digit in the second(lower) number
		for(int j = 0; j < second.coefficients.size(); j++){
			c= 0;
			//re-initializes temp and shifts it over accordingly
			temp.coefficients.clear();
			//for: one zero for each digit after the first digit
			for(int x = 0; x < j; x++){
				temp.coefficients.add(0);
			}
			//for: each digit in the first(higher) number
			for(int i = 0; i < coefficients.size(); i++){
				//multiplies the two digits and adds the carry, if any
				int prod = (coefficients.get(i) * second.coefficients.get(j)) + c;
				//inputs the product into the array
				temp.coefficients.add(prod % base);
				//redefines the carry
				c = prod / base;
			}
			//adds the carry the front
			temp.coefficients.add(c);
			//redefines product to include the latest set of values of temp
			product = product.add(temp);
		}
		//removes unnecessary zeroes if they exist 
		if(product.coefficients.get(product.coefficients.size() - 1) == 0){
			product.coefficients.remove(product.coefficients.size() - 1);
		}
		return product;
	}
	
	
	//  The divide method divides 'this' by 'second' i.e. this/second.   
	//  'this' is the "dividend", 'second' is the "divisor".
	//  This method ignores the remainder.    
	//
	//  You are not allowed to simply subtract the divisor repeatedly.
	//  This would give the correct result, but it is way too slow!
	
	public NaturalNumber divide(NaturalNumber divisor) throws Exception{
		
		//  initialize quotient as an empty list of coefficients
		
		NaturalNumber  quotient = new NaturalNumber(this.base);
				
		//check how far over we start
		NaturalNumber value = new NaturalNumber(this.base);
		int j = -1;
		//while: the divisor is larger than the part of the dividend we are looking at
		while(value.compareTo(divisor) < 0){
			//creates the numerator as a number		
			j++;
			//drops down the next digit from the dividend
			value.coefficients.add(0,coefficients.get(coefficients.size() - 1 - j));
		}
		//calculates how many times the divisor goes into the part of the dividend we're looking at
		//it also calculates the remainder of the part of the dividend minus the multiple of the divisor
		int i = 0;
		while(value.compareTo(divisor) >= 0){
			i++;
			value = value.subtract(divisor);
		}
		//adds the multiple to the front of the quotient
		quotient.coefficients.add(i);
		//enter loop to solve the rest	
		j++;
		//for: the rest of the digits in the dividend
		for(int h = j; h < coefficients.size(); h++){
			//adds the next number to the part of the dividend we are looking at
			value.coefficients.add(0, coefficients.get(coefficients.size() - h - 1));	
			//compute what number should be added to the quotient
			i = 0;
			//again calculates the multiple of the divisor that goes into the part of the dividend
			while(value.compareTo(divisor) >= 0){
				i++;
				value = divisor.subtract(value);
			}
			//adds the multiple to the end of the quotient
			quotient.coefficients.add(0,i);
		}
		return quotient;		
	}

	/*
	 * The methods should not alter the two numbers.  If a method require
	 * that one of the numbers be altered (e.g. borrowing in subtraction)
	 * then you need to clone the number and work with the cloned number 
	 * instead of the original. 
	 */
	
	public NaturalNumber  clone(){

		//  For technical reasons we'll discuss later, this methods 
		//  has to be declared public (not private).
		//  This detail need not concern you now.

		NaturalNumber copy = new NaturalNumber(this.base);
		for (int i=0; i < this.coefficients.size(); i++){
			copy.coefficients.addLast( new Integer( this.coefficients.get(i) ) );
		}
		return copy;
	}
	
	/*
	 *  The subtraction method computes a-b and requires that a>b.   
	 *  The a.compareTo(b) method is useful for checking this condition. 
	 *  It returns -1 if a < b,  it returns 0 if a == b,  
	 *  and it returns 1 if a > b.
	 */
	
	private int 	compareTo(NaturalNumber second){
		
		int diff = this.coefficients.size() - second.coefficients.size();
		if (diff < 0)
			return -1;
		else if (diff > 0)
			return 1;
		else { 
			boolean done = false;
			int i = this.coefficients.size() - 1;
			while (i >=0 && !done){
				diff = this.coefficients.get(i) - second.coefficients.get(i); 
				if (diff < 0){
					return -1;
				}
				else if (diff > 0)
					return 1;
				else{
					i--;
				}
			}
			return 0;
		}
	}

	/*  computes  a*base^n  where a is the number represented by 'this'
	 */
	
	private NaturalNumber multiplyByBaseToThePower(int n){
		for (int i=0; i< n; i++){
			this.coefficients.addFirst(new Integer(0));
		}
		return this;
	}

	//   This method is invoked by System.out.print.
	//   It returns the string with coefficients in the reverse order 
	//   which is the natural format for people to reading numbers,
	//   i.e..  [ a[N-1], ... a[2], a[1], a[0] ] as in the Tester class. 
	//   It does so simply by make a copy of the list with elements in 
	//   reversed order, and then printing the list using the LinkedList's
	//   toString() method.
	
	public String toString(){	
		LinkedList<Integer> reverseCoefficients = new LinkedList<Integer>();
		for (int i=0;  i < coefficients.size(); i++)
			reverseCoefficients.addFirst( coefficients.get(i));
		return reverseCoefficients.toString();		
	}
}

