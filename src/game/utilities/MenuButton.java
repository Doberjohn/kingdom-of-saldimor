package game.utilities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

/*
 * Τα πλήκτρα που εμφανίζονται στο κυρίως μενού αποτελούνται
 * από νοητά ορθογώνια που ελέγχουν αν ο δείκτης του ποντικιού
 * βρίσκεται από αυτά ή όχι. Σε κάθε περίπτωση σχεδιάζουμε
 * διαφορετική εικόνα στο αντίστοιχο πλήκτρο.
 */

public class MenuButton {
	private MouseOverArea MOA;
	private Image NormalStateImage;
	private Image HoverStateImage;

	public MenuButton(GUIContext container, String fileName, int y)
			throws SlickException {
		NormalStateImage = new Image("resources/images/buttons/" + fileName
				+ ".png");
		HoverStateImage = new Image("resources/images/buttons/" + fileName
				+ "(P).png");

		MOA = new MouseOverArea(container, NormalStateImage, 549, y);
		MOA.setMouseOverImage(HoverStateImage);
	}

	public boolean isMouseOver() {
		return MOA.isMouseOver();
	}

	public void render(GUIContext container, Graphics g) {
		MOA.render(container, g);
	}
}
