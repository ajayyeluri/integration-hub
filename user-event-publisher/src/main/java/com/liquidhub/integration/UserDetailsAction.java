package com.liquidhub.integration;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserDetailsAction extends Action {

	public static final String SENT_TO_IHUB = "sent-to-ihub";

	/* (non-Java-doc)
             * @see com.liferay.portal.kernel.events.Action#Action()
             */
	public UserDetailsAction() {
		super();
	}

	/* (non-Java-doc)
	 * @see com.liferay.portal.kernel.events.Action#run(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	public void run(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		System.out.println("in post login");
		
		
		User user=null;
		try {
			user = PortalUtil.getUser(request);
		} catch (  Exception e1) {
			e1.printStackTrace();
		}


	System.out.println("user"+user);

		try {
			PermissionChecker checker = PermissionCheckerFactoryUtil.create(user);
            PermissionThreadLocal.setPermissionChecker(checker);

			System.out.println(user.getFirstName()); 
			System.out.println(user.getBirthday());
			System.out.println(user.getEmailAddress());
			System.out.println(user.getScreenName());
			System.out.println(user.getLastName());
			System.out.println(user.getMiddleName());

			System.out.println("*************************");



			if ( ! user.getExpandoBridge().hasAttribute(SENT_TO_IHUB)) {
					new WebServiceClient().callWebService(user);
					user.getExpandoBridge().addAttribute(SENT_TO_IHUB, true);
			}

		} catch ( Exception e) {
			e.printStackTrace();
			throw new ActionException("Error Sending User Add Event", e);
		}

		


		System.out.println("in post login before exiting ");
		
	}

}