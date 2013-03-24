package og.checker.filewalker.checks;

import java.io.File;
import java.io.IOException;

import og.basics.gui.tracepanel.ITracer;
import og.basics.util.StringHelper;
import og.checker.filewalker.DirectoryInfos;

public class HiddenFileCheckerRepairer extends AbstractChecker {
	private boolean doRepair = false;

	public HiddenFileCheckerRepairer(ITracer tracer, boolean doRepair) {
		super(tracer);
		this.doRepair = doRepair;
	}

	@Override
	String getErrorText() {
		return "Hidden Files";
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
		if (dirInfo.hasHiddenFiles()) {
			if (doRepair)
				repair(dirInfo);
			return false;
		}
		return true;
	}

	private void repair(DirectoryInfos dirInfo) {
		for (File file : dirInfo.getHiddenFiles()) {
			try {
				getTracer().appendInfoText("[" + StringHelper.fillUpWithBlanksRight("Repairing Attributes (-H -S)...", ERROR_LENGTH) + "] " + file.getPath());
				Runtime.getRuntime().exec("attrib -H -S \"" + file.getPath() + "\"");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
