package jquery.dom.items;

import java.util.HashMap;
import java.util.Map;

public class EntryDom {
	private String name;
	private Map<String, String> properties;
	
	public EntryDom(){}
	
	public static EntryDom createEntryDom(String name){
		EntryDom entry;
		
		entry = new EntryDom();
		entry.setName(name);
		return (entry);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Map<String, String> getProperties() {
		if (properties ==  null){
			properties = new HashMap<String, String>();
		}
		return properties;
	}
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	@Override
	public String toString() {
		return "EntryDom [name=" + name + ", properties=" + properties + "]";
	}
}
