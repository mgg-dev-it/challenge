package mggdevit.challengefolderstructure;

import java.util.ArrayList;
import java.util.List;

public class TreeItem {

	private String name;
	private boolean writable;
	private boolean readable;
	private List<TreeItem> children;

	public TreeItem(String name) {
		this.name = name;
		writable = false;
		readable = false;
		children = new ArrayList<>();
	}

	public String getName() {
		return (name);
	}

	public boolean hasChildren() {
		return (children.size() > 0);
	}

	public List<TreeItem> getChildren() {
		return (children);
	}

	public void addChild(TreeItem child) {
		children.add(child);
	}

	public void removeChild(int index) {
		children.remove(index);
	}

	public void removeChild(TreeItem child) {
		children.remove(child);
	}
	
	public TreeItem setReadable(boolean b) {
		readable = b;
		return (this);
	}

	public boolean isReadable() {
		return (readable);
	}

	public TreeItem setWritable(boolean b) {
		writable = b;
		return (this);
	}

	public boolean isWritable() {
		return (writable);
	}

	public static TreeItem createWritableTreeItem(String name) {
		return (new TreeItem(name).setWritable(true));
	}

	public static TreeItem createReadableTreeItem(String name) {
		return (new TreeItem(name).setReadable(true));
	}

	public static TreeItem createTreeItem(String name) {
		return (new TreeItem(name));
	}

}
