package game.utilities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

/*
 * ���� ��� �� ������� ��� ������ �����, ���� ��� �� ������� ��� �����
 * ��� ��������� ������������ ��� ����� ��������� ��� ��� �������� ����
 * � ������� ��� ��������� ��� ��������� ���� ��� ����. �� ��������� ���
 * � ������� ��������� �� ������ ������� ��� ������� ���� ���������� ������ 
 * �������� (��� ����� OptionsState).
 */

public class OptionsButton {
	private MouseOverArea MOA;
	private Image NormalStateImage;

	public OptionsButton(GUIContext container, String fileName, int x, int y)
			throws SlickException {
		NormalStateImage = new Image("resources/images/options/" + fileName
				+ ".png");

		MOA = new MouseOverArea(container, NormalStateImage, x, y);
	}

	public void render(GUIContext container, Graphics g) {
		MOA.render(container, g);
	}

	public boolean isMouseOver() {
		return MOA.isMouseOver();
	}

	public float getX() {
		return MOA.getX();
	}

	public void setX(float newX) {
		MOA.setX(newX);
	}
}
