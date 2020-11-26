package com.jcohy.docs.asciidoctor;

import java.util.Map;

import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.DocumentRuby;
import org.asciidoctor.extension.DocinfoProcessor;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2020/11/26:16:06
 * @since 1.0.0
 */
public class NavigationDocinfoProcessor extends DocinfoProcessor {
	private final String html;

	NavigationDocinfoProcessor(Map<String, Object> config, String html) {
		super(config);
		this.html = html;
	}

	@Override
	public String process(Document document) {
		return this.html;
	}

	@Override
	public String process(DocumentRuby documentRuby) {
		return this.process(this.document(documentRuby));
	}
}
