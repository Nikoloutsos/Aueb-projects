import java.io.PrintStream;
import java.util.NoSuchElementException;

public class IntQueueImplementation<T> implements IntQueue<T> {
	private int size;
	Node<T> head = new Node<>(); 
	Node<T> tail;
	@Override
	public boolean isEmpty() {
		return size()==0;
	}

	@Override
	public void put(T item) {
		Node<T> newNode = new Node<>();
		newNode.data = item;
		if(head.next == null) {
			head.next = newNode;
			tail = newNode;
		} else {
			tail.next = newNode;
			tail = newNode;
		}
		size++;
	}

	@Override
	public T get() throws NoSuchElementException {
		if (head.next ==null) throw new NoSuchElementException();
		T data = head.next.data;
		head.next = head.next.next;
		size--;
		return data;
	}

	@Override
	public T peek() throws NoSuchElementException {
		if (head.next ==null) throw new NoSuchElementException();
		return head.next.data;
	}

	@Override
	public void printQueue(PrintStream stream) {
		Node<T> k=head;
		while(k.next!=null) {
			stream.println(k.next.data);
			k=k.next;
		}
	

	}

	@Override
	public int size() {
		return size;
	}
	
	
	public Node<T> getHead(){
		return head.next;
	}

}



