//Zackary McMurtry
//CSC 364
//This program is a doubly linked circular list which also defines an iterator class

import java.util.*;

public class MyDoublyLinkedList<E> extends MyAbstractSequentialList<E> implements Cloneable {
	private Node<E> head = new Node<E>(null);

	/** Create a default list */
	public MyDoublyLinkedList() {
		head.next = head;
		head.previous = head;
	}

	private static class Node<E> {
		E element;
		Node<E> previous;
		Node<E> next;

		public Node(E element) {
			this.element = element;
		}
	}

	public String toString() {
		StringBuilder result = new StringBuilder("[");

		Node<E> current = head.next;
		for (int i = 0; i < size; i++) {
			result.append(current.element);
			current = current.next;
			if (current != head) {
				result.append(", "); // Separate two elements with a comma
			}
		}
		result.append("]"); // Insert the closing ] in the string

		return result.toString();
	}

	private Node<E> getNode(int index) {
		Node<E> current = head;
		if (index < size / 2)
			for (int i = -1; i < index; i++)
				current = current.next;
		else
			for (int i = size; i > index; i--)
				current = current.previous;
		return current;
	}

	@Override
	public void add(int index, E e) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		Node<E> prev = getNode(index - 1);
		Node<E> next = prev.next;
		Node<E> newNode = new Node<E>(e);
		prev.next = newNode;
		next.previous = newNode;
		newNode.previous = prev;
		newNode.next = next;
		size++;
	}

	//Clear the list
	@Override
	public void clear() {
		this.head.next = this.head;
		this.head.previous = this.head;
		this.size = 0;
	}

	@Override
	public boolean contains(E o) {
		for (Node<E> current = head.next; current != head; current = current.next) {
			E e = current.element;
			if (o == null ? e == null : o.equals(e))
				return true;
		}
		return false;
	}

	//Gets the element at a specified index
	@Override
	public E get(int index) {
		if (size == 0) {
			throw new NoSuchElementException("Index: " + index + ", Size: " + size);
		} else if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		
		Node<E> returnNode = getNode(index);
		
		return returnNode.element;
	}

	//Return the first index of an element
	@Override
	public int indexOf(E e) {
		Node<E> current = this.head.next;
		int pos;
		
		for(pos = 0; pos < this.size; pos++) {
			if (current.element == null) {
				if (current.element == e) {
					break;
				}
			} else {
				if (current.element.equals(e)) {
					break;
				}
			}
			current = current.next;
		}
		
		return pos;
	}

	//Returns the last index of an element
	@Override
	public int lastIndexOf(E e) {
		Node<E> current = this.head.previous;
		int pos;
		
		for(pos = size - 1; pos > 0; pos--) {
				if (current.element == null ? e == null : current.element.equals(e)) {
					break;
				}
			current = current.previous;
		}
		
		return pos;
	}

	//Removes the node at a given index
	@Override
	public E remove(int index) {
		if (size == 0) {
			throw new NoSuchElementException("Index: " + index + ", Size: " + size);
		} else if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		
		Node<E> previousNode = getNode(index - 1);
		Node<E> removeNode = previousNode.next;
		Node<E> nextNode = removeNode.next;
		
		previousNode.next = removeNode.next;
		nextNode.previous = removeNode.previous;
		
		size--;
		return removeNode.element;
	}

	//Sets the element at a given index to a new element
	@Override
	public Object set(int index, E e) {
		if (size == 0) {
			throw new NoSuchElementException("Index: " + index + ", Size: " + size);
		} else if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		
		Node<E> removeNode = getNode(index);
		removeNode.element = e;
		
		return removeNode.element;
	}

	//Returns the element of the first node
	@Override
	public E getFirst() {
		return head.next.element;
	}

	//Returns the element of the last node
	@Override
	public E getLast() {
		return head.previous.element;
	}

	//Adds a new element to the start
	@Override
	public void addFirst(E e) {
		add(0, e);
	}

	//Adds a new element to the end
	@Override
	public void addLast(E e) {
		add(size, e);
	}

	//Removes the first element
	@Override
	public E removeFirst() {
		E removedVal = remove(0);
		return removedVal;
	}

	//Removes the last element
	@Override
	public E removeLast() {
		E removedVal = remove(size - 1);
		return removedVal;
	}
	
	//Hard copies this to another object and returns it
	public Object clone() {
		try {
			@SuppressWarnings("unchecked")
			MyDoublyLinkedList<E> newClone = (MyDoublyLinkedList<E>) super.clone();
			newClone.head = new Node<E>(null);
			newClone.head.next = newClone.head;
			newClone.head.previous = newClone.head;
			newClone.size = 0;
			Node<E> current = this.head.next;
			
			for(int x = 0; x < this.size; x++) {
				newClone.add(current.element);
				current = current.next;
			}
			
			return newClone;
		} catch (CloneNotSupportedException ex) {
			throw new RuntimeException();
		}
	}
	
	//Equals method to see if other equals this
	@SuppressWarnings("unchecked")
	public boolean equals(Object other) {
		if (this == other) return true;
		else if (other == null) return false;
		else if (other.getClass() != this.getClass()) {
			return false;
		}
		
		if(((MyDoublyLinkedList<E>)other).size != this.size) return false;
		else {
			ListIterator<E> thisIter = this.listIterator(0);
			ListIterator<E> otherIter = ((MyDoublyLinkedList<E>) other).listIterator(0);
			
			while(thisIter.hasNext()) {
				E thisNext = thisIter.next();
				E otherNext = otherIter.next();
				if(!(thisNext == null ? otherNext == null : thisNext.equals(otherNext))) {
					return false;
				}
			}
		}
		
		return true;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new MyDoublyLinkedListIterator(index);
	}

	private static enum ITERATOR_STATE {
		CANNOT_REMOVE, CAN_REMOVE_PREV, CAN_REMOVE_CURRENT
	};

	private class MyDoublyLinkedListIterator implements ListIterator<E> {
		private Node<E> current; // node that holds the next element in the
									// iteration
		private int nextIndex; // index of current
		ITERATOR_STATE iterState = ITERATOR_STATE.CANNOT_REMOVE;

		private MyDoublyLinkedListIterator(int index) {
			if (index < 0 || index > size)
				throw new IndexOutOfBoundsException("iterator index out of bounds");
			current = getNode(index);
			nextIndex = index;
		}

		//Add a new element to the list iterator
		@Override
		public void add(E arg0) {
			Node<E> newNode = new Node<E>(arg0);
			
			if (hasPrevious()) {
				current.previous.next = newNode;
				newNode.previous = current.previous;
			}
			current.previous = newNode;
			newNode.next = current;
			
			size++;
		}

		//Returns if there is another element in the list iterator
		@Override
		public boolean hasNext() {
			return nextIndex < size;
		}

		//Return if there is a previous element in the list iterator
		@Override
		public boolean hasPrevious() {
			return nextIndex -1 >= 0;
		}

		//Returns the next element in the list iterator and advances forward
		@Override
		public E next() {
			if (nextIndex >= size)
				throw new NoSuchElementException();
			E returnVal = current.element;
			current = current.next;
			nextIndex++;
			iterState = ITERATOR_STATE.CAN_REMOVE_PREV;
			return returnVal;
		}

		//Returns the next index value
		@Override
		public int nextIndex() {
			return nextIndex;
		}

		//Returns the previous element in the list iterator and steps back
		@Override
		public E previous() {
			if (nextIndex - 1 < 0) {
				throw new NoSuchElementException();
			}
			
			E returnVal = current.previous.element;
			current = current.previous;
			nextIndex--;
			iterState = ITERATOR_STATE.CAN_REMOVE_CURRENT;
			return returnVal;
		}

		//Returns the previous index
		@Override
		public int previousIndex() {
			return nextIndex - 1;
		}

		//Removes the element based upon the iterator principles
		@Override
		public void remove() {
			switch (iterState) {
			case CANNOT_REMOVE:
				throw new IllegalStateException();
			case CAN_REMOVE_PREV:
				Node<E> prev = current.previous.previous;
				
				prev.next = current;
				current.previous = prev;
				
				iterState = ITERATOR_STATE.CANNOT_REMOVE;
				size--;
				break;
			case CAN_REMOVE_CURRENT:
				current.next.previous = current.previous;
				current.previous.next = current.next;
				current = current.next;
				
				iterState = ITERATOR_STATE.CANNOT_REMOVE;				
				size--;
				break;
			}

		}

		//Sets the element based upon the iterator principles
		@Override
		public void set(E arg0) {
			switch (iterState) {
			case CANNOT_REMOVE:
				throw new IllegalStateException();
			case CAN_REMOVE_PREV:
				current.previous.element = arg0;
				
				iterState = ITERATOR_STATE.CANNOT_REMOVE;
				break;
			case CAN_REMOVE_CURRENT:
				current.element = arg0;
				
				iterState = ITERATOR_STATE.CANNOT_REMOVE;
				break;
			}
			
		}
	}
}
