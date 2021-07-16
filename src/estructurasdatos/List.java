package estructurasdatos;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Jorge Padilla
 */
public class List<T> implements Iterable<T> {

    private Node<T> start;
    private Node<T> end;
    private int size;

    public List() {
        start = end = null;
        size = 0;
    }

    public T start() {
        return this.start.getObject();
    }

    public Node<T> startNode() {
        return this.start;
    }

    public T end() {
        return this.end.getObject();
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return start == null && end == null;
    }

    public int indexOf(T object) {
        Node<T> temp = start;
        for (int i = 0; i < size(); i++) {
            if (temp == object) {
                return i;
            }
            temp = temp.getNext();
        }
        return -1;
    }

    public boolean contains(T object) {
        return (indexOf(object) != -1);
    }

    public void clear() {
        start = end = null;
        size = 0;
    }

    public void show() {
        if (isEmpty()) {
            System.out.println("La lista se encuentra vacía.");
        } else {
            Node<T> temp = start;
            while (temp != null) {
                System.out.println(temp.getObject());
                temp = temp.getNext();
            }
        }
    }

    public void showReverse() {
        if (isEmpty()) {
            System.out.println("La lista se encuentra vacía.");
        } else {
            Node<T> temp = end;
            while (temp != null) {
                System.out.println(temp.getObject());
                temp = temp.getPrevious();
            }
        }
    }

    public T get(int index) throws Exception {
        if (isEmpty()) {
            throw new Exception("La lista se encuentra vacía.");
        }
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("No existe el índice expecificado.");
        }
        int counter = 0;
        Node<T> temp = start;
        while (counter < index) {
            counter++;
            temp = temp.getNext();
        }
        return temp.getObject();
    }

    private Node<T> getNode(int index) {
//        if (isEmpty()) {
//            throw new Exception("La lista se encuentra vacía.");
//        }
//        if (index < 0 || index > size()) {
//            throw new IndexOutOfBoundsException("No existe el índice expecificado.");
//        }
        int counter = 0;
        Node<T> temp = start;
        while (counter < index) {
            counter++;
            temp = temp.getNext();
        }
        return temp;
    }

    public void set(int index, T object) throws Exception {
        if (isEmpty()) {
            throw new Exception("La lista se encuentra vacía.");
        }
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("No existe el índice expecificado.");
        }
        int counter = 0;
        Node<T> temp = start;
        while (counter < index) {
            counter++;
            temp = temp.getNext();
        }
        temp.setObject(object);
    }

    public void addStart(T object) {
        Node<T> newNode = new Node(object, null, start);
        if (isEmpty()) {
            start = end = newNode;
        } else {
            start.setPrevious(newNode);
            start = newNode;
        }
        size++;
    }

    public void addStart(List<T> objects) {
        if (isEmpty()) {
            start = objects.start;
            end = objects.end;
        } else {
            objects.end.setNext(start);
            end.setPrevious(objects.end);
            start = objects.start;
        }
        size += objects.size();
    }

    public void addEnd(T object) {
        Node<T> newNode = new Node(object, end, null);
        if (isEmpty()) {
            start = end = newNode;
        } else {
            end.setNext(newNode);
            end = newNode;
        }
        size++;
    }

    public void addEnd(List<T> objects) {
        if (isEmpty()) {
            start = objects.start;
            end = objects.end;
        } else {
            objects.start.setPrevious(end);
            end.setNext(objects.start);
            end = objects.end;
        }
        size += objects.size();
    }

    public void add(int index, T object) throws Exception {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("No existe el índice expecificado.");
        }
        Node<T> temp = start;
        int counter = 0;
        while (counter < index) {
            counter++;
            temp = temp.getNext();
        }
        if (isEmpty()) {
            Node<T> newNode = new Node(object, null, null);
            start = end = newNode;
        } else {
            Node<T> newNode = new Node(object, temp.getPrevious(), temp);
            if (temp.getPrevious() == null) {
                start = newNode;
            } else {
                temp.getPrevious().setNext(newNode);
            }
            temp.setPrevious(newNode);
        }
        size++;
    }

    public void add(int index, List<T> objects) throws Exception {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("No existe el índice expecificado.");
        }
        Node<T> temp = start;
        int counter = 0;
        while (counter < index) {
            counter++;
            temp = temp.getNext();
        }
        if (isEmpty()) {
            start = objects.start;
            end = objects.end;
        } else {
            Node<T> tempPrevious = temp.getPrevious();
            objects.start.setPrevious(tempPrevious);
            objects.end.setNext(temp);
            if (tempPrevious == null) {
                start = objects.start;
            } else {
                tempPrevious.setNext(objects.start);
            }
            temp.setPrevious(objects.end);
        }
        size += objects.size();
    }

    public T removeStart() throws Exception {
        if (isEmpty()) {
            throw new Exception("La lista se encuentra vacía.");
        }
        Node<T> temp = start;
        if (start == end && start != null) {
            start = end = null;
        } else {
            start = start.getNext();
            start.setPrevious(null);
            temp.setNext(null);
        }
        size--;
        return temp.getObject();
    }

    public T removeEnd() throws Exception {
        if (isEmpty()) {
            throw new Exception("La lista se encuentra vacía.");
        }
        Node<T> temp = end;
        if (start == end && start != null) {
            start = end = null;
        } else {
            end = end.getPrevious();
            end.setNext(null);
            temp.setPrevious(null);
        }
        size--;
        return temp.getObject();
    }

    public T remove(int index) throws Exception {
        if (isEmpty()) {
            throw new Exception("La lista se encuentra vacía.");
        }
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("No existe el índice expecificado.");
        }
        Node<T> temp = start;
        int counter = 0;
        while (counter < index) {
            counter++;
            temp = temp.getNext();
        }
        if (temp == start) {
            return removeStart();
        }
        if (temp == end) {
            return removeEnd();
        }
        Node<T> tempPrevious = temp.getPrevious();
        Node<T> tempNext = temp.getNext();
        tempPrevious.setNext(tempNext);
        tempNext.setPrevious(tempPrevious);
        temp.setPrevious(null);
        temp.setNext(null);
        size--;
        return temp.getObject();
    }

    public LinkedList<T> toJavaLinkedList() {
        LinkedList<T> linkedList = new LinkedList<>();
        for (T element : this) {
            linkedList.add(element);
        }
        return linkedList;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator(this);
    }
    
}
