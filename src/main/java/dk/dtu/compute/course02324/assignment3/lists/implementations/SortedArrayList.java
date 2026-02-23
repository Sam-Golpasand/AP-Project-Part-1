package dk.dtu.compute.course02324.assignment3.lists.implementations;

import dk.dtu.compute.course02324.assignment3.lists.types.List;
import dk.dtu.compute.course02324.assignment3.lists.types.SortedList;

import jakarta.validation.constraints.NotNull;

/**
 * An implementation of the interface {@link SortedList} based on the
 * {@link ArrayList} implementation of the interface{@link List}
 * arrays, which dynamically are adapted in size when needed.
 *
 * @param <E> the type of the list's elements.
 */
public class SortedArrayList<E extends Comparable<E>> extends ArrayList<E> implements SortedList<E> {

    @Override
    public boolean add(@NotNull E e) {

        validateNotNull(e);

        int index = findIndexToInsert(e);
        // reuse the same add function as the parent class.
        super.add(index, e);

        return true;
    }

    @Override
    public boolean add(int pos, @NotNull E e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Operation add(int pos, E e) not allowed on SortedLists");
    }

    @Override
    public E set(int pos, @NotNull E e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Operation set(int pos, E e) not allowed on SortedLists");
    }

    /**
     * Finds the position at which a given element should be inserted
     * to the sorted list. The element must not be <code>null</code>.
     * The implementation finds this position by linearly going through
     * the array, stopping at the first element greater or equal to
     * this element.
     *
     * @param e the given element to be inserted
     * @return the position at which the element should be inserted
     */
    private int findIndexToInsert(@NotNull E e) {

        validateNotNull(e);

        for (int i = 0; i < size(); i++) {
            if (e.compareTo(get(i)) <= 0) {
                return i;
            }
        }

        return size();
    }

}
