package de.qaware.refactobot;

import spoon.processing.AbstractProcessor;
import spoon.reflect.factory.PackageFactory;
import spoon.reflect.reference.CtPackageReference;

/**
 * Rewrites annotations
 *
 * @author a.zitzelsberger
 */
public class PackageReferenceProcessor extends AbstractProcessor<CtPackageReference> {

    @Override
    public boolean isToBeProcessed(CtPackageReference candidate) {
        return candidate.getSimpleName().equals("org.jetbrains.annotations");
    }


    public void process(CtPackageReference element) {
        PackageFactory packageFactory = getFactory().Package();
        element.replace(packageFactory.createReference(packageFactory.getOrCreate("severely.harebrained.annotations")));
    }
}
