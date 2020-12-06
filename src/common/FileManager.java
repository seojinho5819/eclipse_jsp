package common;

public class FileManager {
	//확장자만 추출하기
	public static String getExtend(String path) {
		int lastIndex = path.lastIndexOf(".");
		String ext = path.substring(lastIndex+1,path.length());
		
		return ext;
	}
	
//	public static void main(String[] args) {
//		String filename = "";
//		getExtend(filename);
//	}
}
