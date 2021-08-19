

package com.compiler

import com.google.javascript.jscomp.CommandLineRunner
import minifier.CssMinifyPostProcessor

class StarshipCompiler extends CommandLineRunner {
	static version = 5;
	static blogCssVersion = 1

	protected StarshipCompiler(String[] args) {
		super(args)
	}

	public static void main(String[] args) {
		List cssFiles = [
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\theme\\starship\\stylesheet\\bootstrap.css",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\theme\\starship\\stylesheet\\icon.css",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\theme\\starship\\stylesheet\\common.css",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\theme\\starship\\stylesheet\\home.css",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\css\\noUi\\nouislider.min.css",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\theme\\starship\\stylesheet\\category.css",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\theme\\starship\\stylesheet\\product.css",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\theme\\starship\\stylesheet\\accounts.css",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\theme\\starship\\stylesheet\\checkout.css",
		]

		String css = ""
		cssFiles.each {
			css += new File(it).text
		}

		css = new CssMinifyPostProcessor().process(css)
		File file = new File("C:\\xampp\\htdocs\\starcart\\catalog\\view\\theme\\starship\\stylesheet\\stylesheet.min.${version}.css")
		if(file.exists()) file.delete()
		file.createNewFile()
		file.text = css

		cssFiles = [
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\theme\\starship\\stylesheet\\blog.css",
		]

		css = ""
		cssFiles.each {
			css += new File(it).text
		}

		css = new CssMinifyPostProcessor().process(css)
		file = new File("C:\\xampp\\htdocs\\starcart\\catalog\\view\\theme\\starship\\stylesheet\\blog.min.${blogCssVersion}.css")
		if(file.exists()) file.delete()
		file.createNewFile()
		file.text = css

		args = [
				"--js",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\javascript\\lib\\jqlite\\jqlite.js",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\javascript\\cms\\common.js",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\javascript\\cms\\lazy.js",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\javascript\\cms\\search_suggestion.js",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\theme\\starship\\javascript\\site.js",
				"--js_output_file",
				"C:\\xampp\\htdocs\\starcart\\catalog\\view\\theme\\starship\\javascript\\site.min.${version}.js"
		].toArray()

		new StarshipCompiler(args).run()
	}
}
