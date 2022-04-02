package application.controleur;

import application.modele.Link;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class LinkKeyHandler {
	
	private Pane p;
	private Link l;
	
	public LinkKeyHandler(Pane p, Link l) {
		this.l = l;
		this.p = p;
	}
	
	public void createHandler() {
	
		EventHandler<KeyEvent> moveSprite = new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					l.setDir(8);
					l.seDéplacer();
					break;
				case DOWN:
					l.setDir(2);
					l.seDéplacer();
					break;
				case RIGHT:
					l.setDir(6);
					l.seDéplacer();
					break;
				case LEFT:
					l.setDir(4);
					l.seDéplacer();
					break;
				case A:
					l.attaquer();
					break;
				case R:
					l.ramasser();
					break;
				case F:
					l.changerArme();
					break;
				case P:
					l.pousserPierre();
					break;
				case C:
					l.cueillir();
					break;
				default:
					break;
				}
				
			}
		};
		
		p.addEventFilter(KeyEvent.KEY_PRESSED, moveSprite);
		
		l.pvProperty().addListener((ob,ol,nouv) -> {
			if (ob.getValue().intValue()<=0) p.removeEventFilter(KeyEvent.KEY_PRESSED, moveSprite);
		});
	}

	

}
