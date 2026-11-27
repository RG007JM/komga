package org.gotson.komga.downloads

import org.gotson.komga.downloads.kcc.KccOptions
import org.junit.jupiter.api.Test

class KccOptionsTest {
  private val profile = KccProfile("test", "Test", "KoLC")

  @Test
  fun `registry matches pinned CLI device and flag counts`() {
    assertEqualValue("13.0.4", KccOptions.VERSION)
    assertEqualValue(49, KccOptions.devices.size)
    assertEqualValue(54, KccOptions.options.size)
    val libra = KccOptions.devices.single { it.code == "KoLC" }
    assertEqualValue(1264, libra.width)
    assertEqualValue(1680, libra.height)
    assertEqualValue(
      KccOptions.options.size,
      KccOptions.options
        .map { it.flag }
        .distinct()
        .size,
    )
  }

  @Test
  fun `arguments preserve exact values and do not add hidden defaults`() {
    val args = listOf("--cropping=0", "--title=A title with spaces", "--jpeg-quality=95", "--forcecolor", "--nokepub")
    assertEqualValue(listOf("--profile=KoLC", "--format=EPUB", "--manga-style") + args, KccOptions.arguments(profile.copy(extraArgs = args)))
  }

  @Test
  fun `minimal colour preset disables optional transformations`() {
    val args = listOf("--mozjpeg", "--eraserainbow", "--forcecolor", "--cropping=0", "--interpanelcrop=0", "--splitter=1", "--norotate", "--gamma=1", "--noautocontrast", "--metadatatitle=2", "--keepcomicinfo=1")
    assertEqualValue(listOf("--profile=KoLC", "--format=EPUB", "--manga-style") + args, KccOptions.arguments(profile.copy(extraArgs = args)))
  }

  @Test
  fun `managed and malformed options are rejected`() {
    listOf("--delete", "-d", "--output=/tmp", "--filename=unsafe", "--profile=OTHER", "--filefusion", "--help", "other.cbz", "--forcecolor=true", "--gamma=NaN", "--jpeg-quality=96", "--cropping=3").forEach { arg ->
      assertThrowsType(IllegalArgumentException::class.java) { KccOptions.validate(profile.copy(extraArgs = listOf(arg))) }
    }
    assertThrowsType(IllegalArgumentException::class.java) { KccOptions.validate(profile.copy(extraArgs = listOf("--gamma=1", "--gamma=2"))) }
  }

  @Test
  fun `custom devices need both dimensions`() {
    assertThrowsType(IllegalArgumentException::class.java) { KccOptions.validate(profile.copy(device = "OTHER")) }
    KccOptions.validate(profile.copy(device = "OTHER", extraArgs = listOf("--customwidth=1000", "--customheight=1500")))
  }
}
