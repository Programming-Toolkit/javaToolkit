package javaToolkit.lib.utils;

import java.util.*;

public class CollectionUtil {

	/**
	 * https://stackoverflow.com/a/109389/4315608 asc or desc
	 * 
	 * @param map
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> Map<K, V> sortByValue(Map<K, V> map, String order) {

		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Object>() {
			@SuppressWarnings("unchecked")
			public int compare(Object o1, Object o2) {
				if (order.equals("asc")) {
					return ((Comparable<V>) ((Map.Entry<K, V>) (o1)).getValue())
							.compareTo(((Map.Entry<K, V>) (o2)).getValue());
				} else if (order.equals("desc")) {
					return ((Comparable<V>) ((Map.Entry<K, V>) (o2)).getValue())
							.compareTo(((Map.Entry<K, V>) (o1)).getValue());
				} else {
					System.out.println("No such order!");
					System.exit(0);
					return 0;
				}
			}
		});

		Map<K, V> result = new LinkedHashMap<>();
		for (Iterator<Map.Entry<K, V>> it = list.iterator(); it.hasNext();) {
			Map.Entry<K, V> entry = (Map.Entry<K, V>) it.next();
			result.put(entry.getKey(), entry.getValue());
		}

		return result;

	}

	public static Set<Object> mergeMultipleSet(List<Set<Object>> multiSet) {
		Set<Object> mergeSet = new HashSet<>();
		for (Set<Object> set : multiSet) {
			mergeSet.addAll(set);
		}
		return mergeSet;
	}

}
