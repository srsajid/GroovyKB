

package com.compiler

import com.google.javascript.jscomp.CommandLineRunner
import minifier.CssMinifyPostProcessor

class KoylaCompiler extends CommandLineRunner {
	static version = 27;
	static blogCssVersion = 1

	protected KoylaCompiler(String[] args) {
		super(args)
	}

	public static void main(String[] args) {
		List cssFiles = [
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\bootstrap.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\common.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\home.css",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\css\\noUi\\nouislider.min.css",
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
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\blog.css",
		]

		css = ""
		cssFiles.each {
			css += new File(it).text
		}

		css = new CssMinifyPostProcessor().process(css)
		file = new File("C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\stylesheet\\blog.min.${blogCssVersion}.css")
		if(file.exists()) file.delete()
		file.createNewFile()
		file.text = css

		args = [
				"--js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\lib\\jqlite\\jqlite.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\cms\\common.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\cms\\lazy.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\cms\\search_suggestion.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\javascript\\site.js",
				"--js_output_file",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\koyla\\javascript\\site.min.${version}.js"
		].toArray()

		new KoylaCompiler(args).run()
	}
}
