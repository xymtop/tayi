package com.xymtop.tayi.store;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 3:06
 */
import org.rocksdb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RocksDBUtils {
    static {
        RocksDB.loadLibrary();
    }


    @Value("${db.filePath}")
    private String dbPath;

    private RocksDB db;

    public RocksDBUtils() throws RocksDBException {
        try (final Options options = new Options().setCreateIfMissing(true)) {
            db = RocksDB.open(options, dbPath);
        }
    }

    public void put(String key, String value) throws RocksDBException {
        db.put(key.getBytes(), value.getBytes());
    }

    public String get(String key) throws RocksDBException {
        byte[] value = db.get(key.getBytes());
        return value == null ? null : new String(value);
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }
}
