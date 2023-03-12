package org.example.controllers;

import org.example.beans.Pracownik;
import org.example.dao.PracownikDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PracownikController {
    @Autowired
    PracownikDao dao; //wstrzyknięcie dao z pliku XML

    /* Wynikiem działania metody jest przekazanie danych w modelu do
     * strony widoku addForm.jsp, która wyświetla formularz
     * wprowadzania danych, a „command” jest zastrzeżonym atrybutem
     * żądania, umożliwiającym wyświetlenie danych obiektu pracownika
     * w formularzu.
     */
    @RequestMapping("/addForm")
    public String showform(Model m) {
        m.addAttribute("command", new Pracownik());
        return "addForm"; //przekiekrowanie do addForm.jsp
    }

    /* Metoda obsługuje zapis pracownika do BD. @ModelAttribute
     * umozliwia pobranie danych z żądania do obiektu pracownika.
     * Jawnie wskazano RequestMethod.POST (domyślnie jest to GET)
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("pr") Pracownik pr) {
        if (pr.getNazwisko() != "" && String.valueOf(pr.getPensja()) != "" && pr.getFirma() != "") {
            dao.save(pr);
            return "redirect:/viewAll";
        } else {
            return "errorEmptyField";
        }
    }

    /* Metoda pobiera listę pracowników z BD i umieszcza je w modelu */
    @RequestMapping("/viewAll")
    public String viewAll(Model m) {
        List<Pracownik> list = dao.getAll();
        m.addAttribute("list", list);
        return "viewAll"; //przejście do widoku viewAll.jsp
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        if (dao.getPracownikById(id) != null) {
            dao.delete(id);
            return "redirect:/viewAll";
        } else {
            return "errorUserNotFound";
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(Model m, @PathVariable int id) {
        Pracownik p = dao.getPracownikById(id);
        if (p != null) {
            m.addAttribute("pracownikModel", p);
            return "editForm";
        } else {
            return "errorUserNotFound";
        }

    }

    @RequestMapping(value = "/edit/editsave", method = RequestMethod.POST)
    public String editsave(@ModelAttribute("pracownikModel") Pracownik pr) {

        if (pr.getNazwisko() != "" && String.valueOf(pr.getPensja()) != "" && pr.getFirma() != "") {
            dao.edit(pr);
            return "redirect:/viewAll";
        } else {
            return "errorEmptyField";
        }


    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
}
