package assignment2;

/** ADT for the class Identifier 
 *
 * @author Tisiana Henricus, Iliana Kyritsi
 * @elements
 * Strings of the Type String.
 * @structure
 * linear
 * @domain
 *      The identifier consists of alphanumeric characters. The first
 *      character of an identifier is a letter. The identifier cannot be empty.
 * @constructor
 * Identifier(String s);
 *     <dl>
 *  <dt><b>PRE-condition</b><dd>
 *          -
 *  <dt><b>POST-condition</b><dd>
 *          Identifier contains String s.
 *     </dl>
 *    	<br>
 **/

public interface IdentifierInterface {

	/** Return the value of an Identifier object.
	 * @precondition
	 *	   -
	 * @postcondition
	 * 	   The value of the identifier is returned as a String.
	 * */
	public String getValue();

}
