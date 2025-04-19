package es.nachobrito.multitenant.domain.model;

/**
 * Represents an entity with Identifier of type I
 *
 * @author nacho
 */
public interface Entity<I> {

  I getId();
}
