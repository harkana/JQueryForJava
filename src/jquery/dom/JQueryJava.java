package jquery.dom;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import jquery.app.selectors.JQuerySelectedElement;
import jquery.app.selectors.JQuerySelectors;
import jquery.app.selectors.impl.JQuerySelectedElementImpl;
import jquery.app.selectors.impl.JQuerySelectorsImpl;
import jquery.dom.factory.JQueryFactory;

public class JQueryJava {
	Document doc;
	List<Element> selectedElements;
	static JQueryJava _jquery;

	private JQueryJava(JQueryJava jquery) {
		this.doc = jquery.doc.clone();
		this.selectedElements = new ArrayList<Element>();
		this.selectedElements.addAll(jquery.selectedElements);
	}

	public JQueryJava(Document doc) {
		this.doc = doc;
		selectedElements = new ArrayList<Element>();
	}
	
	public static void close(OutputStream writer){
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("Error : " + e.getMessage());
		}
	}
	
	public static OutputStream configure(String name) {
		try {
			_jquery = JQueryFactory.buildJQuery(name);
		} catch (JDOMException | IOException e) {
			System.out.println("Error : " + e.getMessage());
		}
		try {
			return (Files.newOutputStream(Paths.get("example.xml"), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING));
		} catch (IOException e) {
			System.out.println("Error : " + e.getMessage());
			return (null);
		}
	}

	public static JQueryJava JQuery(String str) {
		return (_jquery.selectors(str));
	}
	
	public JQueryJava selectors(String str) {
		JQuerySelectors selectors;
		JQuerySelectedElement selected;
		
		selectors = new JQuerySelectorsImpl();
		selected = new JQuerySelectedElementImpl();	
		selectors.transform(str);
		selected.transform(doc);
		selectors.render().
		stream().
		map(list -> selected.render(list)).
		collect(Collectors.toList()).
		forEach(list -> {
			selectedElements.addAll(list);
		});
		Set<Element> tmp = selectedElements.stream().collect(Collectors.toSet());
		selectedElements.clear();
		selectedElements.addAll(tmp.stream().collect(Collectors.toList()));
		return (new JQueryJava(this));
	}


	public String val() {
		if (this.selectedElements.size() == 1){
			return (this.selectedElements.get(0).getValue());
		}
		return ("");
	}
	
	public void prepend(Element element){
         this.selectedElements.forEach(select -> {
             List<Element> newElements;
             
             newElements = new ArrayList<Element>();
        	 newElements.add(element);
        	 newElements.addAll(select.getChildren());
        	 select.getChildren().clear();
        	 select.getChildren().addAll(newElements);
         });
	}
	
	
	public void append(Element element){
		this.selectedElements.forEach(select -> {
			select.addContent(element.clone());
		});		
	}
	
	public void remove(){
		this.selectedElements.forEach(select -> {
			select.getParent().removeContent(select);
		});
	}
	
	public List<Element> el(){
		return (this.selectedElements);
	}
	
	public List<Element> children(){
		List<Element> childrens;
		
		childrens = new ArrayList<Element>();
		this.selectedElements.forEach(element ->{
			childrens.addAll(new ArrayList<Element>(element.getChildren()));
		});
		return (childrens);
	}

	public String nodeName() {
		if (this.selectedElements.size() == 0) {
			return (null);
		}
		return (this.selectedElements.get(0).getName());
	}
	
	public static void output(OutputStream o){
		try {
			_jquery.renderDOM(o);
		} catch (IOException e) {
			System.out.println("Fail to render DOM");
		}
	}

	public void renderDOM(OutputStream o) throws IOException {
		final XMLOutputter xml = new XMLOutputter();
		xml.setFormat(Format.getPrettyFormat());
		xml.output(doc, o);
	}
	
	public Document rootDoc(){
		return (doc);
	}
}
