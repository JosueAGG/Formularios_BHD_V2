package com.alldatum.ebx.bhd.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.instance.ValueContextForInputValidation;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.SchemaNode;
import com.orchestranetworks.service.ServiceKey;
import com.orchestranetworks.ui.selection.RecordEntitySelection;
import com.orchestranetworks.ui.selection.TableEntitySelection;
import com.orchestranetworks.userservice.ObjectKey;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.UserServiceDisplayConfigurator;
import com.orchestranetworks.userservice.UserServiceEventContext;
import com.orchestranetworks.userservice.UserServiceEventOutcome;
import com.orchestranetworks.userservice.UserServiceObjectContextBuilder;
import com.orchestranetworks.userservice.UserServicePaneContext;
import com.orchestranetworks.userservice.UserServicePaneWriter;
import com.orchestranetworks.userservice.UserServiceProcessEventOutcomeContext;
import com.orchestranetworks.userservice.UserServiceSetupDisplayContext;
import com.orchestranetworks.userservice.UserServiceSetupObjectContext;
import com.orchestranetworks.userservice.UserServiceValidateContext;

public class ComplexNodeUserService<T extends TableEntitySelection> implements UserService<T> {

    private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");
    private static final Path ID_PATH = Path.parse("id");
    private static final Path ENCLOSING_PATH = Path.parse("enclosing");
    private static final Path FILTRO_PATH = Path.parse("filtro");
    private static final Path PRIMERO_PATH = Path.parse("enclosing/primero");
    private static final Path PRIMERO_AUX_PATH = Path.parse("primero_aux");
    private static final String DEFAULT_VALUE = "-1";
    private static final int LIST_SIZE = 7;

    @Override
    public UserServiceEventOutcome processEventOutcome(UserServiceProcessEventOutcomeContext<T> aContext,
            UserServiceEventOutcome anOutcome) {
        return anOutcome;
    }

    @Override
    public void setupDisplay(UserServiceSetupDisplayContext<T> aContext, UserServiceDisplayConfigurator aConfigurator) {
        if (aContext.isInitialDisplay() && aContext.getServiceKey().equals(ServiceKey.CREATE)) {
            initializeDefaultList(aContext);
        }
        aConfigurator.setContent(this::pane);
        aConfigurator.setDefaultButtons(this::save);
    }

    private void initializeDefaultList(UserServiceSetupDisplayContext<T> aContext) {
        List<String> defaultList = createDefaultList();
        aContext.getValueContext(OBJECT_KEY, PRIMERO_AUX_PATH).setNewValue(defaultList);
    }

    private List<String> createDefaultList() {
        return IntStream.range(0, LIST_SIZE).mapToObj(i -> DEFAULT_VALUE).collect(Collectors.toList());
    }

    private UserServiceEventOutcome save(UserServiceEventContext aContext) {
        ValueContextForInputValidation vc = aContext.getValueContext(OBJECT_KEY);
        SchemaNode node = vc.getNode(PRIMERO_PATH);

        @SuppressWarnings("unchecked")
		List<Object> values = (List<Object>) vc.getValue(ENCLOSING_PATH);
        List<String> list = values.stream()
                .map(value -> Optional.ofNullable((String) node.executeRead(value))
                        .filter(ref -> !ref.isBlank())
                        .orElse(DEFAULT_VALUE))
                .collect(Collectors.toList());

        // Completa la lista con valores por defecto si es necesario
        while (list.size() < LIST_SIZE) {
            list.add(DEFAULT_VALUE);
        }

        vc.setNewValue(list);
        aContext.save(OBJECT_KEY);
        return null;
    }

    private void pane(UserServicePaneContext aPaneContext, UserServicePaneWriter aWriter) {
        aWriter.setCurrentObject(OBJECT_KEY);

        aWriter.startTableFormRow();
        aWriter.addFormRow(ID_PATH);
        aWriter.addFormRow(FILTRO_PATH);
        aWriter.addFormRow(ENCLOSING_PATH);
        aWriter.addFormRow(PRIMERO_AUX_PATH);
        aWriter.endTableFormRow();

        String primAuxPrefix = aWriter.getPrefixedPath(PRIMERO_AUX_PATH).format();

        // Agrega la función JavaScript
        aWriter.addJS_cr("function saveSelectedValue(value, index) {");
        aWriter.addJS_cr("  if (value != null) {");
        aWriter.addJS_cr("    value = value.key;");
        aWriter.addJS_cr("  } else {");
        aWriter.addJS_cr("    value = '" + DEFAULT_VALUE + "';");
        aWriter.addJS_cr("  }");
        aWriter.addJS_cr("  ebx_form_setValue('" + primAuxPrefix + "[' + index + ']', value);");
        aWriter.addJS_cr("}");
    }

    @Override
    public void setupObjectContext(UserServiceSetupObjectContext<T> aContext, UserServiceObjectContextBuilder aBuilder) {
        if (aContext.isInitialDisplay()) {
            TableEntitySelection selection = aContext.getEntitySelection();

            if (selection instanceof RecordEntitySelection) {
                Adaptation record = ((RecordEntitySelection) selection).getRecord();
                if (aContext.getServiceKey().equals(ServiceKey.DUPLICATE)) {
                    aBuilder.registerNewDuplicatedRecord(OBJECT_KEY, record);
                } else {
                    aBuilder.registerRecordOrDataSet(OBJECT_KEY, record);
                }
            } else {
                aBuilder.registerNewRecord(OBJECT_KEY, selection.getTable());
            }
        }
    }

    @Override
    public void validate(UserServiceValidateContext<T> aContext) {
        // Se puede implementar validación personalizada aquí si es necesario
    }
}
