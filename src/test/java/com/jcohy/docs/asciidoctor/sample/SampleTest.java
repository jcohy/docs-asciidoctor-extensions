package com.jcohy.docs.asciidoctor.sample;

import java.io.File;
import java.util.HashMap;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Asciidoctor.Factory;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.SafeMode;
import org.junit.Test;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2020/11/27:18:03
 * @since 1.0.0
 */
public class SampleTest {

	@Test
	public void test01(){

		Asciidoctor asciidoctor = Asciidoctor.Factory.create();
		asciidoctor.convertFile(
				new File("document.adoc"),
				OptionsBuilder.options()
						.toFile(true)
						.safe(SafeMode.UNSAFE));
	}


	@Test
	public void test02(){
		Asciidoctor asciidoctor = Factory.create();
		String html = asciidoctor.convert(
				"Writing AsciiDoc is _easy_!",
				new HashMap<String, Object>());
		System.out.println(html);
	}

}
