package my.excercise.travel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import my.excercise.request.Location;
import my.excercise.travel.TravelServiceCaller;

@Controller
public class TravelController {

	@Autowired
	TravelServiceCaller travelService;

	@RequestMapping(value = "/travel", method = RequestMethod.GET)
	public ModelAndView ports() {
		System.out.println(
				"----------------controller airport------------------------------------------------------------------");
		List<Location> airports = travelService.getAirports();
		return new ModelAndView("travel", "ports", airports);

	}

	@RequestMapping(value = "/travel/fare", method = RequestMethod.POST)
	public ModelAndView fare(@ModelAttribute("dep_port") String depPort, @ModelAttribute("arr_port") String arrPort) {
		System.out.println("-----------------------controler fare --------------------------------------");
		FareModel fare = travelService.getFare(depPort, arrPort);

		return new ModelAndView("fare", "travel_fare", fare);
	}
}
