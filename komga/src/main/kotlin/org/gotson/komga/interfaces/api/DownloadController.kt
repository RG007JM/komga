package org.gotson.komga.interfaces.api

import io.swagger.v3.oas.annotations.Hidden
import org.gotson.komga.domain.persistence.LibraryRepository
import org.gotson.komga.downloads.ChapterFilters
import org.gotson.komga.downloads.DownloadFiles
import org.gotson.komga.downloads.DownloadService
import org.gotson.komga.downloads.KccSettings
import org.gotson.komga.downloads.ManualRequest
import org.gotson.komga.downloads.SourceSeries
import org.gotson.komga.downloads.TrackRequest
import org.gotson.komga.downloads.kcc.KccOptions
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.RejectedExecutionException

@Hidden
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/v1/manga-downloads")
class DownloadController(
  private val service: DownloadService,
  private val files: DownloadFiles,
  private val libraries: LibraryRepository,
) {
  @GetMapping
  fun overview() =
    mapOf(
      "state" to service.state(),
      "providers" to service.providers.values.map { mapOf("id" to it.id, "label" to it.label) },
      "libraries" to libraries.findAll().map { mapOf("id" to it.id, "name" to it.name) },
      "health" to service.health(),
      "workerError" to service.workerError,
      "kccCapabilities" to mapOf("version" to KccOptions.VERSION, "devices" to KccOptions.devices, "formats" to KccOptions.formats, "options" to KccOptions.options),
    )

  @GetMapping("/{source}/search")
  fun search(
    @PathVariable source: String,
    @RequestParam q: String,
  ): List<SourceSeries> {
    require(q.isNotBlank() && q.length <= 150) { "Enter a title or a source ID" }
    return service.provider(source).search(q.trim())
  }

  @GetMapping("/{source}/series/{id}/chapters")
  fun chapters(
    @PathVariable source: String,
    @PathVariable id: String,
    @RequestParam(defaultValue = "en") language: String,
  ) = service.provider(source).chapters(id, ChapterFilters(language))

  @GetMapping("/{source}/chapters/{id}/dimensions")
  fun dimensions(
    @PathVariable source: String,
    @PathVariable id: String,
  ) = files.dimensions(service.provider(source).pages(id))

  @PutMapping("/tracked")
  fun track(
    @RequestBody request: TrackRequest,
  ) = service.track(request)

  @DeleteMapping("/tracked/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun stop(
    @PathVariable id: String,
  ) = service.stop(id)

  @PostMapping("/tracked/{id}/check")
  @ResponseStatus(HttpStatus.ACCEPTED)
  fun check(
    @PathVariable id: String,
  ) = service.check(id)

  @PostMapping("/manual")
  @ResponseStatus(HttpStatus.ACCEPTED)
  fun manual(
    @RequestBody request: ManualRequest,
  ) = service.download(request)

  @PutMapping("/kcc")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun kcc(
    @RequestBody settings: KccSettings,
  ) = service.kcc(settings)

  @PostMapping("/history/{id}/retry-conversion")
  @ResponseStatus(HttpStatus.ACCEPTED)
  fun retry(
    @PathVariable id: String,
  ) = service.retry(id)

  @ExceptionHandler(IllegalArgumentException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun badRequest(e: IllegalArgumentException) = mapOf("message" to (e.message ?: "Invalid download request"))

  @ExceptionHandler(RejectedExecutionException::class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  fun busy() = mapOf("message" to "The downloader is busy. Try again shortly.")
}
