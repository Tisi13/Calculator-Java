package assignment2;

public class List<E extends Comparable<E>> implements ListInterface<E> {
	private class Node {
		E data;
		Node prior, next;

		public Node(E d) {
			this(d, null, null);
		}

		public Node(E data, Node prior, Node next) {
			this.data = data == null ? null : data;
			this.prior = prior;
			this.next = next;
		}
	}

	Node current;
	public int listSize;

	public List() { init(); }

	public boolean isEmpty() { return listSize == 0; }

	public List<E> init() {
		current = null;
		listSize = 0;
		return this;
	}

	@Override
	public int size() { return listSize;}

	@Override
	public List<E> insert(E d) {
		if (isEmpty()) {
			current = new Node(d);
			listSize++;
			return this;
		}

		/* will place current either right before or right after place to insert */
		find(d);


		/* there is already an element of equal to d */
		if (current.data.compareTo(d) == 0) {
			/* place new node after current, change current to new node */
			Node temp1 = new Node(d, current, current.next);
			if (current.next != null) {
				current.next = temp1;
				temp1.next.prior = temp1;
				current = temp1;
			} else {
				current.next = temp1;
				current = temp1;
			}

			listSize++;

		}else if (current.data.compareTo(d) > 0) {
        /* current is higher than d, insert between current.prev and current */
			/* your code NOTE: current.prev could be null */
			Node temp2 = new Node(d, current.prior, current);
			if (current.prior != null) {
				current.prior = temp2;
				temp2.prior.next = temp2;
				current = temp2;
			} else {
				current.prior = temp2;
				current = temp2;
			}

			listSize++;
		}else {
		/* current is lower than d, insert between current and current.next */
			/* you code NOTE: current.next could be null */
				Node temp3 = new Node(d, current, current.next);
				if (current.next != null) {
					current.next = temp3;
					temp3.next.prior = temp3;
					current = temp3;
				} else {
					current.next = temp3;
					current = temp3;
				}

				listSize++;
		}

		return this;
	}

	@Override
	public E retrieve() {
		if (isEmpty()) {
			return null;
		}
		return current.data;
	}

	@Override
	public List<E> remove()  {
		if (isEmpty()) {
			return this;
		}

		Node prevNode = current.prior;
		Node nextNode = current.next;

		listSize--;

		/* if it's not the first node */
		if (prevNode != null) {
			prevNode.next = nextNode;
		}

		/* if it's not the last node */
		if (nextNode != null) {
			nextNode.prior = prevNode;
		}

		/* if we removed the last element */
		if (nextNode == null && prevNode == null) {
			current = null;
		} else {
			current = nextNode != null ? nextNode : prevNode;
		}

		return this;

	}

	@Override
	public boolean find(E d) {
		if (isEmpty())
			return false;

		if (current.data.compareTo(d) == 0) {
			/* current holds a node that contains d */
			return true;
		}
		if (current.data.compareTo(d) > 0) {
			/* current holds a value larger than d */
			 /* go back to a point where current.data is <= d or the beginning if
			 * d is smaller than all in list*/
			while (current.data.compareTo(d) > 0 && goToPrevious()) {
			}
			/* if we arrived at a point where current.data == d, then we can
			 * return true */
			if (current.data.compareTo(d) == 0)
				return true;
			/* otherwise current was either the first node, or every node before
			 * current is < d*/
			return false;
		} else {
			/* current holds a value smaller than d */
			/* go back to a point where current.data is >= d or the end if d is
			 * larger than all in list */
			while (current.data.compareTo(d) < 0 && goToNext()) {
			}
			/* if we arrived at a point where current.data == d, then we can
			 * return true */
			if (current.data.compareTo(d) == 0)
				return true;
			/* otherwise current was either the last node, or every node before
			 * current is > d*/
			return false;
		}

	}

	@Override
	public boolean goToFirst() {
		if (isEmpty()) {
			return false;
		}

		while (current.prior != null) {
			current = current.prior;
		}

		return true;
	}

	@Override
	public boolean goToLast() {
		if (isEmpty()) {
			return false;
		}

		while (current.next != null) {
			current = current.next;
		}

		return true;
	}

	@Override
	public boolean goToNext() {
		if (isEmpty()) {
			return false;
		} else if (current.next == null) {
			return false;
		} else {
			current = current.next;
			return true;
		}
	}

	@Override
	public boolean goToPrevious() {
		if (isEmpty()) {
			return false;
		} else if (current.prior == null) {
			return false;
		} else {
			current = current.prior;
			return true;
		}
	}

	@Override
	public List<E> copy() throws APException {

		List<E> result = new List<>();

		/* if it's empty, return an empty list */
		if (!goToFirst()) {
			return result;
		}
		/* current now points to first node*/
		goToNext();
		result.insert(current.data);
		/*if there is next*/
		while(!goToNext()) {
			result.insert(current.data);
		}

		return result;

	}

}
