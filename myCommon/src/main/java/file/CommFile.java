package file;

import java.io.File;
import java.io.FileReader;
import java.nio.CharBuffer;

/**
 * @description: 文件工具
 * @author: ricci
 * @date: 2021-11-19 13:53:31
 */
public class CommFile {
    /**
     * 读取文件转换为 String
     *
     * @param _path 文件路径
     * @return String
     */
    public static String getStringFromFile(String _path) {
        CharBuffer buf = getFileData(_path);
        if (buf != null) {
            String res = new String(buf.array());
            return res.trim();
        }
        return null;
    }

    /**
     * 读取文件里的内容
     *
     * @param _path 文件路径
     * @return CharBuffer
     */
    private static CharBuffer getFileData(String _path) {
        File file = new File(_path);
        CharBuffer cb = null;
        //文件读取
        try (FileReader reader = new FileReader(file)) {
            cb = CharBuffer.allocate((int) file.length());
            int read = reader.read(cb);
            cb.limit(read);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cb;
    }
}
