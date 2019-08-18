package deco2800.skyfall.entities;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PlayerTest {

	private PlayerPeon player = new PlayerPeon(0,0,1,"pTest",10);
	
	@Test
	public void testPlayerSet() {
		PlayerPeon p = new PlayerPeon(0,0,1,"pTest",10);
		assertThat("", p.getTexture(), is(equalTo("spacman_ded")));
	}
	
	@Test
	public void testConstructor() {
		PlayerPeon p = new PlayerPeon(1,1,1,"pTest",10);
		assertThat("", p.getCol(), is(equalTo(1f)));
		assertThat("", p.getRow(), is(equalTo(1f)));
		assertThat("", p.getSpeed(), is(equalTo(1f)));
		assertThat("", p.getTexture(), is(equalTo("spacman_ded")));
	}
}
