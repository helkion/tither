/**
 * 
 */
package nz.hmp.tither.filter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @author helcio
 *
 */
@Component
public class MdcLogEnhancerFilter implements Filter{

	@Override
	public void init(
			FilterConfig filterConfig) 
					throws ServletException {
		
	}

	@Override
	public void doFilter(
			ServletRequest request, 
			ServletResponse response, 
			FilterChain chain)
					throws IOException, ServletException {
		
//		SecurityContext context = SecurityContextHolder.getContext();
		
//		if (context != null && context.getAuthentication() != null) {
//			username = context.getAuthentication().getName();
//		}
		
		final String breadCrumbId = 
				genBreadCrumbId(((HttpServletRequest) request));
		
		MDC.put("breadCrumbId", breadCrumbId);
		((HttpServletResponse) response)
			.addHeader("breadCrumbId", breadCrumbId);
		
		chain.doFilter(request, response);
	}
	
	private String genBreadCrumbId(HttpServletRequest request) {
		String breadCrumbId = Optional
				.ofNullable(request.getHeader("breadCrumbId")) 
				.orElse("ID-" 
						+ UUID.randomUUID()
							.toString());
		return breadCrumbId;
	}

	@Override
	public void destroy() {

	}

}
