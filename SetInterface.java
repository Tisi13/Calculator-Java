package assignment2;




/** ADT for the class Set<E extends Data<E>>
 *
 * @author Iliana Kyritsi, Tisiana Henricus
 * @elements
 *	Elements of type E.
 * @structure
 *	none
 * @domain
 *	an infinite amount of element type E.
 *
 * @constructor
 *	Set<E>();
 *	    <dl>
 *		<dt><b>PRE-condition</b><dd>
 *        -
 *		<dt><b>POST-condition</b><dd>
 *       The new Set-object is empty.
 *	    </dl>
 *	<br>
 **/

public interface SetInterface<E extends Comparable<E>> {
	/**
	 * Initializes the Set object to empty.
	 *
	 * @precondition -
	 * @postcondition -the Set does not contain any element.
	 **/
	SetInterface<E> init();


	/** Adds element to the set
	 * @precondition
	 *	    -
	 * @postcondition
	 *	   The set now contains the object element
	 **/
	void addElement(E element); //throws Exception;

	/** Returns an element from the set.
	 * @precondition
	 *	    The set is not empty.
	 * @postcondition
	 *	    A copy of the element has been returned.
	 **/
	E getElement();

	/** Removes the ElementType element from the set.
	 * @precondition
	 *	    the set is not empty
	 * @postcondition
	 *	    an element has been removed from the set.
	 **/
	void removeElement() throws APException;

	/** Checks if the set object is empty.
	 * @precondition
	 *	    -
	 * @postcondition
	 *	     TRUE: The set .
	 *		 FALSE: The set is not empty.
	 **/
	boolean isEmpty();

	/** takes a complement of a set from the set
	 * @precondition
	 *	    -
	 * @postcondition
	 *	    The complement of set from a set is returned.
	 **/
	SetInterface<E> complement(Set<E> set);

	/** Intersects a set with the set
	 * @precondition
	 *	    -
	 * @postcondition
	 *	    The intersection between two sets is returned.
	 **/
	SetInterface<E> intersection(Set<E> set);

	/** Unifies a set with the set
	 * @precondition
	 *	    -
	 * @postcondition
	 *	    The unification between two sets is returned.
	 **/
	SetInterface<E> union(Set<E> set) throws APException;

	/** Calculates symmetric difference between the argument Set and the Set
	 * @precondition
	 *	    -
	 * @postcondition
	 *	    The symmetric difference between two sets is returned.
	 **/
	SetInterface<E> symmetricDifference(Set<E> set) throws APException;

	/** Returns the number of elements in a set.
	 * 	@precondition
	 * 		-
	 * 	@postcondition
	 * 		The number of elements in a set is returned.
	 **/
	int numberOfElements();

	/** Checks if set contains element of object E
	 * @precondition
	 *   -
	 * @postcondition
	 *      True: if set contains  element of object E.
	 *      False: if the set does not contain element of object E.
	 **/
	boolean contains(E element);

}
