package main.java.so.util.queue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.StringJoiner;
import java.util.function.Consumer;

/**
 * @author Samuel f. Ruiz
 * @version 1.0
 * @since 1/07/20
 */
public class Queue<T> implements Iterable<T>{

    private Node<T> head;
    private Comparator<T> tComparator;

    public Queue(Comparator<T> tComparator) {
        this.tComparator = tComparator;
    }

    /**
     * Agraga un dato al final de la cola
     *
     * @param data dato a agregar
     */
    public void push(T data) {
        if (data != null) {
            if (isEmpty()) {
                head = new Node<>(data);
            } else {
                last().setNext(new Node<>(data));
            }
        }
    }

    protected Node<T> last() {
        if (!isEmpty()) {
            Node<T> actual = head;
            while (actual.getNext() != null) {
                actual = actual.getNext();
            }
            return actual;
        }
        return null;
    }

    /**
     * obtiene y retira el primer dato de la cola
     *
     * @return primer dato de la cola
     */
    public T poll() {
        if (!isEmpty()) {
            Node<T> node = head;
            head = head.getNext();
            return node.getData();
        }
        return null;
    }

    /**
     * Obtiene el primer dato de la cola sin retirarlo
     *
     * @return primer dato
     */
    public T peek() {
        if (!isEmpty()) {
            return head.getData();
        }
        return null;
    }

    /**
     * Verifica si el dato ingresado está en la cola
     *
     * @param data dato a buscar
     * @return si el dato está o no
     */
    public boolean exist(T data) {
        if (!isEmpty()) {
            Node<T> node = head;
            while (node != null && tComparator.compare(node.getData(), data) != 0) {
                node = node.getNext();
            }
            return node != null;
        }
        return false;
    }

    /**
     * Verifica si la cola está vacia
     *
     * @return si esta vacia o no
     */
    public boolean isEmpty() {
        return head == null;
    }

    public String show(){
        if(!isEmpty()) {
            return head.getData().toString();
        }
        return "{}";
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<>(head);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Node<T> node = head;
        while (node != null) {
            action.accept(node.getData());
            node = node.getNext();
        }
    }
}
