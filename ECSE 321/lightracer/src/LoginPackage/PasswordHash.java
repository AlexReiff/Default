package LoginPackage;

import java.security.NoSuchAlgorithmException;

/**
 * The PasswordHash class contains methods for correctly hashing
 * a given string, which will be a password, and for displaying
 * the hash as a hex-string
 * 
 * @author Syed Irtaza Raza
 */
public class PasswordHash {

	/**
	 * Hashes an input string and returns the hash as a byte array
	 * 
	 * @param x   the string to be hashed
	 * @return   a byte array containing the hash
	 */
	public static byte[] computeHash(String x) {
		java.security.MessageDigest d =null;
		try {
			d = java.security.MessageDigest.getInstance("SHA-1");
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		d.reset();
		d.update(x.getBytes());
		return  d.digest();
	}
  		  
	/**
	 * Converts a hash stored in a byte array to a hex-string
	 * 
	 * @param b   the byte-array to be converted
	 * @return   the hex-string representation of the hash
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}		  	
		return sb.toString().toUpperCase();
	}   		  
}
