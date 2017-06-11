package game.state;

import game.utilities.OptionsButton;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/*
 * Το μενού των ρυθμίσεων αποτελείται από 3 επιλογές που μεταβάλουν τις ρυθμίσεις του παιχνιδιού 
 * και ένα πλήκτρο για να επιστρέψουμε στο κυρίως μενού. Οι επιλογές αυτές είναι 1)αλλαγή της
 * ανάλυσης της οθόνης, 2)αλλαγή της έντασης της μουσικής του παιχνιδιού και 3)αλλαγή της
 * εντάσης των ηχητικών εφέ (πχ στον ήχο του κλικ σε κάποιο πλήκτρο).
 */

public class OptionsState extends BasicGameState {
	private Input input;

	private Image background;
	private Image optionRes;
	private Image optionMusic;
	private Image optionSFX;
	
	private OptionsButton backButton;
	private OptionsButton smallShield;
	private OptionsButton bigShield;
	private OptionsButton swordSheath;
	private OptionsButton sword;
	private OptionsButton daggerSheath;
	private OptionsButton dagger;

	private Sound clickSound;

	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		background = new Image("resources/images/backgrounds/menubg.png");

		optionRes = new Image("resources/images/options/optRes.png");
		smallShield = new OptionsButton(container, "shieldSmall", 580, 570);
		bigShield = new OptionsButton(container, "shieldBig", 680, 520);
		optionMusic = new Image("resources/images/options/optMusic.png");
		swordSheath = new OptionsButton(container, "ss", 10, 800);
		sword = new OptionsButton(container, "s", 245, 780);
		optionSFX = new Image("resources/images/options/optSFX.png");
		daggerSheath = new OptionsButton(container, "ds", 950, 800);
		dagger = new OptionsButton(container, "d", 1050, 800);
		backButton = new OptionsButton(container, "back_button", 10, 10);

		clickSound = new Sound("resources/sounds/optionsClick.ogg");
	}

	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		input = container.getInput();

		if (input.isMousePressed(0)) {
			if (smallShield.isMouseOver()) {
				container.setMouseCursor("resources/images/other/Cursor.png",
						0, 0);
				AppGameContainer gc = (AppGameContainer) container;
				gc.setDisplayMode(640, 480, false);
				clickSound.play(1f, container.getSoundVolume());
			} else if (bigShield.isMouseOver()) {
				container.setMouseCursor("resources/images/other/CursorB.png",
						0, 0);
				AppGameContainer gc = (AppGameContainer) container;
				gc.setDisplayMode(1280, 960, false);
				clickSound.play(1f, container.getSoundVolume());
			} else if (sword.isMouseOver() || swordSheath.isMouseOver()) {
				if (sword.getX() > 45) {
					sword.setX(sword.getX() - 100);
					container.setMusicVolume(container.getMusicVolume() - 0.5f);
				} else {
					sword.setX(245);
					container.setMusicVolume(1.0f);
				}
				clickSound.play(1f, container.getSoundVolume());
			} else if (dagger.isMouseOver() || daggerSheath.isMouseOver()) {
				if (dagger.getX() > 950) {
					dagger.setX(dagger.getX() - 50);
					container.setSoundVolume(container.getSoundVolume() - 0.5f);
				} else {
					dagger.setX(1050);
					container.setSoundVolume(1.0f);
				}
				clickSound.play(1f, container.getSoundVolume());
			} else if (backButton.isMouseOver()) {
				clickSound.play(1f, container.getSoundVolume());
				sbg.enterState(0, new FadeOutTransition(),
						new FadeInTransition());
			}
		}
	}

	public void render(GameContainer container, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw();

		optionRes.draw(530, 470);
		smallShield.render(container, g);
		bigShield.render(container, g);
		optionMusic.draw(70, 730);
		sword.render(container, g);
		swordSheath.render(container, g);
		optionSFX.draw(900, 730);
		dagger.render(container, g);
		daggerSheath.render(container, g);
		backButton.render(container, g);
	}

	public int getID() {
		return 7;
	}
}
