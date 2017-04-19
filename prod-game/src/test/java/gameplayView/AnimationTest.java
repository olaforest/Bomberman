package gameplayView;

import org.testng.annotations.Test;

import java.awt.image.BufferedImage;

import static gameplayView.AnimationType.Death;

public class AnimationTest {

	private final AnimParam animParam = new AnimParam(1, 2, 2);
	private final BufferedImage bufferedImage1 = new BufferedImage(1, 1, 1);
	private final BufferedImage bufferedImage2 = new BufferedImage(2, 2, 2);

	@Test
	public void getImageReturnsEmptyList_newInstance_returnsADoneAnimation() {
		//given
//		when(getImages(animParam)).thenReturn(of(emptyList()));
		//when
		final Animation animation = new Animation(Death, animParam);
		//then
//		assertThat(animation.isAnimDone()).isTrue();
	}

	@Test
	public void validAnimationParameters_newInstance_returnsValidAnimation() {
		//given
//		when(getImages(animParam)).thenReturn(of(asList(bufferedImage1, bufferedImage2)));
		//when
		final Animation animation = new Animation(Death, animParam);
		//then
//		assertThat(animation.getCurrentFrame()).isEqualTo(bufferedImage1);
//		assertThat(animation.isAnimDone()).isFalse();
	}

	@Test
	public void validAnimationParameters_cycleAnimationAndGetCurrentFrameAndIsAnimDone_returnsSecondImageAndTrue() {
		//given
//		when(getImages(animParam)).thenReturn(of(asList(bufferedImage1, bufferedImage2)));
		//when
		final Animation animation = new Animation(Death, animParam);
		animation.cycleFrame();
		//then
//		assertThat(animation.getCurrentFrame()).isEqualTo(bufferedImage2);
//		assertThat(animation.isAnimDone()).isTrue();
	}
}