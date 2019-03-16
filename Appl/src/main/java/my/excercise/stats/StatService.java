package my.excercise.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("application")
public class StatService {

	@RequestMapping(value = "/travel/stats", method = RequestMethod.GET)
	public ModelAndView stats() {
		StatsModel stats = new StatsModel();
		return new ModelAndView("stats", "stats", stats);
	}

}
