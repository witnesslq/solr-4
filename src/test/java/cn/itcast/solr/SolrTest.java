package cn.itcast.solr;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.vo.SolrItem;

/**
 * Solr客户端：使用SolrJ来调用Solr服务
 * 
 * @author Administrator
 *
 */
public class SolrTest {

	private HttpSolrServer httpSolrServer;

	// 初始化HttpSolrServer
	@Before
	public void setUp() {
		// 参数为solr的访问地址
		httpSolrServer = new HttpSolrServer("http://localhost:8080/solr");
	}

	/**
	 * 第一种添加 文档的方式：每个域慢慢添加
	 * 
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@Test
	public void testAddDocument() throws SolrServerException, IOException {
		// 创建SolrInputDocument对象，通过它可以来添加一个个的域
		SolrInputDocument solrInputDocument = new SolrInputDocument();

		// 添加域
		/**
		 * 参数一：域名称，必须是在schema.xml中定义好的 参数二：域的值 注意：id域的值必须有
		 */
		solrInputDocument.addField("id", "12345");
		solrInputDocument.addField("title", "这是通过代码添加的SolrInputDucment方式");
		solrInputDocument.addField("price", "2323");
		solrInputDocument.addField("sellPoint", "我是传智播客座下大弟子");
		solrInputDocument.addField("image",
				"https://item.jd.com/10941037480.html?jd_pop=4864585e-2568-45ef-ac88-c360f0bdb039&abt=0");
		solrInputDocument.addField("status", 1);

		// 将文档添加到索引库索引化4
		httpSolrServer.add(solrInputDocument);

		// 提交，更新后记得提交
		httpSolrServer.commit();

	}

	/**
	 * 失败案例 添加文档到索引库的方式二：通过JavaBean
	 * 
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@Test
	public void addDocumentByBean() throws IOException, SolrServerException {
		// 创建提交的对象
		SolrItem solrItem = new SolrItem();

		// 赋值
		solrItem.setId("34342");
		solrItem.setTitle("我是传智播客马仔");
		solrItem.setPrice(3434L);
		solrItem.setSellPoint("松峰莉璃");
		solrItem.setImage("http:");
		solrItem.setStatus(0);

		// 将文档添加到索引库索引化
		httpSolrServer.addBean(solrItem);

		// 提交
		httpSolrServer.commit();
	}

	/**
	 * 删除索引一：根据索引删除
	 * 
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@Test
	public void testDeleteIndexById() throws SolrServerException, IOException {
		// 根据id删除
		httpSolrServer.deleteById("34342");
		// 提交
		httpSolrServer.commit();
	}

	/**
	 * 删除索引一：根据其他删除
	 * 
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@Test
	public void testDeleteIndexByWhere() throws SolrServerException, IOException {
		// 根据id删除
		httpSolrServer.deleteByQuery("title:传智播客");
		// 提交
		httpSolrServer.commit();
	}

	/**
	 * 查询方式一：
	 * 
	 * @throws SolrServerException
	 */
	@Test
	public void testSearch1() throws SolrServerException {
		// 创建查询对象
		SolrQuery solrQuery = new SolrQuery();

		// 设置查询条件：标题中带有联想的
		solrQuery.setQuery("title:TCL");

		// 价格在39900 44900之间，追加过滤条件
		// solrQuery.setFilterQueries("price:[39900 TO 44900]");

		// 按照价格降序排序
		solrQuery.setSort("id", ORDER.desc);

		// 分页索引
		/**
		 * 索引。 页面大小
		 */
		solrQuery.setStart(0);
		solrQuery.setRows(10);

		// 设置高亮
		solrQuery.setHighlight(true); // 是否需要高亮
		solrQuery.addHighlightField("title"); // 设置需要高亮的域
		solrQuery.setHighlightSimplePre("<em>");// 设置高亮的前缀标签
		solrQuery.setHighlightSimplePost("</em>"); // 设置高亮后缀标签

		// 执行查询
		QueryResponse response = httpSolrServer.query(solrQuery);

		// 获取总记录数
		long total = response.getResults().getNumFound();
		System.out.println("总记录数" + total);

		// 获取查询结果
		List<SolrItem> list = response.getBeans(SolrItem.class);

		// 遍历
		if (list != null && list.size() > 0) {
			// 获得高亮数据
			Map<String, Map<String, List<String>>> map = response.getHighlighting();

			for (SolrItem item : list) {
				String title = map.get(item.getId().toString()).get("title").get(0);
				System.out.println("高亮标题" + title);
				System.out.println(item);
			}
		}

	}

	/**
	 * 查询方式二：不适用JavaBean
	 * @throws SolrServerException 
	 */

	@Test
	public void testSearch2() throws SolrServerException {
		// 创建查询对象
		SolrQuery solrQuery = new SolrQuery();

		// 设置查询条件：标题中带有联想的
		solrQuery.setQuery("title:TCL");

		// 价格在39900 44900之间，追加过滤条件
		// solrQuery.setFilterQueries("price:[39900 TO 44900]");

		// 按照价格降序排序
		solrQuery.setSort("id", ORDER.desc);

		// 分页索引
		/**
		 * 索引。 页面大小
		 */
		solrQuery.setStart(0);
		solrQuery.setRows(10);

		// 设置高亮
		solrQuery.setHighlight(true); // 是否需要高亮
		solrQuery.addHighlightField("title"); // 设置需要高亮的域
		solrQuery.setHighlightSimplePre("<em>");// 设置高亮的前缀标签
		solrQuery.setHighlightSimplePost("</em>"); // 设置高亮后缀标签

		// 执行查询
		QueryResponse response = httpSolrServer.query(solrQuery);

		//获得查询结果
		SolrDocumentList list = response.getResults();
		
		// 获取总记录数
		long total = response.getResults().getNumFound();
		System.out.println("总记录数" + total);
		
		//处理结果
		if(list != null && list.size() > 0){
			
			//获得高亮数据
			Map<String, Map<String, List<String>>> map = response.getHighlighting();
			
			for(SolrDocument  solrDocument : list){
				System.out.println("------------------------------------");
				System.out.println("id为："+solrDocument.get("id"));
				//高亮数据
				String title = map.get(solrDocument.get("id").toString()).get("title").get(0);
				System.out.println("高亮title为："+title);
				System.out.println("price为："+solrDocument.get("price"));
			}
		}
	}

}
