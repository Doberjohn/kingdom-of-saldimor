package game.level.tile;

import game.physics.AABoundingRect;

/*
 * ��� ������ game.level.tile ��������� ��� ������� ��� 
 * ����������� �� ��������� ���� ��� ����� �������� �� ���������,
 * � ��� �������� �� ���������, ������� �� ��� �������� ���������,
 * ������������� ���� ���� �� ��������� ����� ���������(������ ����������)
 * ���� ���� ��������� ���� ����� LevelObject.
 */

public class SpikeTile extends Tile{
	public SpikeTile(int x, int y) {
		super(x, y);
		boundingShape = new AABoundingRect(x*64, y*64, 64, 64);
	}
}
