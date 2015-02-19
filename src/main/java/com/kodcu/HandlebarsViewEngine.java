package com.kodcu;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.BlockHelper;
import com.github.jknack.handlebars.helper.EachHelper;
import com.github.jknack.handlebars.helper.EmbeddedHelper;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.mvc.Models;
import javax.mvc.engine.Priorities;
import javax.mvc.engine.ViewEngine;
import javax.mvc.engine.ViewEngineContext;
import javax.mvc.engine.ViewEngineException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by usta on 10.02.2015.
 */
@Priority(Priorities.DEFAULT)
public class HandlebarsViewEngine implements ViewEngine {

    @Inject
    private ServletContext servletContext;

    @Override
    public boolean supports(String view) {
        return view.endsWith(".hbs");
    }

    @Override
    public void processView(ViewEngineContext context) throws ViewEngineException {

        Models models = context.getModels();
        String viewName = context.getView();
        if (!viewName.startsWith("/"))
            viewName = "/" + viewName;

        try (PrintWriter writer = context.getResponse().getWriter();
             InputStream resourceAsStream = servletContext.getResourceAsStream(viewName);
             InputStreamReader in = new InputStreamReader(resourceAsStream, "UTF-8");
             BufferedReader bufferedReader = new BufferedReader(in);) {

            String viewContent = bufferedReader.lines().collect(Collectors.joining());

            Handlebars handlebars = new Handlebars();
            Template template = handlebars.compileInline(viewContent);
            template.apply(models, writer);

        } catch (IOException e) {
            throw new ViewEngineException(e);
        }

    }
}
