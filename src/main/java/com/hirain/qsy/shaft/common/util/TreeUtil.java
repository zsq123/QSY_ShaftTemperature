package com.hirain.qsy.shaft.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hirain.qsy.shaft.model.Menu;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月9日 下午5:38:31
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月9日 changwei.zheng@hirain.com 1.0 create file
 */
public class TreeUtil {

	List<Menu> returnList = new ArrayList<>();

	public List<Menu> getChildTreeObjects(List<Menu> list, int parentId) {

		List<Menu> returnList = new ArrayList<>();
		Iterator<Menu> iterator = list.iterator();

		while (iterator.hasNext()) {
			Menu t = iterator.next();
			if (t.getParentId() == parentId) {
				this.recursionFn(list, t);
				returnList.add(t);
			}
		}

		return returnList;
	}

	private void recursionFn(List<Menu> list, Menu t) {

		List<Menu> childList = this.getChildList(list, t);
		t.setChildren(childList);
		Iterator<Menu> arg4 = childList.iterator();

		while (true) {
			Menu tChild;
			do {
				if (!arg4.hasNext()) {
					return;
				}

				tChild = (Menu) arg4.next();
			} while (!this.hasChild(list, tChild));

			Iterator<Menu> it = childList.iterator();

			while (it.hasNext()) {
				Menu n = (Menu) it.next();
				this.recursionFn(list, n);
			}
		}
	}

	private List<Menu> getChildList(List<Menu> list, Menu t) {

		List<Menu> tlist = new ArrayList<>();
		Iterator<Menu> it = list.iterator();

		while (it.hasNext()) {
			Menu n = (Menu) it.next();
			if (n.getParentId().equals(t.getId())) {
				tlist.add(n);
			}
		}

		return tlist;
	}

	public List<Menu> getChildTreeObjects(List<Menu> list, int typeId, String prefix) {

		if (list == null) {
			return null;
		} else {
			Iterator<Menu> iterator = list.iterator();

			while (iterator.hasNext()) {
				Menu node = (Menu) iterator.next();
				if (node.getParentId().equals(Long.valueOf(typeId))) {
					this.recursionFn(list, node, prefix);
				}
			}

			return this.returnList;
		}
	}

	private void recursionFn(List<Menu> list, Menu node, String p) {

		List<Menu> childList = this.getChildList(list, node);
		if (this.hasChild(list, node)) {
			this.returnList.add(node);
			Iterator<Menu> it = childList.iterator();

			while (it.hasNext()) {
				Menu n = (Menu) it.next();
				n.setName(p + n.getName());
				this.recursionFn(list, n, p + p);
			}
		} else {
			this.returnList.add(node);
		}

	}

	private boolean hasChild(List<Menu> list, Menu t) {

		return this.getChildList(list, t).size() > 0;
	}

	public void main(String[] args) {

	}
}