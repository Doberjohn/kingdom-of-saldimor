package game.physics;

import game.level.tile.Tile;

import java.util.ArrayList;

/*
 * Η κλάση αυτή (AA = AxisAligned) αντιπροσωπεύει το νοητό τετράγωνο για το οποίο 
 * μιλήσαμε στην κλάση LevelObject. Κάθε αντικείμενο που δημιουργείται διαθέτει ένα
 * αντικείμενο αυτής της κλάσης ώστε όλα τα αντικείμενα στο σύνολο να επικοινωνούν μέσω
 * αυτών των τετραγώνων. Διαθέτει 3 βασικές μεθόδους. Η checkCollision() παίρνει
 * σαν όρισμα ένα άλλο αντικείμενο και ελέγχει αν η οντότητα που την κάλεσε συγκρούεται 
 * με το αντικείμενο αυτό. Η μέθοδος getTilesOccupying() επιστρέφει τα Tiles, 
 * ή τετράγωνα μεγέθους 64x64 pixels τα οποία υπάρχουν γύρω από το εκάστοτε αντικείμενο 
 * και η getGroundTiles τα τετράγωνα που βρίσκονται από κάτω του.
 */

public class AABoundingRect extends BoundingShape {

	public float x;
	public float y;
	public float width;
	public float height;

	public AABoundingRect(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void updatePosition(float newX, float newY) {
		this.x = newX;
		this.y = newY;
	}

	public void movePosition(float x, float y) {
		this.x += x;
		this.y += y;
	}

	public boolean checkCollision(AABoundingRect rect) {
		return !(rect.x > this.x + width || rect.x + rect.width < this.x
				|| rect.y > this.y + height || rect.y + rect.height < this.y);
	}

	public ArrayList<Tile> getTilesOccupying(Tile[][] tiles) {
		ArrayList<Tile> occupiedTiles = new ArrayList<Tile>();

		for (int i = (int) x; i <= x + width + (64 - width % 64); i += 64) {
			for (int j = (int) y; j <= y + height + (64 - height % 64); j += 64) {
				occupiedTiles.add(tiles[i / 64][j / 64]);
			}
		}
		return occupiedTiles;
	}

	public ArrayList<Tile> getGroundTiles(Tile[][] tiles) {
		ArrayList<Tile> tilesUnderneath = new ArrayList<Tile>();
		int j = (int) (y + height + 1);

		for (int i = (int) x; i <= x + width + (64 - width % 64); i += 64) {
			tilesUnderneath.add(tiles[i / 64][j / 64]);
		}

		return tilesUnderneath;
	}

}
