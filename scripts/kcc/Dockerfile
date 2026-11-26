# Build on top of the custom Komga Ubuntu-based AMD64/ARM64 image.
# This wrapper is intentionally separate from upstream's JReleaser template.
ARG KOMGA_IMAGE
FROM ${KOMGA_IMAGE}
USER root
COPY scripts/kcc/install-unix.sh /tmp/install-komga-kcc.sh
RUN apt-get update && apt-get install -y --no-install-recommends \
      git python3 python3-venv python3-dev 7zip libglib2.0-0t64 \
      build-essential cmake libffi-dev libjpeg-dev ca-certificates \
    && if ! command -v 7z >/dev/null; then ln -s "$(command -v 7zz)" /usr/local/bin/7z; fi \
    && KCC_PYTHON=python3 bash /tmp/install-komga-kcc.sh /opt/komga-kcc \
    && rm -f /tmp/install-komga-kcc.sh \
    && rm -rf /var/lib/apt/lists/*
ENV KOMGA_KCC_C2E=/opt/komga-kcc/kcc/bin/kcc-c2e
ENV PATH=/opt/komga-kcc/kcc/bin:${PATH}
