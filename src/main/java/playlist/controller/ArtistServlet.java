package playlist.controller;

import playlist.model.ArtistsDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

/**
 * DataStax Academy Sample Application
 *
 * Copyright 2013 DataStax
 *
 */

public class ArtistServlet extends HttpServlet {

  private static final Logger logger = Logger.getLogger(ArtistServlet.class.getName());

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String q = request.getParameter("q");
    String dir = request.getParameter("order");
    String frame = request.getParameter("frame");

    boolean desc = dir != null && dir.contentEquals("down");
    logger.log(Level.INFO, "desc is {0}", desc);

    List<String> artists = null;
    if (q != null && !q.isEmpty()) {
      artists = ArtistsDAO.listArtistByLetter(q, desc);
    }

    request.setAttribute("artists", artists);
    request.setAttribute("frame", frame);
    request.setAttribute("q", q);
    getServletContext().getRequestDispatcher("/artists.jsp").forward(request,response);

  }
}
