/**
 * 
 */
package nz.hmp.tither.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import nz.hmp.tither.configs.AppConstants;
import nz.hmp.tither.configs.LoggerWrapper;


/**
 * @author helcio
 *
 */
public class BaseController extends AppConstants{

	private static final long serialVersionUID = -2501613769973809022L;

	@Autowired 
	protected HttpServletRequest request;
	
	@Autowired 
	protected HttpServletResponse response;
	
	protected static final transient Logger logger = 
			LoggerWrapper.getLogger();
}
