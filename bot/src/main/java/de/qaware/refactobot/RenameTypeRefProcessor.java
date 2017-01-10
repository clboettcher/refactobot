package de.qaware.refactobot;

import spoon.processing.AbstractProcessor;
import spoon.reflect.reference.CtTypeReference;

/**
 * TODO: comment
 *
 * @author a.zitzelsberger
 */
public class RenameTypeRefProcessor extends AbstractProcessor<CtTypeReference<?>> {

    @Override
    public boolean isToBeProcessed(CtTypeReference<?> candidate) {
        return candidate.getQualifiedName().equals("com.zacharyfox.rmonitor.entities.Competitor");
    }

    @Override
    public void process(CtTypeReference<?> element) {
        element.setSimpleName("Archenemy");
    }
}
