package nz.hmp.tither.utils;

import java.lang.reflect.Field;
import java.text.Collator;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

public class StringUtils {

    private static final Locale PT_BR = new Locale("pt", "BR");
    
    public static String getReplaceZero(String str){
		String output = "";
		if (str != null){
			output = str.replaceAll("0", "");
			
			if (!output.isEmpty()){
				output = str;
			} 
		}
		
		return output;
	}
    
	/**
	 * Recebe um parametro, e devolve com um determinado numero de caracteres de acordo com
	 * o tipo requerido (numero)
	 * @param data valor a ser truncado
	 * @param type se é string ou numero
	 * @param maxLength numero de caracteres que a string de retorno ira  conter
	 */
	public static String fillLeftZeroes(int dataValue, int maxLength){

		String data = String.valueOf(dataValue);

		StringBuffer result = new StringBuffer();
		int dataLength = data.length();
		int difference = maxLength - dataLength;

		//put Zeroes before the data context
		for (int x = 0; x < difference; x++){
			result.append("0");
		}

		result.append(dataLength > maxLength ? data.substring(0, maxLength) : data);

		return result.toString();
	}
	
	public static String checkIsNull(String value) {
		value = value != null ? value.trim() : "";
		return value;
	}
	
	public static String getNonNullValue(String element){
		String value = element != null 
				? element : "";
		return value;
	}
	
	public static String getOnlyNumbers(String num){
		
		if (num != null){
			num = num.replaceAll("[^\\d]", "");
		}
		
		return num;
	}

