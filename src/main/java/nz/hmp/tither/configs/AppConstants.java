/**
 * 
 */
package nz.hmp.tither.configs;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import nz.hmp.tither.components.MessagesComponent;
import nz.hmp.tither.utils.CryptoUtils;

/**
 * @author helcio
 *
 */
public abstract class AppConstants implements CryptoUtils{

	private static final long serialVersionUID = 2489844309331618475L;
	
	protected String breadCrumbId;
	
//	@Autowired
//	protected AppConfig appConfig;
		
	@Autowired
	protected MessagesComponent messagesComponent;
	
	public static final String UPTIME = 
			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	
	public static final String SUCCESS = "success";
	
	protected static final int TIMEOUT = 10;
	
	public static String HTTP_AUTH_HEADER = "Authorization";
//	public static String HTTP_AUTH_HEADER_VALUE;
	
	public static final String PRIMEIRA_VIA_MAIOR = "10";
    public static final String PRIMEIRA_VIA = "01";
	
    public static String PATH =  
    		"/templates/";
	
	
	
	
}
