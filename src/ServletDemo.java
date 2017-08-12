
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet implementation class ServletDemo
 */
@WebServlet("/ServletDemo")
public class ServletDemo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletDemo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String access_token = request.getParameter("access_token");

		System.out.println(access_token);
		// popUp true
		/*
		 * Content c =
		 * Request.Get("https://api.amazon.com/auth/o2/tokeninfo?access_token="
		 * +URLEncoder.encode(access_token, "UTF-8") ) .execute()
		 * .returnContent();
		 * 
		 * Map<String,Object> m =
		 * (Map<String,Object>)JSON.parseObject(c.toString());
		 * 
		 * if
		 * (!"amzn1.application-oa2-client.479dcc834fc342ec84077230b5383df0".equals
		 * (m.get("aud"))) { // the access token does not belong to us throw new
		 * RuntimeException("Invalid token"); }
		 * 
		 * 
		 * // exchange the access token for user profile // access_token =
		 * URLDecoder.decode(access_token, "UTF-8"); c =
		 * Request.Get("https://api.amazon.com/user/profile")
		 * .addHeader("Authorization", "bearer " + access_token) .execute()
		 * .returnContent();
		 * 
		 * System.out.println(c.toString());
		 * 
		 * m = (Map<String,Object>)JSON.parseObject(c.toString());
		 * 
		 * System.out.println(String.format("%s %s %s", m.get("name"),
		 * m.get("email"), m.get("user_id"))); }
		 */

		// popUp false

		Content c = Request
				.Get("https://api.amazon.com/auth/o2/tokeninfo?access_token="
						+ access_token).execute().returnContent();

		Map<String, Object> m = (Map<String, Object>) JSON.parseObject(c
				.toString());

		if (!"amzn1.application-oa2-client.479dcc834fc342ec84077230b5383df0"
				.equals(m.get("aud"))) {
			// the access token does not belong to us
			throw new RuntimeException("Invalid token");
		}

		// exchange the access token for user profile
		access_token = URLDecoder.decode(access_token, "UTF-8");
		c = Request.Get("https://api.amazon.com/user/profile")
				.addHeader("Authorization", "bearer " + access_token).execute()
				.returnContent();

		System.out.println(c.toString());

		m = (Map<String, Object>) JSON.parseObject(c.toString());

		System.out.println(String.format("%s %s %s", m.get("name"),
				m.get("email"), m.get("user_id")));
	}

}
