package com.jcohy.docs.asciidoctor;

import java.util.Map;

import org.asciidoctor.ast.Document;
import org.asciidoctor.extension.Postprocessor;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2020/11/26:16:09
 * @since 1.0.0
 */
public class HeaderInjectingPostprocessor extends Postprocessor {
	private final String headerHtml;

	HeaderInjectingPostprocessor(Map<String, Object> config, String headerHtml) {
		super(config);
		this.headerHtml = headerHtml;
	}

	@Override
	public String process(Document document, String output) {
		return !document.basebackend("html") ? output : output.replaceAll("<div id=\"header\">", this.headerHtml + "<div id=\"header\">");
	}

//	@Override
//	public String process(DocumentRuby documentRuby, String output) {
//		return this.process(this.document(documentRuby), output);
//	}

	public Object process(Object document, Object output) {
		return output;
	}
}
