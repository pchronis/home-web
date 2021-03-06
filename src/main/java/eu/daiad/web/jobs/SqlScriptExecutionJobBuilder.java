package eu.daiad.web.jobs;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import eu.daiad.web.service.scheduling.tasklet.SqlScriptExecutionTasklet;

@Component
public class SqlScriptExecutionJobBuilder implements IJobBuilder {

    private static final String PARAMETER_LOCATIONS = "locations";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("applicationDataSource")
    private DataSource dataSource;

    private Step executeScripts() {
        SqlScriptExecutionTasklet sqlScriptExecutionTasklet = new SqlScriptExecutionTasklet();

        sqlScriptExecutionTasklet.setApplicationContext(this.applicationContext);
        sqlScriptExecutionTasklet.setMessageSource(this.messageSource);
        sqlScriptExecutionTasklet.setDataSource(this.dataSource);
        sqlScriptExecutionTasklet.setLocationParameter(PARAMETER_LOCATIONS);

        return stepBuilderFactory.get("executeScripts").tasklet(sqlScriptExecutionTasklet).build();
    }

    @Override
    public Job build(String name, JobParametersIncrementer incrementer) throws Exception {
        return jobBuilderFactory.get(name).incrementer(incrementer).start(executeScripts()).build();
    }
}
