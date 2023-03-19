package com.zombito.exporter.controller

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ThreeCommasExporterController {

    @GetMapping("/")
    fun index() : String {
        return "3Commas Exporter";
    }
}
