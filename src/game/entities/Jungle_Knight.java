package game.entities;

import game.enums.Facing;
import game.state.Level_1_State;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/*
 * ���� ����� ��� ���������� ��� ����� Enemy �������� ��� ���� 
 * ����������� ����� ��� ����������. �� ���� ������ ����� ��������
 * ��� ��������� �������� ��� ������ ��� �������� �� ������������
 * ��� ��� Enemy ���� ��� ����������� ��� ��� ��������. �� ���� ��� 
 * ����� ������������ ���� ������� ��� 1�� ������.
 */

public class Jungle_Knight extends Enemy {
	
	public Jungle_Knight(float x, float y, Facing facing) throws SlickException {
		super(x, y, facing);
		
		spriteSheet = new SpriteSheet(
				"resources/images/characters/Jungle Knight.png", 96, 96);
		
		player = Level_1_State.player;
		
		for (int i = 0; i < 3; i++) {
			idleSprites[i] = spriteSheet.getSubImage(i, 8);
		}
		for (int i = 0; i < 4; i++) {
			walkingSprites[i] = spriteSheet.getSubImage(i, 6);
		}

		setIdleAnimation(idleSprites, 300);
		setMovingAnimation(walkingSprites, 120);
		setAttackingSprite(spriteSheet.getSubImage(0, 1));
		setDeathSprite(spriteSheet.getSubImage(2, 2));
	}
}
