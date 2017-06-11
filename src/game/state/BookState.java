package game.state;

import game.main.Game;

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
 * Σε αυτή την οθόνη, η οποία εμφανίζειτα ανάμεσα στις πίστες, ο παίκτης μπορεί να
 * διαβάσει την ιστορία του παιχνιδιού και έπειτα να προχωρήσει στην επόμενη πίστα.
 * Αποτελείται από μόνο 1 εικόνα.
 */

public class BookState extends BasicGameState {
	private Input input;
	private Image book;
	private Music music1;
	private Music music2;
	private Music music3;
	private Music music;

	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		music1 = new Music("resources/sounds/readingMusic.ogg");
		music2 = new Music("resources/sounds/readingMusic2.ogg");
		music3 = new Music("resources/sounds/readingMusic3.ogg");
	}

	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		input = container.getInput();

		if (input.isMouseButtonDown(0)) {
			container.setMouseGrabbed(true);
			music.fade(1000, 0f, true);
			if (Game.CHOOSEN_LEVEL == 6) {
				Game.CHOOSEN_LEVEL = 5;
				sbg.enterState(0, new FadeOutTransition(Color.black, 1000),
						new FadeInTransition());
			} else {
				sbg.enterState(Game.CHOOSEN_LEVEL, new FadeOutTransition(
						Color.black, 1000), new FadeInTransition());
			}

		}
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		book.draw();
	}

	public void enter(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		switch (Game.CHOOSEN_LEVEL) {
			case 1:
				music = music1;
				break;
			case 6:
				music = music3;
				break;
			default:
				music = music2;
		}
		music.loop(1f, container.getMusicVolume());
		book = new Image("resources/images/books/Story Book ("
				+ Game.CHOOSEN_LEVEL + ").png");
		container.setMouseGrabbed(false);
	}

	public int getID() {
		return 9;
	}
}