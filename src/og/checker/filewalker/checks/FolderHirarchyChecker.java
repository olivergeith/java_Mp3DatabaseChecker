package og.checker.filewalker.checks;

import og.basics.gui.tracepanel.ITracer;
import og.checker.filewalker.DirectoryInfos;

public class FolderHirarchyChecker extends AbstractChecker {

	public FolderHirarchyChecker(ITracer tracer) {
		super(tracer);
	}

	@Override
	String getErrorText() {

		return "Folder Hirarchy Error";
	}

	@Override
	boolean performCheck(DirectoryInfos dirInfo) {
		return dirInfo.isFolderHirarchieOK();
	}

}
