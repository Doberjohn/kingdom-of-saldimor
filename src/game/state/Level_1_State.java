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

/*
 * Μία μικρή εξαίρεση στον κανόνα αποτελεί η Level_1_State της οποίας το περιβάλλον και οι
 * πληροφορίες που μας δίνει η οθόνη του παιχνίδιου διαφοροποιούνται στην αρχή από τις υπόλοιπες
 * καθώς η 1η πίστα πρέπει να ξεκινάει με ένα πολύ σύντομο tutorial για την εξοικίωση του παίκτη
 * με τον χειρισμό του ήρωα.
 */

public class Level_1_State extends LevelState {
	private boolean lesson1;
	private boolean lesson2;
	private boolean lesson3;
	private boolean lesson4;
	private boolean lesson5;
	private boolean lesson6;
	private boolean completedLessons;

	public Level_1_State() throws SlickException {
		super(new Sound("resources/sounds/leafs.ogg"), 1, 20, 12, 18833);
		lesson1 = false;
		lesson2 = false;
		lesson3 = false;
		lesson4 = false;
		lesson5 = false;
		lesson6 = false;
		completedLessons = false;
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		if (!completedLessons) {
			level.render();
			if (!lesson1) {
				g.drawString("Press LEFT/RIGHT (LEFT ANALOG) to MOVE.", 600, 0);
			} else if (!lesson2) {
				g.drawString(
						"Press SHIFT + LEFT/RIGHT (RB + LEFT ANALOG) to MOVE.",
						530, 0);
			} else if (!lesson3) {
				g.drawString("Press UP (A Button) to JUMP.", 600, 0);
			} else if (!lesson4) {
				g.drawString("Press Z (X Button) to ATTACK.", 600, 0);
			} else if (!lesson5) {
				g.drawString("Press DOWN (Y Button) to CROUCH.", 600, 0);
			} else if (!lesson6) {
				g.drawString(
						"While crouching press X (B Button) for a STEALTH KILL.",
						500, 0);
			}
		} else {
			super.render(arg0, arg1, g);
		}
	}

	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		super.update(container, sbg, delta);
		if (player.isMoving())
			lesson1 = true;
		if (player.isRunning())
			lesson2 = true;
		if (!player.isOnGround())
			lesson3 = true;
		if (player.isAttacking())
			lesson4 = true;
		if (player.isCrouching())
			lesson5 = true;
		if (player.isStealthing())
			lesson6 = true;

		if (lesson1 == true && lesson2 == true && lesson3 == true
				&& lesson4 == true && lesson5 == true && lesson6 == true) {
			completedLessons = true;
		}
	}

	public void enter(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		super.enter(container, sbg);
		lesson1 = false;
		lesson2 = false;
		lesson3 = false;
		lesson4 = false;
		lesson5 = false;
		lesson6 = false;
		completedLessons = false;
	}
}
