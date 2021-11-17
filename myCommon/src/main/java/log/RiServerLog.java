package log;

import configReader.RiConfReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

public class RiServerLog {
    private static RiServerLog.LogLevel g_logLevel;
    private static boolean g_bInit = false;

    public RiServerLog() {
    }

    public static void initALServerLog() {
        if (!g_bInit) {
            g_bInit = true;
            Properties properties = new Properties();
            FileInputStream propertiesInputStream = null;

            try {
                propertiesInputStream = new FileInputStream("./conf/RiServerLog.properties");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (propertiesInputStream != null) {
                try {
                    properties.load(propertiesInputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    System.out.println("[Conf init] Load RiServerLog Properties start......");
                    String tmpStr = RiConfReader.readStr(properties, "RiServerLog.LogLevel", "ERROR");
                    g_logLevel = RiServerLog.LogLevel.valueOf(tmpStr.toUpperCase().trim());
                    System.out.println("[Conf init] Finish load RiServerLog Properties ... ...");
                } catch (Exception var13) {
                    System.out.println("[Conf Init Error] Load RiServerLog Properties Error!!");
                    g_logLevel = RiServerLog.LogLevel.ERROR;
                } finally {
                    try {
                        propertiesInputStream.close();
                    } catch (IOException var12) {
                        var12.printStackTrace();
                    }

                }

            }
        }
    }

    public static RiServerLog.LogLevel getLogLevel() {
        return g_logLevel;
    }

    public static void Debug(String text) {
        _Log(RiServerLog.LogLevel.DEBUG, text);
    }

    public static void Info(String text) {
        _Log(RiServerLog.LogLevel.INFO, text);
    }

    public static void Warning(String text) {
        _Log(RiServerLog.LogLevel.WARNING, text);
    }

    public static void Error(String text) {
        _Log(RiServerLog.LogLevel.ERROR, text);
    }

    public static void Fatal(String text) {
        _Log(RiServerLog.LogLevel.FATAL, "");
        _Log(RiServerLog.LogLevel.FATAL, "=================== FATAL ERR =================");
        _Log(RiServerLog.LogLevel.FATAL, text);
        _Log(RiServerLog.LogLevel.FATAL, "===============================================");
        _Log(RiServerLog.LogLevel.FATAL, "");
    }

    public static void Sys(String text) {
        _Log(RiServerLog.LogLevel.SYS, text);
    }

    public static void Info(ByteBuffer _buffer) {
        if (g_logLevel.compareTo(RiServerLog.LogLevel.INFO) <= 0) {
            StringBuffer str = new StringBuffer();
            str.append(" ---- \n");

            for (int i = _buffer.position(); i < _buffer.remaining(); ++i) {
                byte b = _buffer.get(i);
                str.append(b + "\n");
            }

            Info(str.toString());
        }

    }

    protected static void _Log(RiServerLog.LogLevel lev, String logstr) {
        if (g_logLevel.compareTo(lev) <= 0) {
            System.out.println("[" + lev + "]\t" + System.currentTimeMillis() + "\t" + logstr);
        }

    }

    public enum LogLevel {
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        FATAL,
        SYS;

        LogLevel() {
        }
    }
}
