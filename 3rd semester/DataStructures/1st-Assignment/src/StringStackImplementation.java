import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringStackImplementation<T> implements StringStack<T> {
    private int size;
    Node<T> head = new Node<>(); 
    
	@Override
	public boolean isEmpty() {
		return size()==0;
	}

	@Override
	public void push(T item) {
		Node <T> newNode = new Node<>();
		newNode.data = item;
		newNode.next = head;
		head = newNode;
		size++;
	}

	@Override
	public T pop() throws NoSuchElementException {
		if (head.next == null)throw new NoSuchElementException();
		T data = head.data;
		head = head.next;
		size--;
		return data;
	}

	@Override
	public T peek() throws NoSuchElementException {
		if(head.next!=null) return head.data;
		throw new NoSuchElementException();
	}

	@Override
	public void printStack(PrintStream stream) {
		Node<T>k=head;
		while(k.next!=null) {
			stream.println(k.data);
			k=k.next;
		}

	}

	@Override
	public int size() {
		
		return size;
	}

}
