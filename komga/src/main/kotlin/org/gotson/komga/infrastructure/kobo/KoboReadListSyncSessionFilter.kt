package org.gotson.komga.infrastructure.kobo

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

/** Clears remembered tag conflicts at the start of a fresh Kobo sync run. */
@Component
class KoboReadListSyncSessionFilter(
  private val koboReadListMutationGuard: KoboReadListMutationGuard,
) : OncePerRequestFilter() {
  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain,
  ) {
    if (initializationPath.containsMatchIn(request.requestURI)) {
      koboReadListMutationGuard.beginDeviceSession(
        request.getHeader(KOBO_DEVICE_ID_HEADER),
      )
    }
    filterChain.doFilter(request, response)
  }

  private companion object {
    const val KOBO_DEVICE_ID_HEADER = "x-kobo-deviceid"
    val initializationPath = Regex("""(?:^|/)kobo/[^/]+/v1/initialization/?$""")
  }
}
