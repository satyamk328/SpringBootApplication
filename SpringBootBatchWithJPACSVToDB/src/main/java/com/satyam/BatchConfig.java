package com.satyam;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.satyam.listener.JobCompletionListener;
import com.satyam.model.User;
import com.satyam.model.UserMapper;
import com.satyam.step.Processor;
import com.satyam.step.Writer;

@SpringBootApplication
@EnableBatchProcessing
@EnableTransactionManagement
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BatchConfig.class, args);
	}
	
	@Bean
	public Job jobWriteDataInDB()
			throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
		return jobBuilderFactory.get("jobWriteDataInDB").incrementer(new RunIdIncrementer()).listener(listener())
				.flow(stepWriteDataInDB()).end().build();
	}

	@Bean
	public Step stepWriteDataInDB()
			throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
		return stepBuilderFactory.get("stepWriteDataInDB").<User, User>chunk(5).reader(dataReader())
				.processor(processor()).writer(writer()).build();
	}

	@Value("${input}")
	private Resource resource;

	public FlatFileItemReader<User> dataReader()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(new String[] { "Name", "Dept", "Salary" });
		tokenizer.setDelimiter(",");
		tokenizer.setStrict(false);
		
		DefaultLineMapper<User> mapper = new DefaultLineMapper<>();
		mapper.setLineTokenizer(tokenizer);
		mapper.setFieldSetMapper(new UserMapper());
		mapper.afterPropertiesSet();

		FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(mapper);
		flatFileItemReader.afterPropertiesSet();
		
		return flatFileItemReader;
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}
	
	@Bean
	public Processor processor() {
		return new Processor();
	}
	
	@Bean
	public Writer writer() {
		return new Writer();
	}

}
