package gameplayView;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnimParamTest {

	@Test
	public void negativeXCoordinate_newInstance_throwsException() {
		//when
		assertThatThrownBy(() -> new AnimParam(-1, 0, 2))
				//then
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("(x, y) coordinates cannot be negative.");
	}

	@Test
	public void negativeYCoordinate_newInstance_throwsException() {
		//when
		assertThatThrownBy(() -> new AnimParam(0, -1, 2))
				//then
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("(x, y) coordinates cannot be negative.");
	}

	@Test
	public void numberOfFrameIsSmallerThanMinimum_newInstance_throwsException() {
		//when
		assertThatThrownBy(() -> new AnimParam(0, 0, 1))
				//then
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Animation should have at least 2 frames.");
	}
}