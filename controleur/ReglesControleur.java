package application.controleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class ReglesControleur implements Initializable {
	
    @FXML
    private TextArea reglesTextArea;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		reglesTextArea.setText("On est au Moyen Age et vous contrôlez un personnage, Link. Pour gagner, vous devez \ntuer ntous les ennemis que vous allez rencontrer sur la carte. Si ils vous tuent, vous\nperdez la partie.\n\nVous commencez avec un bâton. Vous pourrez ramasser les armes que vous trouvez\n sur la carte ou que les ennemis abandonnent quand ils meurent.\nVous pourrez posséder un bouclier qui vous donnera des points de défense et\namortira les attaques de l'ennemi, il se détruit quand il perd ses points de défense. \nVous pouvez ramasser des fruits quand les arbres deviennent fruitiers.\nUn arbre est vert et il devient bleu quand il donne des fruits.\nVous pouvez pousser les pierres.\nVous pouvez nager, mais vous finirez par perdre des points de vie si vous restez\ntrop dans l'eau.\n\nTOUCHES:\nA: Attaquer\nC: Cueillir fruit\nP: Pousser pierre\nF: Changer d'arme\nR: Ramasser arme/bouclier\nFlèches : se déplacer. \n\nDIFFICULTE:\nEn mode facile, vous pouvez rester dans l'eau plus longtemps,\nles arbres donnent souvent des fruits qui rapportent beaucoup de points de vie.\nDes outils apparaissent souvent et les ennemis apparaissent rarement.\nEn mode moyen, les arbres donnent moins de fruits et ceux ci sont moins pourvus en PV,\net vous perdez plus vite des points de vie dans l'eau.\nLes ennemis apparaissent plus souvent et les outils un peu moins souvent.\nEn mode difficile, c'est la même configuration que le mode moyen mais en plus dur.\n\nSCORE:\nVous gagnez 1 point par ennemi au sol tué, et 2 points par ennemi volant tué.\nVous ne pouvez attaquer les ennemis volants qu'avec l'arc à flèches.");

	}

}
