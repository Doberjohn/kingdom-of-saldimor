package game.state;

import game.entities.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

/*
 * Κάθε Level_#_State κληρονομεί από την μητρική LevelState όλες τις μεθόδους της
 * και ουσιαστικά διαφέρει στον χάρτη τον οποίο φορτώνουμε, το πλήθος των εχθρών που περνάμε
 * σαν όρισμα στον κατασκευαστή της μητρικής κλάσης και το σημείο τερματισμού (σε pixel).
 */

public class Level_4_State extends LevelState {
	private Image king;

	public Level_4_State() throws SlickException {
		super(new Sound("resources/sounds/leafs.ogg"), 4, 50, 50, 18960);

		king = new Image("resources/images/characters/King.png");
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		level.render();
		king.draw(19000 - this.level.getXOffset(), 752);

		g.setColor(Color.black);
		if (Player.KILLED < 50) {
			g.drawString("This is your greatest battle against King Valador!",
					500, 0);
			g.drawString("Kill every soldier on the battlefield!", 540, 40);
		} else {
			g.drawString(
					"Victory is ours! Proceed to FINISH to kill King Valador!",
					500, 0);
		}
	}
}
