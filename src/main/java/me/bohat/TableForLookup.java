package me.bohat;

import org.rocksdb.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TableForLookup {
    public static ColumnFamilyHandle cfHandle;
    public static RocksDB rocksDB;
    public static void createTable(String cfdbPath) throws RocksDBException {
        final ColumnFamilyOptions cfOpts = new ColumnFamilyOptions().optimizeUniversalStyleCompaction();
        String cfName = "COLUMN_FAMILY2";
        final List cfDescriptors = Arrays.asList(
                new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, cfOpts),
                new ColumnFamilyDescriptor(cfName.getBytes(), cfOpts)
        );
        List<ColumnFamilyHandle> cfHandles = new ArrayList<>();
        final DBOptions dbOptions = new DBOptions().setCreateIfMissing(true).setCreateMissingColumnFamilies(true);
        rocksDB = RocksDB.open(dbOptions, cfdbPath, cfDescriptors, cfHandles);
        cfHandle = cfHandles.stream().filter(x -> {
            try {
                return (new String(x.getName())).equals(cfName);
            } catch (RocksDBException e) {
                return false;
            }
        }).collect(Collectors.toList()).get(0);
    }
    public static void testGetData(ColumnFamilyHandle cfHandle,RocksDB rocksDB,long numberOfData) throws RocksDBException {
        System.out.println("Size: " + rocksDB.getColumnFamilyMetaData(cfHandle).size());
        for (long i = 0; i < numberOfData ; i++) {
            String counter = i + "";
            byte[] getValue = rocksDB.get(cfHandle, counter.getBytes());

            assert getValue != null;
//            System.out.println("get Value : " + new String(getValue));
        }
    }
    public static void assignValue(ColumnFamilyHandle cfHandle,RocksDB rocksDB,long numberOfData) throws RocksDBException {
        for (long i = 0; i < numberOfData ; i++) {
            String counter = i + "";
            rocksDB.put(cfHandle, (counter).getBytes(), "{\"id\": \"%VALUE%\", \"name\": \"%VALUE%\", \"description\": \"%VALUE%\", \"price\": \"%VALUE%\"}".replaceAll("%VALUE%",counter).getBytes());
        }
    }
}
