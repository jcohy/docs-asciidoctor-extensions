package com.jcohy.docs.asciidoctor.macro;

import java.util.HashMap;
import java.util.Map;

import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.extension.InlineMacroProcessor;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2020/11/27:17:52
 * @since 1.0.0
 */
public class YellMacroProcessor extends InlineMacroProcessor {

	public YellMacroProcessor(String macroName, Map<String, Object> config) {
		super(macroName, config);
	}

	@Override
	public Object process(ContentNode parent, String target, Map<String, Object> attributes) {
		return createPhraseNode(parent,"quoted", target.toUpperCase(), attributes, new HashMap<>());
	}
}