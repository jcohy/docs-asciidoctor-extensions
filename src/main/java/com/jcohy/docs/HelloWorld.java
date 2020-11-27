package com.jcohy.docs;

import java.io.File;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.SafeMode;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2020/11/26:11:36
 * @since 1.0.0
 */
public class HelloWorld {


	public static void main(String[] args) {
		Asciidoctor asciidoctor = Asciidoctor.Factory.create(); // (1)
		asciidoctor.convertFile(                                // (2)
				new File(args[0]),
				OptionsBuilder.options()                        // (3)
						.toFile(true)
						.safe(SafeMode.UNSAFE));
	}
}
