

package com.compiler

import com.google.javascript.jscomp.CommandLineRunner

class ProductJsProcessor extends CommandLineRunner {
	static contentTypes = ['application/javascript']

	static version = 13;

	protected ProductJsProcessor(String[] args) {
		super(args)
	}

	public static void main(String[] args) {
		args = [
				"--js",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\javascript\\cms\\operator.js",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\javascript\\cms\\product.js",
				"--js_output_file",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\javascript\\prod\\product.min.${version}.js"
		]
		new ProductJsProcessor(args).run()
	}
}
