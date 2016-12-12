package de.qaware.refactobot;

import org.jetbrains.annotations.NotNull;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.reference.CtTypeReference;

/**
 * Enhances all Object parameters with intelliJ NotNull annotation.
 */
public class NotNullParameterProcessor extends AbstractProcessor<CtParameter<?>> {

    public boolean isToBeProcessed(CtParameter<?> element) {
        return !element.getType().isPrimitive();// only for objects
    }

    public void process(CtParameter parameter) {
        CtAnnotation<NotNull> notNull = getFactory().Core().createAnnotation();
        CtTypeReference<NotNull> notNullAnnotation = getFactory().Type().createReference("org.jetbrains.annotations.NotNull");
        notNull.setAnnotationType(notNullAnnotation);
        parameter.addAnnotation(notNull);
    }

}
