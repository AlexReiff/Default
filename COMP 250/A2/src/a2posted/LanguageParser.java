package a2posted;

/*
 *   STUDENT NAME      :  Alexander Reiff
 *   STUDENT ID        : 260504962
 *   
 *   If you have any issues that you wish the T.A.s to consider, then you
 *   should list them here.   In this case you should add a note in the dropbox 
 *   comment section so that the grader is sure to read this comment.
 *      
 */

/**
 * This static class provides the parse() method to parse a sequence of tokens.
 * You are provided with the helper methods isBoolean() and isAssignment().
 * 
 * - You may add other methods as you deem necessary.
 * - You may NOT add any class fields.
 */

public class LanguageParser {
	
	/**
	 * Returns true if the given token is a boolean value, i.e.
	 * if the token is "true" or "false".
	 * 
	 * DO NOT MODIFY THIS METHOD.
	 */
	private static boolean isBoolean (String token) {
		
		return (token.equals("true") || token.equals("false"));
		
	}
	
	/**
	 * Returns true if the given token is an assignment statement of the
	 * type "variable=value", where the value is a non-negative integer.
	 * 
	 * DO NOT MODIFY THIS METHOD.
	 */
	private static boolean isAssignment (String token) {
		
		// The code below uses Java regular expressions. You are NOT required to
		// understand Java regular expressions, but if you are curious, see:
		// <http://java.sun.com/javase/6/docs/api/java/util/regex/Pattern.html>
		//
		// Basically, this method returns true if and only if the token matches 
		// the following structure:
		//   one or more letters, followed by
		//   an equal sign '=', followed by
		//   one or more digits.
		
		return token.matches("[a-zA-Z]+=\\d+");	
	}

	/**
	 * Given a sequence of tokens through a StringSplitter object,
	 * this method returns true if the tokens can be parsed according
	 * to the rules of the language as specified in the assignment.
	 */
	
	public static boolean parse(StringSplitter splitter) {

		//   ADD YOUR CODE HERE
		StringStack stack = new StringStack(); 
		while(splitter.hasMoreTokens()){
			//makes sure that the token is valid
			String curr = splitter.nextToken();
			System.out.println(curr.toString() + ":1");
			if(!(isAssignment(curr) || curr.equals("if"))) {return false;}
			
			//enters the stack scenario if the token is if
			if(curr.equals("if")){
				stack.push("if");
				while(!stack.isEmpty()){
					
					//checks that the next token is the boolean condition for the if statement
					curr = splitter.nextToken();
					System.out.println(curr.toString() + ":2");
					if(isBoolean(curr)){
					
						//makes sure that the next token is "then"
						//"then" should only ever be printed here
						curr = splitter.nextToken();
						System.out.println(curr.toString() + ":3");
						if(!curr.equals("then")) {return false;}

						//makes sure that the next token is either "if" or an assignment
						curr = splitter.nextToken();
						System.out.println(curr.toString() + ":4");
						if(!(curr.equals("if") || isAssignment(curr))) {return false;}
							
						if(curr.equals("if")){
							stack.push(curr);
						}
						
						else{
							//takes the token after the assignment
							curr = splitter.nextToken();
							System.out.println(curr.toString() + ":5");
							if(curr.equals("else")){
								//makes sure that the next token is either "if" or an assignment
								curr = splitter.nextToken();
								System.out.println(curr.toString() + ":4");
								if(!(curr.equals("if") || isAssignment(curr))) {return false;}
								
								if(curr.equals("if")){
									stack.push(curr);
								}
							}
							else if(curr.equals("end")){
								stack.pop();
							}
						}
					}
					
					//or this level of nesting is over
					else if(curr.equals("end"))
					{
						stack.pop();
					}
					
					else{return false;}
				}
			}
		}
//needs to be changed back to return false		
		return true;
	}
}