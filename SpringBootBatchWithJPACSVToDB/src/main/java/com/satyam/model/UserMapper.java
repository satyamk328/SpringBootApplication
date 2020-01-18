package com.satyam.model;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class UserMapper implements FieldSetMapper<User> {

	@Override
	public User mapFieldSet(FieldSet fieldSet) throws BindException {
		final User entry = new User();
		entry.setName(fieldSet.readString("Name"));
		entry.setSalary(fieldSet.readDouble("Salary"));
		entry.setDept(fieldSet.readString("Dept"));
		return entry;
	}

}
