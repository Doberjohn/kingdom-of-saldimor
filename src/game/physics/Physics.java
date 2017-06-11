package game.physics;

import game.entities.Character;
import game.level.Level;
import game.level.LevelObject;
import game.level.tile.FireTile;
import game.level.tile.SpikeTile;
import game.level.tile.Tile;
import game.level.tile.WaterTile;

import java.util.ArrayList;

/*
 * Μία ακόμα πολύ σημαντική κλάση είναι η Physics η οποία είναι υπεύθυνη
 * για την ορθή εφαρμογή όλων των βασικών νόμων της φυσικής που επιλέξαμε
 * να εφαρμόζονται στο παιχνίδι. Αυτοί περιλαμβάνουν  1)απλές συγκρούσεις
 * μεταξύ των αντικειμένων (μέθοδος checkCollision()), 2)νόμος της βαρύτητας,
 * 3)επιτάχυνση και επιβράδυνση του παίκτη για ένα πιο ρεαλιστικό φαίνομενο της κίνησης,
 * 4)θάνατος στις παγίδες των χαρτών (νερό, καρφιά και φωτιά) και τέλος 
 * 5)έλεγχος επαφής των παίκτη με το έδαφος (μέθοδος isOnGround()).
 */

public class Physics {

	private final float gravity = 0.0015f;

	public void handlePhysics(Level level, int delta) {
		handleCharacters(level, delta);
		handleLevelObjects(level, delta);
	}

	private boolean checkCollision(LevelObject obj, Tile[][] mapTiles) {
		ArrayList<Tile> tiles = obj.getBoundingShape().getTilesOccupying(
				mapTiles);
		for (Tile t : tiles) {
			if (t.getBoundingShape() != null) {
				if (t.getBoundingShape().checkCollision(obj.getBoundingShape())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isOnGroud(LevelObject obj, Tile[][] mapTiles) {
		ArrayList<Tile> tiles = obj.getBoundingShape().getGroundTiles(mapTiles);
		obj.getBoundingShape().movePosition(0, 1);

		for (Tile t : tiles) {
			if (t.getBoundingShape() != null) {
				if (t.getBoundingShape().checkCollision(obj.getBoundingShape())) {
					if (t instanceof WaterTile) {
						if (((Character) obj).isAlive()) {
							((Character) obj).drowned();
						}
					}
					if (t instanceof SpikeTile) {
						if (((Character) obj).isAlive()) {
							((Character) obj).spiked();
						}
					}
					if (t instanceof FireTile) {
						if (((Character) obj).isAlive()) {
							((Character) obj).burned();
						}
					}
					obj.getBoundingShape().movePosition(0, -1);
					return true;
				}
			}
		}
		obj.getBoundingShape().movePosition(0, -1);
		return false;
	}

	private void handleCharacters(Level level, int delta) {
		for (Character c : level.getCharacters()) {
			if (!c.isMoving()) {
				c.decelerate(delta);
			}

			handleGameObject(c, level, delta);
		}
	}

	private void handleLevelObjects(Level level, int delta) {
		for (LevelObject obj : level.getLevelObjects()) {
			handleGameObject(obj, level, delta);
		}
	}

	private void handleGameObject(LevelObject obj, Level level, int delta) {
		obj.setOnGround(isOnGroud(obj, level.getTiles()));
		if (!obj.isOnGround() || obj.getYVelocity() < 0) {
			obj.applyGravity(gravity * delta);
		} else
			obj.setYVelocity(0);

		float x_movement = obj.getXVelocity() * delta;
		float y_movement = obj.getYVelocity() * delta;

		float step_y = 0;
		float step_x = 0;

		if (x_movement != 0) {
			step_y = Math.abs(y_movement) / Math.abs(x_movement);
			if (y_movement < 0)
				step_y = -step_y;

			if (x_movement > 0)
				step_x = 1;
			else
				step_x = -1;

			if ((step_y > 1 || step_y < -1) && step_y != 0) {
				step_x = Math.abs(step_x) / Math.abs(step_y);
				if (x_movement < 0)
					step_x = -step_x;
				if (y_movement < 0)
					step_y = -1;
				else
					step_y = 1;
			}
		} else if (y_movement != 0) {
			if (y_movement > 0)
				step_y = 1;
			else
				step_y = -1;
		}

		while (x_movement != 0 || y_movement != 0) {
			if (x_movement != 0) {
				if ((x_movement > 0 && x_movement < step_x)
						|| (x_movement > step_x && x_movement < 0)) {
					step_x = x_movement;
					x_movement = 0;
				} else
					x_movement -= step_x;

				obj.setX(obj.getX() + step_x);
				if (checkCollision(obj, level.getTiles())) {
					obj.setX(obj.getX() - step_x);
					obj.setXVelocity(0);
					x_movement = 0;
				}

			}
			if (y_movement != 0) {
				if ((y_movement > 0 && y_movement < step_y)
						|| (y_movement > step_y && y_movement < 0)) {
					step_y = y_movement;
					y_movement = 0;
				} else
					y_movement -= step_y;

				obj.setY(obj.getY() + step_y);

				if (checkCollision(obj, level.getTiles())) {
					obj.setY(obj.getY() - step_y);
					obj.setYVelocity(0);
					y_movement = 0;
					break;
				}
			}
		}
	}
}
