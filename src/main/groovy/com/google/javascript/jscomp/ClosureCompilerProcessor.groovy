

package com.google.javascript.jscomp

import com.google.javascript.jscomp.CommandLineRunner

class ClosureCompilerProcessor extends CommandLineRunner {
	static contentTypes = ['application/javascript']

	static version = 2;

	protected ClosureCompilerProcessor(String[] args) {
		super(args)
	}

	public static void main(String[] args) {
		args = [
				"--js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\jquery\\jquery-2.1.1.min.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\bootstrap\\js\\bootstrap.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\common.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\lazy.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\search_suggestion.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\startech\\javascript\\site.js",
				"--js_output_file",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\startech\\javascript\\$version\\site.min.js"
		].toArray()
		new ClosureCompilerProcessor(args).run()
		args = [
				"--js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\jquery\\magnific\\jquery.magnific-popup.min.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\operator.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\product.js",
				"--js_output_file",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\product.min.js"
		]
		new ClosureCompilerProcessor(args).run()

	}
}
