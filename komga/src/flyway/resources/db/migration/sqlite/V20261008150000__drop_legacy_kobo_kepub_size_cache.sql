-- Retire the fork-only Kobo size/hash cache after moving to upstream BOOK_PROJECTION.
-- Old values are deliberately not copied: they may refer to outdated source files.
-- Do not remove the original create-table migration: Flyway history must remain stable.
DROP TABLE IF EXISTS KOBO_KEPUB_SIZE_CACHE;
