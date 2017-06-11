package game.state;

import game.controller.PlayerController;
import game.entities.Player;
import game.level.Level;
import game.main.Game;
import game.physics.Physics;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/*
 * ��� ���� ��� ������ ��� ����������, � LevelState ����� � ��� ��������� �����
 * �������� ��� ��������� ����� ������� �� ��� �� �������� �������� ��� ����������.
 * �� ����������� level ����� ���������� � ����� ��� ����� ��� ����������� ��� ������
 * render(). �� ����������� physics ���� ��� ������ ��� (handlePhysics) ���� 
 * ��������� update ��� ����� ��� ���������� �� ��������� �� ���������� ����� 
 * (������ ���� millisecond) ����� ��� ���� ������ ��� ������ ������ ���� ����� 
 * physics ������������. ��� ��� �������� � ������� ��� �� �� � ������� ��� �����������
 * �� ���� �����������, �� ��������� ���� ����, ����� ��� ������ �� �����, �� ���� ����� ��
 * ����. ������������ ������ ��� �������� ��� ����� ����������� �� �� ����������� ��� ������
 * ���� ��� ���������� ���� ������� �� ������� ESCAPE ������������ ��� ������ �����, ����
 * ������ ��� ���� ������� ��� ��� ��� ����. ���� �������� ��� �������� ��� ����� ���� �� ���� �����
 * �� ���� ��� �������� ��������� ����� ��� ������� ���� ��� ����������� �������� (��� LevelState1/2/3/4/5).
 */

public abstract class LevelState extends BasicGameState {
	protected Music music;
	protected Sound sound;
	protected Level level;
	protected Physics physics;
	protected PlayerController playerController;
	
	private int LevelNo;
	private int EnemiesNo;
	private int EnemiesGoal;
	private int LengthGoal;

	public static Player player;

	public LevelState(Sound sound, int LevelNo, int EnemiesNo,
			int EnemiesGoal, int LengthGoal) throws SlickException {
		this.music = new Music("resources/sounds/Level " + LevelNo + " Sound.ogg");
		this.sound = sound;
		this.LevelNo = LevelNo;
		this.EnemiesGoal = EnemiesGoal;
		this.EnemiesNo = EnemiesNo;
		this.LengthGoal = LengthGoal;
	}

	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		player = new Player(100, 741);
		level = new Level("Level " + this.LevelNo, player);
		physics = new Physics();
		playerController = new PlayerController(player, sound);
	}

	public void enter(GameContainer container, StateBasedGame sbg)
			throws SlickException {
		music.loop(1f, container.getMusicVolume());
		init(container, sbg);
	}

	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		playerController.handleInput(container, delta);
		physics.handlePhysics(level, delta);

		if (!player.isAlive()
				|| container.getInput().isKeyDown(Input.KEY_ESCAPE)) {
			MenuState.music.setVolume(container.getMusicVolume());
			sbg.enterState(0, new FadeOutTransition(Color.black, 3000),
					new FadeInTransition());
		}
		
		if (player.getX() >= LengthGoal && Player.KILLED >= EnemiesGoal) {
			player.stopMoving();
			Game.CHOOSEN_LEVEL++;
			if (Game.CHOOSEN_LEVEL > Game.LEVEL) {
				Game.saveGame(Game.CHOOSEN_LEVEL);
			}
			sbg.enterState(9, new FadeOutTransition(Color.black, 3000),
					new FadeInTransition());
		}
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		level.render();
		if (Player.KILLED == 0) {
			g.drawString(
					"You haven't killed anyone! What kind of warrior are you?",
					460, 0);
		} else if (Player.KILLED < EnemiesGoal) {
			int rem = EnemiesGoal - Player.KILLED;
			g.drawString("You killed " + Player.KILLED + " soldiers! " + rem
					+ " remaining to finish this level!", 460, 0);
		} else if (Player.KILLED >= EnemiesGoal) {
			g.drawString(
					"You are ready to complete the Level! Proceed to FINISH!",
					460, 0);
		} else if (Player.KILLED == EnemiesNo) {
			g.drawString(
					"You killed everyone in this level. You are a true warrior! Proceed to FINISH!",
					460, 0);
		}
	}
	
	public int getID() {
		return LevelNo;
	}
}
