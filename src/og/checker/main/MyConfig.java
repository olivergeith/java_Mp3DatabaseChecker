package og.checker.main;

import og.basics.util.KPropertyReader;

public class MyConfig {

	private static final String SETTINGS_PROPERTIES = "settings.properties";
	public static final KPropertyReader READER = new KPropertyReader(SETTINGS_PROPERTIES, true);

	public boolean bFolderMissing = true;
	public boolean bIllegalFiles = true;
	public boolean bIllegalFilesList = false;
	public boolean bHiddenFiles = true;
	public boolean bHiddenFilesRepair = false;
	public boolean bIllegalSubdirs = true;
	public boolean bNoMp3InFolder = true;
	public boolean bAlbumFolderNameHirarchy = true;
	public boolean bGenerateAlbenList = false;
	public boolean bGenerateAlbenListWhatsNew = false;
	public boolean bID3V2ExistCheck = false;
	public boolean bID3V2ExistRepair = false;
	public boolean bID3AlbumNameCheck = false;
	public boolean bID3AlbumArtistCheck = false;
	public boolean bID3AlbumArtistRepair = false;
	public boolean bID3CoverCheck = false;
	public boolean bID3CoverRepair = false;
	public boolean bID3BitrateCheck = false;
	public boolean bReadonlyCheck = true;
	public boolean bReadonlyRepair = false;
	public boolean bID3TagConsistencyCheck = false;
	public boolean bID3GenreCheck = false;
	public String startPath = "c:\\";
	public long bitrateBorder = 128;
	public long maxdays = 7;

	/**
	 * Konstruktor...liest automatisch die eventuell vorhande Datei ein (ein
	 * extra read() ist also überflüssig!
	 */
	public MyConfig() {
		read();
	}

	/**
	 * Schreibt die Settings in die Datei "settings.properties" <br>
	 * Repair-Booleans werden nicht geschrieben oder gelesen!
	 */
	public void write() {
		System.out.println("Saving Config...");
		READER.writeProperty("startPath", startPath);
		READER.writeBooleanProperty("bFolderMissing", bFolderMissing);
		READER.writeBooleanProperty("bIllegalFiles", bIllegalFiles);
		READER.writeBooleanProperty("bIllegalFilesList", bIllegalFilesList);
		READER.writeBooleanProperty("bHiddenFiles", bHiddenFiles);
		READER.writeBooleanProperty("bIllegalSubdirs", bIllegalSubdirs);
		READER.writeBooleanProperty("bNoMp3InFolder", bNoMp3InFolder);
		READER.writeBooleanProperty("bAlbumFolderNameHirarchyOK", bAlbumFolderNameHirarchy);
		READER.writeBooleanProperty("bGenerateAlbenList", bGenerateAlbenList);
		READER.writeBooleanProperty("bGenerateAlbenListWhatsNew", bGenerateAlbenListWhatsNew);
		READER.writeLongProperty("maxdays", maxdays);
		READER.writeBooleanProperty("bID3V2ExistCheck", bID3V2ExistCheck);
		READER.writeBooleanProperty("bID3AlbumArtistCheck", bID3AlbumArtistCheck);
		READER.writeBooleanProperty("bID3CoverCheck", bID3CoverCheck);
		READER.writeBooleanProperty("bID3BitrateCheck", bID3BitrateCheck);
		READER.writeLongProperty("bitrateBorder", bitrateBorder);
		READER.writeBooleanProperty("bID3TagConsistencyCheck", bID3TagConsistencyCheck);
		READER.writeBooleanProperty("bID3GenreCheck", bID3GenreCheck);
		READER.writeBooleanProperty("bReadonlyCheck", bReadonlyCheck);
		READER.writeBooleanProperty("bID3AlbumNameCheck", bID3AlbumNameCheck);
	}

	/**
	 * Liest die Settings aus der Datei "settings.properties" <br>
	 * Repair-Booleans werden nicht geschrieben oder gelesen!
	 */
	public void read() {
		System.out.println("Reading Config...");
		startPath = READER.readProperty("startPath", "c:\\");
		bFolderMissing = READER.readBooleanProperty("bFolderMissing", true);
		bIllegalFiles = READER.readBooleanProperty("bIllegalFiles", true);
		bIllegalFilesList = READER.readBooleanProperty("bIllegalFilesList", false);
		bHiddenFiles = READER.readBooleanProperty("bHiddenFiles", true);
		bIllegalSubdirs = READER.readBooleanProperty("bIllegalSubdirs", true);
		bNoMp3InFolder = READER.readBooleanProperty("bNoMp3InFolder", true);
		bAlbumFolderNameHirarchy = READER.readBooleanProperty("bAlbumFolderNameHirarchyOK", true);
		bGenerateAlbenList = READER.readBooleanProperty("bGenerateAlbenList", false);
		bGenerateAlbenListWhatsNew = READER.readBooleanProperty("bGenerateAlbenListWhatsNew", false);
		maxdays = READER.readLongProperty("maxdays", 7);
		bID3V2ExistCheck = READER.readBooleanProperty("bID3V2ExistCheck", false);
		bID3AlbumArtistCheck = READER.readBooleanProperty("bID3AlbumArtistCheck", false);
		bID3CoverCheck = READER.readBooleanProperty("bID3CoverCheck", false);
		bID3BitrateCheck = READER.readBooleanProperty("bID3BitrateCheck", false);
		bitrateBorder = READER.readLongProperty("bitrateBorder", 128);
		bID3TagConsistencyCheck = READER.readBooleanProperty("bID3TagConsistencyCheck", false);
		bID3GenreCheck = READER.readBooleanProperty("bID3GenreCheck", false);
		bReadonlyCheck = READER.readBooleanProperty("bReadonlyCheck", true);
		bID3AlbumNameCheck = READER.readBooleanProperty("bID3AlbumNameCheck", false);
	}
}
