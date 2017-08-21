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
			resp.getWriter().print(gitTest.getGitFileHis(req.getParameter("pathString"), req.getParameter("url")));
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
	}
}