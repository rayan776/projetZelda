package application.vue;

import java.util.HashMap;

public class HashMapDesMaps {
	
	private HashMap<String,String> listeMaps;
	
	public HashMapDesMaps() {
		listeMaps = new HashMap<String,String>();
		listeMaps.put("Plaine", "src/application/resources/plaine.txt");
		listeMaps.put("Ile", "src/application/resources/ile.txt");
		listeMaps.put("Forêt", "src/application/resources/foret.txt");
	}
	
	public HashMap<String,String> getListeMaps() {
		return this.listeMaps;
	}
	

}
