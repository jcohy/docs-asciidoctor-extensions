package com.jcohy.docs.asciidoctor.block;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.OptionsBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class YellStaticBlockTest {


	@Test
	public void yellStaticBlockTest(){
		Asciidoctor asciidoctor = Asciidoctor.Factory.create();
		asciidoctor.javaExtensionRegistry().block(YellStaticBlock.class);

		final String doc = "[yell]\nHello World";

		final String result = asciidoctor.convert(doc, OptionsBuilder.options());
		Document htmlDoc =  Jsoup.parse(result);
		assertEquals("HELLO WORLD", htmlDoc.select("p").first().text());
	}
}