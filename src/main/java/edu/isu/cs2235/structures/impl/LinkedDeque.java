package edu.isu.cs2235.structures.impl;

import edu.isu.cs2235.structures.Queue;
import java.util.Random;

/**
 *  @author Brandon Watkins
 */

/**
 *  @param <E> The type of element stored
 */
public class LinkedDeque <E extends Comparable> extends DoublyLinkedList<E> implements edu.isu.cs2235.structures.Deque<E>{

    Random rand = new Random();

    public LinkedDeque(){ super(); }

    /**
     * @return The value of the last element of the deque (without removing it),
     * or null if the deque is empty.
     */
    @Override
    public E peekLast() {
        return get(size() - 1);
    }

    /**
     * Inserts the given element into the front of the deque, unless the
     * provided value is null.
     *
     * @param element Element to be inserted to the front of the deque, nothing
     * happens if the value is null.
     */
    @Override
    public void offerFirst(E element) {
        addFirst(element);
    }

    /**
     * Inserts the given element onto the rear of the deque, unless the provided value is null.
     * @param element Element to be inserted to the end of the deque, nothing happens if the value is null.
     */
    public void offerLast(E element){
        addLast(element);
    }

    /**
     * @return The value of the last item in the Deque and removes that value
     * from the deque, if the deque was empty null is returned.
     */
    @Override
    public E pollLast() {
        if (isEmpty()) return null;
        return removeLast();
    }

    /**
    * @return The value of the first item in the Deque, and removes that value.
     * if the deque is empty, it returns null.
     */
    public E pollFirst() {
        if(isEmpty()) return null;
        return removeFirst();
    }

    /**
     * @return The number of elements in the queue
     */
    @Override
    public int size() {
        return super.size();
    }

    /**
     * @return tests whether the queue is empty.
     */
    @Override
    public boolean isEmpty() {
        if (size() > 0) return false;
        else return true;
    }

    /**
     * Inserts an element at the end of the queue.
     *
     * @param element Element to be inserted.
     */
    @Override
    public void offer(E element) {
        addLast(element);
    }

    /**
     * @return The value first element of the queue (with out removing it), or
     * null if empty.
     */
    @Override
    public E peek() {
        if(isEmpty()) return null;
        return first();
    }

    /**
     * @return The value of the first element of the queue (and removes it), or
     * null if empty.
     */
    @Override
    public E poll() {
        if(isEmpty()) return null;
        return removeFirst();
    }

    /**
     * Prints the contents of the queue starting at top, one item per line. Note
     * this method should not empty the contents of the queue.
     */
    @Override
    public void printQueue() {
        printList();
    }

    /**
     * Tranfers the contents of this queue into the provided queue. The contents
     * of this queue are to found in reverse order at the top of the provided
     * queue. This queue should be empty once the transfer is completed. Note
     * that if the provided queue is null, nothing is to happen.
     *
     * @param into The new queue onto which the reversed order of contents from
     * this queue are to be transferred to the top of, unless the provided queue
     * is null.
     */
    @Override
    public void transfer(Queue<E> into) {
        if (into != null && this.size() > 0){
            int listSize = size();
            for (int i = 0; i < listSize; i++){
                into.offer(this.removeLast());
            }
        }
    }

    /**
     * Reverses the contents of this queue.
     * Couldn't think of a better way to do this without altering existing classes.
     * removes, and replaces, all nodes.
     */
    @Override
    public void reverse() {
        int j = size() - 1;
        for (int i = 0; i < size() / 2; i++){
            E tempLeftValue = get(i);
            remove(i);
            insert(get(j - 1), i);
            remove(j);
            insert(tempLeftValue, j);
            j--;
        }
    }

    /**
     * Merges the contents of the provided queue onto the bottom of this queue.
     * The order of both queues must be preserved in the order of this queue
     * after the method call. Furthermore, the provided queue must still contain
     * its original contents in their original order after the method is
     * complete. If the provided queue is null, no changes should occur.
     *
     * @param from Queue whose contents are to be merged onto the bottom of
     * this queue.
     */
    @Override
    public void merge(Queue<E> from) {
        if (from != null && from.size() > 0){
            int listSize = from.size();
            Queue<E> tempQ = new LinkedQueue<E>();
            for (int i = 0; i < listSize; i++){
                E tempE = from.poll();
                tempQ.offer(tempE);
                this.offer(tempE);
            }
            for (int i = 0; i < listSize; i++){
                from.offer(tempQ.poll());
            }
        }
    }


    @Override
    public void enterLine(E element) {
        if (rand.nextInt(10) % 2 == 0) offerFirst((E)element);
        else offerLast((E)element);
    }

    @Override
    public E leaveLine() {
        if(isEmpty()) return null;
        E item1 = first();
        E item2 = last();
        boolean itemOneIsMore = item1.compareTo(item2) >= 0;
        if (item1.compareTo(item2) <= 0) return pollFirst();
        else return pollLast();
    }
}
