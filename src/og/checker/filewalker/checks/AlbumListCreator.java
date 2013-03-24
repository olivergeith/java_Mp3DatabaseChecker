package og.checker.filewalker.checks;

import java.io.File;
import java.text.NumberFormat;

import og.basics.gui.tracepanel.ITracer;
import og.checker.filewalker.DirectoryInfos;

public class AlbumListCreator extends AbstractChecker {

	private int countMp3 = 0;
	private long size = 0;

	public AlbumListCreator(ITracer tracer) {
		super(tracer);
	}

	@Override
	String getErrorText() {
		return "";
	}

	@Override
	boolean performCheck(DirectoryInfos dirInfo) {
		if (dirInfo.isMusicDir()) {
			countMp3 += dirInfo.getMusicFiles().length;
			for (File file : dirInfo.getAllFiles()) {
				size += file.length();
			}
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see og.checker.filewalker.checks.AbstractChecker#createStatisticLine()
	 */
	@Override
	String createStatisticLine() {
		return getCount() + " Alben with " + countMp3 + " Mp3's (" + NumberFormat.getInstance().format(size) + " Byte)";
	}

}
