package com.jcohy.docs.asciidoctor;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.asciidoctor.extension.spi.ExtensionRegistry;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2020/11/26:16:10
 * @since 1.0.0
 */
public class GradleDocsAsciidoctorExtensionRegistry implements ExtensionRegistry {
	private static final String HEAD_HTML_PATH = "/head.html";
	private static final String HEADER_HTML_PATH = "/header.html";
	private static final String FOOTER_HTML_PATH = "/footer.html";
	private String headHtml;
	private String headerHtml;
	private String footerHtml;

	public GradleDocsAsciidoctorExtensionRegistry() {
	}

	@Override
	public void register(Asciidoctor asciidoctor) {
		this.initializeHtmlToInject();
		JavaExtensionRegistry registry = asciidoctor.javaExtensionRegistry();
		registry.docinfoProcessor(MetadataDocinfoProcessor.class);
		registry.docinfoProcessor(new NavigationDocinfoProcessor(new HashMap<>(), headHtml));
		registry.docinfoProcessor(new MultiLanguageSamplesDocinfoProcessor());
		registry.postprocessor(new HeaderInjectingPostprocessor(new HashMap<>(), headerHtml));
		Map<String, Object> footerOptions = new HashMap<>();
		footerOptions.put("location", ":footer");
		registry.docinfoProcessor(new NavigationDocinfoProcessor(footerOptions, footerHtml));
		registry.includeProcessor(GuidesContributeIncludeProcessor.class);
		registry.includeProcessor(SampleIncludeProcessor.class);
	}

	private void initializeHtmlToInject() {
		this.headHtml = this.loadResource("/head.html");
		this.headerHtml = this.loadResource("/header.html");
		this.footerHtml = this.loadResource("/footer.html");
	}

	private String loadResource(String resourcePath) {
		try {
			URL in = this.getClass().getResource(resourcePath);
			if (in == null) {
				System.out.println("Docs Asciidoctor Extension did not find a resource for " + resourcePath);
				return "";
			} else {
				return IOUtils.toString(in, "UTF-8");
			}
		} catch (IOException var3) {
			throw new RuntimeException("Could not read HTML file at " + resourcePath);
		}
	}
}
