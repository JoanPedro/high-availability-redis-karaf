package db.protocols;

import java.util.Map;

public interface ICommands {

	public String get(String key);
	
	public String hashGet(String key, String field);
	
	public void set(String key, String value);
	
	public void hashSet(String key, String field, String value);
	
	public void mapSet(String key, Map<String, String> map);

}
