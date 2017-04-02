package jquery.app;

import static jquery.dom.JQueryJava.JQuery;
import static jquery.dom.JQueryJava.close;
import static jquery.dom.JQueryJava.configure;
import static jquery.dom.JQueryJava.output;

import java.io.OutputStream;

import org.jdom2.Attribute;
import org.jdom2.Element;

public class Main {
	public static void main(String[] args) {
		OutputStream writer = configure("example.xml");
		if (writer != null) {
			Element user1 = new Element("user");
			Element user2 = new Element("user");

			user1.setAttribute(new Attribute("id", "1"));
			user2.setAttribute(new Attribute("id",  "2"));
			JQuery("users").append(user1);
			JQuery("users").append(user2);
			JQuery("user[id=1]").append(new Element("nom").setText("simoes"));
			JQuery("user[id=1]").append(new Element("prenom").setText("thomas"));
			JQuery("user[id=2]").append(new Element("nom").setText("simoes"));
			JQuery("user[id=2]").append(new Element("prenom").setText("lea"));
			output(writer);
			close(writer);
		}
	}
}
