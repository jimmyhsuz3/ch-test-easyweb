package ch.test.easyweb;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ch.util.crc.GitTest;
public class TestServlet extends HttpServlet{
	private static final long serialVersionUID = -23149732195706072L;
	private GitTest gitTest = new GitTest();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("GitTest gitTest = " + gitTest);
		resp.setHeader("Content-Type", "text/plain; charset=utf-8"); // + jetty7
		String action = req.getParameter("action");
		if (action.equals("test")){
			boolean reset = Boolean.valueOf(req.getParameter("reset"));
			if(reset)
				gitTest.close();
			resp.getWriter().print(gitTest.test(reset));
		}else if (action.equals("testByDate"))
			resp.getWriter().print(gitTest.testByDate(req.getParameter("strFrom"), req.getParameter("strTo")));
		else if (action.equals("getGitFileList"))
			resp.getWriter().print(gitTest.getGitFileList());
		else if (action.equals("getGitFileHis"))
			resp.getWriter().print(gitTest.getGitFileHis(req.getParameter("pathString"), req.getParameter("url"), req.getParameter("commitId")));
		else if (action.equals("getGitFile"))
			gitTest.getGitFile(req.getParameter("pathString"), req.getParameter("url"),
					req.getParameter("commitId"), req.getParameter("objectId"), resp.getOutputStream());
		else if (action.equals("getGitFileDiff"))
			gitTest.getGitFileDiff(req.getParameter("pathString"), req.getParameter("url"),
					req.getParameter("commitId1"), req.getParameter("commitId2"), resp.getOutputStream());
		else if (action.equals("getAllUrl"))
			resp.getWriter().print(gitTest.getAllUrl());
		else if (action.equals("fetchLastCommit"))
			resp.getWriter().print(gitTest.fetchLastCommit(req.getParameter("url")));
		else if (action.equals("fetchAllLastCommit"))
			resp.getWriter().print(gitTest.fetchAllLastCommit());
		else if (action.equals("fetchRepoBranchCommit"))
			resp.getWriter().print(gitTest.fetchRepoBranchCommit(req.getParameter("url")));
		else if (action.equals("treeWalkFolder"))
			resp.getWriter().print(gitTest.treeWalkFolder(req.getParameter("url"), req.getParameter("pathString"), req.getParameter("commitId")));
		else if (action.equals("cleanClose"))
			resp.getWriter().print(gitTest.cleanClose());
		else if (action.equals("getPage")){
			resp.setHeader("Content-Type", "text/html; charset=utf-8");
			write(resp.getOutputStream(), "META-INF/html/" + req.getParameter("html"));
		}
	}
	private void write(java.io.OutputStream os, String resource){
		java.io.InputStream is = null;
		try {
			is = getClass().getClassLoader().getResourceAsStream(resource);
			byte[] b = new byte[8192];
			int len = 0;
			while ((len = is.read(b)) != -1)
				os.write(b, 0, len);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
	}
}