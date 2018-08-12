package uk.gov.ida.hub.policy.controllogic;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class MultiCache<K, V> implements ConcurrentMap<K, V> {

    private final List<ConcurrentMap<K, V>> maps;

    public MultiCache(List<ConcurrentMap<K, V>> maps) {
        this.maps = maps;
    }

    @Override
    public int size() {
        return maps.stream().mapToInt(Map::size).sum()/maps.size();
    }

    @Override
    public boolean isEmpty() {
        return maps.stream().allMatch(Map::isEmpty);
    }

    @Override
    public boolean containsKey(Object key) {
        return maps.stream().anyMatch(m -> m.containsKey(key));
    }

    @Override
    public boolean containsValue(Object value) {
        return maps.stream().anyMatch(m -> m.containsValue(value));
    }

    @Override
    public V get(Object key) {
        return maps.stream().map(m -> m.get(key)).collect(toList()).get(0);
    }

    @Override
    public V put(K key, V value) {
        // As `put` has side-effects, we can't use the lazy `findFirst`
        return maps.stream().map(m -> m.put(key, value)).collect(toList()).get(0);
    }

    @Override
    public V remove(Object key) {
        return maps.stream().map(m -> m.remove(key)).collect(toList()).get(0);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        maps.forEach(cm -> cm.putAll(m));
    }

    @Override
    public void clear() {
        maps.forEach(Map::clear);
    }

    @Override
    public Set<K> keySet() {
        return maps.stream().flatMap(m -> m.keySet().stream()).collect(toSet());
    }

    @Override
    public Collection<V> values() {
        return maps.stream().flatMap(m -> m.values().stream()).collect(toList());
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return maps.stream().flatMap(m -> m.entrySet().stream()).collect(toSet());
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return maps.stream().map(m -> m.putIfAbsent(key, value)).collect(toList()).get(0);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return maps.stream().map(m -> m.remove(key, value)).collect(toList()).get(0);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return maps.stream().map(m -> m.replace(key, oldValue, newValue)).collect(toList()).get(0);
    }

    @Override
    public V replace(K key, V value) {
        return maps.stream().map(m -> m.replace(key, value)).collect(toList()).get(0);
    }
}
