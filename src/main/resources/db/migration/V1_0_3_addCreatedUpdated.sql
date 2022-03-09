ALTER TABLE sword_data.bible_topic
  ADD COLUMN created_by varchar(50) NOT NULL;
ALTER TABLE sword_data.bible_topic
  ADD COLUMN created_at timestamp;
ALTER TABLE sword_data.bible_topic
  ADD COLUMN updated_by varchar(50) NOT NULL;
ALTER TABLE sword_data.bible_topic
  ADD COLUMN updated_at timestamp;

ALTER TABLE sword_data.bible_topic
  ADD CONSTRAINT fk_created_by_notes_user
    FOREIGN KEY (created_by)
      REFERENCES sword_data.notes_user (username)

ALTER TABLE sword_data.bible_topic
  ADD CONSTRAINT fk_updated_by_notes_user
    FOREIGN KEY (updated_by)
      REFERENCES sword_data.notes_user (username)
