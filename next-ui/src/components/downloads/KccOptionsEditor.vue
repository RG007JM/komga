<script setup lang="ts">
import { computed } from 'vue'
import type { KccOption } from '@/api/downloads'

const props = defineProps<{ modelValue: string[]; options: KccOption[] }>()
const emit = defineEmits<{ 'update:modelValue': [value: string[]] }>()
const groups = computed(() =>
  ['Main', 'Processing', 'Output settings', 'Custom profile'].map((group) => ({
    name: group,
    options: props.options.filter((option) => option.group === group),
  })),
)
const choices: { [key: string]: { title: string; value: string }[] } = {
  '--splitter': [
    { title: 'Split', value: '0' },
    { title: 'Rotate', value: '1' },
    { title: 'Both', value: '2' },
  ],
  '--cropping': [
    { title: 'Disabled', value: '0' },
    { title: 'Margins', value: '1' },
    { title: 'Margins and page numbers', value: '2' },
  ],
  '--interpanelcrop': [
    { title: 'Disabled', value: '0' },
    { title: 'Horizontal', value: '1' },
    { title: 'Both', value: '2' },
  ],
  '--batchsplit': [
    { title: 'Disabled', value: '0' },
    { title: 'Automatic', value: '1' },
    { title: 'Each subdirectory', value: '2' },
  ],
  '--metadatatitle': [
    { title: 'Filename', value: '0' },
    { title: 'Combined title', value: '1' },
    { title: 'Metadata title only', value: '2' },
  ],
  '--keepcomicinfo': [
    { title: 'No', value: '0' },
    { title: 'Yes', value: '1' },
  ],
}
function label(flag: string) {
  const name = flag.slice(2).replaceAll('-', ' ')
  return name.charAt(0).toUpperCase() + name.slice(1)
}
function enabled(flag: string) {
  return props.modelValue.includes(flag)
}
function value(flag: string) {
  return props.modelValue.find((arg) => arg.startsWith(flag + '='))?.slice(flag.length + 1) ?? null
}
function set(flag: string, value: string | null | boolean) {
  const next = props.modelValue.filter((arg) => arg !== flag && !arg.startsWith(flag + '='))
  if (value === true) next.push(flag)
  else if (typeof value === 'string' && value !== '') next.push(flag + '=' + value)
  emit('update:modelValue', next)
}
</script>

<template>
  <v-expansion-panels
    variant="accordion"
    class="mb-4"
  >
    <v-expansion-panel
      v-for="group in groups"
      :key="group.name"
      :title="group.name"
    >
      <v-expansion-panel-text>
        <template
          v-for="option in group.options"
          :key="option.flag"
        >
          <v-checkbox
            v-if="option.kind === 'flag'"
            :model-value="enabled(option.flag)"
            :label="label(option.flag)"
            :hint="option.description"
            persistent-hint
            density="compact"
            @update:model-value="set(option.flag, Boolean($event))"
          />
          <v-select
            v-else-if="choices[option.flag]"
            :model-value="value(option.flag)"
            :items="choices[option.flag]"
            :label="label(option.flag)"
            :hint="option.description"
            placeholder="KCC default"
            persistent-hint
            clearable
            @update:model-value="set(option.flag, $event ?? null)"
          />
          <v-text-field
            v-else
            :model-value="value(option.flag) ?? ''"
            :label="label(option.flag)"
            :hint="option.description"
            :type="option.kind === 'integer' || option.kind === 'decimal' ? 'number' : 'text'"
            :step="option.kind === 'decimal' ? 'any' : undefined"
            placeholder="KCC default"
            persistent-hint
            clearable
            @update:model-value="set(option.flag, $event ?? null)"
          />
        </template>
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>
