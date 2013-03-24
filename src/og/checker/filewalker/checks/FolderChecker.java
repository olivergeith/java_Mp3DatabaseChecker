package og.checker.filewalker.checks;

import og.basics.gui.tracepanel.ITracer;
import og.checker.filewalker.DirectoryInfos;

public class FolderChecker extends AbstractChecker {

	public FolderChecker(ITracer tracer) {
		super(tracer);
	}

	@Override
	String getErrorText() {
		return "Folder.jpg missing";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * og.checker.filewalker.checks.AbstractCheck#performCheck(og.checker.filewalker
	 * .DirectoryInfos)
	 */
	@Override
	boolean performCheck(DirectoryInfos dirInfo) {
		if (dirInfo.isMusicDir() && !dirInfo.hasFolderJpgs())
			return false;
		return true;
	}
}
