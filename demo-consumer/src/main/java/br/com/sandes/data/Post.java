package br.com.sandes.data;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_tb")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String postTitle;
	private String content;
	private UUID userid;
	
	public Post(UUID id, String postTitle, String content, UUID userid) {
		this.id = id;
		this.postTitle = postTitle;
		this.content = content;
		this.userid = userid;
	}

	public Post(String postTitle, String content, UUID userid) {
		this.postTitle = postTitle;
		this.content = content;
		this.userid = userid;
	}

	public Post() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UUID getUserid() {
		return userid;
	}

	public void setUserid(UUID userid) {
		this.userid = userid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, id, postTitle, userid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(content, other.content) && Objects.equals(id, other.id)
				&& Objects.equals(postTitle, other.postTitle) && Objects.equals(userid, other.userid);
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", postTitle=" + postTitle + ", content=" + content + ", userid=" + userid + "]";
	}
}
