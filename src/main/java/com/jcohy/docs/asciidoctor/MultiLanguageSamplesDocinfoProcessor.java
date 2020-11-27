package com.jcohy.docs.asciidoctor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.asciidoctor.ast.Document;
import org.asciidoctor.extension.DocinfoProcessor;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2020/11/26:16:07
 * @since 1.0.0
 */
public class MultiLanguageSamplesDocinfoProcessor extends DocinfoProcessor {
	public MultiLanguageSamplesDocinfoProcessor() {
		super(new HashMap<>());
	}

	public MultiLanguageSamplesDocinfoProcessor(Map<String, Object> config) {
		super(config);
	}

	@Override
	public String process(Document document) {
		return "<style type=\"text/css\">" + this.readResourceContent("/multi-language-samples.css") + "</style><script type=\"text/javascript\">" + this.readResourceContent("/multi-language-samples.js") + "</script>";
	}


//	@Override
//	public String process(DocumentRuby documentRuby) {
//		return this.process(this.document(documentRuby));
//	}

	private String readResourceContent(String resourcePath) {
		InputStream inputStream = null;
		String str;
		try {
			inputStream = MultiLanguageSamplesDocinfoProcessor.class.getResourceAsStream(resourcePath);
			str = IOUtils.toString(inputStream, StandardCharsets.UTF_8);;
		} catch (Exception e) {
			throw new IllegalStateException("Unable to read source resource for MultiLanguageSamples: " + e.getMessage());
		} finally {
			try {
				if(inputStream != null){
					inputStream.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
}
