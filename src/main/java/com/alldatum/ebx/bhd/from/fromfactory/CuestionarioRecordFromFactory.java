package com.alldatum.ebx.bhd.from.fromfactory;

import com.alldatum.ebx.bhd.form.gdpr.CuestionarioUserService;
import com.orchestranetworks.ui.selection.RecordEntitySelection;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormContext.ForCreate;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormContext.ForDefault;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormContext.ForDuplicate;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormFactory;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormFactoryContext;
public class CuestionarioRecordFromFactory implements UserServiceRecordFormFactory {
    @Override
    public UserService<TableViewEntitySelection> newUserServiceForCreate(ForCreate context) {
        return new CuestionarioUserService<>();
    }
    @Override
    public UserService<RecordEntitySelection> newUserServiceForDefault(ForDefault context) {
        
        return new CuestionarioUserService<>();
    }
    @Override
    public UserService<RecordEntitySelection> newUserServiceForDuplicate(ForDuplicate context) {
        return new CuestionarioUserService<>();
    }
    @Override
    public void setup(UserServiceRecordFormFactoryContext arg0) {
        
    }
}