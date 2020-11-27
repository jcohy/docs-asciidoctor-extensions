package com.jcohy.docs.asciidoctor.block;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.asciidoctor.ast.ContentModel;
import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.BlockProcessor;
import org.asciidoctor.extension.Contexts;
import org.asciidoctor.extension.Name;
import org.asciidoctor.extension.Reader;

import static java.util.stream.Collectors.joining;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2020/11/27:17:19
 * @since 1.0.0
 */
@Contexts(Contexts.PARAGRAPH)
@ContentModel(ContentModel.COMPOUND)
@Name("yell")
public class YellStaticBlock extends BlockProcessor {

	@Override
	public Object process(StructuralNode parent, Reader reader, Map<String, Object> attributes) {
		String upperLines = reader.readLines().stream()
				.map(String::toUpperCase)
				.collect(joining("\n"));

		return createBlock(
				parent,
				"paragraph",
				upperLines,
				attributes,
				new HashMap<>());
	}

}
