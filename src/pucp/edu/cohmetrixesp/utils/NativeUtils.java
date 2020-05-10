package pucp.edu.cohmetrixesp.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class NativeUtils {

	public static void loadLibrary(String library) {
        try {
            System.load(saveLibrary(library));
        } catch (IOException e) {
            System.out.println("Could not find library " + library +
                    " as resource, trying fallback lookup through System.loadLibrary");
            System.loadLibrary(library);
        }
    }


    private static String getOSSpecificLibraryName(String library, boolean includePath) {
        String osArch = System.getProperty("os.arch");
        String osName = System.getProperty("os.name").toLowerCase();
        String name;
        String path;

        if (osName.startsWith("win")) {
            if (osArch.equalsIgnoreCase("x86")) {
                name = library + ".dll";
                path = "win-x86/";
            } else if (osArch.equalsIgnoreCase("x86_64")) {
            	name = library + ".dll";
                path = "win-x86_64/";
            } else {
                throw new UnsupportedOperationException("Platform " + osName + ":" + osArch + " not supported");
            }
        } else if (osName.startsWith("linux")) {
            if (osArch.equalsIgnoreCase("amd64")) {
                name = "lib" + library + ".so";
                path = "linux-x86_64/";
            } else if (osArch.equalsIgnoreCase("ia64")) {
                name = "lib" + library + ".so";
                path = "linux-ia64/";
            } else if (osArch.equalsIgnoreCase("i386")) {
                name = "lib" + library + ".so";
                path = "linux-x86/";
            } else {
                throw new UnsupportedOperationException("Platform " + osName + ":" + osArch + " not supported");
            }
        }else if (osName.startsWith("mac os x")) {
        	 if (osArch.equalsIgnoreCase("x86")) {
                 name = "lib" + library + ".dylib";
                 path = "osx-x86/";
             } else if (osArch.equalsIgnoreCase("x86_64")) {
            	 name = "lib" + library + ".dylib";
            	 path = "osx-x86_64/";
             } else {
                 throw new UnsupportedOperationException("Platform " + osName + ":" + osArch + " not supported");
             }
        } else {
            throw new UnsupportedOperationException("Platform " + osName + ":" + osArch + " not supported");
        }

        return includePath ? path + name : name;
    }

    private static String saveLibrary(String library) throws IOException {
        InputStream in = null;
        OutputStream out = null;

        try {
            String libraryName = getOSSpecificLibraryName(library, true);
            
            in = ClassLoader.getSystemResourceAsStream("libs/" + libraryName);
            System.out.println(ClassLoader.getSystemResource("libs/" + libraryName));
            
            String tmpDirName = System.getProperty("java.io.tmpdir");
            File tmpDir = new File(tmpDirName);
            if (!tmpDir.exists()) {
                tmpDir.mkdir();
            }
            File file = File.createTempFile(library + "-", ".tmp", tmpDir);
            // Clean up the file when exiting
            file.deleteOnExit();
            out = new FileOutputStream(file);

            int cnt;
            byte buf[] = new byte[16 * 1024];
            // copy until done.
            while ((cnt = in.read(buf)) >= 1) {
                out.write(buf, 0, cnt);
            }
            System.out.println("Saved libfile: " + file.getAbsoluteFile());
            return file.getAbsolutePath();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignore) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignore) {
                }
            }
        }
    }
	
}
