package io.github.thiagolvlsantos.git.commons.properties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PropertyUtils {

	public static List<Properties> load(String name) throws IOException {
		List<Properties> result = new LinkedList<>();
		List<String> names = new LinkedList<>(getDefault(name));
		names.add(name);
		for (String n : names) {
			for (URL u : resources(n)) {
				try (InputStream in = u.openStream()) {
					Properties p = new Properties();
					p.load(in);
					result.add(p);
				}
			}
		}
		sort(result);
		return result;
	}

	public static List<String> getDefault(String resource) {
		int pos = resource.lastIndexOf('.');
		String default_short = resource.substring(0, pos) + "_df" + resource.substring(pos);
		String default_long = resource.substring(0, pos) + "_default" + resource.substring(pos);
		return Arrays.asList(default_short, default_long);
	}

	public static List<URL> resources(String... resources) throws IOException {
		List<URL> result = new LinkedList<URL>();
		if (resources != null) {
			for (String resource : resources) {
				Enumeration<URL> urls = ClassLoader.getSystemResources(resource);
				while (urls.hasMoreElements()) {
					result.add(urls.nextElement());
				}
			}
		}
		return result;
	}

	public static void sort(List<Properties> props) {
		Collections.sort(props, new Comparator<Properties>() {
			@Override
			public int compare(Properties o1, Properties o2) {
				String key = "index";
				double index1 = o1.containsKey(key) ? Double.valueOf((String) o1.get(key)) : 0.0;
				double index2 = o2.containsKey(key) ? Double.valueOf((String) o2.get(key)) : 0.0;
				return index1 < index2 ? -1 : (index2 < index1 ? 1 : 0);
			}
		});
	}

	public static Properties merged(String name) throws IOException {
		Properties result = new Properties();
		for (Properties p : load(name)) {
			result.putAll(p);
		}
		result.remove("index");
		return result;
	}
}
