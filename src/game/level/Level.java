package game.level;

import game.entities.Character;
import game.entities.Jungle_Knight;
import game.entities.Kelfar;
import game.entities.Player;
import game.entities.Port_Knight;
import game.entities.Valador;
import game.entities.Village_Knight;
import game.enums.Facing;
import game.level.tile.AirTile;
import game.level.tile.FireTile;
import game.level.tile.SolidTile;
import game.level.tile.SpikeTile;
import game.level.tile.Tile;
import game.level.tile.WaterTile;
import game.main.Game;
import game.objects.Heart;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/*
 * Η πιο σημαντική κλάση του παιχνιδιού. Είναι υπεύθυνη για την δημιουργία των επιπέδων
 * και των οντοτήτων που υπάρχουν μέσα σε αυτά. Διαβάζει τους χάρτες από εξωτερικά
 * αρχεία που περιέχουν πληροφορίες σχετικά με την θέση των εχθρών στον χάρτη και των
 * αντικειμένων που μπορούμε να συλλέξουμε. Μέσω της μεθόδου loadTileMap()
 * προσθέτουμε στην πίστα τον χάρτη σε μορφή tiles και εξετάζουμε κάθε φορά το είδος
 * του εκάστοτε tile ώστε να προετοιμάσουμε το πρόγραμμα για τα κατάλληλα collisions.
 * Μέσω της μεθόδου loadObjects προσθέτουμε όλους τους εχθρούς και τα αντικείμενα,
 * όπως τα έχουμε τοποθετήσει στον εξωτερικό map editor (Tiled).
 */

public class Level {
	private ArrayList<Character> characters;
	private ArrayList<LevelObject> levelObjects;
	private Image background;
	private Tile[][] tiles;
	private Player player;
	private TiledMap map;

	public Level(String level, Player player) throws SlickException {
		map = new TiledMap("resources/levels/" + level + ".tmx",
				"resources/images/tilesets");
		background = new Image("resources/images/levelsbg/"
				+ map.getMapProperty("background", "Level "
						+ Game.CHOOSEN_LEVEL + ".png"));

		characters = new ArrayList<Character>();
		levelObjects = new ArrayList<LevelObject>();

		this.player = player;
		addCharacter(this.player);

		loadTileMap();
		loadObjects();
	}

	private void loadTileMap() {
		tiles = new Tile[map.getWidth()][map.getHeight()];

		int layerIndex = map.getLayerIndex("CollisionLayer");

		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				int tileID = map.getTileId(x, y, layerIndex);

				Tile tile = null;

				switch (map.getTileProperty(tileID, "tileType", "solid")) {
					case "air":
						tile = new AirTile(x, y);
						break;
					case "water":
						tile = new WaterTile(x, y);
						break;
					case "fire":
						tile = new FireTile(x, y);
						break;
					case "spike":
						tile = new SpikeTile(x, y);
						break;
					default:
						tile = new SolidTile(x, y);
						break;
				}
				tiles[x][y] = tile;
			}
		}
	}

	private void loadObjects() throws SlickException {

		int objectAmount = map.getObjectCount(0);

		for (int i = 0; i < objectAmount; i++) {
			switch (map.getObjectName(0, i)) {
				case "Left Jungle Knight":
					addCharacter(new Jungle_Knight((float) map.getObjectX(0,
							i), (float) map.getObjectY(0, i), Facing.LEFT));
					break;
				case "Right Jungle Knight":
					addCharacter(new Jungle_Knight((float) map.getObjectX(0,
							i), (float) map.getObjectY(0, i), Facing.RIGHT));
					break;
				case "Left Port Knight":
					addCharacter(new Port_Knight((float) map.getObjectX(0,
							i), (float) map.getObjectY(0, i), Facing.LEFT));
					break;
				case "Right Port Knight":
					addCharacter(new Port_Knight((float) map.getObjectX(0,
							i), (float) map.getObjectY(0, i), Facing.RIGHT));
					break;
				case "Left Village Knight":
					addCharacter(new Village_Knight((float) map.getObjectX(0,
							i), (float) map.getObjectY(0, i), Facing.LEFT));
					break;
				case "Right Village Knight":
					addCharacter(new Village_Knight((float) map.getObjectX(0,
							i), (float) map.getObjectY(0, i), Facing.RIGHT));
					break;
				case "Kelfar":
					addCharacter(new Kelfar((float) map.getObjectX(0,
							i), (float) map.getObjectY(0, i), Facing.LEFT));
					break;
				case "Valador":
					addCharacter(new Valador((float) map.getObjectX(0,
							i), (float) map.getObjectY(0, i), Facing.LEFT));
					break;
				case "Heart":
					addLevelObject(new Heart((float) map.getObjectX(0,
							i), (float) map.getObjectY(0, i)));
					break;
			}
		}
	}

	public int getXOffset() {
		int offset_x = 0;
		int half_width = 640;
		int maxX = map.getWidth() * 64 - half_width;

		if (this.player.getX() < half_width)
			offset_x = 0;
		else if (player.getX() > maxX)
			offset_x = maxX - half_width;
		else
			offset_x = (int) (player.getX() - half_width);

		return offset_x;
	}

	public void addCharacter(Character c) {
		characters.add(c);
	}

	public ArrayList<Character> getCharacters() {
		return characters;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void addLevelObject(LevelObject obj) {
		levelObjects.add(obj);
	}

	public void removeObject(LevelObject obj) {
		levelObjects.remove(obj);
	}

	public void removeObjects(ArrayList<LevelObject> objects) {
		levelObjects.removeAll(objects);
	}

	public ArrayList<LevelObject> getLevelObjects() {
		return levelObjects;
	}

	private void renderBackground() {
		float backgroundXScrollValue = (background.getWidth() - 1280);
		float mapXScrollValue = ((float) map.getWidth() * 64 - 1280);
		float scrollXFactor = backgroundXScrollValue / mapXScrollValue * -1;

		background.draw(this.getXOffset() * scrollXFactor, 0);
		background.draw(
				this.getXOffset() * scrollXFactor + background.getWidth(), 0);
	}

	public void render() {
		int offset_x = getXOffset();

		renderBackground();

		map.render(-(offset_x % 64), 0, offset_x / 64, 0, 32, 15);

		for (Character c : characters) {
			c.render(offset_x);
		}
		
		for (LevelObject obj : levelObjects) {
			obj.render(offset_x);
		}
	}
}
