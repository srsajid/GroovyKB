

package com.compiler

import com.google.javascript.jscomp.CommandLineRunner

class TailorProductJsProcessor extends CommandLineRunner {
	static contentTypes = ['application/javascript']

	static version = 1;

	protected TailorProductJsProcessor(String[] args) {
		super(args)
	}

	public static void main(String[] args) {
		args = [
				"--js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\cms\\product.js",
				"--js_output_file",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\prod\\product.min.${version}.js"
		]
		new TailorProductJsProcessor(args).run()
	}
}
