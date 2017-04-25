package cn.itcast.vo;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 进行添加文档对象
 * @author Administrator
 *
 */
public class SolrItem {
	/**
	 *     <field name="id" type="string" indexed="true" stored="true" required="true" multiValued="false" />    
		   <field name="title" type="text_ik" indexed="true" stored="true"  required="true" multiValued="false"/>
		   <field name="price" type="long" indexed="true" stored="true"/>
		   <field name="sellPoint" type="string" indexed="false" stored="true"/>
		   <field name="image" type="string" indexed="false" stored="true"/>
		   <field name="status" type="int" indexed="true" stored="false"/>
	 */
	@Field("id")
	private String id;
	
	@Field("title")
	private String title;
	
	@Field("price")
	private Long price;
	
	@Field("sellPoint")
	private String sellPoint;
	
	@Field("image")
	private String image;
	
	@Field("status")
	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SolrItem [id=" + id + ", title=" + title + ", price=" + price + ", sellPoint=" + sellPoint + ", image="
				+ image + ", status=" + status + "]";
	}
	
	
	
	
	
}
