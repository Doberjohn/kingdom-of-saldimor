package game.entities;

import game.enums.Facing;
import game.physics.AABoundingRect;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/*
 * H κλάση η οποία είναι υπεύθυνη για την εμφάνιση, την κίνηση,
 * και την λογική του παίκτη μας. Συνδέεται άρικτα με την κλάση
 * PlayerController αφού και οι 2 μαζί πρέπει να βρίσκονται
 * σε άμεση επικοινωνία έτσι ώστε κάθε πάτημα πλήκτρου από τον
 * παίκτη, να το αναγνωρίζει η PlayerController και να δίνει
 * εντολή στην κλάση Player έτσι ώστε να βλέπουμε την αντίστοιχη
 * κίνηση του ήρωα μας (πχ άλμα, επίθεση, σκύψιμο).
 */

public class Player extends Character {
	private boolean crouching = false;
	private boolean stealthing = false;

	private SpriteSheet spriteSheet;

	private HashMap<Facing, Image> jumpingSprite;
	private HashMap<Facing, Image> fallingSprite;
	private HashMap<Facing, Image> crouchingSprite;
	private HashMap<Facing, Image> stealthAttackSprite;
	private Image[] idleSprites = new Image[3];
	private Image[] walkingSprites = new Image[5];
	private Image[] runningSprites = new Image[4];

	private Image spear;

	public static int SPEARS;
	public static int KILLED;

	public Player(float x, float y) throws SlickException {
		super(x, y);

		KILLED = 0;
		SPEARS = 5;

		lives = 5;
		heart = new Image("resources/images/other/heart.png");
		spear = new Image("resources/images/other/spear.png");

		spriteSheet = new SpriteSheet("resources/images/characters/Elmar.png",
				96, 96);

		for (int i = 0; i < 3; i++) {
			idleSprites[i] = spriteSheet.getSubImage(i + 3, 2);
		}
		for (int i = 0; i < 5; i++) {
			walkingSprites[i] = spriteSheet.getSubImage(i, 8);
		}
		for (int i = 0; i < 4; i++) {
			runningSprites[i] = spriteSheet.getSubImage(i + 2, 4);
		}

		setIdleAnimation(idleSprites, 300);
		setMovingAnimation(walkingSprites, 120);
		setRunningAnimation(runningSprites, 120);
		setAttackingSprite(spriteSheet.getSubImage(2, 6));
		setDeathSprite(spriteSheet.getSubImage(4, 1));
		setJumpingSprite(spriteSheet.getSubImage(2, 3));
		setFallingSprite(spriteSheet.getSubImage(0, 4));
		setCrouchingSprite(spriteSheet.getSubImage(0, 1));
		setStealthAttackSprite(spriteSheet.getSubImage(2, 7));

		boundingShape = new AABoundingRect(x + 31, y + 32, 35, 58);
	}

	public void updateBoundingShape() {
		boundingShape.updatePosition(x + 31, y + 32);
	}

	public void setJumpingSprite(Image i) {
		jumpingSprite = new HashMap<Facing, Image>();
		jumpingSprite.put(Facing.RIGHT, i);
		jumpingSprite.put(Facing.LEFT, i.getFlippedCopy(true, false));
	}

	public void setFallingSprite(Image i) {
		fallingSprite = new HashMap<Facing, Image>();
		fallingSprite.put(Facing.RIGHT, i);
		fallingSprite.put(Facing.LEFT, i.getFlippedCopy(true, false));
	}

	public void setCrouchingSprite(Image i) {
		crouchingSprite = new HashMap<Facing, Image>();
		crouchingSprite.put(Facing.RIGHT, i);
		crouchingSprite.put(Facing.LEFT, i.getFlippedCopy(true, false));
	}

	public void setStealthAttackSprite(Image i) {
		stealthAttackSprite = new HashMap<Facing, Image>();
		stealthAttackSprite.put(Facing.RIGHT, i);
		stealthAttackSprite.put(Facing.LEFT, i.getFlippedCopy(true, false));
	}

	public void crouch() {
		crouching = true;
	}

	public void stand() {
		crouching = false;
	}

	public boolean isCrouching() {
		return crouching;
	}

	public void stealth() {
		if (SPEARS > 0) {
			stealthing = true;
		}
	}
	
	public void stopStealthing() {
		stealthing = false;
	}

	public boolean isStealthing() {
		return stealthing;
	}
	
	public void drowned() {
		drownSound.play();
		this.setX(100);
		this.setY(734);
		this.damage1Life();
	}
	
	public void spiked() {
		spikedSound.play();
		this.setX(100);
		this.setY(734);
		this.damage1Life();
	}
	
	public void burned() {
		burnedSound.play();
		this.setX(100);
		this.setY(734);
		this.damage1Life();
	}
	
	public void gainHealth() {
		lives++;
	}
	
	public void render(float offset_x) {
		for (int i = 0; i < this.lives; i++) {
			heart.draw(i * 70, 0);
		}
		for (int i = 0; i < Player.SPEARS; i++) {
			spear.draw(i * 70, 70);
		}
		float xPos = (x - offset_x);

		if (isAlive()) {
			if (moving) {
				if (!onGround) {
					if (y_velocity >= 0) {
						fallingSprite.get(facing).draw(xPos, y);
					} else {
						jumpingSprite.get(facing).draw(xPos, y);
					}
				} else {
					if (running) {
						stopStealthing();
						runningAnimation.get(facing).draw(xPos, y);
					} else if (attacking) {
						attackSprite.get(facing).draw(xPos, y);
					} else if (crouching) {
						stopRunning();
						if (stealthing) {
							stealthAttackSprite.get(facing).draw(xPos, y);
						} else {
							crouchingSprite.get(facing).draw(xPos, y);
						}
					} else {
						walkingAnimation.get(facing).draw(xPos, y);
					}
				}
			} else {
				if (!onGround) {
					if (y_velocity >= 0) {
						fallingSprite.get(facing).draw(xPos, y);
					} else {
						jumpingSprite.get(facing).draw(xPos, y);
					}
				} else {
					if (running) {
						stopStealthing();
						runningAnimation.get(facing).draw(xPos, y);
					} else if (crouching) {
						stopRunning();
						if (stealthing) {
							stealthAttackSprite.get(facing).draw(xPos, y);
						} else {
							crouchingSprite.get(facing).draw(xPos, y);
						}
					} else if (attacking) {
						attackSprite.get(facing).draw(xPos, y);
					} else {
						idleAnimation.get(facing).draw(xPos, y);
					}
				}
			}
		} else {
			deathSprite.get(facing).draw(xPos, y);
		}
		
		if (damaged == true) {
			BloodAnimation.draw(xPos + 20, y + 40);
		}
	}
}
