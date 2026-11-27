/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

import { createRouter, createWebHistory, type RouterOptions } from 'vue-router'
import { setupLayouts } from 'virtual:generated-layouts'
import { routes } from 'vue-router/auto-routes'
import { parse, stringify } from 'qs'

import { scrollerQuery, scrollMap } from '@/router/scroll'

// Keep the API/resource base independent from the NextUI navigation base.
const resourceBase = window.resourceBaseUrl || '/'
const routerBase = import.meta.env.PROD ? `${resourceBase}next/` : import.meta.env.BASE_URL
if (
  import.meta.env.PROD &&
  [resourceBase, resourceBase.slice(0, -1)].includes(window.location.pathname)
) {
  window.history.replaceState(
    window.history.state,
    '',
    routerBase + window.location.search + window.location.hash,
  )
}

const router = createRouter({
  history: createWebHistory(routerBase),
  routes: setupLayouts(routes),
  scrollBehavior(to, from, savedPosition) {
    // ignore redundant navigations (like useRouteQuery syncing URL parameters)
    // this prevents the page from jumping when filters or tabs are clicked
    if (to.path === from.path) {
      return false
    }

    // use savedPosition has an indicator that this is a popstate navigation
    // scroll position is saved in scrollMap
    const scrollTo = savedPosition ? (scrollMap.get(to.path) ?? 0) : 0
    return new Promise((resolve) => {
      void nextTick(() => {
        document.querySelector(scrollerQuery)?.scrollTo(0, scrollTo)
        resolve(false)
      })
    })
  },
  stringifyQuery: stringify,
  parseQuery: ((query: string) => parse(query)) as unknown as NonNullable<
    RouterOptions['parseQuery']
  >,
})

// Workaround for https://github.com/vitejs/vite/issues/11804
router.onError((err, to) => {
  if (err?.message?.includes?.('Failed to fetch dynamically imported module')) {
    if (!localStorage.getItem('vuetify:dynamic-reload')) {
      console.log('Reloading page to fix dynamic import error')
      localStorage.setItem('vuetify:dynamic-reload', 'true')
      location.assign(router.resolve(to.fullPath).href)
    } else {
      console.error('Dynamic import error, reloading page did not fix it', err)
    }
  } else {
    console.error(err)
  }
})

void router.isReady().then(() => {
  localStorage.removeItem('vuetify:dynamic-reload')
})

export default router
