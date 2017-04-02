package jquery.app.selectors;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import jquery.dom.items.EntryDom;

public interface JQuerySelectedElement {
	void 		  transform(Document root);
	List<Element> render(List<EntryDom> list);
}
