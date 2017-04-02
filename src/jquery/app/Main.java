package jquery.app;

import static jquery.dom.JQueryJava.JQuery;
import static jquery.dom.JQueryJava.close;
import static jquery.dom.JQueryJava.configure;
import static jquery.dom.JQueryJava.output;

import java.io.OutputStream;

public class Main {
	public static void main(String[] args) {
		OutputStream writer = configure("example.xml");
		if (writer != null) {
			String str = JQuery("user pseudo").el().toString();
			JQuery("user[id=2] name").remove();
			System.out.println(JQuery("user pseudo").el().size());
			System.out.println(str);
			output(writer);
			close(writer);
		}
	}
}
