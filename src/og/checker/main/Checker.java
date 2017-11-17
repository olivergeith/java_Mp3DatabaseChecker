
package og.checker.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import og.basics.gui.about.VersionDetails;
import og.checker.gui.Mp3CheckerFrame;

public class Checker {
    public static MyConfig config = new MyConfig();
    private static String description = "This tool scans a music-collection for errors and inconsistencies<br>" + "It checks <br>- the Directory-Structure"
            + "<br>- the ID3-Tags" + "<br>And it creates <br>- Album-Listings or<br>- What's New-Listings";
    private static final ImageIcon logoIcon = new ImageIcon(Checker.class.getResource("logo.png"));
    public static final VersionDetails version = new VersionDetails();

    public static void main(final String[] args) {
        version.setApplicationname("Mp3-Database-Checker");
        version.setVersion("1.8.3");
        version.setCopyright("Oliver Geith");
        version.setLogo(logoIcon);
        version.setCompany("Music Unlimited");
        version.setDate("13.11.2017");
        version.setDescription(description);

        System.out.println("Starting " + version.getApplicationname());
        Logger.getLogger("org.jaudiotagger").setLevel(Level.SEVERE);
        final Mp3CheckerFrame frame = new Mp3CheckerFrame();
        frame.setSize(1200, 900);
        frame.setVisible(true);

    }
}
