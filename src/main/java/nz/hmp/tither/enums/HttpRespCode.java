/**
 * 
 */
package nz.hmp.tither.enums;

import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

/**
 * @author helcio
 *
 */
public enum HttpRespCode {
	OK(HttpServletResponse.SC_OK),
	SUCESSO(HttpServletResponse.SC_OK),
	PERSISTIDO(HttpServletResponse.SC_CREATED),
	NUM_INVALIDOS(HttpServletResponse.SC_BAD_REQUEST),
	FALHA_PERSISTENCIA(HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
	FALHA_COMPOSICAO(HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
	FALHA_ASSINATURA(HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
	DADOS_NAO_ENCONTRADOS(HttpServletResponse.SC_NOT_FOUND),
	IMAGENS_NAO_ENCONTRADAS(HttpServletResponse.SC_NOT_FOUND),
	ARQUIVO_NAO_ENCONTRADO(HttpServletResponse.SC_NOT_FOUND),
	PARAMS_INVALIDOS(HttpServletResponse.SC_BAD_REQUEST),
	ORIGEM_NAO_AUTORIZADA(HttpServletResponse.SC_UNAUTHORIZED),
	ERRO_BUSCA_IMAGENS(HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
	ERRO(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	
	private int code;
	
	private HttpRespCode(int code) {
		this.setCode(code);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public static HttpRespCode get(int codigo){
		Optional<HttpRespCode> opt = Stream.of(
				HttpRespCode
					.values())
						.filter(v -> v.getCode() == codigo)
						.findFirst();
		HttpRespCode instance = 
				opt.isPresent() ? 
						opt.get() : ERRO;
		
		return instance;		
	}

	
	
	
	
}
