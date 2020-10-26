CREATE TABLE IF NOT EXISTS dmp.data_point(
   metric text,
   source_id text,
   day date,
   offset time,
   tags map<text, text>,
   value double,
   PRIMARY KEY((metric, day), offset)
)
WITH gc_grace_seconds = 0
AND default_time_to_live = 31536000
AND memtable_flush_period_in_ms = 600
AND compaction = {'compaction_window_size': '14', 'compaction_window_unit': 'DAYS', 'class': 'org.apache.cassandra.db.compaction.TimeWindowCompactionStrategy'};

CREATE INDEX IF NOT EXISTS tags_idx ON dmp.data_point (ENTRIES(tags));
