

package com.compiler

import com.google.javascript.jscomp.CommandLineRunner
import minifier.CssMinifyPostProcessor

class BabycareCompiler extends CommandLineRunner {
	static version = 6;

	protected BabycareCompiler(String[] args) {
		super(args)
	}

	public static void main(String[] args) {
		List cssFiles = [
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\babycare\\stylesheet\\bootstrap.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\babycare\\stylesheet\\font-awesome.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\babycare\\stylesheet\\style.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\babycare\\stylesheet\\checkout.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\css\\noUi\\nouislider.min.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\babycare\\stylesheet\\product-listing.css",
		]

		String css = ""
		cssFiles.each {
			css += new File(it).text
		}

		css = new CssMinifyPostProcessor().process(css)
		File file = new File("C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\babycare\\stylesheet\\style.min.${version}.css")
		if(file.exists()) file.delete()
		file.createNewFile()
		file.text = css

		args = [
				"--js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\lib\\jqlite\\jqlite.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\cms\\common.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\cms\\lazy.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\cms\\search_suggestion.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\babycare\\javascript\\site.js",
				"--js_output_file",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\babycare\\javascript\\site.min.${version}.js"
		].toArray()

		new BabycareCompiler(args).run()
	}
}
