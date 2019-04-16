package mggdevit.challengefolderstructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class ChallengeFolderStructure {

	private List<String> readableFolders = new ArrayList<>();
	private List<String> writableFolders = new ArrayList<>();
	private TreeItem root = TreeItem.createTreeItem("");
	private final static String path_separator = "/";

	private void createFolderLists() {
		readableFolders.add("/a");
		readableFolders.add("/a/b");
		readableFolders.add("/a/b/d");
		readableFolders.add("/a/b/e");
		readableFolders.add("/a/b/f");

		writableFolders.add("/a/x/d");
		writableFolders.add("/a/y/d");
		writableFolders.add("/a/b/d");
	}

	private TreeItem addTreeItemIfNotExists(TreeItem ti, String name, boolean readable, boolean writable,
			boolean leaf) {
		for (int i = 0; i < ti.getChildren().size(); i++) {
			if (ti.getChildren().get(i).getName().equalsIgnoreCase(name)) {
				if (leaf) {
					if (readable) {
						ti.getChildren().get(i).setReadable(true);
					}
					if (writable) {
						ti.getChildren().get(i).setWritable(true);
					}
				}
				return (ti.getChildren().get(i));
			}
		}
		TreeItem tiNew = TreeItem.createTreeItem(name);
		if (leaf) {
			if (readable) {
				tiNew.setReadable(true);
			}
			if (writable) {
				tiNew.setWritable(true);
			}
		}
		ti.addChild(tiNew);
		return (tiNew);
	}

	private void buildTheTreeFromPath(String path, boolean readable, boolean writable) {
		if (path.startsWith(path_separator)) {
			path = path.substring(path_separator.length());
		}
		if (path.endsWith(path_separator)) {
			path = path.substring(0, path.length() - path_separator.length());
		}
		String folders[] = path.split(path_separator);
		TreeItem current = root;
		for (int i = 0; i < folders.length; i++) {
			current = addTreeItemIfNotExists(current, folders[i], readable, writable, i == folders.length - 1);
		}

	}

	private void loadFolderListsIntoTree() {
		readableFolders.forEach(path -> buildTheTreeFromPath(path, true, false));
		writableFolders.forEach(path -> buildTheTreeFromPath(path, false, true));
	}

	private boolean hasWritableChild(TreeItem ti) {
		if (!ti.hasChildren()) {
			return (ti.isWritable());
		}
		boolean bHasWritableChild = false;
		for (int i = 0; i < ti.getChildren().size(); i++) {
			bHasWritableChild = bHasWritableChild || hasWritableChild(ti.getChildren().get(i));
		}
		return(bHasWritableChild);
	}

	private void eliminateUnreachableFoldersFromTheTree() {
		eliminateUnreachableFoldersFromTheTree(root);
	}

	private void eliminateUnreachableFoldersFromTheTree(TreeItem ti) {
		for (int i = ti.getChildren().size() - 1; i > -1; i--) {
			if (!ti.getChildren().get(i).isReadable() || (ti.getChildren().get(i).isReadable() && !hasWritableChild(ti.getChildren().get(i)))) {
				ti.removeChild(i);
			}
		}
		ti.getChildren().forEach(child -> eliminateUnreachableFoldersFromTheTree(child));
	}

	private void dumpTheTree() {
		dumpTheTree(root, "");
	}

	private void dumpTheTree(TreeItem ti, String parentpath) {
//		String thispath = (ti == root ? "" : parentpath + path_separator + ti.getName());
		String thispath = (ti == root ? ""
				: parentpath + path_separator + ti.getName() + "(" + (ti.isReadable() ? "R" : "")
						+ (ti.isWritable() ? "W" : "") + ")");
		if (ti.hasChildren()) {
			ti.getChildren().forEach(child -> dumpTheTree(child, thispath));
		} else {
			System.out.println(thispath);
		}
	}

	public static void main(String[] args) {
		ChallengeFolderStructure cfs = new ChallengeFolderStructure();
		System.out.println("create ...");
		cfs.createFolderLists();
		System.out.println("load ...");
		cfs.loadFolderListsIntoTree();
		System.out.println("dump before eliminating ...");
		cfs.dumpTheTree();
		System.out.println("eliminate ...");
		cfs.eliminateUnreachableFoldersFromTheTree();
		System.out.println("dump after eliminating ...");
		cfs.dumpTheTree();
	}
}
