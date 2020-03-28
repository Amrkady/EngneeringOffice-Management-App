package common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class FtpTransferFile {
	static String user="";
	static String pass="";
	static String host="192.168.1.4";

	
	
	
	public static String uploadFile(InputStream inputStream, String uploadPath) {
//		String ftpUrl = "ftp://%s:%s@%s/%s";
//		ftpUrl = String.format(ftpUrl, user, pass, host, uploadPath);
		OutputStream outputStream = null;
		try {
			URL url = new URL("ftp://192.168.1.3:21/"+uploadPath+";type=i");
			URLConnection conn = url.openConnection();
			outputStream = conn.getOutputStream();
			byte[] buffer = new byte[99999999];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			inputStream.close();
			outputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
		}
		return uploadPath;
	}
}
