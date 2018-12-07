package utils;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

public class UiTestUtils {

	public static List<Component> getAllComponents(final Container c) {
		Component[] comps = c.getComponents();
		List<Component> compList = new ArrayList<Component>();
		for (Component comp : comps) {
			compList.add(comp);
			if (comp instanceof Container)
				compList.addAll(getAllComponents((Container) comp));
		}
		return compList;
	}
	
	public static <T> List<T> getObjects(Container c, Class<T> clazz) {
		ArrayList<T> arr = new ArrayList<>();
		for (Component comp : getAllComponents(c)) {
			if (clazz.isInstance(comp))
				arr.add(clazz.cast(comp));
		}
		return arr;
	}

}
