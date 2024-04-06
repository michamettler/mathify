package ch.zhaw.mathify.repository;

import java.util.List;

/**
 * @param <T> the type of the repository
 */
public interface Repository<T> {
    /**
     * @param t the object to add to the repository
     */
    void add(T t);

    /**
     * @param t the object to remove from the repository
     */
    void remove(T t);

    /**
     * @return a list of all objects in the repository
     */
    List<T> get();

    /**
     * Saves the repository to a file
     */
    void save();

    /**
     * Loads the repository from a file
     */
    void load();
}
