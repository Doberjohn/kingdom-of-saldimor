package game.state;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/*
 * Αυτή είναι μία πολύ μικρή οθόνη στην οποία φαίνονται τα ονόματα των παιδιών
 * που δούλεψαν για να ολοκληρωθεί το έργο.
 */

public class CreditsState extends BasicGameState {
	private Input input;

	private Music music;

	private Image title;
	private Image[] swords;
	private Image exitMessage;

	private Animation swordDance;
	private int time;

	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		music = new Music("resources/sounds/creditsMusic.ogg");

		title = new Image("resources/images/credits/developementTeam.png");
		exitMessage = new Image("resources/images/credits/ESCnotifier.png");

		swords = new Image[9];
		swords[0] = new Image("resources/images/credits/johnSword.png");
		swords[1] = new Image("resources/images/credits/anestisSword.png");
		swords[2] = new Image("resources/images/credits/faihSword.png");
		swords[3] = new Image("resources/images/credits/kostasSword.png");
		swords[4] = new Image("resources/images/credits/georgeSword.png");
		swords[5] = new Image("resources/images/credits/mariaSword.png");
		swords[6] = new Image("resources/images/credits/arisSword.png");
		swords[7] = new Image("resources/images/credits/lefterisSword.png");
		swords[8] = new Image("resources/images/credits/iwannaSword.png");
		swordDance = new Animation(swords, 3000);
	}

	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		time += delta;
		input = container.getInput();

		if (input.isKeyPressed(Input.KEY_ENTER) || time >= 25000) {
			sbg.enterState(0, new FadeOutTransition(Color.black, 1000),
					new FadeInTransition());
		}
	}

	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		swordDance.draw(300, 400);
		title.draw(200, 0);
		exitMessage.draw(10, 910);
	}

	public void enter(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		music.loop(1f, container.getMusicVolume());
		swordDance.restart();
		time = 0;
	}

	public void leave(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		music.stop();
	}

	public int getID() {
		return 8;
	}
}