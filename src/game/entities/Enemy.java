package game.entities;

import game.controller.PlayerController;
import game.enums.Facing;
import game.physics.AABoundingRect;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/*
 * Αυτή η κλάση χειρίζεται την νοημοσύνη των εχθρών που συναντάμε στο παιχνίδι.
 * Κληρονομεί από την κλάση Character για αυτό και δεν ξαναορίζουμε όλες τις
 * ιδιότητές της παρά μόνο εκείνες που χρειάζεται και φυσικά προστίθενται μέθοδοι
 * που ελέγχουν την αυτόνομη κίνηση των εχθρών στον χάρτη ανάλογα με τη θέση του παίκτη.
 * Αυτή η κλάση ορίζει τις λειτουργίες και τις ιδιότητες των εχθρών, όχι όμως και την εξωτερική τους εμφάνιση. 
 */

public class Enemy extends Character {

	protected SpriteSheet spriteSheet;

	protected Image[] idleSprites = new Image[3];
	protected Image[] walkingSprites = new Image[4];

	protected int VERTICAL_VISION = 50;
	protected int HORIZONTAL_VISION = 400;

	protected Player player;

	public Enemy(float x, float y, Facing facing) throws SlickException {
		super(x, y);
		this.facing = facing;

		heart = new Image("resources/images/other/enemy heart.png");
		lives = 3;

		maximumWalkSpeed = 0.4f;

		boundingShape = new AABoundingRect(x + 17, y + 32, 48, 58);
	}

	public void updateBoundingShape() {
		boundingShape.updatePosition(x + 17, y + 32);
	}

	public boolean spottedPlayerRight() {
		if (player.isCrouching() && facing == Facing.LEFT) {
			HORIZONTAL_VISION = 0;
		} else
			HORIZONTAL_VISION = 300;
		if (player.getX() <= x + HORIZONTAL_VISION && player.getX() >= x
				&& player.getY() >= y - VERTICAL_VISION
				&& player.getY() <= y + VERTICAL_VISION)
			return true;
		return false;
	}

	public boolean spottedPlayerLeft() {
		if (player.isCrouching() && facing == Facing.RIGHT) {
			HORIZONTAL_VISION = 0;
		} else
			HORIZONTAL_VISION = 300;
		if (player.getX() >= x - HORIZONTAL_VISION && player.getX() <= x
				&& player.getY() >= y - VERTICAL_VISION
				&& player.getY() <= y + VERTICAL_VISION) {
			return true;
		}
		return false;
	}

	public boolean touchesPlayer() {
		if (player.getX() >= x - 48 && player.getX() <= x + 48
				&& player.getY() >= y - 60 && player.getY() <= y + 60) {
			if (player.facing == this.facing) {
				if (player.facing == Facing.RIGHT)
					this.facing = Facing.LEFT;
				else
					this.facing = Facing.RIGHT;
			}
			return true;
		}
		return false;
	}

	public void died() {
		super.died();

		player.damaged = false;
		Player.KILLED++;
		if (Player.KILLED % 5 == 0) {
			Player.SPEARS++;
		}
	}

	public void drowned() {
		super.died();
		drownSound.play();
		this.drowned = true;
	}

	public void spiked() {
		super.died();
		spikedSound.play();
		this.spiked = true;
	}

	public void burned() {
		super.died();
		burnedSound.play();
		this.burned = true;
	}

	public void render(float offset_x) {
		if (!drowned && !spiked && !burned) {
			super.render(offset_x);
			if (isAlive()) {
				if (spottedPlayerRight()) {
					VERTICAL_VISION = 500;
					moveRight(PlayerController.delta);
					for (int i = 0; i < lives; i++) {
						heart.draw(1210 - i * 70, 0);
					}
				} else if (spottedPlayerLeft()) {
					VERTICAL_VISION = 500;
					moveLeft(PlayerController.delta);
					for (int i = 0; i < lives; i++) {
						heart.draw(1210 - i * 70, 0);
					}
				} else {
					VERTICAL_VISION = 50;
					stopMoving();
				}
				if (touchesPlayer()) {
					attack();
					if (player.isAttacking()) {
						damage(5);
					} else if (player.isStealthing()) {
						died();
					} else {
						player.damage(20);
					}
				} else {
					stopAttacking();
				}
			}
		}
	}
}
