package a2;

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
	public static boolean parse (StringSplitter splitter) {

		// The intuition behind this approach is the following:
		//   - In a valid statement, the first "end" token we see is ALWAYS
		//     the one that closes the DEEPEST if-statement in the recursion.
		//   - This means that when we see "end", the previous tokens must look
		//     like:   "if BOOLEAN then ASSIGNMENT else ASSIGNMENT end"
		//   - In other words, "end" closes a level 1 statement.
		//   - If it doesn't happen, then the statement is invalid.
		//   - How do we know what happened before the "end" that we just saw?
		//     We use the stack.
		
		// Here is a concrete description of the algorithm:
		//   - Push every token we encounter (except "end") onto the stack.
		//   - When we see "end", pop the previous 6 tokens and check that
		//     they correspond to what we expect, namely:
		//     "ASSIGNMENT, else, ASSIGNMENT, then, BOOLEAN, if "
		//     (If not, return false.)
		//   - If we have just popped a level 1 statement from the stack, 
		//     we REPLACE it by a level 0 statement. We do this by just 
		//     pushing an assignment like "a=0".
		//   - Repeat until the input is traversed.
		//   - At the end, the stack should contain ONE SINGLE assignment.
		//     If that's the case, return true; else, return false.
        //
		// The fact that we replace a fully parsed level 1 statement by an
		// assignment ensures that, in a valid statement, we will see an "end" 
		// ONLY when closing a level 1 statement.
		
		StringStack stack = new StringStack();
		int count = splitter.countTokens();
		String token;
		
		// The empty input was NOT graded. We put this check here for completeness only.
		if (count == 0)
			return false;
		
		// Loop until there are no more tokens, i.e. the entire input is traversed
		while (splitter.hasMoreTokens()) {
			
			// Get the next token of the input
			token = splitter.nextToken();
			
			if (token.equals("end")) {
				
				// We see "end". Pop the previous 6 tokens and check that they match
				// a level 1 statement.
				
				if (!isAssignment(stack.pop()))
					return false;
				
				if (!stack.pop().equals("else"))
					return false;
				
				if (!isAssignment(stack.pop()))
					return false;
				
				if (!stack.pop().equals("then"))
					return false;
				
				if (!isBoolean(stack.pop()))
					return false;
				
				if (!stack.pop().equals("if"))
					return false;
				
				// We just popped a fully parsed level 1 statement. Replace it by the base case.
				stack.push("a=0");
				
			}
			
			else {
				// The token isn't "end". Just put it on the stack.
				stack.push(token);
			}
			
		}
		
		// The input has been fully traversed. Return true IF AND ONLY IF the stack
		// contains a single assignment and nothing more.
		
		if (isAssignment(stack.pop()))
			return stack.isEmpty();
		
		return false;
		
	}
	

	public static boolean parse2 (StringSplitter splitter) {

		// This approach is a bit longer and not as elegant. However, it uses less
		// storage space on the stack, making it more memory-efficient.
		//
		
		StringStack stack = new StringStack();
		int count = splitter.countTokens();
		String token;
		String top;

		// The empty input was NOT graded. We put this check here for completeness only.
		if (count == 0)
			return true;

		// If there's only one token, it must be an assignment.
		else if (count == 1)
			return isAssignment(splitter.nextToken());

		else {
			
			// There is more than 1 token. Therefore we must have an if-statement.
			// The next 2 tokens should be "if BOOLEAN", otherwise we return false.

			token = splitter.nextToken();
			if (!token.equals("if"))
				return false;
			
			token = splitter.nextToken();
			if (!isBoolean(token))
				return false;
			
			// Push a note to ourselves on the stack to signal that an if-statement has begun.
			stack.push("if");
			
			// Loop until there are no more tokens, i.e. the entire input is traversed
			while (splitter.hasMoreTokens()) {

				// Get the next token of the input
				token = splitter.nextToken();

				if (token.equals("then")) {

					// We can only see "then" after "if BOOLEAN". In other words,
					// there should be "if" on the stack.
					
					top = stack.pop();
					if (!top.equals("if"))
						return false;
					
					// We passed the "then" token. Write a note to ourselves to remember it.
					stack.push("then");
					
					// Now we expect to see either an assignment, or "if"
					token = splitter.nextToken();
					
					if (token.equals("if")) {
						
						// We see "if", the next token should be a boolean
						token = splitter.nextToken();
						if (!isBoolean(token))
							return false;
						
						// We saw "if BOOLEAN", so push "if" on the stack
						stack.push("if");
						
					}
					
					else if (!isAssignment(token))
						return false;

				}

				else if (token.equals("else")) {

					// This part is very similar to the "if" part.
					// "else" can only occur some time after seeing "then", so check the stack
					
					top = stack.pop();
					if (!top.equals("then"))
						return false;

					// We passed the "else" token. Write a note to ourselves to remember it.
					stack.push("else");

					// Now we expect to see either an assignment, or "if"
					token = splitter.nextToken();

					if (token.equals("if")) {

						// We see "if", the next token should be a boolean
						token = splitter.nextToken();
						if (!isBoolean(token))
							return false;

						// We saw "if BOOLEAN", so push "if" on the stack
						stack.push("if");

					}

					else if (!isAssignment(token))
						return false;

				}

				else if (token.equals("end")) {

					// "end" can only occur some time after seeing "else", so check the stack
					
					top = stack.pop();
					if (!top.equals("else"))
						return false;
					
				}

				else
					return false;

			}
			
			// At the end there should be nothing left on the stack.
			return stack.isEmpty();
			
		}

	}
	
	
	public static boolean parse3 (StringSplitter splitter) {
		
		int count = splitter.countTokens();
		
		// The empty input was NOT graded. We put this check here for completeness only.
		if (count == 0)
			return true;

		// If there's only one token, it must be an assignment.
		else if (count == 1)
			return isAssignment(splitter.nextToken());
		else	 
			if (parseRecursive(splitter) && !(splitter.hasMoreTokens())){
				return true;
			}
			else{ 
				return false;
			}
	}
	
	//  This method takes a splitter and returns true if there
	//  is a prefix that is a statement, in which case the splitter
	//  has advanced to the next token beyond the prefix (if such a
	//  token exists).  Otherwise (there is no prefix that is a statement), 
	//  it returns false.
	
	public static boolean parseRecursive (StringSplitter splitter) {

		String token;

		token = splitter.nextToken();

		if (isAssignment(token))
			return true;
		else
			if (!token.equals("if")){
				return false;
			}
			else {
				token = splitter.nextToken();
				if (!isBoolean(token)){
					return false;   
				}
				else {
					token = splitter.nextToken();
					if (!token.equals("then")){
						return false;
					}		
					else if (!parseRecursive(splitter)){
						return false;
					}
					else {				
						token = splitter.nextToken();
						if (!token.equals("else")){
							return false;	
						}		
						else{
							if (!parseRecursive(splitter)){
								return false;
							}
							else {
								token = splitter.nextToken();
								if (!token.equals("end")){
									return false;	
								}
							}
						}
					}
				}
			}
			return true;		
		}
}