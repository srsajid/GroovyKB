

package com.google.javascript.jscomp

class ListingJsProcessor extends CommandLineRunner {
	static contentTypes = ['application/javascript']

	static version = 2;

	protected ListingJsProcessor(String[] args) {
		super(args)
	}

	public static void main(String[] args) {
		args = [
				"--js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\lib\\noUi\\nouislider.min.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\cms\\listing.js",
				"--js_output_file",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\prod\\listing.min.${version}.js"
		]
		new ListingJsProcessor(args).run()
	}
}
