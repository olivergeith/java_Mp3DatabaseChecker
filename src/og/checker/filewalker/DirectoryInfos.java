
package og.checker.filewalker;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class DirectoryInfos {

    private final String[] illegalExtensions = { ".db", ".nfo", ".sfv", ".flv", ".mov", ".mpg", ".avi", ".html", ".htm", ".m3u", ".mp4", ".m4a", ".m4p", ".ogg",
            ".wma", ".ini", ".new", ".txt", ".zip", ".rar", ".7z", ".mkv", ".mp2", ".wav", ".lnk", "ds_store", ".ico", ".pls", ".ram", ".asx", ".url", ".flac",
            ".log", ".diz", ".dat", ".asf", ".thm"

    };

    private static final String DIR_PATTERN_SAMPLER = "_Sampler";
    private static final String DIR_PATTERN_XMAS = "_XMas";
    private static final String DIR_PATTERN_OST = "_OST";
    private static final String DIR_PATTERN_MUSICALS = "_Musicals";
    private final File[] subDirs;
    private final File[] musicFiles;
    private final File[] illegalFiles;
    private final File[] folderJPG;
    private final File dir;
    private final File[] allFiles;
    private final File[] hiddenFiles;
    private final File[] readonlyFiles;

    public DirectoryInfos(final File dir) {
        this.dir = dir;
        subDirs = findSubDirs();
        musicFiles = findMP3Files();
        illegalFiles = findIllegalFiles();
        folderJPG = findFolderJpgs();
        allFiles = findAllFiles();
        hiddenFiles = findHiddenFiles();
        readonlyFiles = findReadonlyFiles();
    }

    /**
     * @param dir
     * @return
     */
    private File[] findSubDirs() {
        // Liste aller Subdirs
        final File[] subDirs = dir.listFiles(new FileFilter() {

            @Override
            public boolean accept(final File pathname) {
                return pathname.isDirectory();
            }
        });
        return subDirs;
    }

    /**
     * @param dir
     * @return
     */
    private File[] findAllFiles() {
        // Liste aller Subdirs
        final File[] allFiles = dir.listFiles(new FileFilter() {

            @Override
            public boolean accept(final File pathname) {
                return pathname.isFile();
            }
        });
        return allFiles;
    }

    /**
     * @param dir
     * @return
     */
    private File[] findHiddenFiles() {
        // Liste aller Subdirs
        final File[] files = dir.listFiles(new FileFilter() {

            @Override
            public boolean accept(final File pathname) {
                return pathname.isHidden();
            }
        });
        return files;
    }

    /**
     * @param dir
     * @return
     */
    private File[] findReadonlyFiles() {
        // Liste aller Subdirs
        final File[] files = dir.listFiles(new FileFilter() {

            @Override
            public boolean accept(final File pathname) {
                return !pathname.canWrite();
            }
        });
        return files;
    }

    /**
     * @param dir
     * @return
     */
    private File[] findFolderJpgs() {
        // Liste aller Folder.jpg's
        final File[] folderJPG = dir.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(final File dir, final String name) {
                return name.toLowerCase().equals("folder.jpg");
            }
        });
        return folderJPG;
    }

    /**
     * 
     * Findet Illegale Files
     * 
     * @return
     */
    private File[] findIllegalFiles() {
        final File[] illegalFiles = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(final File dir, final String name) {
                for (final String extension : illegalExtensions) {
                    if (name.toLowerCase().endsWith(extension)) {
                        return true;
                    }
                }
                return false;
            }
        });
        return illegalFiles;
    }

    /**
     * @param dir
     * @return
     */
    private File[] findMP3Files() {
        // Liste aller MP3-Files
        final File[] musicFiles = dir.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(final File dir, final String name) {
                return name.toLowerCase().endsWith(".mp3");
            }
        });
        return musicFiles;
    }

    /**
     * @return the subDirs
     */
    public File[] getSubDirs() {
        return subDirs;
    }

    /**
     * @return the musicFiles
     */
    public File[] getMusicFiles() {
        return musicFiles;
    }

    /**
     * @return the illegalFiles
     */
    public File[] getIllegalFiles() {
        return illegalFiles;
    }

    /**
     * @return
     */
    public boolean isMusicDir() {
        if (musicFiles.length > 0) {
            return true;
        }
        return false;
    }

    /**
     * @return true if Path contains "_Sampler"
     */
    public boolean isInSampler() {
        final String path = getDirPath();
        if (path.indexOf(DIR_PATTERN_SAMPLER) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * @return true if Path contains "_OST"
     */
    public boolean isInOST() {
        final String path = getDirPath();
        if (path.indexOf(DIR_PATTERN_OST) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * @return true if Path contains "_XMas"
     */
    public boolean isInXmas() {
        final String path = getDirPath();
        if (path.indexOf(DIR_PATTERN_XMAS) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * @return true if Path contains "_Musicals"
     */
    public boolean isInMusical() {
        final String path = getDirPath();
        if (path.indexOf(DIR_PATTERN_MUSICALS) >= 0) {
            return true;
        }
        return false;
    }

    /**
     * @return true if in _Sampler usw...
     */
    public boolean isInSamplerXmasMusicalOst() {
        if (isInSampler() || isInMusical() || isInXmas() || isInOST()) {
            return true;
        }
        return false;
    }

    /**
     * @return true if ok
     */
    public boolean isFolderHirarchieOK() {
        if (isMusicDir() && !isInSamplerXmasMusicalOst()) {
            // nun vergleichen wir den Parentfolder mit dem Anfang des
            // Album-Namens
            final String parentDirName = getParentDirName();
            if (!getDirName().startsWith(parentDirName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return
     */
    public boolean hasSubDirs() {
        if (subDirs.length > 0) {
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    public boolean hasFolderJpgs() {
        if (folderJPG.length == 1) {
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    public File getFolderJpg() {
        if (folderJPG.length == 1) {
            return folderJPG[0];
        }
        return null;
    }

    /**
     * @return
     */
    public boolean hasIllegalFiles() {
        if (illegalFiles.length > 0) {
            return true;
        }
        return false;
    }

    public boolean hasHiddenFiles() {
        if (hiddenFiles.length > 0) {
            return true;
        }
        return false;
    }

    public boolean hasReadonlyFiles() {
        if (readonlyFiles.length > 0) {
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    public String getParentDirName() {
        return dir.getParentFile().getName();
    }

    /**
     * @return
     */
    public String getDirPath() {
        return dir.getPath();
    }

    /**
     * @return
     */
    public String getDirName() {
        return dir.getName();
    }

    /**
     * @return
     */
    public File getDir() {
        return dir;
    }

    /**
     * Liefert alle Files des Directories
     * 
     * @return
     */
    public File[] getAllFiles() {
        return allFiles;
    }

    public File[] getHiddenFiles() {
        return hiddenFiles;
    }

    public File[] getReadonlyFiles() {
        return readonlyFiles;
    }
}
