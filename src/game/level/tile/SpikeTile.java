package game.level.tile;

import game.physics.AABoundingRect;

/*
 * Στο πακέτο game.level.tile συναντάμε τις κλάσεις που 
 * δημιουργουν τα τετράγωνα πάνω στα οποία μπορούμε να κινηθούμε,
 * ή δεν μπορούμε να κινηθούμε, ανάλογα με την εκάστοτε περίπτωση,
 * δημιουργώντας γύρω τους τα κατάλληλα νοητά τετράγωνα(εφόσον χρειάζεται)
 * όπως αυτά φαίνονται στην κλάση LevelObject.
 */

public class SpikeTile extends Tile{
	public SpikeTile(int x, int y) {
		super(x, y);
		boundingShape = new AABoundingRect(x*64, y*64, 64, 64);
	}
}
