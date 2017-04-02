package jquery.dom.factory;

import java.io.IOException;
import java.io.OutputStream;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import jquery.dom.JQueryJava;

public class JQueryFactory {	
	private OutputStream writer;
	
	public static JQueryJava buildJQuery(String filename) 
			throws JDOMException, 
			IOException{
		Document doc = new SAXBuilder().build(filename);
		JQueryJava _jquery = new JQueryJava(doc);
		return (_jquery);
	}
}
