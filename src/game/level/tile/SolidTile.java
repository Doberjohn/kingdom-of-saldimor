package game.level.tile;

import game.physics.AABoundingRect;

/*
 * ��� ������ game.level.tile ��������� ��� ������� ��� 
 * ����������� �� ��������� ���� ��� ����� �������� �� ���������,
 * � ��� �������� �� ���������, ������� �� ��� �������� ���������,
 * ������������� ���� ���� �� ��������� ����� ���������(������ ����������)
 * ���� ���� ��������� ���� ����� LevelObject.
 */

public class SolidTile extends Tile {
    public SolidTile(int x, int y) {
        super(x, y);
        boundingShape = new AABoundingRect(x*64, y*64, 64, 64);
    }
}
