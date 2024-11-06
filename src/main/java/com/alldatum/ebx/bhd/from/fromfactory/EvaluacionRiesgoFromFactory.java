package com.alldatum.ebx.bhd.from.fromfactory;


import com.alldatum.ebx.bhd.form.gdpr.EvaluacionRiesgoUserService;
import com.orchestranetworks.ui.selection.RecordEntitySelection;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormContext.ForCreate;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormContext.ForDefault;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormContext.ForDuplicate;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormFactory;
import com.orchestranetworks.userservice.schema.UserServiceRecordFormFactoryContext;

public class EvaluacionRiesgoFromFactory implements UserServiceRecordFormFactory{

	public EvaluacionRiesgoFromFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserService<TableViewEntitySelection> newUserServiceForCreate(ForCreate context) {
		// TODO Auto-generated method stub
		 return new EvaluacionRiesgoUserService<>();
	}

	@Override
	public UserService<RecordEntitySelection> newUserServiceForDefault(ForDefault context) {
		// TODO Auto-generated method stub
		return new EvaluacionRiesgoUserService<>();
	}

	@Override
	public UserService<RecordEntitySelection> newUserServiceForDuplicate(ForDuplicate context) {
		// TODO Auto-generated method stub
		return new EvaluacionRiesgoUserService<>();
	}

	@Override
	public void setup(UserServiceRecordFormFactoryContext arg0) {
		// TODO Auto-generated method stub
		
	}

}
