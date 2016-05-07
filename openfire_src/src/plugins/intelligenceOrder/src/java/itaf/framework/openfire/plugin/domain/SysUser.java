package itaf.framework.openfire.plugin.domain;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1


/**
 * 
 * 系统用户
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class SysUser {

	private Long id;
	private String username;
	private String password;
	private String mobile;
	private Long type;

	public SysUser() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

}
