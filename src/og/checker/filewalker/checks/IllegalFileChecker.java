package og.checker.filewalker.checks;

import java.io.File;

import og.basics.gui.tracepanel.ITracer;
import og.basics.util.StringHelper;
import og.checker.filewalker.DirectoryInfos;

public class IllegalFileChecker extends AbstractChecker {
	private boolean listFiles = false;

	public IllegalFileChecker(ITracer tracer, boolean listFiles) {
		super(tracer);
		this.listFiles = listFiles;
	}

	@Override
	String getErrorText() {
		return "Illegal Files";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * og.checker.filewalker.checks.AbstractChecker#performCheck(og.checker.
	 * filewalker.DirectoryInfos)
	 */
	@Override
	boolean performCheck(DirectoryInfos dirInfo) {
		if (dirInfo.hasIllegalFiles()) {
			if (listFiles) {
				for (File file : dirInfo.getIllegalFiles()) {
					getTracer().appendInfoText("[" + StringHelper.fillUpWithBlanksRight("Illegal File: ", ERROR_LENGTH) + "] " + file.getPath());
				}
			}
			return false;
		}
		return true;
	}

}
