package jquery.app.selectors;

import java.util.List;

import jquery.dom.items.EntryDom;

public interface JQuerySelectors {
	void transform(String selector);
	List<List<EntryDom>> render();
}
