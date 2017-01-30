package gameplayView;

import org.testng.annotations.Test;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

import static gameplayView.ImageManager.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ImageManagerTest {

	@Test
	public void validAnimationParameters_getImages_returnsScaledImageList() {
		//given
		final AnimParam animParam = new AnimParam(4, 3, 2);
		//when
		final Optional<List<BufferedImage>> images = getImages(animParam);
		//then
		assertThat(images).isNotEmpty();
		images.ifPresent(list -> {
			assertThat(list).hasSize(2);
			assertThat(list.get(0).getWidth()).isEqualTo(PIXEL_DIMENSION * ZOOM);
			assertThat(list.get(0).getHeight()).isEqualTo(PIXEL_DIMENSION * ZOOM);
		});
	}
}