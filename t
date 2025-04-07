SELECT con.name AS constraint_name, col.name AS column_name
FROM sysobjects tab
JOIN sysconstraints con ON tab.id = con.tableid
JOIN sysindexes ind ON con.constrid = ind.id
JOIN syscolumns col ON tab.id = col.id
WHERE tab.name = 'your_table_name'
  AND (con.status & 2) = 2  -- Primary key
  AND col.colid IN (
      SELECT key1 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key2 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key3 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key4 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key5 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key6 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key7 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key8 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key9 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key10 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key11 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key12 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key13 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key14 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key15 FROM sysindexes WHERE id = tab.id AND indid = 1
      UNION
      SELECT key16 FROM sysindexes WHERE id = tab.id AND indid = 1
  )
  AND col.name = 'your_column_name';