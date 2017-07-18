package com.edorasware.addons.controller;

import com.edorasware.addons.utils.Minifier;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by rovi on 12.07.17.
 */
@RestController
public class CustomJsController {

    @Value("classpath*:/addon*.js")
    private Resource[] jsFiles;

    @Value("classpath*:/addon*.css")
    private Resource[] cssFiles;

    @RequestMapping(value = "/custom.js", method = RequestMethod.GET)
    public void serveCustomJs(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "private, max-age=3600");

        //minify
        PrintStream codeps = new PrintStream(response.getOutputStream());
        PrintStream errorps = new PrintStream(System.err);
        Minifier minifier = new Minifier(new String[0], concatResourceStreams(jsFiles), codeps, errorps);
        minifier.minify();
        response.flushBuffer();
    }

    @RequestMapping(value = "/custom.css", method = RequestMethod.GET)
    public void serveCustomCss(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "private, max-age=3600");
        response.setHeader("Content-Type", "text/css");
        IOUtils.copy(concatResourceStreams(cssFiles), response.getOutputStream());
        response.flushBuffer();
    }

    private InputStream concatResourceStreams(Resource[] resources) throws IOException {
        InputStream contactStream = null;
        for (int i = 0; i < resources.length; i++) {
            if (resources.length == 1) {
                contactStream = resources[0].getInputStream();
                break;
            } else if (i == 0) {
                contactStream = new SequenceInputStream(resources[0].getInputStream(), resources[1].getInputStream());
                i = 2;
            } else if (contactStream != null) {
                contactStream = new SequenceInputStream(contactStream, resources[i].getInputStream());
            }
        }
        return contactStream;
    }
}
