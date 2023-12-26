package com.xymtop.tayi.store;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/26 3:06
 */
import lombok.Data;
import org.rocksdb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class RocksDBUtils {
    static {
        RocksDB.loadLibrary();
    }



    private RocksDB db;

    public RocksDBUtils() throws RocksDBException {

    }

    public void init(String dbPath) throws RocksDBException {
        Options options = new Options();
        options.setCreateIfMissing(true);
        db = RocksDB.open(options, dbPath);
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
