import java.io.File;
import java.io.IOException;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;

public class Main {
	private static final int WINDOW_WIDTH = 1024;
	private static final int WINDOW_HEIGHT = 768;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		RenderWindow window = new RenderWindow();
		window.create(new VideoMode(WINDOW_WIDTH, WINDOW_HEIGHT), "Click le clown", WindowStyle.DEFAULT);
		
		Texture texture = new Texture();
		texture.loadFromFile((new File("assets/clown.png")).toPath());

		Font font = new Font();
		font.loadFromFile((new File("assets/font.ttf").toPath()));
				
		Sprite spriteClown = new Sprite(texture);
		
		Text lbScore = new Text();
		lbScore.setColor(Color.WHITE);
		lbScore.setFont(font);
		
		Text lbTime = new Text();
		lbTime.setColor(Color.WHITE);
		lbTime.setFont(font);
		lbTime.setPosition(WINDOW_WIDTH - 180, 0);
		
		int x = 0;
		int y = 0;
		int offsetX = 1;
		int offsetY = 1;
		int frame = 0;
		int count = 0;
		Clock timer = new Clock();

		while (window.isOpen()) {

			// Pool events
			Event event = null;
			while ((event = window.pollEvent()) != null) {
				// Handle close event
				if (event.type == Event.Type.CLOSED) {
					window.close();
					return;
				}
				// Handle mouse button event
				if (event.type == Event.Type.MOUSE_BUTTON_RELEASED) {
					int mouseX = event.asMouseButtonEvent().position.x;
					int mouseY = event.asMouseButtonEvent().position.y;
					if (spriteClown.getGlobalBounds().contains(mouseX, mouseY)) {
						count++;
					}
				}
			}
			
			// Clear window
			window.clear();
			
			// Draw clown
			spriteClown.setPosition(x, y);
			window.draw(spriteClown);
			
			// Draw counter
			window.draw(lbScore);
			
			// Draw time
			window.draw(lbTime);
			
			// Display window
			window.display();
			
			if (frame++ % 42 == 0) {
				// Refresh counter
				lbScore.setString("click: " + count);
				
				// Refresh time
				lbTime.setString("time: " + (100 - timer.getElapsedTime().asSeconds()));
				
				// Move clown
				x += offsetX;
				y += offsetY;
				
				if (x + spriteClown.getLocalBounds().width > WINDOW_WIDTH) {
					offsetX = -1;
				}
				if (x <= 0) {
					offsetX = 1;
				}
				if (y + spriteClown.getLocalBounds().height > WINDOW_HEIGHT) {
					offsetY = -1;
				}
				if (y <= 0) {
					offsetY = 1;
				}
			}
		}
	}

}
