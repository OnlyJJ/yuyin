package com.jiujun.voice.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
public class FileUtils {

	/**
	 * 追加文件：使用FileWriter
	 * 
	 * @param folderName
	 * @param fileName
	 * @param content
	 */
	public static void writeFile(final String folderName, final String fileName, final String content) {
		if (content == null || content.isEmpty()) {
			return;
		}
		FileWriter writer = null;
		File file = null;
		try {
			file = new File(folderName);
			if (!file.exists()) {
				// 文件夹不存在，创建
				file.mkdir();
			}
			file = new File(folderName + "/" + fileName);
			if (!file.exists()) {
				// 文件夹不存在，创建
				file.createNewFile();
			}
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer = new FileWriter(folderName + "/" + fileName, true);
			writer.write(content);
		} catch (IOException e) {
			PrintException.printException(e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				PrintException.printException(e);
			}
		}

	}

	public static void write(String path, String context) {
		write(path, context, "utf-8");
	}

	public static void write(String path, String context, String encode) {
		OutputStream pt = null;
		try {
			pt = new FileOutputStream(URLDecoder.decode(path, "UTF-8"));
			pt.write(context.getBytes(encode));
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			try {
				pt.close();
			} catch (IOException e) {
				PrintException.printException(e);
			}
		}
	}

	public static void writeAppend(String path, String context) {
		writeAppend(path, context, "utf-8");
	}

	public static void writeAppend(String path, String context, String encode) {
		BufferedWriter out = null;
		try {
			makeFileDir(path);
			out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(URLDecoder.decode(path, "UTF-8"), true)));
			out.write(context);
		} catch (Exception e) {
			PrintException.printException(e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				PrintException.printException(e);
			}
		}
	}

	public static void writeAppendLine(String path, String context) {
		writeAppendLine(path, context, "utf-8");
	}

	public static void writeAppendLine(String path, String context, String encode) {
		writeAppend(path, context + "\r\n", encode);
	}

	public static String readFile(String path) {
		return readFile(path, "utf-8");
	}

	public static String readFile(String path, String encode) {
		InputStreamReader read = null;
		FileInputStream in = null;
		BufferedReader bufferedReader = null;
		try {

			File file = new File(URLDecoder.decode(path, "UTF-8"));
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				in = new FileInputStream(file);
				read = new InputStreamReader(in, encode);// 考虑到编码格式
				bufferedReader = new BufferedReader(read);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line).append("\r\n");
				}
				String result=sb.toString();
				if(result!=null&&result.endsWith("\r\n")){
					result= sb.substring(0, sb.length()-2);
				}
				 return result;	}
		} catch (Exception e) {
		} finally {
			try {
				bufferedReader.close();
				read.close();
				in.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	@SuppressWarnings("resource")
	public static byte[] readFileByte(String path) {
		try {
			File file = new File(URLDecoder.decode(path, "UTF-8"));
			if (!file.exists()) {
				return null;
			}
			long fileSize = file.length();
			if (fileSize > Integer.MAX_VALUE) {
				return null;
			}
			FileInputStream fi = new FileInputStream(file);
			byte[] buffer = new byte[(int) fileSize];
			int offset = 0;
			int numRead = 0;
			while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
				offset += numRead;
			}
			// 确保所有数据均被读取
			if (offset != buffer.length) {
				throw new IOException("Could not completely read file " + file.getName());
			}
			fi.close();
			return buffer;
		} catch (Exception e) {
			PrintException.printException(e);
			return null;
		}
	}

	/**
	 * 
	 * 获取目录下所有文件
	 * 
	 * @param realpath
	 * @param files
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static List<File> getFiles(String realpath) throws UnsupportedEncodingException {
		List<File> files = new ArrayList<File>();
		File realFile = new File(URLDecoder.decode(realpath, "UTF-8"));
		if (realFile.isDirectory()) {
			File[] subfiles = realFile.listFiles();
			for (File file : subfiles) {
				if (file.isDirectory()) {
					files.addAll(getFiles(file.getAbsolutePath()));
					continue;
				}
				files.add(file);
			}
		}
		return files;
	}

	public static void rewrite(File file, String data) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(data);
		} catch (IOException e) {
			PrintException.printException(e);
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					PrintException.printException(e);
				}
			}
		}
	}

	public static List<String> readList(File file) {
		BufferedReader br = null;
		List<String> data = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(file));
			for (String str = null; (str = br.readLine()) != null;) {
				data.add(str);
			}
		} catch (IOException e) {
			PrintException.printException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					PrintException.printException(e);
				}
			}
		}
		return data;
	}

	public static boolean writeFile(String path, byte[] content) {
		try {
			makeFileDir(path);
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(content);
			fos.close();
			return true;
		} catch (Exception e) {
			PrintException.printException(e);
			return false;
		}

	}

	public static String inputStream2String(InputStream in, String encode) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[1024];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n, encode));
		}
		return out.toString();
	}

	public static void makeFileDir(String path) {
		while (path.contains("\\")) {
			path = path.replace("\\", "/");
		}
		while (path.contains("//")) {
			path = path.replace("//", "/");
		}
		int lastTag = path.lastIndexOf('/');
		if (lastTag == -1) {
			return;
		}
		path = path.substring(0, lastTag);
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
	}
	
	
	private static void test(String fileDir) {
		List<File> fileList = new ArrayList<File>();
		File file = new File(fileDir);
		File[] files = file.listFiles();// 获取目录下的所有文件或文件夹
		if (files == null) {// 如果目录为空，直接退出
			return;
		}
		// 遍历，目录下的所有文件
		for (File f : files) {
			if (f.isFile()) {
					fileList.add(f);
			} else if (f.isDirectory()) {
				//System.out.println(f.getAbsolutePath());
				test(f.getAbsolutePath());
			}
		}
		for (File f1 : fileList) {
			String path=f1.getPath().toString().replace("\\", "/");
			writeAppendLine("d://s.txt", path);
		}
	}
 
	public static void main(String[] args) {
		test("c://windows/");
	}
}
