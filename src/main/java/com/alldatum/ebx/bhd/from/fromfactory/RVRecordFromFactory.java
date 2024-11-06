package com.alldatum.ebx.bhd.from.fromfactory;

import com.alldatum.ebx.bhd.form.gdpr.RVSUserService;
import com.orchestranetworks.ui.selection.RecordEntitySelection;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormContext;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormContext.ForCreate;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormContext.ForDefault;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormContext.ForDuplicate;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormFactory;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormFactoryContext;

public class RVRecordFromFactory implements UserServiceRecordFormFactory {

	@Override
	public UserService<TableViewEntitySelection> newUserServiceForCreate(ForCreate context) {
	
		return new RVSUserService <>();
				
	}

	@Override
	public UserService<RecordEntitySelection> newUserServiceForDefault(ForDefault arg0) {
		
		return new RVSUserService <>();
	}

	@Override
	public UserService<RecordEntitySelection> newUserServiceForDuplicate(ForDuplicate arg0) {
		
		return new RVSUserService <>();
	}

	@Override
	public void setup(UserServiceRecordFormFactoryContext arg0) {
	
		
	}

}