package de.interseroh.report.common;

/**
 * Generic Visitor Pattern Interface
 *
 * @author Ingo Düppe (Crowdcode)
 */
public interface Visitable<T> {

    /**
     * Visitor pattern accept method that should every subclass implement
     * @param visitor
     */
    void accept(T visitor);
}
