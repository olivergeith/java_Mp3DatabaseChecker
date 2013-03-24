package og.checker.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import og.basics.gui.file.FileDialogs;
import og.checker.main.Checker;
import og.checker.main.MyConfig;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ConfigPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JTextField pathField = createPathField();
	private final JButton chooserButton = createChooserButton();

	private final JCheckBox cboxExpert = createCheckbox("Expert", false, "Enabels the Expertmode Repair-Features");
	private final JCheckBox cboxFolderMissing = createCheckbox("Missing Folder.jpg", true, "Check for missing Folder.jpg");
	private final JCheckBox cboxIllegalFiles = createCheckbox("Illegale Files", true, "Check for illegal Files/Filetypes");
	private final JCheckBox cboxIllegalFilesList = createCheckbox("List Files", false, "List up illegal Files");
	private final JCheckBox cboxHiddenFiles = createCheckbox("Hidden Files", true, "Check for hidden Files");
	private final JCheckBox cboxHiddenFilesRepair = createRedCheckbox("Repair", false, "Repair hidden Files...only works on Windows-Systems!!!");
	private final JCheckBox cboxReadonlyCheck = createCheckbox("Readonly Check", true, "Check if Files are readonly");
	private final JCheckBox cboxReadonlyRepair = createRedCheckbox("Repair", false, "Makes Readonly-Files writable");
	private final JCheckBox cboxIllegalSubdirs = createCheckbox("Illegale Subdirs", true, "Check for illegal Subdirs in Music-Folders");
	private final JCheckBox cboxNoMp3InFolder = createCheckbox("Album without MP3's", true, "Check for 'End-Point-Folders' with no Music inside");
	private final JCheckBox cboxAlbumFolderNameOK = createCheckbox("Folder-Hirarchy", false, "Check if Folder-Hirarchy is ok (<Artist>/<Artist> - <Year> ...)");
	private final JCheckBox cboxGenerateAlbenList = createCheckbox("Create Album-List", false, "Creates a list of all Music-Folders found");
	private final JCheckBox cboxGenerateAlbenListWhatsNew = createCheckbox("Whats new? (in days)", false,
			"Creates a List of Music-Albums modyfied within the last <n> days");
	private final JCheckBox cboxID3V2Check = createCheckbox("ID3V2 exists?", false, "Checks if all MP3's have an ID3V2-Tag");
	private final JCheckBox cboxID3V2Repair = createRedCheckbox("Repair", false,
			"Repairs a missing ID3V2-Tag-Frame...not allways successfull....You better fix it with Mediamonkey");
	private final JCheckBox cboxID3AlbumNameCheck = createCheckbox("ID3 Album vs. Folder", false, "Checks, if <Dir:folderName>.endswith(<Tag:AlbumName>)");
	private final JCheckBox cboxID3AlbumArtistCheck = createCheckbox("ID3 Artist vs. Folder", false,
			"Checks, if <Dir:folderName>.beginswith(<Tag:AlbumArtist>)");
	private final JCheckBox cboxID3AlbumArtistRepair = createRedCheckbox("Repair", false, "Repairs the <Tag:AlbumArtist> using the Folder-Strukture");
	private final JCheckBox cboxID3CoverCheck = createCheckbox("ID3 CoverArt Check", false, "Checks if all MP3's have a <Tag:CoverArt>");
	private final JCheckBox cboxID3CoverRepair = createRedCheckbox("Repair", false,
			"Repairs the <Tag:CoverArt> by adding the corresponding Folder.jpg to the Tag");
	private final JCheckBox cboxID3BitrateCheck = createCheckbox("Low Bitrate (kbps)", false, "Checks for Mp3's with a bitrate <= specified");
	private final JCheckBox cboxID3TagConsistencyCheck = createCheckbox("ID3 Tag-Consistency", false,
			"Checks, if all MP3's have the same <Tag:Year> and <Tag:AlbumName> and <Tag:AlbumArtist>");
	private final JCheckBox cboxID3GenreCheck = createCheckbox("ID3 Genre-Consistency", false, "Checks, if all MP3's have the same Genre");

	private final Long[] maxdays = { new Long(1), new Long(2), new Long(3), new Long(4), new Long(5), new Long(6), new Long(7), new Long(14), new Long(30),
			new Long(60), new Long(90), new Long(365) };
	private final Long[] bitrates = { new Long(56), new Long(64), new Long(96), new Long(128), new Long(192) };
	private final JComboBox bitrateCombo = createBitrateCombo(bitrates);
	private final JComboBox maxDayCombo = createMaxdayCombo(maxdays);

	JLabel attention1 = createRedDeviderLabel("Attention: FilesystemChecks need to be ok");
	JLabel attention2 = createRedDeviderLabel("for ExpertMode-Repairs!!!");

	public ConfigPanel() {
		myInit();
	}

	private void myInit() {
		setExpertMode(false);
		cboxExpert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setExpertMode(cboxExpert.isSelected());
			}
		});

		// config aus properties lesen
		setConfig(Checker.config);
		validateControls();
		setLayout(new BorderLayout());
		FormLayout layout = new FormLayout("2dlu, pref, 4dlu, pref, 2dlu",
				"4dlu,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		CellConstraints cc = new CellConstraints();
		PanelBuilder builder = new PanelBuilder(layout);

		int row = 1;
		builder.add(createGroupLabel("Start-Directory"), cc.xyw(2, ++row, 2));
		builder.add(chooserButton, cc.xy(4, row));
		builder.add(pathField, cc.xyw(2, ++row, 3));
		builder.add(createGroupLabel("Filesystem Checks"), cc.xyw(2, ++row, 3));
		builder.add(cboxExpert, cc.xy(4, row));
		builder.add(createBlueDeviderLabel("Filesystem Strukture"), cc.xyw(2, ++row, 3));
		builder.add(cboxFolderMissing, cc.xyw(2, ++row, 3));
		builder.add(cboxIllegalFiles, cc.xy(2, ++row));
		builder.add(cboxIllegalFilesList, cc.xy(4, row));
		builder.add(cboxIllegalSubdirs, cc.xyw(2, ++row, 3));
		builder.add(cboxNoMp3InFolder, cc.xyw(2, ++row, 3));
		builder.add(cboxAlbumFolderNameOK, cc.xyw(2, ++row, 3));
		builder.add(createBlueDeviderLabel("File-Attributes"), cc.xyw(2, ++row, 3));
		builder.add(cboxHiddenFiles, cc.xy(2, ++row));
		builder.add(cboxHiddenFilesRepair, cc.xy(4, row));
		builder.add(cboxReadonlyCheck, cc.xy(2, ++row));
		builder.add(cboxReadonlyRepair, cc.xy(4, row));

		builder.addSeparator("", cc.xyw(2, ++row, 3));
		builder.add(createGroupLabel("Extensiv MP3 Checks"), cc.xyw(2, ++row, 3));
		builder.add(attention1, cc.xyw(2, ++row, 3));
		builder.add(attention2, cc.xyw(2, ++row, 3));
		builder.add(createBlueDeviderLabel("1. Step"), cc.xyw(2, ++row, 3));
		builder.add(cboxID3V2Check, cc.xy(2, ++row));
		builder.add(cboxID3V2Repair, cc.xy(4, row));
		builder.add(createBlueDeviderLabel("2. Step"), cc.xyw(2, ++row, 3));
		builder.add(cboxID3TagConsistencyCheck, cc.xyw(2, ++row, 3));
		builder.add(createBlueDeviderLabel("3. Step"), cc.xyw(2, ++row, 3));
		builder.add(cboxID3AlbumArtistCheck, cc.xy(2, ++row));
		builder.add(cboxID3AlbumArtistRepair, cc.xy(4, row));
		builder.add(createBlueDeviderLabel("4. Step"), cc.xyw(2, ++row, 3));
		builder.add(cboxID3CoverCheck, cc.xy(2, ++row));
		builder.add(cboxID3CoverRepair, cc.xy(4, row));
		builder.add(createBlueDeviderLabel("5. Step (optional)"), cc.xyw(2, ++row, 3));
		builder.add(cboxID3BitrateCheck, cc.xy(2, ++row));
		builder.add(bitrateCombo, cc.xy(4, row));
		builder.add(cboxID3AlbumNameCheck, cc.xyw(2, ++row, 3));
		builder.add(cboxID3GenreCheck, cc.xyw(2, ++row, 3));

		builder.addSeparator("", cc.xyw(2, ++row, 3));
		builder.add(createGroupLabel("Listings"), cc.xyw(2, ++row, 3));
		builder.add(cboxGenerateAlbenList, cc.xyw(2, ++row, 3));
		builder.add(cboxGenerateAlbenListWhatsNew, cc.xy(2, ++row));
		builder.add(maxDayCombo, cc.xy(4, row));

		this.add(builder.getPanel(), BorderLayout.CENTER);
		this.add(createLogo(), BorderLayout.SOUTH);
	}

	/**
	 * Erzeugt den ChooserButton
	 * 
	 * @return
	 */
	private JButton createChooserButton() {
		final JButton chooserButton = new JButton("...");
		chooserButton.setToolTipText("Choose start-directory");
		chooserButton.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent arg0) {
				changeDir();
			}
		});
		return chooserButton;
	}

	/**
	 * Ändern des Start-Directories
	 */
	private void changeDir() {
		final String dir = FileDialogs.chooseDir(pathField.getText());
		if (dir != null) {
			pathField.setText(dir);
			pathField.setToolTipText(dir);
			saveStartPath(dir);
		}
	}

	/**
	 * Erzeugt das Pathfield
	 * 
	 * @return
	 */
	private JTextField createPathField() {
		JTextField pathField = new JTextField();
		pathField.setForeground(Color.GREEN.darker());
		pathField.setEditable(false);
		// pathField.setBorder(new BevelBorder(1));
		final String path = loadStartPath();
		pathField.setText(path);
		pathField.setToolTipText(path);
		return pathField;
	}

	/**
	 * Schreibt den StartPath in eine Property
	 * 
	 * @param inputVerzStr
	 */
	private void saveStartPath(final String inputVerzStr) {
		Checker.config.startPath = inputVerzStr;
	}

	/**
	 * Liest den Startpfad aus einer property
	 * 
	 * @return
	 */
	private String loadStartPath() {
		return Checker.config.startPath;
	}

	/**
	 * @return Den Startpath
	 */
	public String getStartPath() {
		return pathField.getText();
	}

	/**
	 * Erzeugt das Froschlogo mit dem hiddenfeature für Expertmode
	 * 
	 * @return
	 */
	private JLabel createLogo() {
		final JLabel logo = new JLabel(Checker.version.getLogo());
		logo.setToolTipText(Checker.version.getApplicationname() + " Version: " + Checker.version.getVersion());
		return logo;
	}

	/**
	 * Creates a Lable with a big font
	 * 
	 * @param txt
	 * @return
	 */
	private JLabel createGroupLabel(String txt) {
		return createColoredFontLabel(txt, new Font(Font.SANS_SERIF, Font.BOLD, 14), Color.BLUE.darker());
	}

	/**
	 * Creates a Label with a very small blue font
	 * 
	 * @param txt
	 * @return
	 */
	private JLabel createBlueDeviderLabel(String txt) {
		return createColoredFontLabel(txt, new Font(Font.SANS_SERIF, Font.BOLD, 10), Color.BLUE.darker());
	}

	/**
	 * Creates a Label with a very small red font
	 * 
	 * @param txt
	 * @return
	 */
	private JLabel createRedDeviderLabel(String txt) {
		return createColoredFontLabel(txt, new Font(Font.SANS_SERIF, Font.BOLD, 10), Color.RED.darker());
	}

	/**
	 * Creates a Label with a font and Color
	 * 
	 * @param txt
	 * @param font
	 * @param color
	 * @return
	 */
	private JLabel createColoredFontLabel(String txt, Font font, Color color) {
		JLabel label = new JLabel(txt);
		label.setForeground(color);
		label.setFont(font);
		return label;
	}

	/**
	 * Creates the Bitrate Combo
	 * 
	 * @param list
	 * @return
	 */
	private JComboBox createBitrateCombo(Object[] list) {
		final JComboBox combo = new JComboBox(list);
		combo.setToolTipText("Choose a Bitrate");
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (combo.getSelectedItem() instanceof Long)
					Checker.config.bitrateBorder = ((Long) combo.getSelectedItem()).longValue();
			}
		});
		return combo;
	}

	/**
	 * Creates the Maxday-Combobox
	 * 
	 * @param list
	 * @return
	 */
	private JComboBox createMaxdayCombo(Object[] list) {
		final JComboBox combo = new JComboBox(list);
		combo.setToolTipText("How many days back shall we look?");
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (combo.getSelectedItem() instanceof Long)
					Checker.config.maxdays = ((Long) combo.getSelectedItem()).longValue();
			}
		});
		return combo;
	}

	/**
	 * @param text
	 *            Text der Checkbox
	 * @param defaultselection
	 *            Defaultselection
	 * @return
	 */
	private JCheckBox createCheckbox(final String text, final boolean defaultselection, String tooltip) {
		final JCheckBox cbox = new JCheckBox(text);
		cbox.setSelected(defaultselection);
		cbox.setToolTipText(tooltip);
		cbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveConfig();
				validateControls();
			}
		});
		return cbox;
	}

	/**
	 * As the Name says ;-)
	 * 
	 * @param text
	 * @param defaultselection
	 * @param tooltip
	 * @return
	 */
	private JCheckBox createRedCheckbox(final String text, final boolean defaultselection, String tooltip) {
		final JCheckBox cbox = createCheckbox(text, defaultselection, tooltip);
		cbox.setForeground(Color.RED.darker());
		return cbox;
	}

	/**
	 * Saves the Configuration off all Checkboxes to the Config
	 */
	private void saveConfig() {
		Checker.config.bFolderMissing = cboxFolderMissing.isSelected();
		Checker.config.bHiddenFiles = cboxHiddenFiles.isSelected();
		Checker.config.bHiddenFilesRepair = cboxHiddenFilesRepair.isSelected();
		Checker.config.bIllegalFiles = cboxIllegalFiles.isSelected();
		Checker.config.bIllegalFilesList = cboxIllegalFilesList.isSelected();
		Checker.config.bIllegalSubdirs = cboxIllegalSubdirs.isSelected();
		Checker.config.bNoMp3InFolder = cboxNoMp3InFolder.isSelected();
		Checker.config.bAlbumFolderNameHirarchy = cboxAlbumFolderNameOK.isSelected();
		Checker.config.bGenerateAlbenList = cboxGenerateAlbenList.isSelected();
		Checker.config.bGenerateAlbenListWhatsNew = cboxGenerateAlbenListWhatsNew.isSelected();
		Checker.config.bID3V2ExistCheck = cboxID3V2Check.isSelected();
		Checker.config.bID3V2ExistRepair = cboxID3V2Repair.isSelected();
		Checker.config.bID3AlbumNameCheck = cboxID3AlbumNameCheck.isSelected();
		Checker.config.bID3AlbumArtistCheck = cboxID3AlbumArtistCheck.isSelected();
		Checker.config.bID3AlbumArtistRepair = cboxID3AlbumArtistRepair.isSelected();
		Checker.config.bID3CoverCheck = cboxID3CoverCheck.isSelected();
		Checker.config.bID3CoverRepair = cboxID3CoverRepair.isSelected();
		Checker.config.bID3BitrateCheck = cboxID3BitrateCheck.isSelected();
		Checker.config.bID3TagConsistencyCheck = cboxID3TagConsistencyCheck.isSelected();
		Checker.config.bReadonlyCheck = cboxReadonlyCheck.isSelected();
		Checker.config.bReadonlyRepair = cboxReadonlyRepair.isSelected();
		Checker.config.bID3GenreCheck = cboxID3GenreCheck.isSelected();
	}

	protected void validateControls() {
		cboxHiddenFilesRepair.setEnabled(cboxHiddenFiles.isSelected());
		cboxReadonlyRepair.setEnabled(cboxReadonlyCheck.isSelected());
		cboxIllegalFilesList.setEnabled(cboxIllegalFiles.isSelected());
		cboxID3AlbumArtistRepair.setEnabled(cboxID3AlbumArtistCheck.isSelected());
		cboxID3CoverRepair.setEnabled(cboxID3CoverCheck.isSelected());
		cboxID3V2Repair.setEnabled(cboxID3V2Check.isSelected());
		bitrateCombo.setEnabled(cboxID3BitrateCheck.isSelected());
		maxDayCombo.setEnabled(cboxGenerateAlbenListWhatsNew.isSelected());
	}

	/**
	 * Sets the Configuration of all Elements
	 * 
	 * @param config
	 */
	private void setConfig(final MyConfig config) {
		cboxFolderMissing.setSelected(config.bFolderMissing);
		cboxIllegalFiles.setSelected(config.bIllegalFiles);
		cboxIllegalFilesList.setSelected(config.bIllegalFilesList);
		cboxHiddenFiles.setSelected(config.bHiddenFiles);
		cboxIllegalSubdirs.setSelected(config.bIllegalSubdirs);
		cboxNoMp3InFolder.setSelected(config.bNoMp3InFolder);
		cboxAlbumFolderNameOK.setSelected(config.bAlbumFolderNameHirarchy);
		cboxGenerateAlbenList.setSelected(config.bGenerateAlbenList);
		cboxGenerateAlbenListWhatsNew.setSelected(config.bGenerateAlbenListWhatsNew);
		cboxID3V2Check.setSelected(config.bID3V2ExistCheck);
		cboxID3AlbumNameCheck.setSelected(config.bID3AlbumNameCheck);
		cboxID3AlbumArtistCheck.setSelected(config.bID3AlbumArtistCheck);
		cboxID3CoverCheck.setSelected(config.bID3CoverCheck);
		cboxID3BitrateCheck.setSelected(config.bID3BitrateCheck);
		cboxID3TagConsistencyCheck.setSelected(config.bID3TagConsistencyCheck);
		cboxReadonlyCheck.setSelected(config.bReadonlyCheck);
		bitrateCombo.setSelectedItem(new Long(config.bitrateBorder));
		maxDayCombo.setSelectedItem(new Long(config.maxdays));
		cboxID3GenreCheck.setSelected(config.bID3GenreCheck);
	}

	/**
	 * Setzt den Expertmode (macht die Repair-Checkboxes sichtbar
	 * 
	 * @param b
	 */
	private void setExpertMode(boolean b) {
		cboxID3V2Repair.setVisible(b);
		cboxID3AlbumArtistRepair.setVisible(b);
		cboxID3CoverRepair.setVisible(b);
		cboxHiddenFilesRepair.setVisible(b);
		cboxReadonlyRepair.setVisible(b);
		attention1.setVisible(b);
		attention2.setVisible(b);
		if (b == false) {
			cboxID3V2Repair.setSelected(b);
			cboxID3AlbumArtistRepair.setSelected(b);
			cboxID3CoverRepair.setSelected(b);
			cboxHiddenFilesRepair.setSelected(b);
			cboxReadonlyRepair.setSelected(b);
		}
	}

}
