<script setup lang="ts">
import { computed, onMounted, onUnmounted, ref } from 'vue'
import { useDisplay } from 'vuetify'
import {
  downloadsApi,
  type Chapter,
  type Dimensions,
  type Filters,
  type Kcc,
  type Overview,
  type Profile,
  type Record,
  type Series,
  type Tracked,
} from '@/api/downloads'

const { xs } = useDisplay()
const overview = ref<Overview>()
const tab = ref('history')
const filter = ref('')
const error = ref('')
const notice = ref('')
const snackbar = ref(false)
const mode = ref<'manual' | 'track' | 'edit'>()
const profile = ref<Profile>()
const stopEntry = ref<Tracked>()
const details = ref<{ provider: string; seriesId: string; title: string }>()
const working = ref(false)
const provider = ref('')
const query = ref('')
const results = ref<Series[]>()
const selected = ref<Series>()
const chapters = ref<Chapter[]>([])
const selectedChapters = ref<string[]>([])
const dimensions = ref<{ [id: string]: Dimensions[] }>({})
const dimensionError = ref<{ [id: string]: string }>({})
const expanded = ref<string>()
const libraryId = ref('')
const folder = ref('')
const language = ref('en')
const includes = ref('')
const excludes = ref('')
const profileId = ref('inherit')
const state = computed(() => overview.value?.state)
const visible = (title: string) => title.toLowerCase().includes(filter.value.toLowerCase().trim())
const library = (id: string) => overview.value?.libraries.find((item) => item.id === id)?.name ?? id
const source = (id: string) => overview.value?.providers.find((item) => item.id === id)?.label ?? id
const records = computed(() => state.value?.history.filter((item) => visible(item.title)) ?? [])
const history = computed(() =>
  records.value.filter(
    (item, index, all) =>
      all.findIndex(
        (other) => other.provider === item.provider && other.seriesId === item.seriesId,
      ) === index,
  ),
)
const tracked = computed(() => state.value?.tracked.filter((item) => visible(item.title)) ?? [])
const activity = computed(() => state.value?.history.filter((item) => item.conversion) ?? [])
const detailRecords = computed(
  () =>
    state.value?.history.filter(
      (item) =>
        item.provider === details.value?.provider && item.seriesId === details.value?.seriesId,
    ) ?? [],
)
const detailTrack = computed(() =>
  state.value?.tracked.find(
    (item) =>
      item.provider === details.value?.provider && item.seriesId === details.value?.seriesId,
  ),
)
const profileName = (id?: string | null) =>
  state.value?.kcc.profiles.find((item) => item.id === id)?.name ?? 'Skip KCC'
const profileOptions = computed(() => [
  { title: 'Skip KCC', value: 'off' },
  ...(state.value?.kcc.profiles ?? []).map((item) => ({ title: item.name, value: item.id })),
])
const autoOptions = computed(() => [
  { title: 'Use default (' + profileName(state.value?.kcc.autoProfileId) + ')', value: 'inherit' },
  ...profileOptions.value,
])
const manualOptions = computed(() => [
  { title: 'Use default (' + profileName(state.value?.kcc.manualProfileId) + ')', value: 'inherit' },
  ...profileOptions.value,
])
const historyHeaders = [
  { title: 'Manga', key: 'title' },
  { title: 'Latest download', key: 'downloadedAt' },
  { title: 'Saved to', key: 'file' },
  { title: 'Auto-fetch', key: 'mode' },
  { title: 'Status', key: 'scan' },
]
const trackHeaders = [
  { title: 'Manga', key: 'title' },
  { title: 'Auto-fetch', key: 'enabled' },
  { title: 'KCC profile', key: 'kccProfileId' },
  { title: 'Expected release', key: 'expectedRelease' },
  { title: 'Next check', key: 'nextCheck' },
  { title: 'Last check', key: 'lastCheck' },
  { title: '', key: 'actions', sortable: false },
]
const alreadyDownloaded = computed(
  () =>
    new Set(
      state.value?.history
        .filter(
          (item) =>
            item.provider === provider.value &&
            item.seriesId === selected.value?.id &&
            item.destination.libraryId === libraryId.value &&
            item.destination.folder === folder.value.trim(),
        )
        .map((item) => item.chapter.id),
    ),
)
const stamp = (value?: string) => (value ? new Date(value).toLocaleString() : '—')
const chip = (status?: string) =>
  ['Failed', 'Unavailable', 'Offline', 'Not found'].includes(status ?? '')
    ? 'error'
    : ['Queued', 'Converting', 'Checking'].includes(status ?? '')
      ? 'info'
      : undefined
