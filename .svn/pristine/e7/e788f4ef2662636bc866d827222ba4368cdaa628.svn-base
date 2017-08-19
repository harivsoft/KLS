package com.vsoftcorp.kls.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vsoftcorp.kls.data.BankParameterData;
import com.vsoftcorp.kls.data.LoginCredentialsData;
import com.vsoftcorp.kls.data.UserLoginDetailsData;
import com.vsoftcorp.kls.service.IBankParameterService;
import com.vsoftcorp.kls.service.factory.KLSServiceFactory;
import com.vsoftcorp.kls.util.EntityManagerUtil;

@Path("/user")
public class KLSLoginRestService {
	private static final Logger logger = Logger
			.getLogger(KLSLoginRestService.class);

	public KLSLoginRestService() {

	}

	private static final KLSLoginRestService INSTANCE = new KLSLoginRestService();

	public static KLSLoginRestService getInstance() {
		return INSTANCE;
	}

	@POST
	@Path("/getlogindetails")
	@Consumes("*/*")
	@Produces("application/json")
	public String getLoggedInUserDetails() {

		logger.info("Start : Calling bank parameter service to get bank detalis for the login using getLoggedInUserDetails method.");
		List<BankParameterData> bankParameterDataList = new ArrayList<BankParameterData>();
		UserLoginDetailsData loginDetails = new UserLoginDetailsData();
		//System.out.println("loginDetails---" + loginDetails.isLoggedin());
		BankParameterData bankParamData = null;
		String jsonLoggedInUserDetailsString = "";
		Gson gson = new GsonBuilder().create();
		try {
			IBankParameterService bankParameterService = KLSServiceFactory
					.getBankParameterService();
			bankParameterDataList = bankParameterService.getAllBankParameters();
			if (bankParameterDataList.size() > 0) {
				bankParamData = bankParameterDataList.get(0);
			}
			Subject currentUser = SecurityUtils.getSubject();
			logger.info("chk---" + currentUser);
			logger.info("chk---" + currentUser.getPrincipal());
			loginDetails.setUserName("TEST_USER");
			loginDetails.setUserId("TESTUSER");
			if (currentUser.getPrincipal() != null) {
				loginDetails.setUserName(currentUser.getPrincipal().toString());
				loginDetails.setUserId(currentUser.getPrincipal().toString());
				loginDetails.setLoggedin(true);
			} else {
				loginDetails.setLoggedin(false);
			}
			loginDetails.setBankName(bankParamData.getBankName());
			loginDetails.setBusinessDate(bankParamData.getBusinessDate());
			loginDetails.setBranchId(1);
			loginDetails.setPacsId(1);

			Session session = currentUser.getSession();
			session.setAttribute("branchId", loginDetails.getBranchId());
			session.setAttribute("pacsId", loginDetails.getPacsId());
			session.setAttribute("loggedInUser", loginDetails.getUserName());
			session.setAttribute("businessDate", loginDetails.getBusinessDate());
			session.setAttribute("bankName", loginDetails.getBankName());
			jsonLoggedInUserDetailsString = gson.toJson(loginDetails);
		} catch (Exception e) {
			e.printStackTrace();
			// loginDetails.setLoggedin(false);
			jsonLoggedInUserDetailsString = gson.toJson(loginDetails);
			return jsonLoggedInUserDetailsString;
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : successfully got all user details.");
		return jsonLoggedInUserDetailsString;
	}

	@POST
	@Path("/logout")
	@Consumes("*/*")
	@Produces("application/json")
	public String getUserLogoutDetails() {

		logger.info("user going to logout using getUserLogoutDetails() method.");

		String jsonLoggedInUserDetailsString = "";
		try {
			String logoutInfo = SecurityUtils.getSubject().getPrincipal()
					+ " Successfully Logged out...!";
			SecurityUtils.getSubject().logout();

			Gson gson = new GsonBuilder().create();
			jsonLoggedInUserDetailsString = gson.toJson(logoutInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("successfully logged out.");
		return jsonLoggedInUserDetailsString;
	}

	@POST
	@Path("/login")
	@Consumes("*/*")
	@Produces("application/json")
	public String validateUser(String userCredentialsString) {

		logger.info("validating the user using validateUser() method.");
		String jsonLoggedInUserDetailsString = "";
		try {
			LoginCredentialsData data = null;
			Factory<SecurityManager> factory = new IniSecurityManagerFactory(
					"classpath:shiro.ini");

			SecurityManager securityManager = factory.getInstance();

			SecurityUtils.setSecurityManager(securityManager);

			Subject currentUser = SecurityUtils.getSubject();
			Gson gson = new Gson();
			data = gson.fromJson(userCredentialsString,
					LoginCredentialsData.class);
			if (!currentUser.isAuthenticated()) {
				UsernamePasswordToken token = new UsernamePasswordToken(
						StringUtils.trim(data.getUserName()),
						data.getPassword());
				currentUser.login(token);
			}

		} catch (Exception e) {
			UserLoginDetailsData loginDetails = new UserLoginDetailsData();
			loginDetails.setLoggedin(false);
			Gson gson = new GsonBuilder().create();

			jsonLoggedInUserDetailsString = gson.toJson(loginDetails);
			return jsonLoggedInUserDetailsString;

		} finally {
			EntityManagerUtil.closeSession();
		}
		logger.info("End : validating the user using validateUser() method.");
		return getLoggedInUserDetails();
	}

}