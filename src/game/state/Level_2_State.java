package game.state;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

/*
 * Κάθε Level_#_State κληρονομεί από την μητρική LevelState όλες τις μεθόδους της
 * και ουσιαστικά διαφέρει στον χάρτη τον οποίο φορτώνουμε, το πλήθος των εχθρών που περνάμε
 * σαν όρισμα στον κατασκευαστή της μητρικής κλάσης και το σημείο τερματισμού (σε pixel).
 */

public class Level_2_State extends LevelState {
	public Level_2_State() throws SlickException {
		super(new Sound("resources/sounds/footsteps.ogg"), 2, 25, 17, 18960);
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		super.render(arg0, arg1, g);
	}
}
