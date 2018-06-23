

package com.google.javascript.jscomp

class ProductJsProcessor extends CommandLineRunner {
	static contentTypes = ['application/javascript']

	static version = 4;

	protected ProductJsProcessor(String[] args) {
		super(args)
	}

	public static void main(String[] args) {
		args = [
				"--js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\jquery\\magnific\\jquery.magnific-popup.min.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\operator.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\product.js",
				"--js_output_file",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\product.min.${version}.js"
		]
		new ProductJsProcessor(args).run()

	}
}
