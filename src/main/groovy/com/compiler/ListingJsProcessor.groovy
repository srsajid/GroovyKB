

package com.compiler

import com.google.javascript.jscomp.CommandLineRunner

class ListingJsProcessor extends CommandLineRunner {
	static contentTypes = ['application/javascript']

	static version = 5;

	protected ListingJsProcessor(String[] args) {
		super(args)
	}

	public static void main(String[] args) {
		args = [
				"--js",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\javascript\\lib\\noUi\\nouislider.min.js",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\javascript\\cms\\listing.js",
				"--js_output_file",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\javascript\\prod\\listing.min.${version}.js"
		]
		new ListingJsProcessor(args).run()
	}
}
