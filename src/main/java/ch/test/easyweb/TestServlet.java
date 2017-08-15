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
		resp.getWriter().print(gitTest.testByDate(req.getParameter("strFrom"), req.getParameter("strTo")));
	}
}