	public static String formataTelefone(String input) {
		if ( input!=null ){
			MaskFormatter formatter = null;
			String format = "(##)#####-####";
			input = getOnlyNumbers(input);
			int size = input.length();
			switch (size) {
				case 8:
					format = "####-####";
					break;
				case 9:
					format = "#####-####";
					break;
				case 10:
					format = "(##)####-####";
					break;
				case 11:
					format = "(##)#####-####";
					break;
				case 15:
					format = "(####)#####-####";
					break;
				default:
					format = null;
					break;
			}
			if (format != null){
				try {
					formatter = new MaskFormatter(format);
					formatter.setValueContainsLiteralCharacters(false);
					input = formatter.valueToString(input);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return input;
		}
		return input;
	}	

	/**
     * Formata a string para envio para o Mainframe, removendo acentos e substitui caracteres especiais
     * 
     * @param value
     * @return
     */
    public static String formatMainframeStr(String value) {
    	if (value != null) {
	    	value = normalizeAccents(value.toUpperCase());
	    	value = exchangeMainframeChars(value);
    	}
    	return value;
    }	
    
	public static String normalizeSymbolsAndAccents(String str) {
		return replaceWithPattern(normalizeAccents(str).replaceAll("[+.^:,!@$~%_<>`'&{}\\[\\]\\\\?]",""));
	}
    
    public static String normalizeAccents(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		String result = pattern.matcher(nfdNormalizedString).replaceAll("");
		return result;
	}
    
    
    public static String replaceList(String str, Map<String, String> map) {
    	for (Map.Entry<String, String> entry : map.entrySet()) {
    		String key = entry.getKey(); 
    		
			if (key.indexOf(" ") >= 0)
				str = str.replaceAll(key, entry.getValue());
			else
				str = str.replaceAll("[" + key + "]", entry.getValue());
		}
    	return str;
    } 

    public static String exchangeMainframeChars(String value) {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("!", "Á");
    	map.put("@", "Â");
    	map.put("+", "À");
    	map.put("\\]", "Ã");
    	map.put("<", "Ä");
    	map.put("\\[", "Ç");
    	map.put("#", "É");
    	map.put("$", "Ê");
    	map.put("~", "È");
    	map.put("%", "Ë");
//    	map.put("_,", "Í");
    	map.put("_", "Í");
    	map.put(">", "Ï");
    	map.put("`", "Ñ");
    	map.put("\\\\", "Ó");
    	map.put("\\^", "Ô");
    	map.put("=", "Ò");
    	map.put("'", "Õ");
    	map.put("}", "Ö");
    	map.put("&", "Ú");
    	map.put("?", "Ù");
    	map.put("{", "Ü");
    	map.put("|", "‘");
    	map.put("-", " ");
    	value = replaceList(value, map);
    	return value;
    }

    public static int compare(String source, String target) {
		Collator ptBR = Collator.getInstance(PT_BR);
		return ptBR.compare(source, target);
    }

    public static String makeString(String str, String prefix) {
    	return makeString(str, prefix, null, null).toString();
    }

    public static String makeString(String str, String prefix, String postfix) {
    	return makeString(str, prefix, postfix, null).toString();
    }

    public static StringBuilder makeString(String str, String prefix, String postfix, StringBuilder builder) {
		if (builder == null) {
		    builder = new StringBuilder();
		}
	
		builder.append(prefix);
		builder.append(str);
	
		if (postfix != null) {
		    builder.append(postfix);
		}
	
		return builder;
    }

    public static StringBuilder makeString(String str, String prefix, StringBuilder builder) {
    	return makeString(str, prefix, null, builder);
    }

    public static String makeString(String[] strings, String prefix, String postfix, StringBuilder builder) {
		if (builder == null) {
		    builder = new StringBuilder();
		}
	
		for (int i = 0, size = strings.length; i < size; i++) {
		    if (i < (size - 1)) {
		    	builder = makeString(strings[i], prefix, postfix, builder);
		    }
		    else {
		    	builder = makeString(strings[i], prefix, builder);
		    }
		}
	
		return builder.toString();
    }

    public static String makeString(String[] strings, String prefix, String postfix) {
		StringBuilder builder = new StringBuilder();
	
		for (int i = 0, size = strings.length; i < size; i++) {
		    if (i < (size - 1)) {
		    	builder = makeString(strings[i], prefix, postfix, builder);
		    }
		    else {
		    	builder = makeString(strings[i], prefix, builder);
		    }
		}
	
		return builder.toString();
    }
	
	/**
     * This method was created to solve the problem of the persistence of search history log 
     * when some information has single quotes
     * 
     * @param text
     * @return
     */
    public static String singleQuotesPrevent(String text){
    	return text.replaceAll("'", "''");
    }

    /**
     * Torna todos os campos String uppercase, contanto que nao estejam contidos
     * na lista de excecoes
     * 
     * @param obj
     * @param exceptionNames
     */
    public static void uppercaseFields(Object obj, String[] exceptionNames) {
		Field[] fields = obj.getClass().getDeclaredFields();
	
		fieldsLoop: for (Field f : fields) {

	    if (exceptionNames != null) {
			for (String excName : exceptionNames) {
			    if (f.getName().equals(excName)) {
			    	continue fieldsLoop;
			    }
			}
	    }

	    if (f.getType().equals(String.class)) {
	    	f.setAccessible(true);
			try {
			    String val = f.get(obj) != null ? f.get(obj).toString() : null;
	
			    if (val != null) {
			    	f.set(obj, val.toUpperCase());
			    }
			}
			catch (Exception e) {}
	    }
	}
    }
	
	public static String replaceWithPattern(String str){
    	Pattern ptn = Pattern.compile("\\s+");
    	Matcher mtch = ptn.matcher(str);
    	str = mtch.replaceAll(" ");
    	return str;
    }

    public static String removeTags(String text, String[] exceptions) {
		String txtResult = text;
		final String space = " ";
		final String tagIni = "<";
		final String tagFin = ">";
		int startPos = 0;
		int posTagFin = 0;
		String tagName = "";
		boolean isException = false;
	
		int j = 0;
		List<String> tagList = new ArrayList<String>();
	
		while ((startPos = txtResult.indexOf(tagIni, startPos)) >= 0) {
		    posTagFin = txtResult.indexOf(tagFin, startPos) + 1;
	
		    if (posTagFin == 0) {
		    	break;
		    }
	
		    tagName = txtResult.substring(startPos, posTagFin);
	
		    if (startPos + 2 > txtResult.length()) {
		    	break;
		    }
		    else if (txtResult.substring(startPos + 1, startPos + 2).equals(space)) {
		    	startPos = posTagFin;
		    	continue;
		    }
	
		    startPos = posTagFin;
		    tagList.add(tagName);
		}
	
		for (String tag : tagList) {
		    isException = false;
	
		    for (j = 0; j < exceptions.length; j++) {
				if (exceptions[j].equals(tag)) {
				    isException = true;
				    break;
				}
		    }
	
		    if (!isException) {
		    	txtResult = txtResult.replace(tag, "");
		    }
		}
	
		return txtResult;
    }
    
    public static String getSedexFormatted(String sedex){
    	
    	if (sedex != null && !sedex.trim().isEmpty()){
    		
    		sedex = sedex.replaceAll("RAST\\.", "");
	    	String [] split = sedex.trim().split("\\(");
	    	
	    	if (split.length > 0){
	    		sedex = split[0].trim() + "BR";
	    		
	    		if (split.length > 1){
	    			sedex += "(" + split[1].trim();
	    		}
	    	}
    	}
    	
    	return sedex;
    }
    
    public static String inserirCharAt(String str, String charac, int deslDireito){
		str = new StringBuilder(str).insert(
				str.length() - deslDireito, "-").toString();
		
		return str;
    }
    
    public static boolean isEmpty(String value){
    	boolean isEmpty = value == null || value.isEmpty();
    	return isEmpty;
    }
}