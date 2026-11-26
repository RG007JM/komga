import { ApiBaseUrl } from '@/api/base'

export type Destination = { libraryId: string; folder: string }
export type Series = { id: string; title: string }
export type Chapter = { id: string; number: string; title: string; groups: string[] }
export type Dimensions = { number: number; width?: number; height?: number }
export type Filters = { language: string; includeGroups: string[]; excludeGroups: string[] }
export type Tracked = {
  id: string
  provider: string
  seriesId: string
  title: string
  destination: Destination
  filters: Filters
  enabled: boolean
  kccProfileId?: string | null
  lastCheck?: string
  nextCheck?: string
  expectedRelease?: string
  releaseBasis?: string
  result?: string
  error?: string
}
export type KccOption = { flag: string; group: string; kind: string; description: string }
export type KccDevice = { code: string; name: string; width: number; height: number }
export type KccCapabilities = { version: string; devices: KccDevice[]; formats: string[]; options: KccOption[] }
export type Profile = {
  id: string
  name: string
  device: string
  format: string
  manga: boolean
  keepOriginal: boolean
  outputFolder: string
  extraArgs: string[]
}
export type Record = {
  id: string
  provider: string
  seriesId: string
  title: string
  chapter: Chapter
  destination: Destination
  file: string
  downloadedAt: string
  mode: string
  scan: string
  conversion?: { profile: Profile; status: string; file?: string; files?: string[]; error?: string }
}
export type Kcc = { autoProfileId: string; manualProfileId: string; profiles: Profile[] }
export type Overview = {
  state: { tracked: Tracked[]; history: Record[]; kcc: Kcc }
  providers: { id: string; label: string }[]
  libraries: { id: string; name: string }[]
  health: { [component: string]: string }
  kccCapabilities: KccCapabilities
  workerError?: string
}

export async function downloadsApi<T>(path = '', method = 'GET', data?: unknown): Promise<T> {
  const response = await fetch(ApiBaseUrl.slash + 'api/v1/manga-downloads' + path, {
    method,
    credentials: 'include',
    headers: {
      'X-Requested-With': 'XMLHttpRequest',
      ...(data === undefined ? {} : { 'Content-Type': 'application/json' }),
    },
    body: data === undefined ? undefined : JSON.stringify(data),
  })
  if (!response.ok) {
    const error = await response.json().catch(() => null)
    throw new Error(
      error?.detail ?? error?.message ?? 'Request failed (HTTP ' + response.status + ')',
    )
  }
  return response.status === 204 || response.status === 202 ? (undefined as T) : response.json()
}
