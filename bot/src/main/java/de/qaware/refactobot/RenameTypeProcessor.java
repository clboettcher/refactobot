package de.qaware.refactobot;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtType;

/**
 * TODO: comment
 *
 * @author a.zitzelsberger
 */
public class RenameTypeProcessor extends AbstractProcessor<CtType<?>> {
    @Override
    public boolean isToBeProcessed(CtType<?> candidate) {
        return candidate.getQualifiedName().equals("com.zacharyfox.rmonitor.entities.Competitor");
    }

    @Override
    public void process(CtType<?> element) {
        element.setSimpleName("Archenemy");
    }
}
