package org.gotson.komga.downloads.kcc

import org.gotson.komga.downloads.KccProfile

data class KccDevice(
  val code: String,
  val name: String,
  val width: Int,
  val height: Int,
)

data class KccOption(
  val flag: String,
  val group: String,
  val kind: String,
  val description: String,
)

/** CLI contract from KCC 13.0.4, comic2ebook.makeParser and image.ProfileData. */
object KccOptions {
  const val VERSION = "13.0.4"
  val formats = listOf("EPUB", "CBZ", "PDF")
  val devices =
    listOf(
      KccDevice("K1", "Kindle 1", 600, 670),
      KccDevice("K2", "Kindle 2", 600, 670),
      KccDevice("KDX", "Kindle DX/DXG", 824, 1000),
      KccDevice("K34", "Kindle Keyboard/Touch", 600, 800),
      KccDevice("K57", "Kindle 5/7", 600, 800),
      KccDevice("KPW", "Kindle Paperwhite 1/2", 758, 1024),
      KccDevice("KV", "Kindle Voyage", 1072, 1448),
      KccDevice("KPW34", "Kindle Paperwhite 3/4/Oasis", 1072, 1448),
      KccDevice("K810", "Kindle 8/10", 600, 800),
      KccDevice("KO", "Kindle Oasis 2/3", 1264, 1680),
      KccDevice("K11", "Kindle 11/2026", 1072, 1448),
      KccDevice("KPW5", "Kindle Paperwhite 5/Signature Edition", 1236, 1648),
      KccDevice("KPW6", "Kindle Paperwhite 6/2026", 1272, 1696),
      KccDevice("K1920", "Kindle 1440x1920", 1440, 1920),
      KccDevice("KS1860", "Kindle 1860", 1860, 1920),
      KccDevice("KS1920", "Kindle 1920", 1920, 1920),
      KccDevice("KS1240", "Kindle 1240", 1240, 1860),
      KccDevice("KS1324", "Kindle 1324", 1324, 1986),
      KccDevice("KS", "Kindle Scribe 1/2", 1860, 2480),
      KccDevice("KCS", "Kindle Colorsoft", 1272, 1696),
      KccDevice("KS3", "Kindle Scribe 3", 1986, 2648),
      KccDevice("KSCS", "Kindle Scribe Colorsoft", 1986, 2648),
      KccDevice("KoMT", "Kobo Mini/Touch", 600, 800),
      KccDevice("KoG", "Kobo Glo", 768, 1024),
      KccDevice("KoGHD", "Kobo Glo HD", 1072, 1448),
      KccDevice("KoA", "Kobo Aura", 758, 1024),
      KccDevice("KoAHD", "Kobo Aura HD", 1080, 1440),
      KccDevice("KoAH2O", "Kobo Aura H2O", 1080, 1430),
      KccDevice("KoAO", "Kobo Aura ONE", 1404, 1872),
      KccDevice("KoN", "Kobo Nia", 758, 1024),
      KccDevice("KoC", "Kobo Clara HD/Kobo Clara 2E/Kobo Clara BW", 1072, 1448),
      KccDevice("KoCC", "Kobo Clara Colour", 1072, 1448),
      KccDevice("KoL", "Kobo Libra H2O/Kobo Libra 2", 1264, 1680),
      KccDevice("KoLC", "Kobo Libra Colour", 1264, 1680),
      KccDevice("KoF", "Kobo Forma", 1440, 1920),
      KccDevice("KoS", "Kobo Sage", 1440, 1920),
      KccDevice("KoE", "Kobo Elipsa", 1404, 1872),
      KccDevice("Rmk1", "reMarkable 1", 1404, 1872),
      KccDevice("Rmk2", "reMarkable 2", 1404, 1872),
      KccDevice("RmkPP", "reMarkable Paper Pro", 1620, 2160),
      KccDevice("RmkPPMove", "reMarkable Paper Pro Move", 954, 1696),
      KccDevice("OTHER", "Other / custom", 0, 0),
      KccDevice("2400x3200", "2400 × 3200", 2400, 3200),
      KccDevice("1860x2480", "1860 × 2480", 1860, 2480),
      KccDevice("1650x2200", "1650 × 2200", 1650, 2200),
      KccDevice("1536x2048", "1536 × 2048", 1536, 2048),
      KccDevice("1404x1872", "1404 × 1872", 1404, 1872),
      KccDevice("1264x1680", "1264 × 1680", 1264, 1680),
      KccDevice("1072x1448", "1072 × 1448", 1072, 1448),
    )
  val options =
    listOf(
      KccOption("--lightnovel", "Main", "flag", "Only resize images and preserve original file structure."),
      KccOption("--wallpaper", "Main", "flag", "Crop to fill screen."),
      KccOption("--ebok", "Main", "flag", "Force EBOK tag instead of PDOC for MOBI"),
      KccOption("--invertdirection", "Main", "flag", "Invert page turn direction"),
      KccOption("--hq", "Main", "flag", "Try to increase the quality of magnification"),
      KccOption("--two-panel", "Main", "flag", "Display two not four panels in Panel View mode"),
      KccOption("--vertical4panel", "Main", "flag", "Display side panels first in virtual panel view"),
      KccOption("--legacypanelview", "Main", "flag", "Use legacy panel view method from KCC 6"),
      KccOption("--targetsize", "Main", "integer", "the maximal size of output file in MB. [Default=100MB for webtoon and 400MB for others]"),
      KccOption("--title", "Output settings", "text", "Comic title [Default=filename or directory name]"),
      KccOption("--metadatatitle", "Output settings", "integer", "Write title using ComicInfo.xml or other embedded metadata. 1: Combine Title with default schema 2: Use Title only"),
      KccOption("--keepcomicinfo", "Output settings", "integer", "Keep any original ComicInfo.xml files"),
      KccOption("--author", "Output settings", "text", "Author name [Default=KCC]"),
      KccOption("--language", "Output settings", "text", "EPUB language [Default=en-US]"),
      KccOption("--nokepub", "Output settings", "flag", "If format is EPUB, output file with '.epub' extension rather than '.kepub.epub'"),
      KccOption("--batchsplit", "Output settings", "integer", "Split output into multiple files. 0: Don't split 1: Automatic mode 2: Consider every subdirectory as separate volume [Default=0]"),
      KccOption("--spreadshift", "Output settings", "flag", "Shift first page to opposite side in landscape for spread alignment"),
      KccOption("--onepagelandscape", "Output settings", "flag", "Show a single centered page in landscape"),
      KccOption("--norotate", "Output settings", "flag", "Do not rotate double page spreads in spread splitter option."),
      KccOption("--rotateright", "Output settings", "flag", "Rotate double page spreads in opposite direction."),
      KccOption("--rotatefirst", "Output settings", "flag", "Put rotated 2 page spread first in spread splitter option."),
      KccOption("--noprocessing", "Processing", "flag", "Do not modify image and ignore any profile or processing option"),
      KccOption("--legacyextract", "Processing", "flag", "Use the legacy PDF/EPUB image extraction method from older KCC versions"),
      KccOption("--pdfwidth", "Processing", "flag", "Render vector PDFs to device width instead of height."),
      KccOption("--smartcovercrop", "Processing", "flag", "Attempt to crop main cover from wide image"),
      KccOption("--coverfill", "Processing", "flag", "Crop cover to fill screen"),
      KccOption("--upscale", "Processing", "flag", "Resize images smaller than device's resolution"),
      KccOption("--stretch", "Processing", "flag", "Stretch images to device's resolution"),
      KccOption("--splitter", "Processing", "integer", "Double page parsing mode. 0: Split 1: Rotate 2: Both [Default=0]"),
      KccOption("--gamma", "Processing", "decimal", "Apply gamma correction to linearize the image [Default=Auto]"),
      KccOption("--autolevel", "Output settings", "flag", "Set most common dark pixel value to be black point for leveling."),
      KccOption("--noautocontrast", "Output settings", "flag", "Disable autocontrast."),
      KccOption("--colorautocontrast", "Output settings", "flag", "Autocontrast color pages too. Skipped for pages without near blacks or whites."),
      KccOption("--cropping", "Processing", "integer", "Set cropping mode. 0: Disabled 1: Margins 2: Margins + page numbers [Default=2]"),
      KccOption("--croppingpower", "Processing", "decimal", "Set cropping power [Default=1.0]"),
      KccOption("--preservemargin", "Processing", "integer", "After calculating crop, back up specified percentage amount. [Default=0]"),
      KccOption("--croppingminimum", "Processing", "decimal", "Set cropping minimum area ratio [Default=0.0]"),
      KccOption("--interpanelcrop", "Processing", "integer", "Crop empty sections. 0: Disabled 1: Horizontally 2: Both [Default=0]"),
      KccOption("--blackborders", "Processing", "flag", "Disable autodetection and force black borders"),
      KccOption("--whiteborders", "Processing", "flag", "Disable autodetection and force white borders"),
      KccOption("--forcecolor", "Processing", "flag", "Don't convert images to grayscale"),
      KccOption("--eraserainbow", "Output settings", "flag", "Erase rainbow effect on color eink screen by attenuating interfering frequencies"),
      KccOption("--forcepng", "Processing", "flag", "Create PNG files instead JPEG for black and white images"),
      KccOption("--force-png-rgb", "Processing", "flag", "Force color images to be saved as PNG"),
      KccOption("--webp", "Processing", "flag", "Replace JPG with lossy WEBP and PNG with lossless WEBP"),
      KccOption("--pnglegacy", "Processing", "flag", "Use a more compatible 8 bit png instead of 4 bit"),
      KccOption("--noquantize", "Processing", "flag", "Don't quantize to 16 color PNG"),
      KccOption("--mozjpeg", "Processing", "flag", "Create JPEG files using mozJpeg"),
      KccOption("--jpeg-quality", "Processing", "integer", "The JPEG quality, on a scale from 0 (worst) to 95 (best). Default 85 for most devices."),
      KccOption("--maximizestrips", "Processing", "flag", "Turn 1x4 strips to 2x2 strips"),
      KccOption("--tempdir", "Processing", "flag", "Create temporary files directory on source file drive."),
      KccOption("--customwidth", "Custom profile", "integer", "Replace screen width provided by device profile"),
      KccOption("--customheight", "Custom profile", "integer", "Replace screen height provided by device profile"),
      KccOption("--webtoon", "Main", "flag", "Webtoon processing mode"),
    )
  private val byFlag = options.associateBy { it.flag }

