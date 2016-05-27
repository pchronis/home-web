package eu.daiad.web.controller.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.daiad.web.controller.BaseController;
import eu.daiad.web.model.RestResponse;
import eu.daiad.web.model.admin.AccountActivity;
import eu.daiad.web.model.admin.AccountActivityResponse;
import eu.daiad.web.model.group.GroupQueryRequest;
import eu.daiad.web.model.group.GroupQueryResponse;
import eu.daiad.web.model.security.AuthenticatedUser;
import eu.daiad.web.repository.application.IGroupRepository;
import eu.daiad.web.repository.application.IUserRepository;

@RestController
public class AdminController extends BaseController {

	private static final Log logger = LogFactory.getLog(AdminController.class);

	@Autowired
	private IGroupRepository groupRepository;

	@Autowired
	private IUserRepository userRepository;

	@RequestMapping(value = "/action/admin/trial/activity", method = RequestMethod.GET, produces = "application/json")
	@Secured("ROLE_ADMIN")
	public RestResponse getTrialUserActivity(@AuthenticationPrincipal AuthenticatedUser user) {
		RestResponse response = null;

		try {
			AccountActivityResponse controllerResponse = new AccountActivityResponse();

			List<AccountActivity> records = userRepository.getAccountActivity(user.getUtilityId());

			for (AccountActivity a : records) {
				controllerResponse.getAccounts().add(a);
			}

			response = controllerResponse;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);

			response = new RestResponse();
			response.add(this.getError(ex));
		}

		return response;
	}

	@RequestMapping(value = "/action/admin/group/query", method = RequestMethod.POST, produces = "application/json")
	@Secured("ROLE_ADMIN")
	public RestResponse getGroups(@RequestBody GroupQueryRequest request) {
		RestResponse response = null;

		try {
			return new GroupQueryResponse(this.groupRepository.getAll());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);

			response = new RestResponse();
			response.add(this.getError(ex));
		}

		return response;
	}

}
