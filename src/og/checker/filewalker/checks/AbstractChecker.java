package og.checker.filewalker.checks;

import og.basics.gui.tracepanel.ITracer;
import og.basics.util.StringHelper;
import og.checker.filewalker.DirectoryInfos;
import og.checker.filewalker.IChecker;

public abstract class AbstractChecker implements IChecker {
	protected static final int ERROR_LENGTH = 35;
	private int count = 0;
	/**
	 * Definiert, ob ein Check überhaut gemacht werden soll
	 */
	private final ITracer tracer;

	/**
	 * @return Der ITracer
	 */
	protected ITracer getTracer() {
		return tracer;
	}

	/**
	 * @return Anzahl Fehler
	 */
	protected int getCount() {
		return count;
	}

	/**
	 * @param doCheck
	 *            definiert ob ein Check überhaupt ausgeführt werden soll
	 */
	public AbstractChecker(ITracer tracer) {
		this.tracer = tracer;
	}

	public boolean check(DirectoryInfos dirInfo) {
		if (!performCheck(dirInfo)) {
			tracer.appendErrorText(createErrorLine(dirInfo));
			count++;
			return false;
		}
		return true;

	}

	/**
	 * Erzeugt die Fehlermeldungszeile
	 * 
	 * @param dirInfo
	 * @return
	 */
	private String createErrorLine(DirectoryInfos dirInfo) {
		String error = getErrorText();
		if (error.length() > 0) {
			error = StringHelper.fillUpWithBlanksRight(error, ERROR_LENGTH);
			return "[" + error + "] " + dirInfo.getDirPath();
		} else
			return dirInfo.getDirPath();
	}

	/**
	 * @param dirInfo
	 * @return false if check not successfull
	 */
	abstract boolean performCheck(DirectoryInfos dirInfo);

	/**
	 * @return the Error-Text Wenn Leerstring, dann wird als Error nur das
	 *         Verzeichnis ausgegeben (z.b. bei der AlbumListCreator)
	 */
	abstract String getErrorText();

	/**
	 * Erzeugt die Statistik <br>
	 * Kann bei Bedarf überschrieben werden
	 * 
	 * @return
	 */
	String createStatisticLine() {
		return getCount() + " " + getErrorText();
	}

	/**
	 * Output der Statistikline
	 */
	public void traceStatisticLine() {
		if (getCount() > 0)
			tracer.appendInfoText("Found: " + createStatisticLine());
		else
			tracer.appendSuccessText("Found: " + createStatisticLine());
	}

}
