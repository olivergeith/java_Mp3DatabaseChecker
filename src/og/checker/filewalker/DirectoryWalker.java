package og.checker.filewalker;

import java.io.File;
import java.io.FileFilter;
import java.util.Vector;

import javax.swing.JProgressBar;

/**
 * Diese Klasse Scannt einen Directory Tree recursiv durch und führt Checks aus!
 * 
 * @author Oliver
 * 
 */
public class DirectoryWalker {

	private final JProgressBar progressBar;
	private boolean isrunning = false;
	private final Vector<IChecker> checkerlist;
	private Thread t = null;
	private boolean stopnow = false;
	private int dirCounter = 0;
	private int maxDircount = 0;

	/**
	 * DirectoryWalker nach jedem Durchlauf immer neu erzeugen! Da der Thread
	 * hier drin nicht recycelt werden soll
	 * 
	 * @param progressBar
	 * @param folder
	 * @param tracerErrors
	 * @param tracerAlben
	 * @param config
	 */
	public DirectoryWalker(JProgressBar progressBar, final String folder, final Vector<IChecker> checkerlist) {
		this.progressBar = progressBar;
		this.checkerlist = checkerlist;
		isrunning = true;
		final File startDir = new File(folder);

		startThread(startDir);
	}

	/**
	 * Startet den Thread
	 * 
	 * @param startDir
	 */
	private void startThread(final File startDir) {
		if (t != null)
			stopThread();
		if (t == null) {
			t = new Thread(new Runnable() {
				public void run() {
					stopnow = false;
					isrunning = true;
					startWalk(startDir);
					isrunning = false;
				}
			});

			t.start();
		}
	}

	public synchronized void stopThread() {
		if (t != null) {
			stopnow = true;
			t = null;
		}
	}

	/**
	 * @return true, wenn Thread noch läuft
	 */
	public synchronized boolean isTreadRunning() {
		return isrunning;
	}

	/**
	 * @param startDir
	 */
	private void startWalk(final File startDir) {

		// Kopfzeile in den Tracern eintragben

		// Counter init
		dirCounter = 0;
		progressBar.setIndeterminate(true);
		progressBar.setString("Counting Directorys");
		int max = getMaxDirCount(startDir);
		progressBar.setIndeterminate(false);
		// ProgressBar (Grenzen setzen)
		progressBar.setMinimum(0);
		progressBar.setMaximum(max);

		// Den Verzeichnisbaum rekursiv traversieren...
		treeWalk(startDir);
	}

	/**
	 * Recursive Methode zum Durchlaufen durch den Dir-Baum
	 * 
	 * @param file
	 */
	private void treeWalk(final File file) {
		// Wir betrachten nur Directories
		if (file.isDirectory()) {
			DirectoryInfos dirInfo = new DirectoryInfos(file);
			progressBar.setValue(dirCounter++);
			progressBar.setString(dirInfo.getDirName());
			// Abbruch angefordert ?
			if (stopnow == true)
				return;

			// Loop über alle Checker
			for (IChecker checker : checkerlist) {
				checker.check(dirInfo);
			}

			// Weiter gehts über alle Unterverzueichnisse
			final File[] subDirs = dirInfo.getSubDirs();
			for (int i = 0; i < subDirs.length; i++) {
				treeWalk(subDirs[i]);
			}
		}
	}

	/**
	 * Rekursive Methode zum Ermittelt die Anzahl der zu scannenden
	 * Verzeichnisse!
	 * 
	 * @param file
	 */
	private void countDirs(final File file) {
		// Wir betrachten nur Directories
		if (file.isDirectory()) {
			if (stopnow == true)
				return;
			maxDircount++;
			final File[] subDirs = file.listFiles(new FileFilter() {

				public boolean accept(final File pathname) {
					return pathname.isDirectory();
				}
			});
			for (int i = 0; i < subDirs.length; i++) {
				countDirs(subDirs[i]);
			}
		}
	}

	/**
	 * Ermittelt die Anzahl der zu scannenden Verzeichnisse!
	 * 
	 * @param file
	 * @return
	 */
	private int getMaxDirCount(final File file) {
		maxDircount = 0;
		countDirs(file);
		return maxDircount;
	}

}