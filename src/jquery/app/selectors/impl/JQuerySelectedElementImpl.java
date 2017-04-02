package jquery.app.selectors.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.jdom2.Document;
import org.jdom2.Element;

import jquery.app.selectors.JQuerySelectedElement;
import jquery.dom.items.EntryDom;

public class JQuerySelectedElementImpl implements JQuerySelectedElement{
	
	private Element rootElement;
	private List<Element> selectedElements;

	public JQuerySelectedElementImpl(){
		selectedElements = new ArrayList<Element>();
	}
	
	@Override
	public void transform(Document root) {
		rootElement = root.getRootElement();
	}

	@Override
	public List<Element> render(List<EntryDom> list) {
		if (rootElement.getName().equals(list.get(0).getName())){
			if (list.size() == 1){
				selectedElements.add(rootElement);
				return (selectedElements);
			}
			list.remove(0);
		}
		getElements(rootElement, list);
		return (selectedElements);
	}
	
	public void getElements(Element root, List<EntryDom> list) {
		list.forEach(item -> {
			if (hasChild(root, item)) {
				List<EntryDom> elements = new ArrayList<EntryDom>(list);
				elements.remove(item);
				List<Element> childrens = getChilds(root, item);
				childrens.forEach(child -> getElements(child, elements));
				if (elements.size() == 0) {
					this.selectedElements.addAll(childrens);
				}
			} else {
				root.getChildren().forEach(oneItem -> getElements(oneItem, list));
			}
		});
	}
	
	public List<Element> getChilds(Element element, EntryDom entry) {
		List<Element> list;

		list = new ArrayList<Element>();
		element.getChildren().forEach(children -> {
			if (children.getName().equals(entry.getName())) {
				Element child;
				Iterator<Entry<String, String>> it_entry;
				
				child = children;
				it_entry = entry.
						getProperties().entrySet().iterator();
				while (it_entry.hasNext()) {
					Entry<String, String> item = it_entry.next();
					
					if (child.getAttribute(item.getKey()) != null){
						if (!child.getAttributeValue(item.getKey())
								.equals(item.getValue())) {
							return;
						}	
					}
					else{
						return;
					}
				}
				list.add(children);
			}
		});
		return (list);
	}

	public boolean hasChild(Element element, EntryDom entry) {
		return (element.getChild(entry.getName()) != null);
	}
}
