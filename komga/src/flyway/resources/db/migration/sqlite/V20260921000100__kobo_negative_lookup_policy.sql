-- Legacy negative results cannot attest which storefronts/edition policy were checked.
-- NULL forces one fresh lookup; subsequent negatives record their actual policy.
ALTER TABLE KOBO_PRODUCT_MAPPING ADD COLUMN LOOKUP_POLICY varchar;