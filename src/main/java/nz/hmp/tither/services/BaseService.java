package nz.hmp.tither.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.HttpMethod;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import nz.hmp.tither.configs.AppConstants;
import nz.hmp.tither.configs.LoggerWrapper;



public abstract class BaseService extends AppConstants {
	
	protected static final long serialVersionUID = 3996841456359532181L;
	protected static final Logger logger = LoggerWrapper.getLogger();
	
	protected String breadCrumbId;

	static SSLContext insecureContext() {
		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] xcs, String string) {
				}

				public void checkServerTrusted(X509Certificate[] xcs, String string) {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			} }, null);
		} catch (KeyManagementException | NoSuchAlgorithmException ex) {
			logger.error("Erro ao criar o contexto SSL", ex);
		}
		return sc;
	}

	private static final int TIMEOUT = 30;
	
	private static final RequestConfig requestConfig = RequestConfig
    		.custom()
    		.setConnectTimeout(TIMEOUT * 1000)
    		.setConnectionRequestTimeout(TIMEOUT * 1000)
    		.setSocketTimeout(TIMEOUT * 1000)
    		.build();
		
	private final HttpClient httpClient = HttpClients
			.custom()
			.setSSLSocketFactory(new SSLConnectionSocketFactory(
					insecureContext(), 
					NoopHostnameVerifier.INSTANCE))
			.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
			.build();

	public HttpResponse retrieve(
			String uri, 
			Map<String, String> headers, 
			String method, 
			String body,
			ContentType contentType,
			List<Integer> successCodes) 
					throws IOException {
//	    String responseString = "";

	    HttpEntity entity = null;
	    if (body != null) {
	        entity = EntityBuilder
	        		.create()
	        		.setContentType(contentType)
	        		.setBinary(body.getBytes(StandardCharsets.UTF_8))
	        		.build();
	    }

	    HttpRequestBase request;
	    switch (method.toUpperCase()) {
	        case HttpMethod.GET:
	            request = new HttpGet(uri);
	            break;
	        case HttpMethod.POST:
	            request = new HttpPost(uri);
	            ((HttpPost) request).setEntity(entity);
	            break;
	        case HttpMethod.PUT:
	            request = new HttpPut(uri);
	            ((HttpPut) request).setEntity(entity);
	            break;
	        case HttpMethod.DELETE:
	            request = new HttpDelete(uri);
	            break;
	        default:
	            throw new IllegalArgumentException(
	            		"Metodo invalido: " + method);
	    }
	    request.setConfig(requestConfig);

	    if (headers != null) {
	        headers.forEach((k, v) -> 
	        	request.setHeader(k, v));
	    }

	    HttpResponse response = httpClient.execute(request);
	    int code = response.getStatusLine().getStatusCode();
	    logger.info(
	    		"Codigo da Resposta do {}: {}", 
	    		method, code);
	    boolean success = successCodes == null 
	    		|| successCodes.contains(code); 
//	    boolean asString = ContentType
//	    		.APPLICATION_JSON
//	    		.equals(contentType);
	    if (success /*&& asString*/) {
//	        entity = response.getEntity();
//	        if (entity != null) {
//	            responseString = EntityUtils.toString(
//	            		entity, StandardCharsets.UTF_8);
//	            logger.debug(
//	            		"Corpo da resposta: {}", 
//	            		responseString);
//	        }
	    } else {
	        logger.error(
	        		"Resposta inesperada para metodo {}: {}", 
	        		method, code);
	    }
	    
	    return response;
	}
	
	public HttpResponse retrievePost(
			String uri, 
			String body, 
			Map<String, String> headers)
			throws IOException, InterruptedException {
		HttpResponse response = (HttpResponse) retrieve(
				uri, headers, HttpMethod.POST,
				body, ContentType.APPLICATION_JSON, 
				Arrays.asList(
						new Integer[]{
								HttpURLConnection.HTTP_OK}));
		return response;
	}

	public String retrievePutAsString(
			String uri, 
			String body, 
			Map<String, String> headers)
			throws IOException, InterruptedException {
		String responseString = null;
		
		List<Integer> successCodes = Arrays.asList(
				new Integer[]{
						HttpURLConnection.HTTP_OK, 
						HttpURLConnection.HTTP_CREATED}); 

		HttpResponse response = (HttpResponse) retrieve(
				uri, headers, HttpMethod.PUT,
				body, ContentType.APPLICATION_JSON,
				successCodes);
		
		int code = response.getStatusLine().getStatusCode();
		
		boolean success = successCodes == null 
	    		|| successCodes.contains(code);
		
		if (success) {
			HttpEntity entity = response.getEntity();
			responseString = EntityUtils.toString(
	        		entity, StandardCharsets.UTF_8);
		}
		
		return responseString;
	}

	public String retrieveGetAsString(String uri, Map<String, String> headers)
			throws IOException, InterruptedException {
		
		String responseString = null;
		
		List<Integer> successCodes = Arrays.asList(
				new Integer[]{
						HttpURLConnection.HTTP_OK}); 

		HttpResponse response = (HttpResponse) retrieve(
				uri, headers, HttpMethod.GET,
				null, null,
				successCodes);
		
		int code = response.getStatusLine().getStatusCode();
		
		boolean success = successCodes == null 
	    		|| successCodes.contains(code);
		
		if (success) {
			HttpEntity entity = response.getEntity();
			responseString = EntityUtils.toString(
	        		entity, StandardCharsets.UTF_8);
		}
		
		return responseString;
	}
	
	public byte[] retrieveGetAsBytes(String uri, Map<String, String> headers)
			throws IOException, InterruptedException {
		
		byte[] bytes = null;
		
		List<Integer> successCodes = Arrays.asList(
				new Integer[]{
						HttpURLConnection.HTTP_OK}); 

		HttpResponse response = (HttpResponse) retrieve(
				uri, headers, HttpMethod.GET,
				null, null,
				successCodes);
		
		int code = response.getStatusLine().getStatusCode();
		
		boolean success = successCodes == null 
	    		|| successCodes.contains(code);
		
		if (success) {
			HttpEntity entity = response.getEntity();
			bytes = EntityUtils.toByteArray(entity);
		}
		
		return bytes;
	}

	public String retrievePostAsString(
			String uri, String body, Map<String, String> headers)
			throws IOException, InterruptedException {
		String responseString = null;
		
		List<Integer> successCodes = Arrays.asList(
				new Integer[]{
						HttpURLConnection.HTTP_OK, 
						HttpURLConnection.HTTP_CREATED}); 

		HttpResponse response = (HttpResponse) retrievePost(
				uri,  
				body, 
				headers);
		
		int code = response.getStatusLine().getStatusCode();
		
		boolean success = successCodes == null 
	    		|| successCodes.contains(code);
		
		if (success) {
			HttpEntity entity = response.getEntity();
			responseString = EntityUtils.toString(
	        		entity, StandardCharsets.UTF_8);
		}
		
		return responseString;
	}

}
