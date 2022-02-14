package com.lex.cat.controller;

import com.lex.cat.model.Cat;
import com.lex.cat.service.CatService;
import com.lex.cat.util.OffsetBasedPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/")
public class CatController {

    private final CatService catService;

    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("cats")
    public List<Cat> redirectWithUsingRedirectView(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "100") @Min(1) @Max(10_000) Integer limit,
            @RequestParam(value = "attribute", defaultValue = "name") String attribute,
            @RequestParam(value = "order", defaultValue = "ASC") Sort.Direction order
    ) {
        return catService.getAll(
                new OffsetBasedPageRequest(
                        offset,
                        limit,
                        Sort.by(order, attribute)
                )
        );
    }

    @PostMapping("cat")
    public void redirectWithUsingRedirectView(@RequestBody Cat cat) {
        catService.save(cat);
    }
}
