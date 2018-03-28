/*
* Copyright 2014 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package minifier

import com.google.javascript.jscomp.CommandLineRunner
import groovy.transform.CompileStatic

@CompileStatic
class ClosureCompilerProcessor {
	static contentTypes = ['application/javascript']

	static version = 1;
	ClosureCompilerProcessor() {


	}

	public static void main(String[] args) {
		CommandLineRunner.main("--js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\jquery\\jquery-2.1.1.min.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\bootstrap\\js\\bootstrap.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\common.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\javascript\\search_suggestion.js",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\startech\\javascript\\site.js",
				"--js_output_file",
				"C:\\xampp\\htdocs\\startech\\catalog\\view\\theme\\startech\\javascript\\$version\\site.min.js",
				"--debug",
		)
	}
}
