package eu.daiad.web.controller.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.daiad.web.controller.BaseController;
import eu.daiad.web.domain.admin.ScheduledJobExecution;
import eu.daiad.web.model.RestResponse;
import eu.daiad.web.model.scheduling.ExecutionMessageResponse;
import eu.daiad.web.model.scheduling.ExecutionQuery;
import eu.daiad.web.model.scheduling.ExecutionQueryRequest;
import eu.daiad.web.model.scheduling.ExecutionQueryResponse;
import eu.daiad.web.model.scheduling.ExecutionQueryResult;
import eu.daiad.web.model.scheduling.JobCollectionResponse;
import eu.daiad.web.model.scheduling.JobExecutionInfo;
import eu.daiad.web.model.scheduling.JobResponse;
import eu.daiad.web.service.scheduling.ISchedulerService;

/**
 * Provides actions for scheduling jobs.
 *
 */
@RestController
public class SchedulerController extends BaseController {

    private static final Log logger = LogFactory.getLog(AdminController.class);

    @Value("${daiad.batch.server-time-zone:Europe/Athens}")
    private String serverTimeZone;

    @Autowired
    private ISchedulerService jobService;

    /**
     * Gets all registered jobs.
     * 
     * @return the jobs.
     */
    @RequestMapping(value = "/action/scheduler/jobs", method = RequestMethod.GET, produces = "application/json")
    @Secured("ROLE_ADMIN")
    public RestResponse getJobs() {
        RestResponse response = null;

        try {
            return new JobCollectionResponse(this.jobService.getJobs());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);

            response = new RestResponse();
            response.add(this.getError(ex));
        }

        return response;
    }

    /**
     * Returns all job executions, optionally filtered by a query.
     * 
     * @param request the request
     * @return the executions.
     */
    @RequestMapping(value = "/action/scheduler/executions", method = RequestMethod.POST, produces = "application/json")
    @Secured("ROLE_ADMIN")
    public RestResponse getExecutions(@RequestBody ExecutionQueryRequest request) {

        try {
            // Set default values
            if (request.getQuery() == null) {
                request.setQuery(new ExecutionQuery());
            }
            if ((request.getQuery().getIndex() == null) || (request.getQuery().getIndex() < 0)) {
                request.getQuery().setIndex(0);
            }
            if (request.getQuery().getSize() == null) {
                request.getQuery().setSize(10);
            }

            ExecutionQueryResult result = jobService.getJobExecutions(request.getQuery());

            ExecutionQueryResponse response = new ExecutionQueryResponse();

            response.setTotal(result.getTotal());

            response.setIndex(request.getQuery().getIndex());
            response.setSize(request.getQuery().getSize());

            List<JobExecutionInfo> executions = new ArrayList<JobExecutionInfo>();

            DateTime utcDateTime;

            for (ScheduledJobExecution entity : result.getExecutions()) {
                JobExecutionInfo e = new JobExecutionInfo();

                if (entity.getStartedOn() != null) {
                    utcDateTime = entity.getStartedOn().toDateTime(DateTimeZone.forID(serverTimeZone));
                    e.setStartedOn(utcDateTime.getMillis());
                }
                if (entity.getCompletedOn() != null) {
                    utcDateTime = entity.getCompletedOn().toDateTime(DateTimeZone.forID(serverTimeZone));
                    e.setCompletedOn(utcDateTime.getMillis());
                }
                e.setExecutionId(entity.getJobExecutionId());
                e.setExitCode(entity.getExitCode());
                e.setInstanceId(entity.getJobInstanceId());
                e.setJobId(entity.getJobId());
                e.setStatusCode(entity.getStatusCode());
                e.setJobName(entity.getJobName());

                executions.add(e);
            }
            response.setExecutions(executions);

            return response;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);

            RestResponse response = new RestResponse();
            response.add(this.getError(ex));

            return response;
        }
    }

    /**
     * Loads a job based on its id and a subset of its executions.
     * 
     * @param jobId
     *            the job id.
     * @param startPosition
     *            the execution start index.
     * @param maxResult
     *            the maximum number of executions to return.
     * @return the job and its executions.
     */
    @RequestMapping(value = "/action/scheduler/job/{jobId}/{startPosition}/{maxResult}", method = RequestMethod.GET, produces = "application/json")
    @Secured("ROLE_ADMIN")
    public RestResponse getJob(long jobId, int startPosition, int maxResult) {
        RestResponse response = null;

        try {
            JobResponse controllerResponse = new JobResponse();

            controllerResponse.setJob(this.jobService.getJob(jobId));
            controllerResponse.setExecutions(this.jobService.getJobExecutions(jobId, startPosition, maxResult));

            return controllerResponse;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);

            response = new RestResponse();
            response.add(this.getError(ex));
        }

        return response;
    }

    /**
     * Enables a job by its id.
     * 
     * @param jobId the job id.
     * @return the controller's response.
     */
    @RequestMapping(value = "/action/scheduler/job/enable/{jobId}", method = RequestMethod.PUT, produces = "application/json")
    @Secured("ROLE_ADMIN")
    public RestResponse enableJob(@PathVariable long jobId) {
        RestResponse response = new RestResponse();

        try {
            this.jobService.enable(jobId);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);

            response.add(this.getError(ex));
        }

        return response;
    }

    /**
     * Disables a job by its id.
     * 
     * @param jobId
     *            the job id.
     * @return the controller's response.
     */
    @RequestMapping(value = "/action/scheduler/job/disable/{jobId}", method = RequestMethod.PUT, produces = "application/json")
    @Secured("ROLE_ADMIN")
    public RestResponse disableJob(@PathVariable long jobId) {
        RestResponse response = new RestResponse();

        try {
            this.jobService.disable(jobId);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);

            response.add(this.getError(ex));
        }

        return response;
    }

    /**
     * Launches a job by its id.
     * 
     * @param jobId
     *            the job id.
     * @return the controller's response.
     */
    @RequestMapping(value = "/action/scheduler/job/launch/{jobId}", method = RequestMethod.PUT, produces = "application/json")
    @Secured("ROLE_ADMIN")
    public RestResponse launchJob(@PathVariable long jobId) {
        RestResponse response = new RestResponse();

        try {
            this.jobService.launch(jobId);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);

            response.add(this.getError(ex));
        }

        return response;
    }

    /**
     * Returns the message of an execution by its id.
     * 
     * @param executionId
     *            the execution id.
     * @return the execution message.
     */
    @RequestMapping(value = "/action/scheduler/execution/{executionId}/message/'", method = RequestMethod.GET, produces = "application/json")
    @Secured("ROLE_ADMIN")
    public RestResponse getExecutionMessage(@PathVariable long executionId) {

        try {
            ExecutionMessageResponse response = new ExecutionMessageResponse();

            response.setMessage(this.jobService.getExecutionMessage(executionId));

            return response;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);

            RestResponse response = new RestResponse();
            response.add(this.getError(ex));

            return response;
        }
    }

    /**
     * Sends a message to stop a job execution. The scheduler does not
     * guarantees immediate job termination.
     * 
     * @param executionId
     *            the execution id.
     * @return the controller's response.
     */
    @RequestMapping(value = "/action/scheduler/execution/stop/{executionId}", method = RequestMethod.DELETE, produces = "application/json")
    @Secured("ROLE_ADMIN")
    public RestResponse stopExecution(long executionId) {
        RestResponse response = new RestResponse();

        try {
            this.jobService.stop(executionId);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);

            response.add(this.getError(ex));
        }

        return response;
    }
}
