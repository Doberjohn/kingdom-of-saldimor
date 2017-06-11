package game.state;

import game.entities.Player;
import game.main.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/*
 * Κάθε Level_#_State κληρονομεί από την μητρική LevelState όλες τις μεθόδους της
 * και ουσιαστικά διαφέρει στον χάρτη τον οποίο φορτώνουμε, το πλήθος των εχθρών που περνάμε
 * σαν όρισμα στον κατασκευαστή της μητρικής κλάσης και το σημείο τερματισμού (σε pixel).
 */

public class Level_5_State extends LevelState {
	public Level_5_State() throws SlickException {
		super(new Sound("resources/sounds/leafs.ogg"), 5, 1, 1, 1000);
	}
	
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		playerController.handleInput(container, delta);
		physics.handlePhysics(level, delta);

		if (!player.isAlive()
				|| container.getInput().isKeyDown(Input.KEY_ESCAPE)) {
			MenuState.music.setVolume(container.getMusicVolume());
			sbg.enterState(0, new FadeOutTransition(Color.black, 3000),
					new FadeInTransition());
		}
		
		if (Player.KILLED == 1) {
			player.stopMoving();
			Game.CHOOSEN_LEVEL++;
			sbg.enterState(9, new FadeOutTransition(Color.black, 5000),
					new FadeInTransition());
		}
	}
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		level.render();
	}
}
