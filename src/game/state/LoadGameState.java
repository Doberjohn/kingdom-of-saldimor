package game.state;

import game.main.Game;
import game.utilities.OptionsButton;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/*
 * Η οθόνη αποτελείται από έναν χάρτη στον οποίο μπορούμε να επιλέξουμε 
 * ποια πίστα θέλουμε να παίξουμε. Η εικόνα που εμφανίζεται είναι ανάλογη
 * με το πλήθος των επιπέδων που έχουμε τερματίσει και αυτός ο αριθμός
 * διαβάζεται από το πρόγραμμα κάθε φορά που το ανοίγουμε. Βρίσκεται
 * αποθηκευμένος σε ένα αρχείο κειμένου και κάθε φορά που τερματίζουμε μία
 * πίστα ενημερώνεται αυτό το αρχείο ώστε να δείχνει στον σωστό αριθμό.
 */

public class LoadGameState extends BasicGameState {
	private Input input;
	private Image background;

	private OptionsButton backButton;

	private Sound clickSound;
	private Sound errorSound;

	private Circle mouse;
	private Circle Level1;
	private Circle Level2;
	private Circle Level3;
	private Circle Level4;
	private Circle Level5;

	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {

		backButton = new OptionsButton(container, "back_button", 10, 10);

		clickSound = new Sound("resources/sounds/optionsClick.ogg");
		errorSound = new Sound("resources/sounds/error.wav");

		mouse = new Circle(0, 0, 1);

		Level1 = new Circle(424, 195, 85);
		Level2 = new Circle(967, 748, 85);
		Level3 = new Circle(340, 707, 85);
		Level4 = new Circle(925, 260, 85);
		Level5 = new Circle(655, 490, 85);
	}

	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		input = container.getInput();

		mouse.setCenterX(input.getMouseX());
		mouse.setCenterY(input.getMouseY());

		if (input.isMousePressed(0)) {
			if (backButton.isMouseOver()) {
				clickSound.play(1f, container.getSoundVolume());
				sbg.enterState(0, new FadeOutTransition(),
						new FadeInTransition());
			}
			if (mouse.intersects(Level1))
				loadLevel(container, sbg, 1);

			if (mouse.intersects(Level2))
				loadLevel(container, sbg, 2);

			if (mouse.intersects(Level3))
				loadLevel(container, sbg, 3);

			if (mouse.intersects(Level4))
				loadLevel(container, sbg, 4);

			if (mouse.intersects(Level5))
				loadLevel(container, sbg, 5);
		}
	}

	public void loadLevel(GameContainer container, StateBasedGame sbg, int level) {
		if (Game.LEVEL < level) {
			errorSound.play(1f, container.getSoundVolume());
		} else {
			clickSound.play(1f, container.getSoundVolume());
			MenuState.music.fade(3000, 0f, true);
			Game.CHOOSEN_LEVEL = level;
			sbg.enterState(9, new FadeOutTransition(Color.black, 3000),
					new FadeInTransition());
		}
	}
	
	public void enter(GameContainer container, StateBasedGame sbg) throws SlickException {
		background = new Image("resources/images/chapters/Kingdom Map ("
				+ Game.LEVEL + ").png");
	}
	
	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw();
		backButton.render(container, g);
	}

	public int getID() {
		return 6;
	}
}