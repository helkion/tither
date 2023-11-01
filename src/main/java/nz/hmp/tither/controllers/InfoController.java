/**
 * 
 */
package nz.hmp.tither.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 */
@RestController
public class InfoController extends BaseController{

	private static final long serialVersionUID = -8146470575215117098L;
	
	@Value("${info.app.version}")
	private String appVersion;

	@GetMapping("/")
	public String getAppVersion() {
		return "Application Version: " + appVersion;
	}
}
