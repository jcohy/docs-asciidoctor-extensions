package com.jcohy.docs.asciidoctor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.asciidoctor.ast.Document;
import org.asciidoctor.extension.IncludeProcessor;
import org.asciidoctor.extension.PreprocessorReader;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2020/11/26:14:51
 * @since 1.0.0
 */
public class SampleIncludeProcessor extends IncludeProcessor {

	public static final String SAMPLE = "sample";

	private static final Map<String,String> FILE_SUFFIX_TO_SYNTAX = initializeSyntaxMap();

	private static Map<String, String> initializeSyntaxMap() {
		Map<String, String> map = new HashMap<>();
		map.put("gradle", "groovy");
		map.put("kt", "kotlin");
		map.put("kts", "kotlin");
		map.put("py", "python");
		map.put("sh", "bash");
		map.put("rb", "ruby");
		return Collections.unmodifiableMap(map);
	}

	public SampleIncludeProcessor() {
		super(new HashMap<>());
	}

	public SampleIncludeProcessor(Map<String, Object> config) {
		super(config);
	}

	@Override
	public boolean handles(String target) {
		return target.equals(SAMPLE);
	}


//	public void process(DocumentRuby document, PreprocessorReader reader, String target, Map<String, Object> attributes) {
//		this.process(document(document), reader, target, attributes);
//	}
	@Override
	public void process(Document document, PreprocessorReader reader, String target, Map<String, Object> attributes) {
		if (attributes.containsKey("dir") && attributes.containsKey("files")) {
			String sampleBaseDir = document.getAttribute("samples-dir", ".").toString();
			String sampleDir = attributes.get("dir").toString();
			List<String> files = Arrays.asList(attributes.get("files").toString().split(";"));
			String sampleContent = getSampleContent(sampleBaseDir, sampleDir, files);
			reader.push_include(sampleContent, target, target, 1, attributes);
		} else {
			throw new IllegalStateException("Both the 'dir' and 'files' attributes are required to include a sample");
		}
	}

	private String getSampleContent(String sampleBaseDir, String sampleDir, List<String> files) {
		StringBuilder builder = new StringBuilder(String.format("%n[.testable-sample.multi-language-sample,dir=\"%s\"]%n=====%n", sampleDir));

		String sourceRelativeLocation;
		String sourceSyntax;
		String source;

		for(String file : files){
			sourceRelativeLocation = parseSourceFilePath(file);
			List<String> tags = parseTags(file);
			sourceSyntax = getSourceSyntax(sourceRelativeLocation);
			String sourcePath = String.format("%s/%s/%s", sampleBaseDir, sampleDir, sourceRelativeLocation);
			source = getContent(sourcePath);
			if(!tags.isEmpty()){
				source = filterByTag(source, sourceSyntax, tags);
			}
			builder.append(String.format(".%s%n[source,%s]%n----%n%s%n----%n", sourceRelativeLocation, sourceSyntax, source));
		}
		builder.append(String.format("=====%n"));
		return builder.toString();
	}

	private String filterByTag(String source, String sourceSyntax, List<String> tags) {
		Pattern tagPattern;
		if (!sourceSyntax.equals("html") && !sourceSyntax.equals("xml")) {
			tagPattern = Pattern.compile(".*(tag|end)::(\\S+)\\[]\\s*");
		} else {
			tagPattern = Pattern.compile("\\s*<!--\\s*(tag|end)::(\\S+)\\[]\\s*-->");
		}

		StringBuilder result = new StringBuilder(source.length());
		String activeTag = null;
		BufferedReader reader = new BufferedReader(new StringReader(source));

		String line;
		try {
			while((line = reader.readLine()) != null) {
				if (activeTag != null) {
					if (line.contains("end::" + activeTag + "[]")) {
						activeTag = null;
					} else if (!tagPattern.matcher(line).matches()) {
						result.append(line).append("\n");
					}
				} else {
					activeTag = this.determineActiveTag(line, tags);
				}
			}
		} catch (IOException var9) {
			throw new RuntimeException("Unexpected exception while filtering tagged content");
		}

		return result.toString();
	}

	private String determineActiveTag(String line, List<String> tags) {
		Iterator var3 = tags.iterator();

		String tag;
		do {
			if (!var3.hasNext()) {
				return null;
			}

			tag = (String)var3.next();
		} while(!line.contains("tag::" + tag + "[]"));

		return tag;
	}

	private String getContent(String sourcePath) {
		try {
			return new String(Files.readAllBytes(Paths.get(sourcePath)));
		} catch (IOException var3) {
			throw new IllegalStateException("Unable to read source file " + Paths.get(sourcePath).toAbsolutePath().toFile().getAbsolutePath());
		}
	}

	private String getSourceSyntax(String sourceRelativeLocation) {
		String syntax = "txt";
		int i = sourceRelativeLocation.lastIndexOf(46);
		if (i > 0) {
			String substring = sourceRelativeLocation.substring(i + 1);
			syntax = (String)FILE_SUFFIX_TO_SYNTAX.getOrDefault(substring, substring);
		}

		return syntax;
	}

	private List<String> parseTags(String files) {
		List<String> tags = new ArrayList<>();
		Pattern pattern = Pattern.compile(".*\\[tags?=(.*)].*");
		Matcher matcher = pattern.matcher(files);
		if(matcher.matches()){
			tags.addAll(Arrays.asList(matcher.group(1).split(".")));
		}
		return tags;
	}

	private String parseSourceFilePath(String file) {
		return file.replaceAll("\\[[^]]*]", "");
	}

}
