package by.naakcii.adminka.backend.utils.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element size after trimming must be between the specified boundaries (included).
 * <p>
 * Supported types are:
 * <ul>
 *     <li>{@code CharSequence} (length of character sequence is evaluated)</li>
 * </ul>
 * <p>
 * {@code null} elements are considered valid.
 */

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(PureSize.List.class)
@Documented
@Constraint(validatedBy = PureSizeValidator.class)
public @interface PureSize {
	
	String message() default "{by.naakcii.validation.constraints.PureSize.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
	 * @return trimmed size of the element must be higher or equal to
	 */
	int min() default 0;

	/**
	 * @return trimmed size of the element must be lower or equal to
	 */
	int max() default Integer.MAX_VALUE;

	/**
	 * Defines several {@link PureSize} annotations on the same element.
	 *
	 * @see PureSize
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		PureSize[] value();
	}
}