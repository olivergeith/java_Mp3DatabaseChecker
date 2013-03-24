package og.checker.filewalker.checks;

import og.basics.gui.tracepanel.ITracer;
import og.checker.filewalker.DirectoryInfos;

public class IllegalSubDirChecker extends AbstractChecker {

	public IllegalSubDirChecker(ITracer tracer) {
		super(tracer);
		// TODO Auto-generated constructor stub
	}

	@Override
	String getErrorText() {
		return "Illegal SubDirs";
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
		if (dirInfo.isMusicDir() && dirInfo.hasSubDirs())
			return false;
		return true;
	}

}
