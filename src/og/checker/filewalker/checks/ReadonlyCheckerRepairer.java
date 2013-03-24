package og.checker.filewalker.checks;

import java.io.File;

import og.basics.gui.tracepanel.ITracer;
import og.basics.util.StringHelper;
import og.checker.filewalker.DirectoryInfos;

public class ReadonlyCheckerRepairer extends AbstractChecker {

	private boolean doRepair = false;

	public ReadonlyCheckerRepairer(ITracer tracer, boolean doRepair) {
		super(tracer);
		this.doRepair = doRepair;
	}

	@Override
	String getErrorText() {
		return "Files are read only";
	}

	@Override
	boolean performCheck(DirectoryInfos dirInfo) {
		if (dirInfo.hasReadonlyFiles()) {
			if (doRepair)
				repair(dirInfo);
			return false;
		}
		return true;
	}

	private void repair(DirectoryInfos dirInfo) {
		for (File file : dirInfo.getReadonlyFiles()) {
			getTracer().appendInfoText("[" + StringHelper.fillUpWithBlanksRight("Repairing Readonly-File", ERROR_LENGTH) + "] " + file.getPath());
			file.setWritable(true);
		}
	}

}
