package gameplayView;

import org.testng.annotations.Test;

public class AnimationTest {

	@Test
	public void test() {
		//given
		final AnimParam animParam = new AnimParam(1, 2, 3);
		//when
		new Animation(animParam);
	}
}