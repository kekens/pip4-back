package pip.pip4back.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pip.pip4back.dto.DotDto;
import pip.pip4back.model.Dot;
import pip.pip4back.service.DotService;
import pip.pip4back.service.ValidationService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/dots")
public class DotController {

    private DotService dotService;
    private ValidationService validationService;

    public DotController(DotService dotService, ValidationService validationService) {
        this.dotService = dotService;
        this.validationService = validationService;
    }

    @PostMapping("/add")
    public ResponseEntity<Dot> createDot(@Valid @RequestBody DotDto dotDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = validationService.combineBindingResultErrors(bindingResult);
            log.info(errors);
            return new ResponseEntity<>(new Dot(), HttpStatus.BAD_REQUEST);
        } else {
            Dot dot = dotService.addDot(dotDto);
            return new ResponseEntity<>(dot, HttpStatus.OK);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Dot>> showAllDots() {
        return new ResponseEntity<>(dotService.showAllDots(), HttpStatus.OK);
    }

}
