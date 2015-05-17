package edu.sjsu.cmpe.cache.repository;

import edu.sjsu.cmpe.cache.domain.Entry;
import net.openhft.chronicle.map.ChronicleMapBuilder;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


public class ChronicleMapCache implements CacheInterface {

    private final Map<Long, Entry> memoryMap;

    public ChronicleMapCache() throws IOException {
        File myFile = new File("ChronicleMapFileC.dat");
        memoryMap = ChronicleMapBuilder.of(Long.class, Entry.class).createPersistedTo(myFile);
    }

    @Override
    public Entry save(Entry newDataEntry) {
        checkNotNull(newDataEntry, "New Data Entry instance should not be null");
        memoryMap.putIfAbsent(newDataEntry.getKey(), newDataEntry);
        return newDataEntry;
    }

    @Override
    public Entry get(Long key) {
        checkArgument(key > 0,
                "Key was %s but expected greater than zero value", key);
        return memoryMap.get(key);
    }

    @Override
    public List<Entry> getAll() {
        return new ArrayList<Entry>(memoryMap.values());
    }
}