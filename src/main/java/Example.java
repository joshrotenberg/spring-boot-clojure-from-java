import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

// import these for clojure
import clojure.java.api.Clojure;
import clojure.lang.IFn;

@RestController
@EnableAutoConfiguration
public class Example {

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

	@RequestMapping("/reverse")
	String reverse(@RequestParam("input") String input) {
		return new StringBuilder(input).reverse().toString();
	}

	IFn apply = Clojure.var("clojure.core", "apply");
	IFn str = Clojure.var("clojure.core", "str");
	IFn reverse = Clojure.var("clojure.core", "reverse");

	@RequestMapping("/cljreverse")
	String cljreverse(@RequestParam("input") String input) {
	      // this is evquivalent to calling (apply str (reverse input))
              return apply.invoke(str, reverse.invoke(input)).toString();
	}   
	
	// in main we require clojure.string
	IFn lc = Clojure.var("clojure.string", "lower-case");
	@RequestMapping("/cljlowercase")
	String cljlowercase(@RequestParam("input") String input) {
		// equivalent to (lower-case input)
		return lc.invoke(input).toString();
	}

	public static void main(String[] args) throws Exception {
		IFn require = Clojure.var("clojure.core", "require");
		require.invoke(Clojure.read("clojure.string"));
		
		SpringApplication.run(Example.class, args);
	}

}
