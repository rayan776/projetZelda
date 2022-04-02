package application.modele;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TileMap {
	
	private int[][] map;
	
	public TileMap() {
		
	}

	public int getNombre(int x, int y) {
		return this.map[x][y];
	}
	
	public void setNombre(int x, int y, int n) {
		this.map[x][y] = n;
	}
	
	public int getHauteur() {
		return this.map.length;
	}
	
	public int getLargeur() {
		return this.map[0].length;
	}
	
	public void intervertir(int xA, int yA, int xB, int yB) {
		// intervertit les valeurs de deux cases A et B du tableau
		
		int pivot = this.map[xB][yB];
		this.map[xB][yB] = this.map[xA][yA];
		this.map[xA][yA] = pivot;
	}

	
	public void chargerFichier(String filename) throws MapException {
		
		String[] tab;
		
		int numeroLigne = 0;
		int hauteur, largeur;
		
		try {
			List<String> lignes = Files.readAllLines(Paths.get(filename));
			
			
			if (lignes.size()<Constantes.HAUTEUR_MAP_MIN||lignes.size()>Constantes.HAUTEUR_MAP_MAX) 
				throw new MapException();
			else {
				
				hauteur = lignes.size();
				
				if (!Utilitaires.tousMemeLongueur(lignes)||!Utilitaires.longueurDesLignes(Constantes.LONGUEUR_MAP_MIN, Constantes.LONGUEUR_MAP_MAX, lignes)) throw new MapException(); // la map doit être de même longueur partout.
				
				largeur = lignes.get(0).length();
				
				this.map = new int[hauteur][largeur];
				
				for (String ligne : lignes) {
					tab = ligne.split("");
					
					for (int i=0; i<tab.length; i++) {
						
						try {
							if (tab[i].trim().length()>0)
								this.map[numeroLigne][i] = Integer.parseInt(tab[i].trim());
						}
						catch (Exception e) {
							
							throw new MapException(); // la map ne doit contenir que des chiffres.
						}
				
						
					}
					
					numeroLigne++;
				}
			}
			
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	

}
