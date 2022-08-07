package seoultech.startapp.plan.application.port.in;

import org.springframework.data.domain.PageRequest;
import seoultech.startapp.plan.application.PlanPagingResponse;
import seoultech.startapp.plan.application.PlanResponse;

import java.util.List;

public interface PlanGetUseCase {

    PlanPagingResponse getAllPlanByPaging(PageRequest pageRequest);

    List<PlanResponse> getPlanByYearAndMonth(int year,int month);

}