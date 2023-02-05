package com.watchapedia.watchpedia_user.service.comment;

import com.watchapedia.watchpedia_user.model.dto.comment.ReportDto;
import com.watchapedia.watchpedia_user.model.entity.comment.Report;
import com.watchapedia.watchpedia_user.model.network.request.comment.ReportRequest;
import com.watchapedia.watchpedia_user.model.repository.comment.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    public boolean reportSave(ReportRequest request){
        Report check = reportRepository.findByReportCommTypeAndReportCommIdx(
                request.commType(), request.commIdx()
        );
        List<String> userList;
        if(check != null){
            if(check.getReportReporter().contains(",")){
                userList = new ArrayList<>(Arrays.asList(check.getReportReporter().split(", ")));
            }else{
                userList = new ArrayList<>(Arrays.asList(check.getReportReporter()));
            }
            for(int i=0; i < userList.size(); i++) {
                if (request.spoiler() == true) {
                    if (Long.parseLong(userList.get(i).trim().split("[|]")[0]) == request.reporter()
                        && userList.get(i).trim().split("[|]")[1].equals("스포")
                    ) {
                        if(userList.size() == 1){
                            reportRepository.deleteById(check.getReportIdx());
                        }else{
                            userList.remove(i);
                            spoilerDelete(check, userList);
                        }
                        return false;
                    }
                }else{
                    if (Long.parseLong(userList.get(i).trim().split("[|]")[0]) == request.reporter()
                            && userList.get(i).trim().split("[|]")[1].equals("부적절")
                    ) {
                        if(userList.size() == 1){
                            reportRepository.deleteById(check.getReportIdx());
                        }else{
                            userList.remove(i);
                            inapDelete(check, userList);
                            System.out.println(userList.toString());
                        }
                        return false;
                    }
                }
            }
        }

        if(request.spoiler() == true){
//            스포일러 신고
            if(check == null){
                reportRepository.save(ReportDto.of(request.userIdx(), request.commType(), request.commIdx(), request.text(),
                        1L,null, String.valueOf(request.reporter())+"|스포").toEntity());
                return true;
            }else{
                check.setReportSpoiler(check.getReportSpoiler() == null ? 1L : check.getReportSpoiler() + 1L);
                check.setReportReporter(check.getReportReporter()+", "+request.reporter()+"|스포");
                reportRepository.save(check);
                return true;
            }
        }else{
//            부적절한 표현 신고
            if(check == null){
                reportRepository.save(ReportDto.of(request.userIdx(), request.commType(), request.commIdx(), request.text(),
                        null,1L, String.valueOf(request.reporter())+"|부적절").toEntity());
                return true;
            }else{
                check.setReportInappropriate(check.getReportInappropriate() == null? 1L : check.getReportInappropriate()+1L);
                check.setReportReporter(check.getReportReporter()+", "+request.reporter()+"|부적절");
                reportRepository.save(check);
                return true;
            }
        }
    }

    public void spoilerDelete(Report entity, List<String> userList){
        entity.setReportSpoiler(entity.getReportSpoiler() == 1L ? null : entity.getReportSpoiler() - 1L);
        entity.setReportReporter(userList.toString().split("\\[")[1]
                .split("]")[0]);
        reportRepository.save(entity);
    }

    public void inapDelete(Report entity, List<String> userList){
        entity.setReportInappropriate(entity.getReportInappropriate() == 1L ? null : entity.getReportInappropriate() - 1L);
        entity.setReportReporter(userList.toString().split("\\[")[1]
                .split("]")[0]);
        reportRepository.save(entity);
    }
}
