package game.objects;

import game.entities.Player;
import game.level.LevelObject;
import game.main.Game;
import game.state.Level_1_State;
import game.state.Level_2_State;
import game.state.Level_3_State;
import game.state.Level_4_State;
import game.state.Level_5_State;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/*
 * Οι καρδούλες που μαζεύει ο παίκτης κατά τη διάρκεια του παιχνιδιού
 * και του αυξάνουν τη ζωή.
 */

public class Heart extends LevelObject {

	private Image image;
	private Player player;
	private boolean gained;
	private Sound gainSound;

	public Heart(float x, float y) throws SlickException {
		super(x, y);

		image = new Image("resources/images/other/heart.png");
		gainSound = new Sound("resources/sounds/gainSound.ogg");

		switch (Game.CHOOSEN_LEVEL) {
			case 1:
				player = Level_1_State.player;
				break;
			case 2:
				player = Level_2_State.player;
				break;
			case 3:
				player = Level_3_State.player;
				break;
			case 4:
				player = Level_4_State.player;
				break;
			case 5:
				player = Level_5_State.player;
				break;
		}
	}

	public void CheckIfGained() {
		if (player.getX() >= x - 60 && player.getX() <= x + 60
				&& player.getY() >= y - 60 && player.getY() <= y + 60) {
			gained = true;
		} else
			gained = false;
	}

	public void render(float offset_x) {
		float xPos = (x - offset_x);

		if (!gained) {
			CheckIfGained();
			if (!gained)
				image.draw(xPos, y - 15);
			else {
				player.gainHealth();
				gained = true;
				gainSound.play();
			}
		}
	}
}
