package etestyonline.web;

import etestyonline.model.util.DescriptionDTO;
import etestyonline.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addCategory(Model model) {
        model.addAttribute("descriptionObj", new DescriptionDTO());
        return "addCategory";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCategoryPOST(@ModelAttribute("descriptionObj") @Valid DescriptionDTO descriptionDTO, BindingResult result) {

        if(result.hasErrors()){
            return "addCategory";
        }

        categoryService.addCategory(descriptionDTO.getDescription());

        return "redirect:/category/all";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allCategories(Model model, Pageable pageable) {

        model.addAttribute("categories", categoryService.getAllCategories(pageable));

        return "allCategories";
    }

    @RequestMapping(value = "/delete/{categoryId}", method = RequestMethod.GET)
    public String deleteCategory(@PathVariable("categoryId") String categoryId) {

        categoryService.deleteCategoryById(categoryId);

        return "redirect:/category/all";
    }
}
