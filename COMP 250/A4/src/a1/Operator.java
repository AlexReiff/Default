package a1;

import java.math.BigInteger;

public class Operator {
	BigInteger firstNum;
	BigInteger secondNum;
	
	public Operator(String arg1, String arg2){
		firstNum  = new BigInteger(arg1); 
		secondNum  = new BigInteger(arg2); 
	}
	
	public String addition(){
		String result = firstNum.add(secondNum).toString();
		return result;
	}
	
	public String subtraction(){
		String result = firstNum.subtract(secondNum).toString();
		return result;
	}
	
	public String multiplication(){
		String result = firstNum.multiply(secondNum).toString();
		return result;
	}
	
	public String division(){
		String result = firstNum.divide(secondNum).toString();
		return result;
	}
}
