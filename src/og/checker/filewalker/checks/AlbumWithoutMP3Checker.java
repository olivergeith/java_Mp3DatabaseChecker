package og.checker.filewalker.checks;

import og.basics.gui.tracepanel.ITracer;
import og.checker.filewalker.DirectoryInfos;

public class AlbumWithoutMP3Checker extends AbstractChecker {

	public AlbumWithoutMP3Checker(ITracer tracer) {
		super(tracer);
	}

	@Override
	String getErrorText() {
		return "Endnode contains no MP3's";
	}

	@Override
	boolean performCheck(DirectoryInfos dirInfo) {
		if (!dirInfo.hasSubDirs() && !dirInfo.isMusicDir())
			return false;
		return true;
	}

}
