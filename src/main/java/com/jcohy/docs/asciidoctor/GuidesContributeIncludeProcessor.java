package com.jcohy.docs.asciidoctor;

import java.util.HashMap;
import java.util.Map;

import org.asciidoctor.ast.Document;
import org.asciidoctor.extension.IncludeProcessor;
import org.asciidoctor.extension.PreprocessorReader;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2020/11/26:16:09
 * @since 1.0.0
 */
public class GuidesContributeIncludeProcessor extends IncludeProcessor {
	private static final String CONTRIBUTE = "contribute";

	public GuidesContributeIncludeProcessor() {
		super(new HashMap<>());
	}

	public GuidesContributeIncludeProcessor(Map<String, Object> config) {
		super(config);
	}

	@Override
	public boolean handles(String target) {
		return target.equals("contribute");
	}


	@Override
	public void process(Document document, PreprocessorReader reader, String target, Map<String, Object> attributes) {
		String contributeMessage = this.getContributeMessage((String)attributes.getOrDefault("guide-name", document.getAttributes().get("guide-name")));
		reader.push_include(contributeMessage, target, target, 1, attributes);
	}

	private String issueUrl(String guideName) {
		return guideName == null ? "https://github.com/gradle/guides/issues/new" : String.format("https://github.com/gradle/guides/issues/new?labels=in:%s", guideName);
	}

	private String repositoryUrl(String guideName) {
		return guideName == null ? "https://github.com/gradle/guides" : String.format("https://github.com/gradle/guides/tree/master/subprojects/%s", guideName);
	}

	private String getContributeMessage(String guideName) {
		return String.format("%n[.contribute]%n== Help improve this guide%n%nHave feedback or a question? Found a typo? Like all Gradle guides, help is just a GitHub issue away. Please %s[add an issue] or pull request to %s[gradle/guides] and we'll get back to you.", this.issueUrl(guideName), this.repositoryUrl(guideName));
	}
}