  fun validate(profile: KccProfile) {
    require(devices.any { it.code == profile.device }) { "Unknown KCC device: ${profile.device}" }
    require(profile.format in formats) { "Unsupported library output format: ${profile.format}" }
    val keys = mutableSetOf<String>()
    profile.extraArgs.forEach { arg ->
      val flag = arg.substringBefore('=')
      val option = requireNotNull(byFlag[flag]) { "Unsupported or managed KCC option: $flag" }
      require(keys.add(flag)) { "Duplicate KCC option: $flag" }
      if (option.kind == "flag") {
        require(arg == flag) { "$flag does not take a value" }
      } else {
        require('=' in arg) { "Use $flag=value" }
        val value = arg.substringAfter('=')
        require(value.isNotBlank() && value.length <= 512 && value.none { it.isISOControl() }) { "Invalid value for $flag" }
        if (flag == "--language") require(value.matches(Regex("[A-Za-z0-9-]{1,35}"))) { "Enter a valid language tag" }
        when (option.kind) {
          "integer" -> {
            val number = requireNotNull(value.toIntOrNull()) { "$flag requires an integer" }
            val range =
              when (flag) {
                "--jpeg-quality" -> 0..95
                "--cropping", "--splitter", "--interpanelcrop", "--batchsplit", "--metadatatitle" -> 0..2
                "--keepcomicinfo" -> 0..1
                "--preservemargin" -> 0..100
                "--customwidth", "--customheight" -> 0..16384
                "--targetsize" -> 1..8192
                else -> 0..Int.MAX_VALUE
              }
            require(number in range) { "Invalid range for $flag: $range" }
          }
          "decimal" -> {
            val number = requireNotNull(value.toDoubleOrNull()) { "$flag requires a number" }
            require(number.isFinite() && number >= 0) { "Invalid value for $flag" }
          }
        }
      }
    }
    require(!keys.containsAll(listOf("--blackborders", "--whiteborders"))) { "Choose only one border color" }
    if (profile.device == "OTHER") {
      require(
        listOf("--customwidth", "--customheight").all { flag ->
          profile.extraArgs.any { it.startsWith("$flag=") && (it.substringAfter('=').toIntOrNull() ?: 0) > 0 }
        },
      ) { "Custom devices require both dimensions" }
    }
  }

  fun arguments(profile: KccProfile): List<String> {
    validate(profile)
    return buildList {
      add("--profile=${profile.device}")
      add("--format=${profile.format}")
      if (profile.manga) add("--manga-style")
      addAll(profile.extraArgs)
    }
  }
}
