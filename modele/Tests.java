package application.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class Tests {
	
	@Test
	public final void mapExceptionTest() throws MapException {
		
		TileMap tm = new TileMap();
		
		// map trop petite
		Exception exception = assertThrows(MapException.class, () -> {
			tm.chargerFichier("src/application/resources/mapTropPetite.txt");
		});
		
		// map qui ne contient pas que des chiffres
		exception = assertThrows(MapException.class, () -> {
			tm.chargerFichier("src/application/resources/mapPasQueDesChiffres.txt");
		});
		
		// map dont les lignes ne sont pas toutes de même longueur
		exception = assertThrows(MapException.class, () -> {
			tm.chargerFichier("src/application/resources/mapLignesPasDeMemeLongueur.txt");
		});
		
	
	}
	
	@Test
	public final void testsDivers() throws MapException {
		TileMap tm = new TileMap();
		tm.chargerFichier("src/application/resources/plaine.txt");
		
		Environnement env = new Environnement(tm, 1);
		
		EnnemiQuiVole eqv = new EnnemiQuiVole(0,0, env);
		
		Link lnk = new Link(0,1,env);
		
		Arc arc = new Arc();
		
		env.ajouterActeur(lnk);
		env.ajouterActeur(eqv);
		
		lnk.ajouterOutil(arc);
		lnk.setArme(arc);
		
		lnk.attaquer();
		
		// Link est censé attaquer l'ennemi qui se trouve à une case de lui, avec son arc (15 PA). 
		// Les PV de l'ennemi devraient être de 50-15=35 pv
		assertEquals(35, eqv.getPV());
		
		// Link est censé se déplacer d'un rang à droite, donc ses coordonnées X doivent devenir 1
		lnk.setDir(6);
		lnk.seDéplacer();
		
		assertEquals(1, lnk.getX());
		
		lnk.decrementePV(50);
		// Link passe de 100 à 50 PV
		
		tm.setNombre(2, 1, 5);
		// L'arbre qui est a coté de lui devient fruitier
		
		lnk.cueillir();
		// Link est censé ramasser un fruit de l'arbre et donc gagner 20 PV (on est en mode facile)
		
		assertEquals(70, lnk.getPV());
		
		
		
	}

}
