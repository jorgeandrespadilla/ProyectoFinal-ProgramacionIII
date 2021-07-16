package estructurasdatos;

import java.util.Iterator;

/**
 *
 * @author Jorge Padilla
 * @param <T>
 */
public class ListIterator<T> implements Iterator<T> {
    Node<T> current;
      
    public ListIterator(List<T> list)
    {
        current = list.startNode();
    }
    
    @Override
    public boolean hasNext() {
        return current != null;
    }
    
    @Override
    public T next() {
        T data = current.getObject();
        current = current.getNext();
        return data;
    }
}
