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
public class InfoController {

	@Value("${info.app.version}")
	private String appVersion;

	@GetMapping("/")
	public String getAppVersion() {
		return "Application Version: " + appVersion;
	}
}