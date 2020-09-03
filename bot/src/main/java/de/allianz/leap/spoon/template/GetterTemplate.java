package de.allianz.leap.spoon.template;

import spoon.template.Parameter;
import spoon.template.StatementTemplate;

public class GetterTemplate<_FieldType_> extends StatementTemplate {

    @Parameter
    public String _fieldNameCapitalized_;

    _FieldType_ _field_;

    public _FieldType_ get_fieldNameCapitalized_() {
        return _field_;
    }

    @Override
    public void statement() throws Throwable {
        get_fieldNameCapitalized_();
    }
}
