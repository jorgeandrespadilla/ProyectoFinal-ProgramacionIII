package estructurasdatos;

/**
 *
 * @author Jorge Padilla
 * @param <T>
 */
public class Node<T> {
    private T object;
    private Node<T> previous;
    private Node<T> next;

    public Node(T object, Node previous, Node next) {
        this.object = object;
        this.previous = previous;
        this.next = next;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
