package game.entities;

import game.enums.Facing;
import game.level.*;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/*
 * Μία αφηρημένη κλάση που αντιπροσωπεύει τους χαρακτήρες που εμφανίζονται 
 * στο παιχνίδι (τον ήρωα, τους εχθρούς, και τον αρχηγό) και όλες τις βασικές
 * λειτουργίες και ιδιότητες που είναι κοινές για τις οντότητες που συναντάμε.
 */

public abstract class Character extends LevelObject {
	protected HashMap<Facing, Animation> idleAnimation;
	protected HashMap<Facing, Animation> walkingAnimation;
	protected HashMap<Facing, Animation> runningAnimation;
	protected HashMap<Facing, Image> deathSprite;
	protected HashMap<Facing, Image> attackSprite;

	protected Animation BloodAnimation;

	protected Facing facing;

	protected boolean alive = true;
	protected boolean moving = false;
	protected boolean running = false;
	protected boolean attacking = false;
	protected boolean drowned = false;
	protected boolean spiked = false;
	protected boolean burned = false;
	protected boolean damaged = false;

	protected float accelerationSpeed = 0.001f;
	protected float maximumWalkSpeed = 0.15f;
	protected float maximumRunSpeed = 0.3f;
	protected float maximumFallSpeed = 0.5f;
	protected float decelerationSpeed = 0.001f;

	protected Image heart;
	protected int lives;
	protected int damage = 0;

	protected Sound drownSound;
	protected Sound burnedSound;
	protected Sound spikedSound;

	public Character(float x, float y) throws SlickException {
		super(x, y);
		facing = Facing.RIGHT;
		drownSound = new Sound("resources/sounds/waterSplash.ogg");
		burnedSound = new Sound("resources/sounds/flameDeath.wav");
		spikedSound = new Sound("resources/sounds/spikeSlash.wav");

		BloodAnimation = new Animation();
		for (int i = 1; i <= 6; i++) {
			BloodAnimation.addFrame(new Image("resources/images/blood/blood ("
					+ i + ").png"), 100);
		}
	}

	protected void setIdleAnimation(Image[] images, int frameDuration) {
		idleAnimation = new HashMap<Facing, Animation>();
		idleAnimation.put(Facing.RIGHT, new Animation(images, frameDuration));

		Animation facingLeftAnimation = new Animation();
		for (Image i : images) {
			facingLeftAnimation.addFrame(i.getFlippedCopy(true, false),
					frameDuration);
		}
		idleAnimation.put(Facing.LEFT, facingLeftAnimation);
	}

	protected void setMovingAnimation(Image[] images, int frameDuration) {
		walkingAnimation = new HashMap<Facing, Animation>();
		walkingAnimation
				.put(Facing.RIGHT, new Animation(images, frameDuration));

		Animation facingLeftAnimation = new Animation();
		for (Image i : images) {
			facingLeftAnimation.addFrame(i.getFlippedCopy(true, false),
					frameDuration);
		}
		walkingAnimation.put(Facing.LEFT, facingLeftAnimation);
	}

	protected void setRunningAnimation(Image[] images, int frameDuration) {
		runningAnimation = new HashMap<Facing, Animation>();
		runningAnimation
				.put(Facing.RIGHT, new Animation(images, frameDuration));

		Animation facingLeftAnimation = new Animation();
		for (Image i : images) {
			facingLeftAnimation.addFrame(i.getFlippedCopy(true, false),
					frameDuration);
		}
		runningAnimation.put(Facing.LEFT, facingLeftAnimation);
	}

	protected void setAttackingSprite(Image i) {
		attackSprite = new HashMap<Facing, Image>();
		attackSprite.put(Facing.RIGHT, i);
		attackSprite.put(Facing.LEFT, i.getFlippedCopy(true, false));
	}

	protected void setDeathSprite(Image i) {
		deathSprite = new HashMap<Facing, Image>();
		deathSprite.put(Facing.RIGHT, i);
		deathSprite.put(Facing.LEFT, i.getFlippedCopy(true, false));
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isMoving() {
		return moving;
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void stopMoving() {
		moving = false;
	}

	public void stopRunning() {
		running = false;
	}

	public void attack() {
		attacking = true;
	}

	public void stopAttacking() {
		attacking = false;
	}

	public void died() {
		alive = false;
		stopMoving();
		damaged = false;
	}

	public abstract void drowned();

	public abstract void spiked();

	public abstract void burned();

	public void jump() {
		if (onGround) {
			y_velocity = -0.80f;
		}
	}

	public void damage(int limit) {
		this.damage++;
		damaged = true;
		if (this.damage >= limit) {
			this.damage = 0;
			damage1Life();
		}
	}

	public void damage1Life() {
		lives--;
		if (this.lives == 0)
			died();
	}

	public void decelerate(int delta) {
		if (x_velocity > 0) {
			x_velocity -= decelerationSpeed * delta;
			if (x_velocity < 0)
				x_velocity = 0;
		} else if (x_velocity < 0) {
			x_velocity += decelerationSpeed * delta;
			if (x_velocity > 0)
				x_velocity = 0;
		}
	}

	public void moveLeft(int delta) {
		if (x_velocity > -maximumWalkSpeed) {
			x_velocity -= accelerationSpeed * delta;
			if (x_velocity < -maximumWalkSpeed) {
				x_velocity = -maximumWalkSpeed;
			}
		}
		moving = true;
		facing = Facing.LEFT;
	}

	public void moveRight(int delta) {
		if (x_velocity < maximumWalkSpeed) {
			x_velocity += accelerationSpeed * delta;
			if (x_velocity > maximumWalkSpeed) {
				x_velocity = maximumWalkSpeed;
			}
		}
		moving = true;
		facing = Facing.RIGHT;
	}

	public void runLeft(int delta) {
		if (x_velocity > -maximumRunSpeed) {
			x_velocity -= accelerationSpeed * delta;
			if (x_velocity < -maximumRunSpeed) {
				x_velocity = -maximumRunSpeed;
			}
		}
		moving = true;
		running = true;
		facing = Facing.LEFT;
	}

	public void runRight(int delta) {
		if (x_velocity < maximumRunSpeed) {
			x_velocity += accelerationSpeed * delta;
			if (x_velocity > maximumRunSpeed) {
				x_velocity = maximumRunSpeed;
			}
		}
		moving = true;
		running = true;
		facing = Facing.RIGHT;
	}

	public void render(float offset_x) {
		float xPos = (x - offset_x);
		if (isAlive()) {
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
		} else {
			deathSprite.get(facing).draw(xPos, y);
		}

		if (damaged == true) {
			BloodAnimation.draw(xPos + 20, y + 40);
		}
	}
}