const asMessage = (cause: unknown) => (cause instanceof Error ? cause.message : String(cause))
const chapterPath = () =>
  '/' +
  encodeURIComponent(provider.value) +
  '/series/' +
  encodeURIComponent(selected.value?.id ?? '') +
  '/chapters?language=' +
  encodeURIComponent(language.value)
const matches = (item: Record) =>
  state.value?.tracked.find(
    (track) => track.provider === item.provider && track.seriesId === item.seriesId,
  )
const parseGroups = (value: string) =>
  value
    .split(',')
    .map((group) => group.trim())
    .filter(Boolean)
const currentFilters = (): Filters => ({
  language: language.value,
  includeGroups: parseGroups(includes.value),
  excludeGroups: parseGroups(excludes.value),
})

let poll: ReturnType<typeof setInterval> | undefined
let loadSequence = 0
async function refresh() {
  const sequence = ++loadSequence
  const result = await downloadsApi<Overview>()
  if (sequence === loadSequence) overview.value = result
}
function notify(value: string) {
  notice.value = value
  snackbar.value = true
}
async function perform(action: () => Promise<void>) {
  if (working.value) return
  working.value = true
  error.value = ''
  try {
    await action()
  } catch (cause) {
    error.value = asMessage(cause)
  } finally {
    working.value = false
  }
}
async function update(message: string) {
  await refresh()
  notify(message)
}
onMounted(() => {
  void perform(refresh)
  poll = setInterval(() => {
    if (!working.value)
      void refresh().catch((cause) => {
        error.value = asMessage(cause)
      })
  }, 4000)
})
onUnmounted(() => {
  if (poll) clearInterval(poll)
})

