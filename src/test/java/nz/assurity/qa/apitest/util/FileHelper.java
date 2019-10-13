package nz.assurity.qa.apitest.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * File name processing features:
 * find file extension, generate time stamped file names for a given extension.
 *
 * @author Nilmi
 */
public class FileHelper {

    /**
     * Returns file extension of a given file as a String.
     *
     * @param file File object
     * @return File extension as a String
     */
    public static String getFileExtension(File file) {
        return getFileExtension(file.getName());
    }

    /**
     * Returns file extension of a given file name String.
     *
     * @param fileName File name String
     * @return File extension as a String
     */
    public static String getFileExtension(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }

    /**
     * Returns time stamped file name with given file extension.
     *
     * @param extension File extension to be added to the file name as a String
     * @return File name as a String
     */
    public static String getTimestampedName(String extension) {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date())
                + (StringUtils.isEmpty(extension) ? "" : "." + extension);
    }

    /**
     * Returns time stamped unique text.
     *
     * @return Returns time stamped unique text
     */
    public static String getTimestampedUniqueText() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }


    /**
     * Creates directories.
     *
     * @param file File instance
     */
    public static boolean createDirs(File file) {
        if (!file.exists()) {
            return file.mkdirs();
        }
        return false;
    }

}
