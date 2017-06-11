package game.level.tile;

import game.physics.BoundingShape;

/*
 * Στο πακέτο game.level.tile συναντάμε τις κλάσεις που 
 * δημιουργουν τα τετράγωνα πάνω στα οποία μπορούμε να κινηθούμε,
 * ή δεν μπορούμε να κινηθούμε, ανάλογα με την εκάστοτε περίπτωση,
 * δημιουργώντας γύρω τους τα κατάλληλα νοητά τετράγωνα(εφόσον χρειάζεται)
 * όπως αυτά φαίνονται στην κλάση LevelObject.
 */

public abstract class Tile {

	protected int x;
	protected int y;
	protected BoundingShape boundingShape;

	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		boundingShape = null;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public BoundingShape getBoundingShape() {
		return boundingShape;
	}
}
