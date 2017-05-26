package net.realmproject.dcm.stock.examples.webserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.print.attribute.standard.PrinterMessageFromOperator;
import javax.servlet.http.HttpServletResponse;

import net.realmproject.dcm.parcel.core.Parcel;

public class FileServer implements Function<Parcel<?>, Parcel<?>> {

	private String basepath = ".";
	
	public FileServer() {}
	
	@Override
	public Parcel<?> apply(Parcel<?> parcel) {
		WebContext cx = (WebContext) parcel.getPayload();
		
		String relpath = String.join("/", cx.getRemainingPath());
		String pathname = basepath + relpath;
		Path path = Paths.get(pathname);
		
		HttpServletResponse r = cx.getResponse();
		r.setContentType("text/html;charset=utf-8");

		
		
		
		try {
		

						
			if (Files.exists(path)) {

				r.setContentType("text/html;charset=utf-8");
		        r.setStatus(HttpServletResponse.SC_OK);

				if (Files.isDirectory(path)) {

					writeHeader(r.getWriter(), relpath);
					
					List<String> filePath = new ArrayList<>(); 
					for (Path p : Files.list(path).collect(Collectors.toList())) {
						filePath.clear();					
						filePath.addAll(cx.getTraversedPath());
						filePath.addAll(cx.getRemainingPath());
						filePath.add(p.getFileName().toString());
						
						r.getWriter().println("<a href='" + "/" + String.join("/", filePath) + "'>" + p.getFileName() + "</a><br />");
					}
					
					writeFooter(r.getWriter());
					
									
				} else {
			        
										
					for (String l : Files.lines(path).collect(Collectors.toList())) {
						r.getWriter().println(l);
					}
					
				}
				
				

				
				
				
			} else {
				r.setContentType("text/html;charset=utf-8");
		        r.setStatus(HttpServletResponse.SC_NOT_FOUND);
		        writeHeader(r.getWriter(), relpath);
				r.getWriter().println("<h1>404</h1><br/><hr/>File not found: " + relpath);
				writeFooter(r.getWriter());
			}
			
		} catch (IOException e) {
	        r.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return parcel;
	}

	
	private void writeHeader(PrintWriter w, String title) {
		w.println("<html><head><style>");
		w.println("body {font-family: sans; background: #f7f7f7; margin-top: 40px;}");
		w.println("#dir-outer {padding: 40px; max-width: 700px; margin: 0 auto; background: white; border-radius: 3px; box-shadow: 0px 1px 4px #aaa;}");
		w.println("#dir-inner { }");
		w.println("</style></head><body>");
		
		w.println("<div id='dir-outer'>");
		w.println("<div id='dir-inner'>");
		w.println("<div style='font-size: 200%;'>" + title + "</div><br/><br/>");
	}

	private void writeFooter(PrintWriter w) {
		w.println("</div>");
		w.println("</div>");
		w.println("</body></html>");
	}

	
	public String getBasepath() {
		return basepath;
	}

	public void setBasepath(String basepath) {
		this.basepath = basepath;
		if (!this.basepath.endsWith("/")) {
			this.basepath += "/";
		}
	}
	
	
	
}
