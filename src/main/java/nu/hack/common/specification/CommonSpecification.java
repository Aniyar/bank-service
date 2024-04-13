package nu.hack.common.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

/**
 * A utility class for building Specifications commonly used in JPA Criteria Queries.
 *
 * @param <T> The type of the entity class to which the Specifications apply.
 */
public class CommonSpecification<T> {

    /**
     * Creates a Specification that always evaluates to true, effectively returning all records.
     *
     * @param <T> The type of the entity class.
     * @return A Specification that always evaluates to true.
     */
    public static <T> Specification<T> alwaysTrue() {
        return (root, query, cb) -> cb.and();
    }

    /**
     * Creates a Specification that checks if the specified attribute contains the given value
     * in a case-insensitive manner.
     *
     * @param attribute The name of the attribute to search.
     * @param value     The value to search for.
     * @param <T>       The type of the entity class.
     * @return A Specification that checks if the attribute contains the value.
     */
    public static <T> Specification<T> contains(String attribute, String value) {
        Objects.requireNonNull(value, "Value should not be empty");
        Objects.requireNonNull(attribute, "Attribute should not be empty");

        var searchVal = "%" + value.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get(attribute)), searchVal);
    }

    /**
     * Creates a Specification that checks if a nested attribute (nested within another attribute)
     * contains the given value in a case-insensitive manner.
     *
     * @param attributeFirst  The name of the outer attribute.
     * @param attributeSecond The name of the nested attribute within the outer attribute.
     * @param value           The value to search for.
     * @param <T>             The type of the entity class.
     * @return A Specification that checks if the nested attribute contains the value.
     */
    public static <T> Specification<T> contains(String attributeFirst, String attributeSecond, String value) {
        Objects.requireNonNull(value, "Value should not be empty");
        Objects.requireNonNull(attributeFirst, "First attribute should not be empty");
        Objects.requireNonNull(attributeSecond, "Second attribute should not be empty");

        var searchVal = "%" + value.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get(attributeFirst).get(attributeSecond)), searchVal);
    }

    /**
     * Creates a Specification that checks if the specified attribute equals the given value.
     *
     * @param attribute The name of the attribute to compare.
     * @param value     The value to compare with.
     * @param <T>       The type of the entity class.
     * @return A Specification that checks if the attribute equals the value.
     */
    public static <T> Specification<T> attributeEquals(String attribute, Object value) {
        Objects.requireNonNull(value, "Value should not be null");
        Objects.requireNonNull(attribute, "Attribute should not be empty");

        return (root, query, cb) -> cb.equal(root.get(attribute), value);
    }

    /**
     * Creates a Specification that checks if two attributes of an entity are equal.
     *
     * @param attribute1 The name of the first attribute to compare.
     * @param attribute2 The name of the second attribute to compare.
     * @param <T>        The type of the entity class.
     * @return A Specification that checks if the two attributes are equal.
     */
    public static <T> Specification<T> attributeEquals(String attribute1, String attribute2, Object value) {
        Objects.requireNonNull(attribute1, "First attribute should not be empty");
        Objects.requireNonNull(attribute2, "Second attribute should not be empty");
        Objects.requireNonNull(value, "Value should not be null");

        return (root, query, cb) -> cb.equal(root.get(attribute1).get(attribute2), value);
    }

    /**
     * Creates a Specification that checks if two attributes of an entity are not equal.
     *
     * @param attribute1 The name of the first attribute to compare.
     * @param attribute2 The name of the second attribute to compare.
     * @param <T>        The type of the entity class.
     * @return A Specification that checks if the two attributes are not equal.
     */
    public static <T> Specification<T> attributeNotEquals(String attribute1, String attribute2, Object value) {
        Objects.requireNonNull(attribute1, "First attribute should not be empty");
        Objects.requireNonNull(attribute2, "Second attribute should not be empty");
        Objects.requireNonNull(value, "Value should not be null");

        return (root, query, cb) -> cb.notEqual(root.get(attribute1).get(attribute2), value);
    }

    /**
     * Creates a Specification that checks if the specified attribute equals the given value. Null Safe.
     *
     * @param attribute The name of the attribute to compare.
     * @param value     The value to compare with.
     * @return A Specification that checks if the attribute equals the value.
     */
    public static <T> Specification<T> nullSafeEquals(String attribute, Object value) {
        Objects.requireNonNull(attribute, "Attribute should not be empty");
        if (value == null) {
            return Specification.where(null);
        }

        return (root, query, cb) -> cb.equal(root.get(attribute), value);
    }

    /**
     * Creates a Specification that checks if two attributes of an entity are equal.
     *
     * @param attribute1 The name of the first attribute to compare.
     * @param attribute2 The name of the second attribute to compare.
     * @param <T>        The type of the entity class.
     * @return A Specification that checks if the two attributes are equal.
     */
    public static <T> Specification<T> nullSafeEquals(String attribute1, String attribute2, Object value) {
        Objects.requireNonNull(attribute1, "First attribute should not be empty");
        Objects.requireNonNull(attribute2, "Second attribute should not be empty");
        if (value == null) {
            return Specification.where(null);
        }
        return (root, query, cb) -> cb.equal(root.get(attribute1).get(attribute2), value);
    }

    /**
     * Creates a Specification that checks if the current date falls between the specified start and end date attributes.
     *
     * @param startAttribute The name of the attribute representing the start date.
     * @param endAttribute   The name of the attribute representing the end date.
     * @param value          The date to compare with.
     * @return A Specification that checks if the current date is between the start and end dates.
     */
    public static <T> Specification<T> between(String startAttribute, String endAttribute, Comparable value) {
        Objects.requireNonNull(startAttribute, "Start attribute should not be empty");
        Objects.requireNonNull(endAttribute, "End attribute should not be empty");
        Objects.requireNonNull(value, "Value not be null");

        return (root, query, cb) -> {
            var startPredicate = cb.lessThanOrEqualTo(root.get(startAttribute), value);

            var endPredicate = cb.or(cb.isNull(root.get(endAttribute)),
                    cb.greaterThanOrEqualTo(root.get(endAttribute), value));

            return cb.and(startPredicate, endPredicate);
        };
    }

    /**
     * Creates a Specification that checks if the specified attribute is null.
     *
     * @param attribute The name of the attribute to check for null.
     * @param <T>       The type of the entity class.
     * @return A Specification that checks if the attribute is null.
     */
    public static <T> Specification<T> isNull(String attribute) {
        Objects.requireNonNull(attribute, "Attribute should not be empty");
        return (root, query, cb) -> cb.isNull(root.get(attribute));
    }

    /**
     * Creates a Specification that checks if an entity has items in a one-to-many relationship
     * where the items have a specified attribute equal to a given value.
     *
     * @param attribute1     The name of the list attribute in the entity.
     * @param attribute2     The name of the attribute in the items of the list.
     * @param value          The value to compare against.
     * @param <T>            The type of the entity class.
     * @return A Specification that checks for items with the specified attribute value in the list.
     */
    public static <T> Specification<T> hasAttributeInList(String attribute1, String attribute2, Enum<?> value) {
        Objects.requireNonNull(attribute1, "First attribute should not be empty");
        Objects.requireNonNull(attribute2, "Second attribute should not be empty");
        Objects.requireNonNull(value, "Value should not be empty");

        return (root, query, cb) -> {
            Join<?, ?> join = root.join(attribute1, JoinType.LEFT);
            Predicate predicate = cb.equal(join.get(attribute2), value);

            return predicate;
        };
    }

    /**
     * Creates a Specification that checks if the specified comparable attribute is greater than/equal to the given value.
     *
     * @param attribute The name of the attribute to compare.
     * @param value     The value to compare with.
     * @return A Specification that checks if the attribute equals the value.
     */
    public static <T> Specification<T> greaterThanOrEqualTo(String attribute, Comparable value) {
        Objects.requireNonNull(attribute, "Attribute should not be empty");
        Objects.requireNonNull(value, "Value should not be empty");

        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * Creates a Specification that checks if the specified comparable attribute is less than/equal to the given value.
     *
     * @param attribute The name of the attribute to compare.
     * @param value     The value to compare with.
     * @return A Specification that checks if the attribute is less than/equal to the value.
     */
    public static <T> Specification<T> lessThanOrEqualTo(String attribute, Comparable value) {
        Objects.requireNonNull(attribute, "Attribute should not be empty");
        Objects.requireNonNull(value, "Value should not be empty");

        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), value);
    }


    /**
     * Creates a Specification that checks if the specified comparable attribute is less than the given value.
     *
     * @param attribute The name of the attribute to compare.
     * @param value     The value to compare with.
     * @return A Specification that checks if the attribute is less than the value.
     */
    public static <T> Specification<T> lessThan(String attribute, Comparable value) {
        Objects.requireNonNull(attribute, "Attribute should not be empty");
        Objects.requireNonNull(value, "Value should not be empty");

        return (root, query, cb) -> cb.lessThan(root.get(attribute), value);
    }


    /**
     * Creates a Specification that safely checks if the specified comparable attribute is in the given range.
     *
     * @param attribute The name of the attribute to compare.
     * @param min       Start of the range.
     * @param max       End of the range.
     * @return A Specification that checks if the attribute is in the given range.
     */
    public static <T, Y extends Comparable<Y>> Specification<T> filterByRange(String attribute, Y min, Y max) {
        Objects.requireNonNull(attribute, "Attribute should not be empty");
        if (min == null && max == null)
            throw new IllegalArgumentException("Range must not be null");

        if (min != null && max != null) {
            return (root, query, cb) -> cb.between(root.get(attribute), min, max);
        } else if (min != null) {
            return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(attribute), min);
        } else {
            return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(attribute), max);
        }
    }
}
