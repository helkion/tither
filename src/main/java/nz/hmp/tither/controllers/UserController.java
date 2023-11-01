/**
 * 
 */
package nz.hmp.tither.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import nz.hmp.tither.services.UserService;

/**
 * 
 */
public class UserController extends BaseController{

	private static final long serialVersionUID = 4804052973791190089L;
	
	@Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login"; // 
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // 
    }
}
