package org.jsoup.integration.servlets;

import org.jsoup.integration.TestServer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectServlet extends BaseServlet {
    public static final String Url;
    public static final String TlsUrl;
    static {
        TestServer.ServletUrls urls = TestServer.map(RedirectServlet.class);
        Url = urls.url;
        TlsUrl = urls.tlsUrl;
    }
    public static final String LocationParam = "loc";
    public static final String CodeParam = "code";
    public static final String SetCookiesParam = "setCookies";
    private static final int DefaultCode = HttpServletResponse.SC_MOVED_TEMPORARILY;

    @Override
    protected void doIt(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String location = req.getParameter(LocationParam);
        if (location == null)
            location = "";

        int intCode = DefaultCode;
        String code = req.getParameter(CodeParam);
        if (code != null)
            intCode = Integer.parseInt(code);

        if (req.getParameter(SetCookiesParam) != null) {
            res.addCookie(new Cookie("token", "asdfg123"));
            res.addCookie(new Cookie("uid", "foobar"));
            res.addCookie(new Cookie("uid", "jhy")); // dupe, should use latter
        }

        res.setHeader("Location", location);
        res.setStatus(intCode);
        res.flushBuffer();
    }

}
