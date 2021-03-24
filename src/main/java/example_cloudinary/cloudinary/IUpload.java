package example_cloudinary.cloudinary;

public interface IUpload {
	
	public String getURLImg(String localPath,String fileName,String serverPath,String currentPath);
	
	public String[] properties(String realPath);
}
