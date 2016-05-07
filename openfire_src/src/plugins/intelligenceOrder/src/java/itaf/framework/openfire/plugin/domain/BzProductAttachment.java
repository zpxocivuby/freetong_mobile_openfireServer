package itaf.framework.openfire.plugin.domain;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

/**
 * 
 * 商品附件
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzProductAttachment {

	private Long id;
	private Long bzProductId;
	private String srcFileName;
	private String fileName;
	private Long fileType;
	private String fileExt;

	public BzProductAttachment() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBzProductId() {
		return bzProductId;
	}

	public void setBzProductId(Long bzProductId) {
		this.bzProductId = bzProductId;
	}

	public String getSrcFileName() {
		return this.srcFileName;
	}

	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileType() {
		return this.fileType;
	}

	public void setFileType(Long fileType) {
		this.fileType = fileType;
	}

	public String getFileExt() {
		return this.fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

}
