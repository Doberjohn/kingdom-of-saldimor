package game.state;

import game.main.Game;
import game.utilities.MenuButton;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/*
 * H οθόνη του κυρίως μενού του παιχνιδιού. Αποτελείται από όλα τα βασικά πλήκτρα
 * για την πλοήγηση και στις υπόλοιπες οθόνες του παιχνιδιού και κάθε κλικ σε κάποιο
 * από τα πλήκτρα, μας μεταφέρει αυτόματα στην αντίστοιχη οθόνη.
 */

public class MenuState extends BasicGameState {
	public static Music music;

	private Input input;

	private Sound hoverSound;
	private Sound clickSound;

	private Image background;

	private MenuButton playButton;
	private MenuButton loadButton;
	private MenuButton optionsButton;
	private MenuButton creditsButton;
	private MenuButton exitButton;

	private boolean playSound = true;
	
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		
		background = new Image("resources/images/backgrounds/menubg.png");
		container.setMouseCursor("resources/images/other/CursorB.png", 0, 0);

		music = new Music("resources/sounds/Main_Menu_Music.ogg");

		hoverSound = new Sound("resources/sounds/swordOver.ogg");
		clickSound = new Sound("resources/sounds/swordClick.ogg");

		playButton = new MenuButton(container, "New_Game", 460);
		loadButton = new MenuButton(container, "Load_Chapter", 560);
		optionsButton = new MenuButton(container, "Options", 660);
		creditsButton = new MenuButton(container, "Credits", 760);
		exitButton = new MenuButton(container, "Exit", 860);
	}

	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		input = container.getInput();

		if (input.isMousePressed(0)) {
			if (playButton.isMouseOver()) {
				Game.CHOOSEN_LEVEL = 1;
				Game.saveGame(1);
				clickSound.play(1f, container.getSoundVolume() - 0.5f);
				music.fade(3000, 0f, true);
				sbg.enterState(9, new FadeOutTransition(Color.black, 3000),
						new FadeInTransition());
			} else if (loadButton.isMouseOver()) {
				clickSound.play(1f, container.getSoundVolume() - 0.5f);
				sbg.enterState(6, new FadeOutTransition(),
						new FadeInTransition());
			} else if (optionsButton.isMouseOver()) {
				clickSound.play(1f, container.getSoundVolume() - 0.5f);
				sbg.enterState(7, new FadeOutTransition(),
						new FadeInTransition());
			} else if (creditsButton.isMouseOver()) {
				clickSound.play(1f, container.getSoundVolume() - 0.5f);
				music.fade(2000, 0f, true);
				container.setMouseGrabbed(true);
				sbg.enterState(8, new FadeOutTransition(Color.black, 2000),
						new FadeInTransition());
			} else if (exitButton.isMouseOver()) {
				container.exit();
			}
		}

		if (playButton.isMouseOver() || loadButton.isMouseOver()
				|| optionsButton.isMouseOver()
				|| creditsButton.isMouseOver() || exitButton.isMouseOver()) {
			if (playSound) {
				hoverSound.play(1f, container.getSoundVolume());
				playSound = false;
			}
		} else
			playSound = true;
	}

	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw();

		playButton.render(container, g);
		loadButton.render(container, g);
		optionsButton.render(container, g);
		creditsButton.render(container, g);
		exitButton.render(container, g);
	}

	public void enter(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		container.setMouseGrabbed(false);
		if (!music.playing()) {
			music.loop(1f, container.getMusicVolume());
		}
	}

	public int getID() {
		return 0;
	}
}
