package com.jcohy.docs.asciidoctor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.asciidoctor.ast.Document;
import org.asciidoctor.extension.DocinfoProcessor;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2020/11/26:16:08
 * @since 1.0.0
 */
public class MetadataDocinfoProcessor extends DocinfoProcessor {
	private static final String META_NAME = "meta-name-";
	private static final String META_PROPERTY = "meta-property-";

	public MetadataDocinfoProcessor() {
		this(new HashMap<>());
	}

	public MetadataDocinfoProcessor(Map<String, Object> config) {
		super(config);
	}

	@Override
	public String process(Document document) {
		StringBuilder outputHtml = new StringBuilder();
		Map<String, Object> attributes = document.getAttributes();

		for (Map.Entry<String,Object> attr : attributes.entrySet()) {
			String attributeKey = (String)attr.getKey();
			String name;
			String content;
			if (attributeKey.startsWith("meta-name-")) {
				name = attributeKey.substring("meta-name-".length()).replaceAll("_", ":");
				content = attr.getValue().toString();
				outputHtml.append(String.format("<meta name=\"%s\" content=\"%s\">\n", name, content));
			} else if (attributeKey.startsWith("meta-property-")) {
				name = attributeKey.substring("meta-property-".length()).replaceAll("_", ":");
				content = attr.getValue().toString();
				outputHtml.append(String.format("<meta property=\"%s\" content=\"%s\">\n", name, content));
			}
		}
		return outputHtml.toString();
	}

	public Object process(Object document, Object output) {
		return output;
	}
}
