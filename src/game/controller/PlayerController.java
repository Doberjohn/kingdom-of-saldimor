package game.controller;

import game.entities.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/*
 * Αυτή η κλάση είναι υπεύθυνη για τον χειρισμό του παίκτη μας, ο οποίος γίνεται μέσω
 * του πατήματος συγκεκριμένων πλήκτρων στα οποία αντιστοιχίζουμε και από μία μοναδική κίνηση του ήρωα. 
 */

public class PlayerController {
	private Player player;
	private Sound sound;

	private boolean canAttack;
	private boolean canStealth;

	private int aCooldown = 0;
	private int sCooldown = 0;

	public static int delta;

	public PlayerController(Player player, Sound sound) throws SlickException {
		this.player = player;
		this.sound = sound;
		this.canAttack = true;
		this.canStealth = true;
	}

	public void handleInput(GameContainer container, int delta) {
		PlayerController.delta = delta;
		Input i = container.getInput();

		if (player.isAlive()) {
			if (i.isKeyDown(Input.KEY_LEFT)) {
				player.moveLeft(delta);
				playWalkingSound(container);
			} else if (i.isKeyDown(Input.KEY_RIGHT)) {
				player.moveRight(delta);
				playWalkingSound(container);
			} else {
				player.stopMoving();
			}

			if (i.isKeyDown(Input.KEY_LEFT) && i.isKeyDown(Input.KEY_LSHIFT)) {
				player.runLeft(delta);
				playWalkingSound(container);
			} else if (i.isKeyDown(Input.KEY_RIGHT)
					&& i.isKeyDown(Input.KEY_LSHIFT)) {
				player.runRight(delta);
				playWalkingSound(container);
			} else {
				player.stopRunning();
			}

			if (i.isKeyDown(Input.KEY_Z)) {
				if (canAttack) {
					player.attack();
					aCooldown += delta;
					if (aCooldown >= 100) {
						canAttack = false;
						player.stopAttacking();
					}
				}
			} else {
				player.stopAttacking();
				canAttack = true;
				aCooldown = 0;
			}

			if (i.isKeyDown(Input.KEY_UP)) {
				player.jump();
			}

			if (i.isKeyDown(Input.KEY_DOWN)) {
				player.crouch();
				if (i.isKeyDown(Input.KEY_X)) {
					if (canStealth) {
						player.stealth();
						sCooldown += delta;
						if (sCooldown >= 10 && sCooldown < 20) {
							sCooldown = 21;
							Player.SPEARS--;
						}
						if (sCooldown >= 120) {
							canStealth = false;
							player.stopStealthing();
						}
					}
				} else {
					player.stopStealthing();
					canStealth = true;
					sCooldown = 0;
				}
			} else {
				player.stand();
			}
		}
	}

	public void playWalkingSound(GameContainer container) {
		if (!sound.playing() && player.isOnGround() && !player.isCrouching()) {
			sound.play(1f, container.getSoundVolume());
		}
	}
}
