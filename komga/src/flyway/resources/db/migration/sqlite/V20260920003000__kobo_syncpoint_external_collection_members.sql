-- Snapshot per-user Kobo Store membership together with each ReadList SyncPoint.
-- A change to this set must generate a ChangedTag even when Komga's ReadList is unchanged.
CREATE TABLE SYNC_POINT_EXTERNAL_COLLECTION_MEMBER (
    SYNC_POINT_ID varchar NOT NULL,
    READLIST_ID varchar NOT NULL,
    REVISION_ID varchar NOT NULL,
    PRIMARY KEY (SYNC_POINT_ID, READLIST_ID, REVISION_ID),
    FOREIGN KEY (SYNC_POINT_ID) REFERENCES SYNC_POINT(ID) ON DELETE CASCADE
);
