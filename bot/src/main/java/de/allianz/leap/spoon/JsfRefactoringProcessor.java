package de.allianz.leap.spoon;

import de.allianz.leap.spoon.template.GetterTemplate;
import org.apache.commons.lang3.StringUtils;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtTypeMember;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.FieldAccessFilter;

import java.util.Collections;
import java.util.List;

public class JsfRefactoringProcessor extends AbstractProcessor<CtMethod<?>> {

    // TODO read from jsf beans xml
    private String managedBeanName = "refactoringInput";
    private String managedBeanClass = "de.cb.jsf.scopes.RefactoringInput";
    private String managedPropertyName = "car";

    private String managedPropertyValue = "#{carBean}";
    private String managedPropertyValueRaw;

    private String searchSetter;

    public JsfRefactoringProcessor() {
        searchSetter = this.managedBeanClass + "#set" + StringUtils.capitalize(managedPropertyName);
        managedPropertyValueRaw = managedPropertyValue.replaceAll("#\\{(\\w+)}", "$1");
    }

    @Override
    public boolean isToBeProcessed(CtMethod<?> candidate) {
        CtClass<?> parent = candidate.getParent(CtClass.class);
        String setterRef = parent.getQualifiedName() + "#" + candidate.getSimpleName();

        // TODO optional: check parameter count, type and name
        return setterRef.equals(searchSetter);
    }

    @Override
    public void process(final CtMethod<?> setterMethod) {
        // Fiend field
        CtBlock<?> body = setterMethod.getBody();
        List<CtStatement> statements = body.getStatements();
        if (statements.size() != 1) {
            throw new IllegalStateException(
                    "Encountered setter with != 1 statements: " + getFullQualifiedName(setterMethod));
        }
        CtAssignment<?, ?> assignment = (CtAssignment<?, ?>) statements.get(0);
        CtFieldWrite<?> fieldWrite = (CtFieldWrite<?>) assignment.getAssigned();
        CtFieldReference<?> fieldRef = fieldWrite.getVariable();
        CtField<?> field = fieldRef.getFieldDeclaration();

        // Find all references to the field
        Filter<CtFieldAccess<?>> fieldAccessFilter = new FieldAccessFilter(fieldRef);
        CtClass<?> parent = setterMethod.getParent(CtClass.class);
        List<CtFieldAccess<?>> fieldAccesses = Query.getElements(parent, fieldAccessFilter);

        // Rewrite all references (except getter) to the field to the getter
        // and getter body to lazy lookup.
        processFieldAccesses(fieldAccesses);

        // Delete field
        field.delete();

        // Delete setter method
        setterMethod.delete();
    }

    private void processFieldAccesses(final List<CtFieldAccess<?>> fieldAccesses) {
        CtClass<GetterTemplate<?>> templateClazz = getFactory().Class().get(GetterTemplate.class);

        for (CtFieldAccess<?> fieldAccess : fieldAccesses) {
            CtMethod<?> accessingMethod = fieldAccess.getParent(CtMethod.class);

            boolean accessInGetter = isGetter(accessingMethod, fieldAccess.getVariable().getDeclaration());
            boolean accessInSetter = isSetter(accessingMethod, fieldAccess.getVariable().getDeclaration());

            String fullQualifiedAccessingMethod = getFullQualifiedName(accessingMethod);
            if (accessInSetter) {
                System.out.println("Skipping field access in method " + fullQualifiedAccessingMethod);
            } else if (accessInGetter) {
                System.out.println("Rewriting getter to bean context lookup: " + fullQualifiedAccessingMethod);
                rewriteGetterToLazyLoading(accessingMethod, fieldAccess.getVariable().getDeclaration());
            } else {
                // Else: rewrite the field access to calling the getter
                System.out.println(String.format(
                        "Rewriting direct access to field [%s] to getter in method [%s]",
                        getFullQualifiedName(fieldAccess.getVariable().getDeclaration()),
                        fullQualifiedAccessingMethod
                ));
                GetterTemplate<?> getterTemplate = new GetterTemplate<>();
                getterTemplate._fieldNameCapitalized_ =
                        StringUtils.capitalize(fieldAccess.getVariable().getSimpleName());
                CtStatement apply = getterTemplate.apply(templateClazz);
                fieldAccess.replace(apply);
            }
        }
    }

    private void rewriteGetterToLazyLoading(final CtMethod getterMethod, final CtField<?> field) {
        CtCodeSnippetStatement stat = getFactory().createCodeSnippetStatement(
                String.format(
                        "return JSFHelper.getManagedBean(\"%s\", %s.class)",
                        managedPropertyValueRaw,
                        field.getType().getSimpleName()
                ));
        CtBlock<?> body = getterMethod.getBody();
        body.setStatements(Collections.singletonList(stat));
    }

    private boolean isGetter(final CtMethod<?> method, CtField<?> field) {
        return method.getSimpleName().equals("get" + StringUtils.capitalize(field.getSimpleName()));
    }


    private boolean isSetter(final CtMethod<?> method, CtField<?> field) {
        return method.getSimpleName().equals("set" + StringUtils.capitalize(field.getSimpleName()));
    }

    private String getFullQualifiedName(CtTypeMember member) {
        CtClass<?> clazz = member.getParent(CtClass.class);
        return clazz.getQualifiedName() + "#" + member.getSimpleName();

    }
}
