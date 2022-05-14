package main.java.so.util.queue;

import java.util.Iterator;

/**
 * @author Samuel f. Ruiz
 * @version 1.0
 * @since 1/07/20
 */
public class ListIterator<T> implements Iterator<T>{

    private Node<T> actual;

    public ListIterator(Node<T> node) {
        this.actual = node;
    }

    @Override
    public boolean hasNext() {
        return actual != null;
    }

    @Override
    public T next() {
        T element = actual.getData();
        actual = actual.getNext();
        return element;
    }
}
