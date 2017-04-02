package jquery.app.selectors.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jquery.app.selectors.JQuerySelectors;
import jquery.dom.items.EntryDom;

public class JQuerySelectorsImpl implements JQuerySelectors {
	private List<List<EntryDom>> entries;
	
	
	public boolean addAttribute(String originalName, EntryDom lexeme, int startIndex){
		final int begin = originalName.indexOf("[", startIndex);
		final int end = originalName.indexOf("]", startIndex);
		final int separator = originalName.indexOf("=", startIndex);
		if (begin != -1 && end != -1 && separator != -1) {
			final String typeProperty = originalName.substring(begin + 1, separator);
			final String valueProperty = originalName.substring(separator + 1, end);

			lexeme.getProperties().put(typeProperty, valueProperty);
			addAttribute(originalName, lexeme, end + 1);
			return (true);
		} 
		return (false);
	}
	
	@Override
	public List<List<EntryDom>> render() {
		return (entries);
	}

	@Override
	public void transform(String allSelectors) {
		final String[] allSelects = allSelectors.split(",");
		List<List<EntryDom>> allLexemes;
		
		allLexemes = new ArrayList<List<EntryDom>>();
		Arrays.asList(allSelects).forEach(selector -> {
			final String[] tab = selector.split("[ ]+");
			final List<EntryDom> lexemes = new ArrayList<EntryDom>();		
			
			for (int i = 0; i < tab.length; i++) {
				EntryDom entry;
				int   end;
				
				end = tab[i].indexOf("[");
				if (end != -1){
					entry = EntryDom.createEntryDom(tab[i].substring(0, end));	
				}
				else{
					entry = EntryDom.createEntryDom(tab[i]);
				}
				addAttribute(tab[i], entry, 0);
				lexemes.add(entry);
			}
			allLexemes.add(new ArrayList<EntryDom>(lexemes));
		});
		entries = allLexemes;
		System.out.println(entries);
	}

}
