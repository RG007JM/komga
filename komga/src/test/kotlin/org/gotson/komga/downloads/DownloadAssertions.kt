package org.gotson.komga.downloads

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

internal fun assertEqualValue(
  expected: Any?,
  actual: Any?,
) {
  assertThat(actual).isEqualTo(expected)
}

internal fun assertTrueValue(actual: Boolean) {
  assertThat(actual).isTrue()
}

internal fun assertFalseValue(actual: Boolean) {
  assertThat(actual).isFalse()
}

internal fun assertNullValue(actual: Any?) {
  assertThat(actual).isNull()
}

internal fun assertThrowsType(
  type: Class<out Throwable>,
  action: () -> Unit,
) {
  assertThatThrownBy { action() }.isInstanceOf(type)
}
