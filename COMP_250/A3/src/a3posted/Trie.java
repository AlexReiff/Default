package a3posted;

//  COMP 250 - Introduction to Computer Science - Fall 2012
//  Assignment #3 - Question 1

//Alexander Reiff
//260504962

import java.util.*;

// Trie class.  Each node is associated with a prefix of some key 
// stored in the trie.   (Note any string is a prefix of itself.)

public class Trie
{
   private TrieNode root;

   // Empty trie has just a root node.  All the children are null.

   public Trie() 
   {
	   root = new TrieNode();
   }

   public TrieNode getRoot(){
	   return root;
   }
 
   // Return true if key is contained in the trie (i.e. it was added by insert), false otherwise

   public boolean contains(String key)
   {
	   //finds the largest prefix in order to save time
		TrieNode curr = getPrefixNode(key);
		int charIndex = curr.getDepth();
		while(charIndex < key.length()){
			if(curr.getChild(key.charAt(charIndex)) != null){
				curr = curr.getChild(key.charAt(charIndex));
			}
			else{
				return false;
			}
			charIndex++;
		}
		//returns true if the key is also a word in the trie
		return curr.isEndOfKey();
   }
    
   // Insert key into the trie.  The first step should be finding the longest 
   // prefix of key already in the trie (use getPrefixNode() below).  
   // Then add TrieNodes in such a way that the key is inserted.

   public void insert(String key)
   {
	   //finds the largest prefix already in the tree
		TrieNode curr = getPrefixNode(key);
		int charIndex = curr.getDepth();
		//goes through all remaining characters not in the tree
		while(charIndex < key.length()){
			//adds a node if need be
			if(curr.getChild(key.charAt(charIndex)) == null){
				TrieNode last = curr.createChild(key.charAt(charIndex));
				//if it's the last character, tell the node that
				if(charIndex == key.length() - 1){
					last.setEndOfKey(true);			
				}
			}
			curr = curr.getChild(key.charAt(charIndex));
			charIndex++;
		}  
   }
   
   // insert each key in the list (keys)

   public void loadKeys(ArrayList<String> keys)
   {
      for (int i = 0; i < keys.size(); i++)
      {
         //System.out.println("Inserting " + keys.get(i));
         insert(keys.get(i));
      }
      return;
   }

   // Return the TrieNode corresponding the longest prefix of a key that is found. 
   // If no prefix is found, return the root. 
   // In the example in the PDF, running getPrefixNode("any") should return the
   // dashed node under "n", since "an" is the longest prefix of "any" in the trie. 
   // getPrefixNode("addition") should return the node which is the first 
   // child of the root since "a" is the longest prefix of "addition" in the trie.
   
   private TrieNode getPrefixNode(String word)
   {
	   TrieNode curr = root;
	   int charIndex = 0;
	   //goes through every character in the key
	   while(charIndex < word.length()){
		   //if the next character is in the tree
		   if(curr.getChild(word.charAt(charIndex)) != null){
			   curr = curr.getChild(word.charAt(charIndex));
		   }
		   //otherwise this is the longest prefix
		   else{
			   return curr;
		   }
		   charIndex++;
	   }
	   return curr;
   }

   // Similar to getPrefixNode() but now return the prefix as a String, 
   // rather than as a TrieNode.   

   public String getPrefix(String word)
   {
	   TrieNode temp = getPrefixNode(word);
	   if(temp.equals(root)){
		   return "";
	   }
	   return temp.toString();
   }

     
   // Return a list of all keys in the trie that have the given prefix.  

   public ArrayList<String> getAllPrefixMatches( String prefix )
   {
	   //finds the correct prefix in the trie
	   TrieNode runner = getPrefixNode(prefix);
	   ArrayList<String> running = new ArrayList<String>();
	   //checks if there is no prefix in the tree
	   if(runner == root){
		   return running;
	   }
	   //if the current node is a word, add it to the list
	   if(runner.isEndOfKey()){
		   running.add(runner.toString());
	   }
	   //finds all of the nodes non-null children
	   //and recursively calls getAllPrefixMatches() for the children
	   for(int x = 0; x < 256; x++){
		   TrieNode currKid = runner.getChild((char) x);
		   if(currKid != null){
			   ArrayList<String> tempAL =  getAllPrefixMatches(currKid.toString());
			   for(int y = 0; y < tempAL.size(); y++){
				   running.add(tempAL.get(y));
			   }
		   }
	   }
	   //returns all of the ArrayLists
	   return running;
   }
   
}
