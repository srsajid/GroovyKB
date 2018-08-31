

package com.google.javascript.jscomp

import minifier.CssMinifyPostProcessor

class MuthoPhoneCompiler extends CommandLineRunner {
	static version = 1;

	protected MuthoPhoneCompiler(String[] args) {
		super(args)
	}

	public static void main(String[] args) {
		List cssFiles = [
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\zebra\\stylesheet\\bootstrap.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\zebra\\stylesheet\\stylesheet.css",
		]

		String css = ""
		cssFiles.each {
			css += new File(it).text
		}

		css = new CssMinifyPostProcessor().process(css)
		File file = new File("C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\zebra\\stylesheet\\stylesheet.min.${version}.css")
		if(file.exists()) file.delete()
		file.createNewFile()
		file.text = css

		args = [
				"--js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\jquery\\jquery-2.1.1.min.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\bootstrap\\js\\bootstrap.button.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\common.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\lazy.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\search_suggestion.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\zebra\\js\\site.js",
				"--js_output_file",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\zebra\\js\\site.min.${version}.js"
		].toArray()

		new MuthoPhoneCompiler(args).run()

	}
}
