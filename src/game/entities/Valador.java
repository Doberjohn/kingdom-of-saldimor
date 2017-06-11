package game.entities;

import java.util.HashMap;

import game.controller.PlayerController;
import game.enums.Facing;
import game.state.Level_5_State;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/*
 * Αυτή η κλάση αντιπροσωπεύει τον αρχηγό που εμφανίζεται στην τελευταία πίστα.
 * Για άλλη μία φορά κληρονομούμε την κλάση Enemy και αλλάζουμε όσα στοιχεία
 * χρειάζεται ώστε να τον κάνουμε πιο δυνατό από τους υπόλοιπους εχθρούς.
 */

public class Valador extends Enemy {

	private HashMap<Facing, Image> tiredSprite;
	private Facing preFacing;
	private boolean tired;
	private int faceChanges;
	private int tiredTime;

	public Valador(float x, float y, Facing facing) throws SlickException {
		super(x, y, facing);

		HORIZONTAL_VISION = 2500;
		VERTICAL_VISION = 1000;

		spriteSheet = new SpriteSheet(
				"resources/images/characters/Valador.png", 96, 96);

		tiredTime = 0;
		faceChanges = 0;
		lives = 10;
		maximumWalkSpeed = 0.6f;

		player = Level_5_State.player;

		for (int i = 0; i < 3; i++) {
			idleSprites[i] = spriteSheet.getSubImage(i + 2, 7);
		}
		for (int i = 0; i < 4; i++) {
			walkingSprites[i] = spriteSheet.getSubImage(i, 6);
		}

		setIdleAnimation(idleSprites, 300);
		setMovingAnimation(walkingSprites, 120);
		setAttackingSprite(spriteSheet.getSubImage(3, 4));
		setDeathSprite(spriteSheet.getSubImage(5, 1));
		setTiredSprite(spriteSheet.getSubImage(1, 2));
	}

	public boolean isTired() {
		return tired;
	}

	public void getTired() {
		tiredTime += PlayerController.delta;
		tired = true;
	}

	public void setTiredSprite(Image i) {
		tiredSprite = new HashMap<Facing, Image>();
		tiredSprite.put(Facing.RIGHT, i);
		tiredSprite.put(Facing.LEFT, i.getFlippedCopy(true, false));
	}

	public boolean spottedPlayerRight() {
		if (player.getX() <= x + HORIZONTAL_VISION && player.getX() >= x
				&& player.getY() >= y - VERTICAL_VISION
				&& player.getY() <= y + VERTICAL_VISION)
			return true;
		return false;
	}

	public boolean spottedPlayerLeft() {
		if (player.getX() >= x - HORIZONTAL_VISION && player.getX() <= x
				&& player.getY() >= y - VERTICAL_VISION
				&& player.getY() <= y + VERTICAL_VISION) {
			return true;
		}
		return false;
	}

	public void render(float offset_x) {
		float xPos = (x - offset_x);
		
		if (isAlive()) {
			if (!isTired()) {
				if (moving) {
					if (attacking) {
						attackSprite.get(facing).draw(xPos, y);
					} else {
						walkingAnimation.get(facing).draw(xPos, y);
					}
				} else {
					if (attacking) {
						attackSprite.get(facing).draw(xPos, y);
					} else {
						idleAnimation.get(facing).draw(xPos, y);
					}
				}
			}
			if (spottedPlayerRight()) {
				for (int i = 0; i < lives; i++) {
					heart.draw(1210 - i * 70, 0);
				}
				if (!isTired()) {
					if (preFacing == Facing.LEFT) {
						faceChanges++;
					}
					moveRight(PlayerController.delta);
					preFacing = Facing.RIGHT;
				}
			} else if (spottedPlayerLeft()) {
				for (int i = 0; i < lives; i++) {
					heart.draw(1210 - i * 70, 0);
				}
				if (!isTired()) {
					if (preFacing == Facing.RIGHT) {
						faceChanges++;
					}
					moveLeft(PlayerController.delta);
					preFacing = Facing.LEFT;
				}
			}
			if (!player.isOnGround() && !isTired()) {
				jump();
			}
			if (touchesPlayer()) {
				if (player.isAttacking()) {
					damage(30);
				} else if (!isTired()) {
					attack();
					player.damage(30);
				}
			} else {
				stopAttacking();
			}
		} else {
			deathSprite.get(facing).draw(xPos, y);
		}

		if (faceChanges == 10) {
			getTired();
			tiredSprite.get(facing).draw(xPos, y);
			stopMoving();
			stopAttacking();
		}

		if (tiredTime >= 3000) {
			tiredTime = 0;
			tired = false;
			faceChanges = 0;
		}

		if (damaged == true) {
			BloodAnimation.draw(xPos + 20, y + 40);
		}
	}
}
