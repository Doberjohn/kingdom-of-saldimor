package game.main;

import game.state.BookState;
import game.state.CreditsState;
import game.state.Level_1_State;
import game.state.Level_2_State;
import game.state.Level_3_State;
import game.state.Level_4_State;
import game.state.Level_5_State;
import game.state.LoadGameState;
import game.state.MenuState;
import game.state.OptionsState;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/*
 * � �������� ����� ��� ����������. ��� ��� �������� � ��������, 
 * ������������� ��� ����������� ����� Game, �� ����� �����������
 * ���� ��� ������ ����� ������ ��� ��� ������ ��� ����������. ��� ������
 * ������ ������������� ������������ � ������������ ���� ��� ������ 
 * �� ������� ��� ���� ���� ��� �������� ��� ���������� ��� ��� �������� � 
 * ������� �� ��� ��������� ������� ��� ��� ������ �� ��� �����, ����� ���
 * �� �������� ����� ��� ���������. ���� ���������� ��� ������� ������ 
 * loading time ��� �������, ���� �������� �� �������� ��� �� ���������
 * �� ����� ��� � ������� ���� ������� ��� ��������.
 */

public class Game extends StateBasedGame {
	public static int LEVEL = 1;
	public static int CHOOSEN_LEVEL = 1;

	public Game(String title) {
		super(title);
	}

	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MenuState());
		addState(new Level_1_State());
		addState(new Level_2_State());
		addState(new Level_3_State());
		addState(new Level_4_State());
		addState(new Level_5_State());
		addState(new LoadGameState());
		addState(new OptionsState());
		addState(new CreditsState());
		addState(new BookState());
	}

	public static void saveGame(Integer level) {
		try {
			FileWriter writer = new FileWriter(System.getProperty("user.home")
					+ "/Kingdom Of Saldimor/level.txt");
			BufferedWriter out = new BufferedWriter(writer);
			LEVEL = level;
			out.write(level.toString());
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws SlickException {
		File yourFile = new File(System.getProperty("user.home")
				+ "/Kingdom Of Saldimor/");
		if (!yourFile.exists()) {
			yourFile.mkdir();
			try {
				FileWriter writer = new FileWriter(
						System.getProperty("user.home")
								+ "/Kingdom Of Saldimor/level.txt");
				BufferedWriter out = new BufferedWriter(writer);

				out.write("1");
				out.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		BufferedReader in = null;
		try {
			FileReader reader = new FileReader(System.getProperty("user.home")
					+ "/Kingdom Of Saldimor/level.txt");
			in = new BufferedReader(reader);
			LEVEL = Integer.parseInt(in.readLine());
		} catch (IOException ex) {
			System.out.println("File Not Found");
		}

		AppGameContainer App = new AppGameContainer(new ScalableGame(new Game(
				"Kingdom Of Saldimor"), 1280, 960));
		App.setDisplayMode(1280, 960, false);
		App.setIcon("resources/images/other/icon.png");
		App.setAlwaysRender(true);
		App.setTargetFrameRate(60);
		App.setShowFPS(false);
		App.start();
	}
}