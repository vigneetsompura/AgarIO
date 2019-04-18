package server;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Graphics2D;

import org.junit.jupiter.api.Test;

import agario.Food;

	public class FoodTest {
		@Test
		public void objectCreatedSuccessfully() {
			Food f = new Food(0, 0);
			assertEquals(f.getX(), 0);
			assertEquals(0, f.getY());
		}
		
		@Test
		public void verifyXCoordinate() {
			Food f = new Food(0, 0);
			assertEquals(0, f.getX());
		}
		
		@Test
		public void verifyYCoordinate() {
			Food f = new Food(0, 0);
			assertEquals(0, f.getY());
		}
		
		@Test
		public void verifySetXY() {
			Food f = new Food(0, 0);
			f.setXY(1, 1);
			assertEquals(1, f.getX());
			assertEquals(1, f.getY());
		}
		
		@Test
		public void verifyfillColor() {
//			Graphics2D g = null;
//			Food f = new Food(0, 0);
//			f.fillColor(g);
//			assertEquals(Color.YELLOW, g.getColor());
		}

}
