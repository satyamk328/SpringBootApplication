package com.satyam.listener;

import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.satyam.dao.UserDao;
import com.satyam.model.User;

public class JobCompletionListener extends JobExecutionListenerSupport {

	@Autowired
	private UserDao userDao;

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			System.out.println(
					"==================================================job finished=================================");
			List<User> results = userDao.findAll();
			for (User batchDetails : results) {
				System.out.println("+++++++++++++++++++++++" + batchDetails.toString());
			}

		}
	}

}