const editing = ref<Tracked>()
function open(kind: 'manual' | 'track' | 'edit', item?: Tracked) {
  error.value = ''
  editing.value = item
  provider.value = item?.provider ?? overview.value?.providers[0]?.id ?? ''
  query.value = ''
  results.value = undefined
  selected.value = item ? { id: item.seriesId, title: item.title } : undefined
  chapters.value = []
  selectedChapters.value = []
  dimensions.value = {}
  dimensionError.value = {}
  expanded.value = undefined
  libraryId.value = item?.destination.libraryId ?? overview.value?.libraries[0]?.id ?? ''
  folder.value = item?.destination.folder ?? ''
  language.value = item?.filters.language ?? 'en'
  includes.value = item?.filters.includeGroups.join(', ') ?? ''
  excludes.value = item?.filters.excludeGroups.join(', ') ?? ''
  profileId.value = item?.kccProfileId ?? 'inherit'
  mode.value = kind
}
function editFromHistory(item: Tracked) {
  open('edit', item)
  details.value = undefined
}
function doSearch() {
  void perform(async () => {
    selected.value = undefined
    chapters.value = []
    results.value = await downloadsApi<Series[]>(
      '/' +
        encodeURIComponent(provider.value) +
        '/search?q=' +
        encodeURIComponent(query.value.trim()),
    )
  })
}
function resetSearch() {
  selected.value = undefined
  results.value = undefined
  chapters.value = []
  selectedChapters.value = []
}
function pick(series: Series) {
  selected.value = series
  const existing = state.value?.tracked.find(
    (item) => item.seriesId === series.id && item.provider === provider.value,
  )
  if (mode.value === 'track' && existing) {
    open('edit', existing)
    return
  }
  folder.value = series.title.replace(/[\\/:*?"<>|]/g, '_')
  selectedChapters.value = []
  chapters.value = []
  expanded.value = undefined
  if (mode.value === 'manual') loadChapters()
}
function loadChapters() {
  void perform(async () => {
    selectedChapters.value = []
    chapters.value = await downloadsApi<Chapter[]>(chapterPath())
  })
}
async function showDimensions(chapter: Chapter) {
  if (expanded.value === chapter.id) {
    expanded.value = undefined
    return
  }
  expanded.value = chapter.id
  if (dimensions.value[chapter.id]) return
  delete dimensionError.value[chapter.id]
  try {
    dimensions.value[chapter.id] = await downloadsApi<Dimensions[]>(
      '/' +
        encodeURIComponent(provider.value) +
        '/chapters/' +
        encodeURIComponent(chapter.id) +
        '/dimensions',
    )
  } catch (cause) {
    dimensionError.value[chapter.id] = asMessage(cause)
  }
}
function submit() {
  void perform(async () => {
    if (!selected.value) return
    const payload = {
      provider: provider.value,
      seriesId: selected.value.id,
      title: selected.value.title,
      destination: { libraryId: libraryId.value, folder: folder.value.trim() },
      filters: currentFilters(),
    }
    if (mode.value === 'manual') {
      await downloadsApi('/manual', 'POST', {
        ...payload,
        chapterIds: selectedChapters.value.filter((id) => !alreadyDownloaded.value.has(id)),
        kccProfileId: profileId.value === 'inherit' ? null : profileId.value,
      })
      tab.value = 'history'
      await update('Download queued')
    } else {
      await downloadsApi('/tracked', 'PUT', {
        ...payload,
        enabled: editing.value?.enabled ?? true,
        kccProfileId: profileId.value === 'inherit' ? null : profileId.value,
      })
      tab.value = 'tracked'
      await update('Auto-fetch settings saved')
    }
    mode.value = undefined
  })
}
function toggle(item: Tracked) {
  void perform(async () => {
    const { provider, seriesId, title, destination, filters, enabled, kccProfileId } = item
    await downloadsApi('/tracked', 'PUT', {
      provider,
      seriesId,
      title,
      destination,
      filters,
      enabled: !enabled,
      kccProfileId,
    })
    await update(enabled ? 'Auto-fetch paused' : 'Auto-fetch resumed')
  })
}
function check(item: Tracked) {
  void perform(async () => {
    await downloadsApi('/tracked/' + item.id + '/check', 'POST')
    await update('Check queued')
  })
}
function checkAll() {
  void perform(async () => {
    for (const item of state.value?.tracked.filter((item) => item.enabled) ?? []) {
      await downloadsApi('/tracked/' + item.id + '/check', 'POST')
    }
    await update('Auto-fetch checks queued')
  })
}
function stop() {
  void perform(async () => {
    if (!stopEntry.value) return
    await downloadsApi('/tracked/' + stopEntry.value.id, 'DELETE')
    stopEntry.value = undefined
    await update('Auto-fetch stopped. History was kept.')
  })
}
function editProfile(item?: Profile) {
  profile.value = item
    ? { ...item }
    : {
        id: crypto.randomUUID(),
        name: '',
        device: 'KoLC',
        format: 'EPUB',
        manga: true,
        keepOriginal: true,
        outputFolder: 'Converted',
        extraArgs: [],
      }
}
function saveKcc(settings: Kcc, done?: () => void) {
  void perform(async () => {
    await downloadsApi('/kcc', 'PUT', settings)
    done?.()
    await update('KCC settings saved')
  })
}
function saveProfile() {
  if (!profile.value || !state.value) return
  const saved = {
    ...profile.value,
    name: profile.value.name.trim(),
    device: profile.value.device.trim(),
    outputFolder: profile.value.outputFolder.trim(),
  }
  const profiles = state.value.kcc.profiles.filter((item) => item.id !== saved.id)
  saveKcc({ ...state.value.kcc, profiles: [...profiles, saved] }, () => {
    profile.value = undefined
  })
}
function retry(item: Record) {
  void perform(async () => {
    await downloadsApi('/history/' + item.id + '/retry-conversion', 'POST')
    await update('Conversion queued')
  })
}
</script>

<template>
  <v-container
    fluid
    class="pa-4"
  >
    <h1 class="text-headline-small mb-3">Downloads</h1>
    <v-tabs
      v-model="tab"
      color="primary"
    >
      <v-tab value="history">Download history</v-tab>
      <v-tab value="tracked">Auto-fetch</v-tab>
      <v-tab value="health">Healthcheck</v-tab>
      <v-tab value="kcc">KCC</v-tab>
    </v-tabs>
    <v-divider />
    <v-alert
      v-if="error && !mode && !profile && !stopEntry"
      type="error"
      variant="tonal"
      class="my-3"
      >{{ error }}</v-alert
    >
    <v-alert
      v-if="overview?.workerError"
      type="warning"
      variant="tonal"
      class="my-3"
      >{{ overview.workerError }}</v-alert
    >
    <v-progress-linear
      v-if="working"
      indeterminate
      color="primary"
    />

    <template v-if="tab === 'history' || tab === 'tracked'">
      <div class="d-flex flex-wrap align-center ga-3 my-4">
        <v-btn
          v-if="tab === 'history'"
          color="primary"
          prepend-icon="i-mdi:download"
          :disabled="!overview?.providers.length || !overview?.libraries.length"
          @click="open('manual')"
          >Download manga</v-btn
        >
        <v-btn
          v-else
          color="primary"
          prepend-icon="i-mdi:plus"
          :disabled="!overview?.providers.length || !overview?.libraries.length"
          @click="open('track')"
          >Add auto-fetch</v-btn
        >
        <v-spacer />
        <v-text-field
          v-model="filter"
          label="Search manga"
          prepend-inner-icon="i-mdi:magnify"
          density="compact"
          hide-details
          style="max-width: 22rem"
        />
      </div>
      <v-data-table
        v-if="tab === 'history'"
        :headers="historyHeaders"
        :items="history"
        :mobile="xs"
        :sort-by="[{ key: 'downloadedAt', order: 'desc' }]"
      >
        <template #item.title="{ item }"
          ><v-btn
            variant="text"
            color="primary"
            @click="details = item"
            >{{ item.title }}</v-btn
          >
          <div class="text-body-small">{{ source(item.provider) }}</div></template
        >
        <template #item.downloadedAt="{ item }"
          >Chapter {{ item.chapter.number }}
          <div class="text-body-small">
            {{ stamp(item.downloadedAt) }} · {{ item.mode === 'auto' ? 'Auto-fetch' : 'Manual' }}
          </div></template
        >
        <template #item.file="{ item }"
          >{{ library(item.destination.libraryId) }}
          <div class="text-body-small">{{ item.conversion?.file ?? item.file }}</div></template
        >
        <template #item.mode="{ item }">{{
          matches(item)?.enabled ? 'Enabled' : matches(item) ? 'Paused' : 'Untracked'
        }}</template>
      </v-data-table>
      <v-data-table
        v-else
        :headers="trackHeaders"
        :items="tracked"
        :mobile="xs"
      >
        <template #item.title="{ item }"
          ><v-btn
            variant="text"
            color="primary"
            @click="details = item"
            >{{ item.title }}</v-btn
          >
          <div class="text-body-small">{{ source(item.provider) }}</div></template
        >
        <template #item.enabled="{ item }"
          ><v-chip
            size="small"
            variant="tonal"
            :color="item.enabled ? 'success' : undefined"
            >{{ item.enabled ? 'Enabled' : 'Paused' }}</v-chip
          ></template
        >
        <template #item.kccProfileId="{ item }"
          >{{ profileName(item.kccProfileId ?? state?.kcc.autoProfileId) }}
          <div class="text-body-small">
            {{ item.kccProfileId == null ? 'Default' : 'Manga override' }}
          </div></template
        >
        <template #item.expectedRelease="{ item }"
          >{{ item.expectedRelease ? stamp(item.expectedRelease) : 'Unknown' }}
          <div
            v-if="item.releaseBasis"
            class="text-body-small"
          >
            {{ item.releaseBasis }}
          </div></template
        >
        <template #item.nextCheck="{ item }">{{ stamp(item.nextCheck) }}</template>
        <template #item.lastCheck="{ item }"
          >{{ stamp(item.lastCheck) }}
          <div
            v-if="item.error"
            class="text-body-small text-error"
          >
            {{ item.error }}
          </div></template
        >
        <template #item.actions="{ item }"
          ><v-btn
            variant="text"
            :disabled="working"
            @click="check(item)"
            >Check now</v-btn
          ><v-btn
            variant="text"
            @click="open('edit', item)"
            >Edit</v-btn
          ></template
        >
      </v-data-table>
    </template>

    <template v-else-if="tab === 'health'">
      <div class="d-flex flex-wrap ga-3 my-4">
        <v-btn
          color="primary"
          prepend-icon="i-mdi:heart-pulse"
          :disabled="working"
          @click="
            perform(async () => {
              await refresh()
              notify('Healthcheck complete')
            })
          "
          >Run healthcheck</v-btn
        >
        <v-btn
          variant="text"
          :disabled="working || !state?.tracked.some((item) => item.enabled)"
          @click="checkAll"
          >Check all enabled manga</v-btn
        >
      </div>
      <v-card
        variant="outlined"
        title="Components"
        class="mb-4"
      >
        <v-list
          ><v-list-item
            v-for="(status, component) in overview?.health"
            :key="component"
            :title="component"
            ><template #append
              ><v-chip
                size="small"
                variant="tonal"
                :color="chip(status)"
                >{{ status }}</v-chip
              ></template
            ></v-list-item
          ></v-list
        >
      </v-card>
      <v-card
        variant="outlined"
        title="Latest auto-fetch results"
      >
        <v-list lines="two">
          <v-list-item
            v-for="item in state?.tracked"
            :key="item.id"
            :title="item.title"
            :subtitle="
              stamp(item.lastCheck) + ' · ' + (item.error ?? item.result ?? 'No check recorded yet')
            "
          >
            <template #append
              ><v-chip
                size="small"
                variant="tonal"
                :color="item.error ? 'error' : undefined"
                >{{
                  item.error
                    ? 'Failed'
                    : !item.enabled
                      ? 'Paused'
                      : item.lastCheck
                        ? 'Succeeded'
                        : 'Pending'
                }}</v-chip
              ></template
            >
          </v-list-item>
        </v-list>
      </v-card>
    </template>

    <template v-else-if="state">
      <v-card
        variant="outlined"
        title="Conversion defaults"
        class="my-4"
      >
        <v-card-text class="d-flex flex-wrap ga-4">
          <v-select
            :model-value="state.kcc.autoProfileId"
            :items="profileOptions"
            label="Auto-fetch downloads"
            :disabled="working"
            @update:model-value="saveKcc({ ...state!.kcc, autoProfileId: $event })"
          />
          <v-select
            :model-value="state.kcc.manualProfileId"
            :items="profileOptions"
            label="Manual downloads"
            :disabled="working"
            @update:model-value="saveKcc({ ...state!.kcc, manualProfileId: $event })"
          />
        </v-card-text>
      </v-card>
      <v-card
        variant="outlined"
        title="Profiles"
        class="mb-4"
      >
        <template #append
          ><v-btn
            variant="text"
            color="primary"
            prepend-icon="i-mdi:plus"
            @click="editProfile()"
            >New profile</v-btn
          ></template
        >
        <v-list>
          <v-list-item
            v-for="item in state.kcc.profiles"
            :key="item.id"
            :title="item.name"
            :subtitle="item.device + ' · ' + item.format + ' · ' + item.outputFolder"
          >
            <template #append
              ><v-btn
                variant="text"
                @click="editProfile(item)"
                >Edit</v-btn
              ></template
            >
          </v-list-item>
        </v-list>
      </v-card>
      <v-card
        variant="outlined"
        title="Conversion activity"
      >
        <v-list lines="two">
          <v-list-item
            v-for="item in activity"
            :key="item.id"
            :title="item.title + ' · Chapter ' + item.chapter.number"
            :subtitle="item.conversion?.profile.name + ' · ' + (item.conversion?.file ?? item.file)"
          >
            <template #append
              ><v-chip
                :color="chip(item.conversion?.status)"
                variant="tonal"
                size="small"
                >{{ item.conversion?.status }}</v-chip
              ></template
            >
            <div
              v-if="item.conversion?.error"
              class="text-error my-2"
            >
              {{ item.conversion.error }}
            </div>
            <v-btn
              v-if="item.conversion?.status === 'Failed'"
              variant="text"
              color="primary"
              :disabled="working"
              @click="retry(item)"
              >Retry conversion</v-btn
            >
          </v-list-item>
          <v-list-item
            v-if="!activity.length"
            title="No conversions recorded"
          />
        </v-list>
      </v-card>
    </template>

    <v-dialog
      :model-value="!!mode"
      :fullscreen="xs"
      max-width="700"
      scrollable
      @update:model-value="mode = undefined"
    >
      <v-card
        :title="
          mode === 'manual'
            ? 'Download manga'
            : mode === 'edit'
              ? 'Edit auto-fetch'
              : 'Add auto-fetch'
        "
      >
        <v-card-text>
          <form
            v-if="mode !== 'edit'"
            class="d-flex flex-wrap ga-3 align-center mb-4"
            @submit.prevent="doSearch"
          >
            <v-select
              v-model="provider"
              :items="overview?.providers"
              item-title="label"
              item-value="id"
              label="Source"
              hide-details
              @update:model-value="resetSearch"
            />
            <v-text-field
              v-model="query"
              label="Manga title or source ID"
              hide-details
            />
            <v-btn
              type="submit"
              :disabled="working || !query.trim()"
              >Search</v-btn
            >
          </form>
          <v-list
            v-if="results"
            nav
          >
            <v-list-item
              v-for="series in results"
              :key="series.id"
              :active="series.id === selected?.id"
              :title="series.title"
              @click="pick(series)"
            />
            <v-list-item
              v-if="!results.length"
              title="No results"
            />
          </v-list>
          <template v-if="selected">
            <v-text-field
              v-model="language"
              label="Chapter language code"
              :disabled="working"
              @change="mode === 'manual' && loadChapters()"
            />
            <template v-if="mode === 'manual'">
              <div class="d-flex align-center justify-space-between">
                <h2 class="text-title-medium">Chapters</h2>
                <v-btn
                  variant="text"
                  color="primary"
                  @click="
                    selectedChapters = chapters
                      .filter((chapter) => !alreadyDownloaded.has(chapter.id))
                      .slice(0, 100)
                      .map((chapter) => chapter.id)
                  "
                  >Select up to 100</v-btn
                >
              </div>
              <p class="text-body-small text-medium-emphasis">
                Page dimensions are measured before conversion and may take a moment to load.
              </p>
              <div
                v-for="chapter in chapters"
                :key="chapter.id"
              >
                <div class="d-flex align-center justify-space-between flex-wrap">
                  <v-checkbox
                    v-model="selectedChapters"
                    :value="chapter.id"
                    :disabled="alreadyDownloaded.has(chapter.id)"
                    :label="
                      'Chapter ' +
                      chapter.number +
                      (chapter.title ? ' · ' + chapter.title : '') +
                      (alreadyDownloaded.has(chapter.id) ? ' · Downloaded' : '')
                    "
                    hide-details
                    density="compact"
                  />
                  <v-btn
                    variant="text"
                    color="primary"
                    :aria-expanded="expanded === chapter.id"
                    @click="showDimensions(chapter)"
                    >{{ expanded === chapter.id ? 'Hide page details' : 'Page details' }}</v-btn
                  >
                </div>
                <template v-if="expanded === chapter.id">
                  <v-alert
                    v-if="dimensionError[chapter.id]"
                    type="error"
                    variant="tonal"
                    >{{ dimensionError[chapter.id] }}</v-alert
                  >
                  <v-table
                    v-else-if="dimensions[chapter.id]?.length"
                    density="compact"
                  >
                    <thead>
                      <tr>
                        <th scope="col">Page</th>
                        <th scope="col">Source dimensions</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr
                        v-for="page in dimensions[chapter.id]"
                        :key="page.number"
                      >
                        <td>{{ page.number }}</td>
                        <td>
                          {{
                            page.width && page.height
                              ? page.width + ' × ' + page.height + ' px'
                              : 'Unavailable'
                          }}
                        </td>
                      </tr>
                    </tbody>
                  </v-table>
                  <div
                    v-else-if="dimensions[chapter.id]"
                    class="text-medium-emphasis"
                  >
                    No source pages are available
                  </div>
                  <v-progress-linear
                    v-else
                    indeterminate
                  />
                </template>
                <v-divider />
              </div>
              <p
                v-if="!chapters.length && !working"
                class="text-medium-emphasis"
              >
                No chapters available for the selected language.
              </p>
            </template>
            <v-select
              v-model="libraryId"
              class="mt-4"
              :items="overview?.libraries"
              item-title="name"
              item-value="id"
              label="Destination library"
            />
            <v-text-field
              v-model="folder"
              label="Relative folder"
            />
            <v-select
              v-model="profileId"
              :items="mode === 'manual' ? manualOptions : autoOptions"
              :label="mode === 'manual' ? 'KCC profile for this download' : 'KCC profile for this manga'"
            />
            <template v-if="mode !== 'manual'">
              <v-text-field
                v-model="includes"
                label="Include chapter groups"
                hint="Comma-separated names"
              />
              <v-text-field
                v-model="excludes"
                label="Exclude chapter groups"
                hint="Comma-separated names"
              />
              <p
                v-if="mode === 'track'"
                class="text-body-small text-medium-emphasis"
              >
                Auto-fetch starts with newly released chapters.
              </p>
            </template>
          </template>
          <v-alert
            v-if="error"
            type="error"
            variant="tonal"
            >{{ error }}</v-alert
          >
        </v-card-text>
        <v-card-actions>
          <v-spacer /><v-btn
            variant="text"
            @click="mode = undefined"
            >Cancel</v-btn
          >
          <v-btn
            color="primary"
            :disabled="
              working ||
              !selected ||
              !libraryId ||
              !folder.trim() ||
              (mode === 'manual' && !selectedChapters.some((id) => !alreadyDownloaded.has(id)))
            "
            @click="submit"
            >{{
              mode === 'manual' ? 'Download' : mode === 'edit' ? 'Save' : 'Enable auto-fetch'
            }}</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-dialog
      :model-value="!!details"
      :fullscreen="xs"
      max-width="650"
      scrollable
      @update:model-value="details = undefined"
    >
      <v-card
        :title="details?.title"
        :subtitle="source(details?.provider ?? '')"
      >
        <v-card-text>
          <div
            v-if="detailTrack"
            class="d-flex flex-wrap ga-2"
          >
            <v-btn
              variant="text"
              :disabled="working"
              @click="toggle(detailTrack)"
              >{{ detailTrack.enabled ? 'Pause' : 'Resume' }}</v-btn
            >
            <v-btn
              variant="text"
              @click="editFromHistory(detailTrack)"
              >Edit settings</v-btn
            >
            <v-btn
              variant="text"
              @click="stopEntry = detailTrack"
              >Stop tracking</v-btn
            >
          </div>
          <v-list lines="two">
            <v-list-item
              v-for="item in detailRecords"
              :key="item.id"
              :title="'Chapter ' + item.chapter.number"
              :subtitle="
                (item.conversion?.file ?? item.file) +
                ' · ' +
                item.scan +
                ' · ' +
                stamp(item.downloadedAt)
              "
            />
            <v-list-item
              v-if="!detailRecords.length"
              title="No downloads recorded"
            />
          </v-list>
        </v-card-text>
        <v-card-actions
          ><v-spacer /><v-btn
            variant="text"
            @click="details = undefined"
            >Close</v-btn
          ></v-card-actions
        >
      </v-card>
    </v-dialog>

    <v-dialog
      :model-value="!!stopEntry"
      max-width="460"
      @update:model-value="stopEntry = undefined"
    >
      <v-card
        title="Stop tracking?"
        text="Automatic checks will stop. Downloaded chapters and history will be kept."
      >
        <v-alert
          v-if="error"
          type="error"
          variant="tonal"
          >{{ error }}</v-alert
        >
        <v-card-actions
          ><v-spacer /><v-btn @click="stopEntry = undefined">Cancel</v-btn
          ><v-btn
            color="error"
            :disabled="working"
            @click="stop"
            >Stop tracking</v-btn
          ></v-card-actions
        >
      </v-card>
    </v-dialog>

    <v-dialog
      :model-value="!!profile"
      :fullscreen="xs"
      max-width="650"
      scrollable
      @update:model-value="profile = undefined"
    >
      <v-card
        v-if="profile"
        title="KCC profile"
      >
        <v-card-text>
          <v-text-field
            v-model="profile.name"
            label="Profile name"
          />
          <v-text-field
            v-model="profile.device"
            label="KCC device profile code"
          />
          <v-select
            v-model="profile.format"
            :items="['EPUB', 'CBZ']"
            label="Output format"
          />
          <v-text-field
            v-model="profile.outputFolder"
            label="Library-relative output folder"
          />
          <v-select
            v-model="profile.extraArgs"
            :items="['--cropping=0', '--eraserainbow']"
            label="Extra KCC options"
            multiple
            chips
          />
          <v-checkbox
            v-model="profile.manga"
            label="Manga reading order"
            hide-details
          />
          <v-checkbox
            v-model="profile.keepOriginal"
            label="Keep original CBZ after conversion"
            hide-details
          />
          <v-alert
            v-if="error"
            type="error"
            variant="tonal"
            >{{ error }}</v-alert
          >
        </v-card-text>
        <v-card-actions
          ><v-spacer /><v-btn @click="profile = undefined">Cancel</v-btn
          ><v-btn
            color="primary"
            :disabled="working || !profile.name.trim() || !profile.device.trim()"
            @click="saveProfile"
            >Save profile</v-btn
          ></v-card-actions
        >
      </v-card>
    </v-dialog>
    <v-snackbar v-model="snackbar">{{ notice }}</v-snackbar>
  </v-container>
</template>

<route lang="yaml">
meta:
  requiresRole: ADMIN
</route>
