import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

@Controller
public class UrlForwardTest extends HttpServlet implements Filter {

	// Spring-related test cases
	@GetMapping("/bad1")
	public ModelAndView bad1(String url) {
		return new ModelAndView(url); // $ hasUrlForward
	}

	@GetMapping("/bad2")
	public ModelAndView bad2(String url) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(url); // $ hasUrlForward
		return modelAndView;
	}

	@GetMapping("/bad3")
	public String bad3(String url) {
		return "forward:" + url + "/swagger-ui/index.html"; // $ hasUrlForward
	}

	@GetMapping("/bad4")
	public ModelAndView bad4(String url) {
		ModelAndView modelAndView = new ModelAndView("forward:" + url); // $ hasUrlForward
		return modelAndView;
	}

	@GetMapping("/bad5")
	public void bad5(String url, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher(url).include(request, response); // $ hasUrlForward
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/bad6")
	public void bad6(String url, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/WEB-INF/jsp/" + url + ".jsp").include(request, response); // $ hasUrlForward
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/bad7")
	public void bad7(String url, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/WEB-INF/jsp/" + url + ".jsp").forward(request, response); // $ hasUrlForward
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/good1")
	public void good1(String url, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("/index.jsp?token=" + url).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Non-Spring test cases (UnsafeRequest*Path*)
	private static final String BASE_PATH = "/pages";

	@Override
	// BAD: Request dispatcher from servlet path without check
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		String path = ((HttpServletRequest) request).getServletPath();
		// A sample payload "/%57EB-INF/web.xml" can bypass this `startsWith` check
		if (path != null && !path.startsWith("/WEB-INF")) {
			request.getRequestDispatcher(path).forward(request, response); // $ hasUrlForward
		} else {
			chain.doFilter(request, response);
		}
	}

	// BAD: Request dispatcher from servlet path with check that does not decode
	// the user-supplied path; could bypass check with ".." encoded as "%2e%2e".
	public void doFilter2(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		String path = ((HttpServletRequest) request).getServletPath();

		if (path.startsWith(BASE_PATH) && !path.contains("..")) {
			request.getRequestDispatcher(path).forward(request, response); // $ hasUrlForward
		} else {
			chain.doFilter(request, response);
		}
	}

	// GOOD: Request dispatcher from servlet path with whitelisted string comparison
	public void doFilter3(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		String path = ((HttpServletRequest) request).getServletPath();

		if (path.equals("/comaction")) {
			request.getRequestDispatcher(path).forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	// Non-Spring test cases (UnsafeServletRequest*Dispatch*)
	@Override
	// BAD: Request dispatcher constructed from `ServletContext` without input validation
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String returnURL = request.getParameter("returnURL");

		ServletConfig cfg = getServletConfig();
		if (action.equals("Login")) {
			ServletContext sc = cfg.getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/Login.jsp");
			rd.forward(request, response);
		} else {
			ServletContext sc = cfg.getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher(returnURL); // $ hasUrlForward
			rd.forward(request, response);
		}
	}

	@Override
	// BAD: Request dispatcher constructed from `HttpServletRequest` without input validation
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String returnURL = request.getParameter("returnURL");

		if (action.equals("Login")) {
			RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher(returnURL); // $ hasUrlForward
			rd.forward(request, response);
		}
	}

	@Override
	// GOOD: Request dispatcher with a whitelisted URI
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action.equals("Login")) {
			RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");
			rd.forward(request, response);
		} else if (action.equals("Register")) {
			RequestDispatcher rd = request.getRequestDispatcher("/Register.jsp");
			rd.forward(request, response);
		}
	}

	// BAD: Request dispatcher without path traversal check
	protected void doHead2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("path");

		// A sample payload "/pages/welcome.jsp/../WEB-INF/web.xml" can bypass the `startsWith` check
		if (path.startsWith(BASE_PATH)) {
			request.getServletContext().getRequestDispatcher(path).include(request, response); // $ hasUrlForward
		}
	}

	// BAD: Request dispatcher with path traversal check that does not decode
	// the user-supplied path; could bypass check with ".." encoded as "%2e%2e".
	protected void doHead3(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("path");

		if (path.startsWith(BASE_PATH) && !path.contains("..")) {
			request.getServletContext().getRequestDispatcher(path).include(request, response); // $ hasUrlForward
		}
	}

	// BAD: Request dispatcher with path normalization and comparison, but
	// does not decode before normalization.
	protected void doHead4(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("path");

		// Since not decoded before normalization, "%2e%2e" can remain in the path
		Path requestedPath = Paths.get(BASE_PATH).resolve(path).normalize();

		if (requestedPath.startsWith(BASE_PATH)) {
			request.getServletContext().getRequestDispatcher(requestedPath.toString()).forward(request, response); // $ hasUrlForward
		}
	}

	// BAD: Request dispatcher with negation check and path normalization, but without URL decoding.
	protected void doHead5(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("path");
		// Since not decoded before normalization, "/%57EB-INF" can remain in the path and pass the `startsWith` check.
		Path requestedPath = Paths.get(BASE_PATH).resolve(path).normalize();

		if (!requestedPath.startsWith("/WEB-INF") && !requestedPath.startsWith("/META-INF")) {
			request.getServletContext().getRequestDispatcher(requestedPath.toString()).forward(request, response); // $ hasUrlForward
		}
	}

	// BAD: Request dispatcher with path traversal check and single URL decoding; may be vulnerable to double-encoding
	protected void doHead7(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("path");
		path = URLDecoder.decode(path, "UTF-8");

		if (!path.startsWith("/WEB-INF/") && !path.contains("..")) {
			request.getServletContext().getRequestDispatcher(path).include(request, response); // $ hasUrlForward
		}
	}

	// GOOD: Request dispatcher with path traversal check and URL decoding in a loop to avoid double-encoding bypass
	protected void doHead6(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("path"); // TODO: remove this debugging comment: // v

		if (path.contains("%")){ // TODO: remove this debugging comment: // v.getAnAccess()
			while (path.contains("%")) {
				path = URLDecoder.decode(path, "UTF-8");
			}
		}

		if (!path.startsWith("/WEB-INF/") && !path.contains("..")) {
			request.getServletContext().getRequestDispatcher(path).include(request, response);
		}
	}

	// GOOD: Request dispatcher with URL encoding check and path traversal check
	protected void doHead16(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("path");

		if (!path.contains("%")){
			if (!path.startsWith("/WEB-INF/") && !path.contains("..")) {
				request.getServletContext().getRequestDispatcher(path).include(request, response);
			}
		}
	}

	// TODO: clean-up
	// BAD (I added): Request dispatcher with path traversal check and single URL decoding; may be vulnerable to double-encoding
	// Tests urlEncoding BarrierGuard "a guard that considers a string safe because it is checked for URL encoding sequences,
    // having previously been checked against a block-list of forbidden values."
	protected void doHead10(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("path");
		if (path.contains("%")){ // BAD: wrong check
		if (!path.startsWith("/WEB-INF/") && !path.contains("..")) {
			// if (path.contains("%")){ // BAD: wrong check
				request.getServletContext().getRequestDispatcher(path).include(request, response); // $ hasUrlForward
			// }
		}
	}
	}

	// TODO: clean-up
	// "GOOD" (I added): Request dispatcher with path traversal check and single URL decoding; may be vulnerable to double-encoding
	// Tests urlEncoding BarrierGuard "a guard that considers a string safe because it is checked for URL encoding sequences,
    // having previously been checked against a block-list of forbidden values."
	protected void doHead11(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("path");

		if (!path.startsWith("/WEB-INF/") && !path.contains("..")) {
			if (!path.contains("%")){ // GOOD: right check
				request.getServletContext().getRequestDispatcher(path).include(request, response);
			}
		}
	}

	// GOOD: Request dispatcher with path traversal check and URL decoding in a loop to avoid double-encoding bypass
	protected void doHead8(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("path"); // TODO: remove this debugging comment: // v
		while (path.contains("%")) {
			path = URLDecoder.decode(path, "UTF-8");
		}

		if (!path.startsWith("/WEB-INF/") && !path.contains("..")) {
			request.getServletContext().getRequestDispatcher(path).include(request, response);
		}
	}

	// TODO: see if can fix?
	// FP now....
	// GOOD: Request dispatcher with path traversal check and URL decoding in a loop to avoid double-encoding bypass
	protected void doHead9(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getParameter("path"); // v
		boolean hasEncoding = path.contains("%");
		while (hasEncoding) {
			path = URLDecoder.decode(path, "UTF-8");
			hasEncoding = path.contains("%");
		}

		if (!path.startsWith("/WEB-INF/") && !path.contains("..")) {
			request.getServletContext().getRequestDispatcher(path).include(request, response); // $ SPURIOUS: hasUrlForward
		}
	}

	// BAD: `StaplerResponse.forward` without any checks
	public void generateResponse(StaplerRequest req, StaplerResponse rsp, Object obj) throws IOException, ServletException {
		String url = req.getParameter("target");
		rsp.forward(obj, url, req); // $ hasUrlForward
	}

	// QHelp example
	private static final String VALID_FORWARD = "https://cwe.mitre.org/data/definitions/552.html";

	protected void doGet2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletConfig cfg = getServletConfig();
		ServletContext sc = cfg.getServletContext();

		// BAD: a request parameter is incorporated without validation into a URL forward
		sc.getRequestDispatcher(request.getParameter("target")).forward(request, response); // $ hasUrlForward

		// GOOD: the request parameter is validated against a known fixed string
		if (VALID_FORWARD.equals(request.getParameter("target"))) {
			sc.getRequestDispatcher(VALID_FORWARD).forward(request, response);
		}
	}
}
