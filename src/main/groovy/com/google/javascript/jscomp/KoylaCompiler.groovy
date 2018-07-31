

package com.google.javascript.jscomp

import minifier.CssMinifyPostProcessor

class KoylaCompiler extends CommandLineRunner {
	static version = 10;

	protected KoylaCompiler(String[] args) {
		super(args)
	}

	public static void main(String[] args) {
		List cssFiles = [
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\bootstrap.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\common.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\home.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\category.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\product.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\accounts.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\checkout.css",
		]

		String css = ""
		cssFiles.each {
			css += new File(it).text
		}

		css = new CssMinifyPostProcessor().process(css)
		File file = new File("C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\stylesheet.min.${version}.css")
		if(file.exists()) file.delete()
		file.createNewFile()
		file.text = css

		cssFiles = [
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\bootstrap.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\common.css",
				"C:\\xampp\\htdocs\\blog\\wp-content\\themes\\koyla\\blog.css"
		]

		css = ""
		cssFiles.each {
			css += new File(it).text
		}

		css = new CssMinifyPostProcessor().process(css)
		file = new File("C:\\xampp\\htdocs\\blog\\wp-content\\themes\\koyla\\style.css")
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
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\javascript\\site.js",
				"--js_output_file",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\javascript\\site.min.${version}.js"
		].toArray()

		new KoylaCompiler(args).run()

		args = [
				"--js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\jquery\\magnific\\jquery.magnific-popup.min.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\operator.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\product.js",
				"--js_output_file",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\product.min.${version}.js"
		]
		new KoylaCompiler(args).run()

	}
}
