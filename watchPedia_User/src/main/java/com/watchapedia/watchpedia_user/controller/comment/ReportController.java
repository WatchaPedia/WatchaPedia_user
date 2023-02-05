package com.watchapedia.watchpedia_user.controller.comment;

import com.watchapedia.watchpedia_user.model.network.request.comment.ReportRequest;
import com.watchapedia.watchpedia_user.service.comment.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    final ReportService reportService;
    @PostMapping("/save")
    @ResponseBody
    public boolean reportSave(
            @RequestBody ReportRequest request
    ){
        return reportService.reportSave(request);
    }
}
