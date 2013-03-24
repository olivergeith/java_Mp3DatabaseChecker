package og.checker.filewalker;


public interface IChecker {
	/**
	 * Gibt eine Statistikzeile aus
	 */
	public void traceStatisticLine();

	/**
	 * Führt den Check durch
	 * 
	 * @param dirInfo
	 * @return
	 */
	public boolean check(DirectoryInfos dirInfo);
}
