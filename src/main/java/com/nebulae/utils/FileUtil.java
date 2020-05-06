package com.nebulae.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 文件操作工具类
 * 
 * @author lb
 * @version 1.0
 * @since 1.0
 */
public class FileUtil {
	/**
	 * 修改文件夹下的所有符合条件的文件的扩展名，递归文件夹下所有文件
	 * 
	 * @param dir
	 *            文件夹的路径
	 * @param oldExtensionName
	 *            旧的文件扩展名 例如：.jpg
	 * @param newExtensionName
	 *            新的文件扩展名 例如：.png
	 */
	public static void modifyFileExtensionNameInDir(File dir, String oldExtensionName, String newExtensionName) {
		// 1.判断输入的文件是否是文件夹
		boolean isdirectory = dir.isDirectory();
		if (isdirectory == true) {
			// 获取目录下的所有的文件以及目录
			File[] files = dir.listFiles();
			if (files != null && files.length > 0) {
				for (File file : files) {
					if (file.isDirectory()) {
						modifyFileExtensionNameInDir(file, oldExtensionName, newExtensionName);
					} else {
						modifyFileExtensionName(file, oldExtensionName, newExtensionName);
					}
				}
			}
		} else {
			File file = dir;
			modifyFileExtensionName(file, oldExtensionName, newExtensionName);
		}
	}

	/**
	 * 修改单个文件的扩展名
	 * 
	 * @param file
	 *            文件对象
	 * @param oldExtensionName
	 *            旧的文件扩展名 例如：.jpg
	 * @param newExtensionName
	 *            新的文件扩展名 例如：.png
	 */
	public static void modifyFileExtensionName(File file, String oldExtensionName, String newExtensionName) {
		String fileName = file.getName();
		if (fileName.endsWith(oldExtensionName)) {
			// 去掉扩展名
			int lastIndexOfPoint = fileName.lastIndexOf('.');
			String newFilename = fileName.substring(0, lastIndexOfPoint);
			// 重命名文件
			file.renameTo(new File(file.getParent() + "/" + newFilename + newExtensionName));
		}
	}

	public static void uploadFile(byte[] file, String filePath, String fileName) {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath + fileName);
			out.write(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	public static String RenameToUUID(String fileName) {
		return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	// 将文件转换成Byte数组
	public static byte[] getBytesByFile(String pathStr) {
		File file = new File(pathStr);
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			byte[] data = bos.toByteArray();
			bos.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 将文件转换成Byte数组
		public static byte[] getBytesByFile(File file) {
			try {
				FileInputStream fis = new FileInputStream(file);
				ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
				byte[] b = new byte[1000];
				int n;
				while ((n = fis.read(b)) != -1) {
					bos.write(b, 0, n);
				}
				fis.close();
				byte[] data = bos.toByteArray();
				bos.close();
				return data;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	// 将Byte数组转换成文件
	public static void getFileByBytes(byte[] bytes, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + "\\" + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	 /** 
     * 功能描述：列出某文件夹及其子文件夹下面的文件，并可根据扩展名过滤 
     * @param path           文件夹 
     */  
	public static void list(File path) {
		if (!path.exists()) {
			System.out.println("文件名称不存在!");
		} else {
			if (path.isFile()) {
				if (path.getName().toLowerCase().endsWith(".pdf") || path.getName().toLowerCase().endsWith(".doc")
						|| path.getName().toLowerCase().endsWith(".chm")
						|| path.getName().toLowerCase().endsWith(".html")
						|| path.getName().toLowerCase().endsWith(".htm")) {// 文件格式
					System.out.println(path);
					System.out.println(path.getName());
				}
			} else {
				File[] files = path.listFiles();
				for (int i = 0; i < files.length; i++) {
					list(files[i]);
				}
			}
		}
	}
	
	/** 
     * 功能描述：拷贝一个目录或者文件到指定路径下，即把源文件拷贝到目标文件路径下 
     *  
     * @param source 源文件 
     * @param target 目标文件路径 
     * @return void 
     */  
	public static void copy(File source, File target) {
		File tarpath = new File(target, source.getName());
		if (source.isDirectory()) {
			tarpath.mkdir();
			File[] dir = source.listFiles();
			for (int i = 0; i < dir.length; i++) {
				copy(dir[i], tarpath);
			}
		} else {
			try {
				InputStream is = new FileInputStream(source); // 用于读取文件的原始字节流
				OutputStream os = new FileOutputStream(tarpath); // 用于写入文件的原始字节的流
				byte[] buf = new byte[1024];// 存储读取数据的缓冲区大小
				int len = 0;
				while ((len = is.read(buf)) != -1) {
					os.write(buf, 0, len);
				}
				is.close();
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}