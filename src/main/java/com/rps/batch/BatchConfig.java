package com.rps.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.poi.util.SystemOutLogger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.rps.entities.AppUsers;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	final private static Logger log = Logger.getLogger(BatchConfig.class.getName());

	/*
	 * Spring Batch is a api which helps in building code block to run the larger
	 * or big job/task into steps effectively.
	 *                                       -> ItemReader    -> Listener 
	 *                                                        -> Mapper
	 *                                              
	 * JobLauncher -> Job --------> Step  ----> ItemProcessor -> Listener
	 *                 ^             ^
	 *                 |             |
	 *             JobFactory    StepFactory 
	 *                                       -> ItemWriter    -> Listener
	 *                                                        -> Notification 
	 * ===================Job Repository====================
	 * 
	 * 1 JobLauncher may have multiple Jobs
	 * 1 Job may have multiple steps
	 * 1 Step will have multiple chunks
	 * 1 Chunk will have three executions as, read, process and write
	 * 
	 */
	
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBulderFactory;
	
	@Autowired
	private DataSource dataSource;

	
	/* CompletionNofifictionListener */
	static class JobCompletionNofifictionListener extends JobExecutionListenerSupport {
		
		@Autowired
		private JdbcTemplate jdbcTemplate;
		
		public void afterJob(JobExecution jobExecution) {
			if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
				log.info("Job Finished - NofifictionLitener");
			}
			List<AppUsers> list = jdbcTemplate.query("select * from users", new RowMapper<AppUsers>() {

				@Override
				public AppUsers mapRow(ResultSet rs, int i) throws SQLException {
					AppUsers usr = new AppUsers();
					usr.setId(rs.getLong(1));
					usr.setFirstName(rs.getString(2));
					usr.setLastName(rs.getString(3));
					return usr;
				}
			});
			
			list.stream().forEach((e) -> {
				System.out.println(e.getId());
				System.out.println(e.getFirstName());
				System.out.println(e.getLastName());
			}); 
		}
	}
	
	
	/* ItemProcessor Implementation */
	static class UserItemProcessor implements ItemProcessor<AppUsers, AppUsers> {
		@Override
		public AppUsers process(AppUsers usr) throws Exception {
			AppUsers tmp = new AppUsers();
			tmp.setId(usr.getId());
			tmp.setFirstName(usr.getFirstName().toUpperCase());
			tmp.setLastName(usr.getLastName().toUpperCase());
			log.info("Processor 1 - Processing : " + usr.getId());
			return tmp;
		}
	}

	@Bean
	public FlatFileItemReader<AppUsers> reader() {
		FlatFileItemReader<AppUsers> reader = new FlatFileItemReader<AppUsers>();
		reader.setResource(new ClassPathResource("file.csv"));
		reader.setLineMapper(new DefaultLineMapper<AppUsers>(){
			//Init Block
			{
				//Setting tokenizer 
				setLineTokenizer(new DelimitedLineTokenizer() {
					//Init Block
					{
						setNames(new String[] {"firstName", "lastName"});
					}
				});
				
				//Setting mapper with bean
				setFieldSetMapper(new BeanWrapperFieldSetMapper<AppUsers>() {
					//Init Block
					{
						setTargetType(AppUsers.class);
					}
				});
			}
		});
		
		return reader;
	}
	

	@Bean
	public UserItemProcessor processor() {
		return new UserItemProcessor();
	}
	
	
	@Bean
	public JdbcBatchItemWriter<AppUsers> writer() {
		JdbcBatchItemWriter<AppUsers> writer = new JdbcBatchItemWriter<AppUsers>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<AppUsers>());
		writer.setSql("insert into users (first_name, last_name) values (:firstName, :lastName)");
		writer.setDataSource(dataSource);
		return writer;
	}

	//Created Step1 for adding into job
	@Bean
	public Step step1() {
		return stepBulderFactory.get("step1")
				.<AppUsers, AppUsers>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}

	@Bean
	public JobCompletionNofifictionListener listener() {
		return new JobCompletionNofifictionListener();
	}
	
	
	@Bean(name = "importJob1")
	public Job importJob1(JobCompletionNofifictionListener listener) {
		return jobBuilderFactory.get("importJob1")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1())
				.end()
				.build();
	}
	
	
}
