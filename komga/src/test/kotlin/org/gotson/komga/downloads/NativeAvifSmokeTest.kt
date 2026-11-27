package org.gotson.komga.downloads

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import java.util.Base64
import javax.imageio.ImageIO

/** Opt-in on Linux CI with a real libheif decoder and the JDK used in the Docker image. */
@EnabledIfEnvironmentVariable(named = "KOMGA_TEST_NATIVE_AVIF", matches = "true")
class NativeAvifSmokeTest {
  @Test
  fun `native decoder reads ordinary avif pixels`() {
    val bytes = Base64.getDecoder().decode("AAAAIGZ0eXBhdmlmAAAAAGF2aWZtaWYxbWlhZk1BMUIAAADrbWV0YQAAAAAAAAAhaGRscgAAAAAAAAAAcGljdAAAAAAAAAAAAAAAAAAAAAAOcGl0bQAAAAAAAQAAAB5pbG9jAAAAAEQAAAEAAQAAAAEAAAETAAAAKgAAAChpaW5mAAAAAAABAAAAGmluZmUCAAAAAAEAAGF2MDFDb2xvcgAAAABqaXBycAAAAEtpcGNvAAAAFGlzcGUAAAAAAAAAEAAAAAwAAAAQcGl4aQAAAAADCAgIAAAADGF2MUOBAAwAAAAAE2NvbHJuY2x4AAEADQAGgAAAABdpcG1hAAAAAAAAAAEAAQQBAoMEAAAAMm1kYXQSAAoJGAz+2iAhoNCAMhsSB/f2qQCCCD5AAAB1hVyH4oqFrduNrt2Ga4A=")
    val image = checkNotNull(ImageIO.read(bytes.inputStream())) { "Install native AVIF support (libheif and an AV1 decoder)" }
    assertEqualValue(16, image.width)
    assertEqualValue(12, image.height)
    val pixel = java.awt.Color(image.getRGB(8, 6))
    assertTrueValue(pixel.blue > pixel.red + 50)
  }
}
